package se.lu.ics.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import se.lu.ics.models.Factory;
import se.lu.ics.models.Vehicle;
import se.lu.ics.models.populator.TestDataPopulator;
import se.lu.ics.models.populator.TestDataPopulatorSql;
import se.lu.ics.data.SharedVehicleDataModel;

public class MainViewController {

    private Factory factory = new Factory();
    private SharedVehicleDataModel sharedDataModel; // Shared data model

    @FXML
    private Button buttonTest;

    @FXML
    private ImageView gifImageView; 

    @FXML
    private Label labelTime;

    @FXML
    private Button buttonTestSql;

    @FXML
    private Button buttonAboutMe;

    @FXML
    private Button buttonCargo;

    @FXML
    private Button buttonAddVehicle;

    @FXML
    private Button buttonCalculations;

    @FXML
    private Button buttonDrivers;

    @FXML
    private Button buttonMaintenance;

    @FXML
    private Button buttonService;

    @FXML
    private Button buttonVehicles;

    @FXML
    private Button buttonWorkshops;

    @FXML
    private TextArea textAreaVehicleInformation;

    @FXML
    private TextField textFieldLocation;

    @FXML
    private TextField textFieldSearch;

    @FXML
    private TextField textFieldVehicleName;

    @FXML
    private TextField textFieldVehicleType;

    @FXML
    private TextField textFieldVin;

    @FXML
    private TextField textFieldWeightCapacity;

    @FXML
    private TableView<Vehicle> mainViewTableView;

    @FXML
    private TableColumn<Vehicle, Integer> mainViewTableColumnVin;

    @FXML
    private TableColumn<Vehicle, String> mainViewTableColumnVehicleType;

    @FXML
    private TableColumn<Vehicle, String> mainViewTableColumnName;

    @FXML
    private TableColumn<Vehicle, String> mainViewTableColumnVehicleLocation;

    @FXML
    private TableColumn<Vehicle, Double> mainViewTableColumnCargoWeight;

    @FXML
    private TableColumn<Vehicle, Double> mainViewTableColumnWeightCapacity;

    @FXML
    private TableColumn<Vehicle, String> mainViewTableColumnDriver;

    @FXML
    private TableColumn<Vehicle, String> mainViewTableColumnCargo;

    @FXML
    public void initialize() {

        applyDynamicTooltip(buttonAboutMe, "Learn more about this application");
        applyDynamicTooltip(buttonTest, "Test the application's features");
        applyDynamicTooltip(buttonCargo, "Manage cargo details");
        applyDynamicTooltip(buttonVehicles, "View and manage vehicles"); 
        applyDynamicTooltip(buttonCalculations, "Perform various calculations");
        applyDynamicTooltip(buttonDrivers, "View and manage employees");
        applyDynamicTooltip(buttonMaintenance, "Manage vehicle maintenance");
        applyDynamicTooltip(buttonService, "Manage vehicle services");
        applyDynamicTooltip(buttonWorkshops, "View and manage workshops");

        setupTableColumns();
        initializeSharedDataModel();
        initializeSearchFunctionality();
        // Add a listener to refresh the TableView when the window gains focus
        addWindowFocusListener();

        Image gifImage = new Image(getClass().getResourceAsStream("/Deliver.gif"));
        gifImageView.setImage(gifImage);

        Timeline clockTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> updateClock()));

        // Set the timeline to run indefinitely
        clockTimeline.setCycleCount(Timeline.INDEFINITE);

        // Start the timeline
        clockTimeline.play();
    }

    private void initializeSearchFunctionality() {
        textFieldSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTable(newValue);
        });
    }

    private void filterTable(String searchQuery) {
        if (searchQuery.isEmpty()) {
            mainViewTableView.setItems(sharedDataModel.getVehicles()); // Set all items if search is empty
        } else {
            ObservableList<Vehicle> filteredList = FXCollections.observableArrayList();
            for (Vehicle vehicle : sharedDataModel.getVehicles()) {
                String vinString = String.valueOf(vehicle.getVin());
                if (vinString.contains(searchQuery)) {
                    filteredList.add(vehicle);
                }
            }
            mainViewTableView.setItems(filteredList); // Set filtered items
        }
        mainViewTableView.refresh();
    }

    private void updateClock() {
        // Get the current time
        LocalDateTime currentTime = LocalDateTime.now();

        // Format the time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = currentTime.format(formatter);

        // Update the Label with the formatted time
        labelTime.setText(formattedTime);
    }

    public void setSharedDataModel(SharedVehicleDataModel sharedDataModel) {
        this.sharedDataModel = sharedDataModel;
        mainViewTableView.setItems(sharedDataModel.getVehicles()); // Link the shared list to the TableView
    }

    private void addWindowFocusListener() {
        if (mainViewTableView.getScene() != null && mainViewTableView.getScene().getWindow() != null) {
            mainViewTableView.getScene().getWindow().focusedProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    mainViewTableView.refresh();
                }
            });
        } else {
            // Add listener after the stage is shown
            mainViewTableView.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) { // New scene is set
                    newScene.windowProperty().addListener((windowObs, oldWindow, newWindow) -> {
                        if (newWindow != null) { // New window is set
                            newWindow.focusedProperty().addListener((focusedObs, wasFocused, isNowFocused) -> {
                                if (isNowFocused) {
                                    mainViewTableView.refresh();
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    private void setupTableColumns() {
        mainViewTableColumnVin.setCellValueFactory(new PropertyValueFactory<>("vin"));
        mainViewTableColumnVehicleType.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        mainViewTableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainViewTableColumnVehicleLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        mainViewTableColumnCargoWeight.setCellValueFactory(new PropertyValueFactory<>("cargoWeight"));
        mainViewTableColumnWeightCapacity.setCellValueFactory(new PropertyValueFactory<>("weightCapacity"));

        mainViewTableColumnCargo
                .setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCargoDetails()));
        mainViewTableColumnDriver
                .setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDriverNames()));

    }

    public void refreshMainViewTable() {
        mainViewTableView.refresh();
    }

    private void initializeSharedDataModel() {
        if (sharedDataModel == null) {
            sharedDataModel = new SharedVehicleDataModel();
        }
        mainViewTableView.setItems(sharedDataModel.getVehicles());
    }

    @FXML
    private void handleButtonVehicleAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/vehicleView.fxml"));
            Parent vehiclesView = loader.load();

            VehicleController vehicleController = loader.getController();
            if (sharedDataModel == null) {
                // Initialize the sharedDataModel if it's null
                sharedDataModel = new SharedVehicleDataModel();
            }
            vehicleController.setSharedDataModel(sharedDataModel); // Set the shared data model

            Scene vehiclesScene = new Scene(vehiclesView);
            Stage newStage = new Stage();
            Image applicationIcon = new Image(getClass().getResourceAsStream("/vikingexpresslogo.PNG"));
            newStage.getIcons().add(applicationIcon);
            newStage.setScene(vehiclesScene);
            newStage.setTitle("Vehicles");
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleButtonCargoAction(ActionEvent event) {
        try {
            // Load CargoView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cargoView.fxml")); // Adjust the path
            Parent root = loader.load();

            // Get the CargoController
            CargoController cargoController = loader.getController();

            // Set the shared data model
            cargoController.setSharedDataModel(sharedDataModel);

            // Show the view in a new stage or scene
            Stage stage = new Stage();
            Image applicationIcon = new Image(getClass().getResourceAsStream("/vikingexpresslogo.PNG"));
            stage.getIcons().add(applicationIcon);
            stage.setScene(new Scene(root));
            stage.setTitle("Cargo Information");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
     
    }


    @FXML
    public void handleButtonMaintenanceAction(ActionEvent event) {
        try {

            // Load MaintenanceView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MaintenanceView.fxml"));

            // Load the FXML file
            Parent root = loader.load();

            // Show the view in a new stage or scene
            Stage stage = new Stage();
            Image applicationIcon = new Image(getClass().getResourceAsStream("/vikingexpresslogo.PNG"));
            stage.getIcons().add(applicationIcon);
            stage.setScene(new Scene(root));
            stage.setTitle("Maintenance Information");
            stage.show();

            // Get the MaintenanceController
            MaintenanceController maintenanceController = loader.getController();

            // Set the sharedDataModel
            maintenanceController.setSharedDataModel(sharedDataModel);

        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("General error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleButtonDriversAction(ActionEvent event) {
        try {
            // Load the FXML file (adjust the path as needed)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/employeeView.fxml"));

            // Load the FXML content and get the root element
            Parent root = loader.load();

            // Get the EmployeeController associated with the FXML file
            EmployeeController employeeController = loader.getController();

            // Check if sharedDataModel is already initialized
            if (sharedDataModel != null) {
                // Set the sharedDataModel in the EmployeeController
                employeeController.setSharedDataModel(sharedDataModel);
            } else {
                System.err.println("sharedDataModel is null. Unable to open Employee Information.");  
                return; 
            }

            // Show the view in a new stage or scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/vikingexpresslogo.PNG"));
            stage.setTitle("Employee Information");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
           
        }
    }

    @FXML
    void handleButtonTestAction(ActionEvent event) {
        TestDataPopulator.populateTestData(sharedDataModel, factory);
        mainViewTableView.setItems(sharedDataModel.getVehicles()); // Refresh the table view
    }

    @FXML
    void handleButtonSqlAction(ActionEvent event) {
        TestDataPopulatorSql.populateTestDataSql(sharedDataModel, factory);
        mainViewTableView.setItems(sharedDataModel.getVehicles()); // Refresh the table view
    }



    @FXML
    public void handleButtonServiceAction(ActionEvent event) {
        try {
            // Load the FXML file for the service view (adjust the path as needed)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ServiceView.fxml"));
            Parent root = loader.load();

            // Get the controller and set the sharedDataModel
            ServiceController serviceController = loader.getController();

            if (sharedDataModel != null) {
                // Set the sharedDataModel in the EmployeeController
                serviceController.setSharedDataModel(sharedDataModel);
            } else {
                // Handle the case where sharedDataModel is null
                System.err.println("sharedDataModel is null. Unable to open Employee Information.");
               
                return; 
            }

            // Show the service view in a new stage or scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/vikingexpresslogo.PNG"));
            stage.setTitle("Service Information");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
           
        }
    }

    @FXML
    public void handleButtonWorkshopsAction(ActionEvent event) {
        try {
            // Load the FXML file for the workshop view (adjust the path as needed)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/WorkshopView.fxml"));
            Parent root = loader.load();

            // Get the controller and set the sharedDataModel
            WorkshopController workshopController = loader.getController();
            if (sharedDataModel != null) {
                // Set the sharedDataModel in the WorkshopController
                workshopController.setSharedDataModel(sharedDataModel);
            } else {
                // Handle the case where sharedDataModel is null
                System.err.println("sharedDataModel is null. Unable to open Workshop Information.");
          
                return; 
            }

            // Show the workshop view in a new stage or scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/vikingexpresslogo.PNG"));
            stage.setTitle("Workshop Information");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
           
        }
    }

    @FXML
    public void handleButtonCalculationsAction(ActionEvent event) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Calculations");

        // Set the icon for the dialog stage
        Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
        if (dialogStage != null) {
            dialogStage.getIcons().add(new Image(getClass().getResourceAsStream("/vikingexpresslogo.PNG")));
        }

        // Create a TextArea to display results
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMinHeight(200); 
        textArea.setStyle("-fx-background-color: #f5f5f5; -fx-text-fill: #000d2f;");

        // Create buttons for calculations
        Button btnMostExpensiveServiceActivity = new Button("Most Expensive Service Activity(s)");
        btnMostExpensiveServiceActivity.setStyle("-fx-background-color: #f5f5f5;");
        btnMostExpensiveServiceActivity.setCursor(Cursor.HAND);
        btnMostExpensiveServiceActivity.setOnAction(e -> {
            String result = sharedDataModel.getMostExpensiveServiceActivity();
            textArea.setText(result);
        });

        Button btnAverageServiceCost = new Button("Average Service Cost");
        btnAverageServiceCost.setStyle("-fx-background-color: #f5f5f5;");
        btnAverageServiceCost.setCursor(Cursor.HAND);
        btnAverageServiceCost.setOnAction(e -> {
            double averageCost = sharedDataModel.getAverageServiceCost();
            textArea.setText(String.format("Average Service Cost: %.2f", averageCost));
        });

        Button btnMostExpensiveWorkshop = new Button("Most Expensive Workshop");
        btnMostExpensiveWorkshop.setStyle("-fx-background-color: #f5f5f5;");
        btnMostExpensiveWorkshop.setCursor(Cursor.HAND);
        btnMostExpensiveWorkshop.setOnAction(e -> {
            String result = sharedDataModel.getMostExpensiveWorkshopDetails();
            textArea.setText(result);
        });

        // add button to print the total cost of all service activities

        Button btnTotalCostServiceActivities = new Button("Total Cost of all Service Activities");
        btnTotalCostServiceActivities.setStyle("-fx-background-color: #f5f5f5;");
        btnTotalCostServiceActivities.setCursor(Cursor.HAND);
        btnTotalCostServiceActivities.setOnAction(e -> {
            double totalCost = sharedDataModel.getTotalServiceCost();
            textArea.setText(String.format("Total Cost of all Service Activities: %.2f", totalCost));
        });

        // Create a VBox to hold buttons and text area
        VBox vbox = new VBox(10); // 10 is the spacing between components
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(
                btnMostExpensiveServiceActivity,
                btnAverageServiceCost,
                btnMostExpensiveWorkshop,
                btnTotalCostServiceActivities, 
                textArea);

        // Set the content of the dialog
        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().setStyle("-fx-background-color: #000d2f;");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        // Show the dialog
        dialog.showAndWait();
    }

    @FXML
    public void handleAboutMeAction(ActionEvent event) {
        Alert aboutMeAlert = new Alert(Alert.AlertType.INFORMATION);
        aboutMeAlert.setTitle("About Application");
        aboutMeAlert.setHeaderText("Application Information");
        aboutMeAlert.setContentText(
                "Viking Express - Logistics Management System\n" +
                        "Version: 1.0.0\n" +
                        "Developed by:\nSimon Baas\nMirnes Dizdar\nSänna Janfada\n" +
                        "Group 25 - Lund University\n" +
                        "©2023 Group 25 - Lund University\n\n" +
                        "Description: This Logistics Management System is a comprehensive application designed to efficiently manage and track various logistics operations. It offers features for handling vehicles, cargo, employees, maintenance schedules, service activities, and workshops. The system provides a seamless and integrated solution for streamlining logistics and fleet management tasks, ensuring effective and organized operations.");

        // Load the image as an icon
        Image image = new Image(getClass().getResourceAsStream("/vikingexpresslogo.PNG"));

        // Get the Stage of the Alert and set the icon
        Stage stage = (Stage) aboutMeAlert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(image);

        aboutMeAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        aboutMeAlert.showAndWait();
    }

    private void applyDynamicTooltip(Control control, String tooltipText) {
        control.setOnMouseEntered(e -> control.setTooltip(createDelayedTooltip(tooltipText)));
        control.setOnMouseExited(e -> control.setTooltip(null));
    }

    private Tooltip createDelayedTooltip(String text) {
        Tooltip tooltip = new Tooltip(text);
        tooltip.setShowDelay(Duration.seconds(1));
        return tooltip;
    }

}
