package se.lu.ics.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

import java.util.ArrayList;
import java.util.Random;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import se.lu.ics.models.Factory;
import se.lu.ics.models.MaintenanceSchedule;
import se.lu.ics.models.ServiceHistory;
import se.lu.ics.models.Vehicle;
import se.lu.ics.models.Workshop;
import se.lu.ics.data.SharedVehicleDataModel;
import se.lu.ics.models.Employee;

public class VehicleController {

    private SharedVehicleDataModel sharedDataModel;

    private Factory factory = new Factory();

    @FXML
    private Button buttonViewWorkshops;

    @FXML
    private Button buttonAddVehicle;

    @FXML
    private Button buttonEditVehicle;

    @FXML
    private Button buttonRemoveVehicle;

    @FXML
    private ComboBox<String> comboBoxType;

    @FXML
    private ComboBox<Employee> comboBoxDriver;

    @FXML
    private TextField textFieldVehicleName;

    @FXML
    private TextField textFieldSearchVin;

    @FXML
    private TextField textFieldLocation;

    @FXML
    private TextField textFieldWeightCapacity;

    @FXML
    private TableView<Vehicle> vehiclesTableView;

    @FXML
    private TableColumn<Vehicle, String> columnCargo;

    @FXML
    private TableColumn<Vehicle, Integer> columnVin;

    @FXML
    private TableColumn<Vehicle, String> columnName;

    @FXML
    private TableColumn<Vehicle, String> columnVehicleType;

    @FXML
    private TableColumn<Vehicle, String> columnLocation;

    @FXML
    private TableColumn<Vehicle, Double> columnCargoWeight;

    @FXML
    private TableColumn<Vehicle, Double> columnWeightCapacity;

    @FXML
    private TableColumn<Vehicle, String> columnDriver;

    @FXML
    private Label vehicleInfoLabel;

    public void setSharedDataModel(SharedVehicleDataModel sharedDataModel) {
        this.sharedDataModel = sharedDataModel;
        loadDataIntoUI();
    }

    private void loadDataIntoUI() {
        // Initialize ComboBoxes and TableView with data from sharedDataModel
        if (sharedDataModel != null) {
            comboBoxType.setItems(FXCollections.observableArrayList("Van", "Medium Truck", "Large Truck")); // Add
                                                                                                            // vehicle
                                                                                                            // types to
                                                                                                            // the
                                                                                                            // ComboBox
            comboBoxDriver.setItems(FXCollections.observableArrayList(sharedDataModel.getEmployees()));
            vehiclesTableView.setItems(sharedDataModel.getVehicles());
        }
    }

    @FXML
    public void initialize() {

        setupComboBoxConverters();
        setupTableColumns();
        if (sharedDataModel != null && vehiclesTableView != null) {
            vehiclesTableView.setItems(sharedDataModel.getVehicles());

            // Add a selection listener to the TableView
            vehiclesTableView.getSelectionModel().selectedItemProperty()
                    .addListener((observable, oldValue, newValue) -> {
                        if (newValue != null) {
                            vehicleInfoLabel.setText(newValue.toStringTwo());
                            buttonRemoveVehicle.setDisable(false);
                        } else {
                            buttonRemoveVehicle.setDisable(true);
                        }
                    });
        }

        applyDynamicTooltip(buttonAddVehicle, "Add a new vehicle to the system");
        applyDynamicTooltip(buttonEditVehicle, "Edit the selected vehicle's details");
        applyDynamicTooltip(buttonRemoveVehicle, "Remove the selected vehicle from the system");
        applyDynamicTooltip(comboBoxType, "Select the type of vehicle");
        applyDynamicTooltip(comboBoxDriver, "Select a driver for the vehicle");
        applyDynamicTooltip(textFieldVehicleName, "Enter the vehicle name");
        applyDynamicTooltip(textFieldSearchVin, "Search vehicles by VIN");
        applyDynamicTooltip(textFieldLocation, "Enter the vehicle's location");
        applyDynamicTooltip(textFieldWeightCapacity, "Enter the vehicle's weight capacity");
        applyDynamicTooltip(buttonViewWorkshops, "View the workshops visited by the selected vehicle");
        applyDynamicTooltip(buttonEditVehicle, "Edit the selected vehicle's details");

        initializeSearchFunctionality();
    }

    private void setupComboBoxConverters() {
        comboBoxDriver.setConverter(new StringConverter<Employee>() {
            @Override
            public String toString(Employee employee) {
                return employee != null ? employee.getName() : "";
            }

            @Override
            public Employee fromString(String string) {
                return null; // No conversion fromString needed
            }
        });
    }

    private void initializeSearchFunctionality() {
        textFieldSearchVin.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTable(newValue);
        });
    }

    private void filterTable(String searchQuery) {
        if (searchQuery.isEmpty()) {
            vehiclesTableView.setItems(sharedDataModel.getVehicles()); // Set all items if search is empty
        } else {
            ObservableList<Vehicle> filteredList = FXCollections.observableArrayList();
            for (Vehicle vehicle : sharedDataModel.getVehicles()) {
                String vinString = String.valueOf(vehicle.getVin());
                if (vinString.contains(searchQuery)) {
                    filteredList.add(vehicle);
                }
            }
            vehiclesTableView.setItems(filteredList); // Set filtered items
        }
        vehiclesTableView.refresh();
    }

    // setup combo box with 3 vehicle type strings

    private void setupTableColumns() {
        columnVin.setCellValueFactory(new PropertyValueFactory<>("vin"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnVehicleType.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        columnLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        columnCargoWeight.setCellValueFactory(new PropertyValueFactory<>("cargoWeight"));
        columnWeightCapacity.setCellValueFactory(new PropertyValueFactory<>("weightCapacity"));
        columnCargo.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCargoDetails()));
        columnDriver.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDriverNames()));
    }

    @FXML
    private void handleButtonVehicleAddAction(ActionEvent event) {
        // selected employee from combo box
        Employee selectedDriver = comboBoxDriver.getSelectionModel().getSelectedItem();

        String vehicleType = comboBoxType.getValue();
        String name = textFieldVehicleName.getText();
        String location = textFieldLocation.getText();
        String weightCapacityText = textFieldWeightCapacity.getText();

        if (vehicleType == null || name.isEmpty() || location.isEmpty() || weightCapacityText.isEmpty()) {
            vehicleInfoLabel.setText("Please fill in all fields.");
            return;
        }

        try {
            int vin = generateUniqueVin(); // Generate a unique VIN for the new vehicle
            double weightCapacity = Double.parseDouble(weightCapacityText);

            // if weight capacity is less than 0

            if (weightCapacity < 0) {
                vehicleInfoLabel.setText("Weight capacity cannot be less than 0.");
                return;
            }

            // Create a new ServiceHistory instance for the vehicle
            ServiceHistory newServiceHistory = new ServiceHistory();

            // Create the Vehicle with the new ServiceHistory
            Vehicle vehicle = factory.createVehicle(vin, vehicleType, name, location, new ArrayList<>(),
                    weightCapacity, 0, newServiceHistory, new MaintenanceSchedule(), new ArrayList<>());

            // Add the vehicle to the shared data model
            sharedDataModel.getVehicles().add(vehicle);
            if (selectedDriver != null) {
                selectedDriver.setDriverOf(vehicle);
                vehicle.assignDriver(selectedDriver);
            } else {
                vehicleInfoLabel.setText("No driver selected for vehicle.");
            }

            // Update the text area with the vehicle's information
            vehicleInfoLabel.setText(vehicle.toStringTwo());
        } catch (NumberFormatException e) {
            vehicleInfoLabel.setText("Error: Invalid input format for weight capacity.");
        } catch (Exception e) {
            vehicleInfoLabel.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleButtonVehicleRemoveAction(ActionEvent event) {
        Vehicle selectedVehicle = vehiclesTableView.getSelectionModel().getSelectedItem();
        if (selectedVehicle != null) {
            sharedDataModel.getVehicles().remove(selectedVehicle);
            vehicleInfoLabel.setText("Vehicle removed: " + selectedVehicle.getName());
        }
    }

    @FXML
    private void handleButtonVehicleEditAction(ActionEvent event) {
        Vehicle selectedVehicle = vehiclesTableView.getSelectionModel().getSelectedItem();
        if (selectedVehicle != null) {
            Dialog<ButtonType> dialog = new Dialog<>();

            Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
            Image applicationIcon = new Image(getClass().getResourceAsStream("/vikingexpresslogo.PNG"));
            dialogStage.getIcons().add(applicationIcon);
            dialog.setTitle("Edit Vehicle");

            // Set up the GridPane layout
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(20, 150, 10, 10)); // Adjust insets as needed
            dialog.getDialogPane().setPrefWidth(400); // You may need to increase this value

            // Adjust the GridPane column widths
            ColumnConstraints labelsColumn = new ColumnConstraints(100); // Increase this value as needed
            ColumnConstraints fieldsColumn = new ColumnConstraints(300, 300, Double.MAX_VALUE);
            fieldsColumn.setHgrow(Priority.ALWAYS);
            labelsColumn.setHgrow(Priority.NEVER);
            fieldsColumn.setFillWidth(true);
            fieldsColumn.setMinWidth(250); // Set a min width for the text fields
            gridPane.getColumnConstraints().addAll(labelsColumn, fieldsColumn);

            // VIN Label (non-editable)
            Label vinLabel = new Label(String.valueOf(selectedVehicle.getVin()));

            // Create and populate fields
            TextField nameField = new TextField(selectedVehicle.getName());
            TextField locationField = new TextField(selectedVehicle.getLocation());
            TextField weightCapacityField = new TextField(String.valueOf(selectedVehicle.getWeightCapacity()));

            // if weight capacity is less than 0

            // ComboBox for vehicle type
            ComboBox<String> comboBoxType = new ComboBox<>(
                    FXCollections.observableArrayList("Van", "Small Truck", "Large Truck"));
            comboBoxType.setValue(selectedVehicle.getVehicleType());

            // Add fields to gridPane in the specified order
            gridPane.add(new Label("VIN:"), 0, 0);
            gridPane.add(vinLabel, 1, 0); // VIN as a label, not editable
            gridPane.add(new Label("Name:"), 0, 1);
            gridPane.add(nameField, 1, 1);
            gridPane.add(new Label("Vehicle Location:"), 0, 2);
            gridPane.add(locationField, 1, 2);
            gridPane.add(new Label("Vehicle Type:"), 0, 3);
            gridPane.add(comboBoxType, 1, 3); // ComboBox for vehicle type
            gridPane.add(new Label("Weight Capacity:"), 0, 4);
            gridPane.add(weightCapacityField, 1, 4);

            // Add gridPane to dialog
            dialog.getDialogPane().setContent(gridPane);

            // Add confirm and cancel buttons
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // Show dialog and wait for response
            dialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {

                        // Retrieve new values from dialog fields
                        String newName = nameField.getText();
                        String newLocation = locationField.getText();
                        String newVehicleType = comboBoxType.getValue();
                        double newWeightCapacity = Double.parseDouble(weightCapacityField.getText());

                        // Check if the new weight capacity is valid
                        if (newWeightCapacity < 0) {
                            vehicleInfoLabel.setText("Error: Weight capacity cannot be less than 0.");
                            return;
                        }
                        // Update Vehicle object with new values from the text fields and combo box
                        selectedVehicle.setName(newName);
                        selectedVehicle.setLocation(newLocation);
                        selectedVehicle.setVehicleType(newVehicleType);
                        selectedVehicle.setWeightCapacity(newWeightCapacity);

                        // Refresh the TableView
                        vehiclesTableView.refresh();

                        // Update the text area with the vehicle's information
                        vehicleInfoLabel.setText(selectedVehicle.getName() + " was updated!");
                    } catch (NumberFormatException e) {
                        vehicleInfoLabel.setText("Error: Invalid input format.");
                    } catch (Exception e) {
                        vehicleInfoLabel.setText("Error: " + e.getMessage());
                    }
                }
            });
        } else {
            vehicleInfoLabel.setText("No vehicle selected for editing.");
        }
    }

    @FXML
    void handleButtonViewVisitedWorkshopsAction(ActionEvent event) {
        Vehicle selectedVehicle = vehiclesTableView.getSelectionModel().getSelectedItem();
        if (selectedVehicle != null) {
            Dialog<ButtonType> dialog = new Dialog<>();

            Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
            Image applicationIcon = new Image(getClass().getResourceAsStream("/vikingexpresslogo.PNG"));
            dialogStage.getIcons().add(applicationIcon);
            dialog.setTitle("Visited Workshops");

            // Set up the GridPane layout
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(20, 150, 10, 10));

            // Create and populate the label
            Label workshopsLabel = new Label(formatVisitedWorkshops(selectedVehicle));
            workshopsLabel.setWrapText(true); // Enable text wrapping

            // Add the label to the gridPane
            gridPane.add(workshopsLabel, 0, 0);

            // Add gridPane to dialog
            dialog.getDialogPane().setContent(gridPane);

            // Add a close button
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

            // Show dialog
            dialog.showAndWait();
        } else {
            vehicleInfoLabel.setText("No vehicle selected.");
        }
    }

    private String formatVisitedWorkshops(Vehicle vehicle) {
        StringBuilder builder = new StringBuilder();
        for (Workshop workshop : vehicle.getVisitedWorkshops()) {
            builder.append("Name: ").append(workshop.getName())
                    .append("\nAddress: ").append(workshop.getAddress())
                    .append("\nInternal: ").append(workshop.isInternal() ? "Yes" : "No")
                    .append("\n\n"); // Added extra new line for spacing between workshops
        }
        return builder.toString();
    }

    private int generateUniqueVin() {
        Random random = new Random();
        int vin;
        do {
            vin = 100000 + random.nextInt(900000); // VINs in the range 100000-999999
        } while (vinExists(vin));
        return vin;
    }
    // Check if a vehicle with the specified VIN already exists
    private boolean vinExists(int vin) {
        for (Vehicle vehicle : sharedDataModel.getVehicles()) {
            if (vehicle.getVin() == vin) {
                return true;
            }
        }
        return false;
    }

    // Add a tooltip to a control that is shown after a delay
    private void applyDynamicTooltip(Control control, String tooltipText) {
        control.setOnMouseEntered(e -> control.setTooltip(createDelayedTooltip(tooltipText)));
        control.setOnMouseExited(e -> control.setTooltip(null));
    }
    // Create a tooltip that is shown after a delay
    private Tooltip createDelayedTooltip(String text) {
        Tooltip tooltip = new Tooltip(text);
        tooltip.setShowDelay(Duration.seconds(1));
        return tooltip;
    }

}
