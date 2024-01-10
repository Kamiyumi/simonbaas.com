package se.lu.ics.controllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import se.lu.ics.data.SharedVehicleDataModel;
import se.lu.ics.models.ServiceActivity;
import se.lu.ics.models.Vehicle;
import se.lu.ics.models.Workshop;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ServiceController {

    private SharedVehicleDataModel sharedDataModel;

    @FXML
    private Button buttonEditActivity;

    @FXML
    private Button buttonRemoveActivity;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<Workshop> comboBoxWorkshop;

    @FXML
    private TextArea textAreaServiceDescription;

    @FXML
    private TextField textFieldSearchService;

    @FXML
    private TextField textFieldServiceCost;

    @FXML
    private TextField textFieldServiceStatus;

    @FXML
    private TextField textFieldPartsReplaced;

    @FXML
    private TableView<ServiceActivity> serviceTableView;

    @FXML
    private ComboBox<Vehicle> comboBoxVehicle;

    @FXML
    private TableColumn<ServiceActivity, String> columnServiceID;

    @FXML
    private TableColumn<ServiceActivity, String> columnServiceProvider;

    @FXML
    private TableColumn<ServiceActivity, String> columnServiceDate;

    @FXML
    private TableColumn<ServiceActivity, String> columnServiceType;

    @FXML
    private TableColumn<ServiceActivity, Double> columnServiceCost;

    @FXML
    private TableColumn<ServiceActivity, String> columnServiceStatus;

    @FXML
    private TableColumn<ServiceActivity, String> columnPartsReplaced;

    @FXML
    private TableColumn<ServiceActivity, String> columnPartsReplacedCount;

    @FXML
    private Button buttonHistory;

    @FXML
    private Label serviceInfoLabel;

    @FXML
    private Button buttonAddService;

    public void setSharedDataModel(SharedVehicleDataModel sharedDataModel) {
        this.sharedDataModel = sharedDataModel;
        // Initialize combo boxes and set up converters
        loadDataIntoUI();
    }

    private void loadDataIntoUI() {
        if (sharedDataModel != null) {
            serviceTableView.setItems(sharedDataModel.getServiceActivities());
            comboBoxVehicle.setItems(FXCollections.observableArrayList(sharedDataModel.getVehicles()));
            comboBoxWorkshop.setItems(FXCollections.observableArrayList(sharedDataModel.getWorkshops()));

        }
    }

    private void setupComboBoxConverters() {
        comboBoxWorkshop.setConverter(new StringConverter<Workshop>() {
            @Override
            public String toString(Workshop workshop) {
                return workshop != null ? workshop.getName() : "";
            }

            @Override
            public Workshop fromString(String string) {
                return null;
            }
        });

        comboBoxVehicle.setConverter(new StringConverter<Vehicle>() {
            @Override
            public String toString(Vehicle vehicle) {
                return vehicle != null ? vehicle.getName() : "";
            }

            @Override
            public Vehicle fromString(String string) {
                return null;
            }
        });
    }

    @FXML
    public void initialize() {
        setupTableColumns();
        setupComboBoxConverters();
        initializeSearchFunctionality();
        applyDynamicTooltip(buttonEditActivity, "Edit the selected service activity");
        applyDynamicTooltip(buttonRemoveActivity, "Remove the selected service activity");
        applyDynamicTooltip(datePicker, "Select the date for the service activity");
        applyDynamicTooltip(comboBoxWorkshop, "Select a workshop for the service activity");
        applyDynamicTooltip(textAreaServiceDescription, "Enter the description for the service activity");
        applyDynamicTooltip(textFieldServiceCost, "Enter the cost of the service");
        applyDynamicTooltip(textFieldServiceStatus, "Enter the status of the service");
        applyDynamicTooltip(textFieldPartsReplaced, "Enter the parts that were replaced! (separate with comma)");
        applyDynamicTooltip(comboBoxVehicle, "Select a vehicle for the service activity");
        applyDynamicTooltip(textFieldSearchService, "Search for service activities");
        applyDynamicTooltip(buttonHistory, "View the service history for the selected vehicle");
    }

    private void setupTableColumns() {
        columnServiceID.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
                String.valueOf(cellData.getValue().getServiceForVehicle().getName())));
        columnServiceProvider.setCellValueFactory(
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getWorkshop().getName()));
        columnServiceDate.setCellValueFactory(
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDateOfService().toString()));
        columnServiceType.setCellValueFactory(new PropertyValueFactory<>("serviceDescription"));
        columnServiceCost.setCellValueFactory(new PropertyValueFactory<>("serviceCost"));
        columnServiceStatus.setCellValueFactory(new PropertyValueFactory<>("serviceStatus"));
        columnPartsReplaced.setCellValueFactory(new PropertyValueFactory<>("partsReplaced"));
        columnPartsReplacedCount.setCellValueFactory(cellData -> {
            String partsReplaced = cellData.getValue().getPartsReplaced();
            if (partsReplaced == null || partsReplaced.trim().isEmpty() || partsReplaced.trim().equals(",")) {
                return new ReadOnlyStringWrapper("0"); // Return "0" if there are no parts replaced
            } else {
                // Split by commas, filter out empty parts, and count non-empty parts
                long partsCount = Arrays.stream(partsReplaced.split(","))
                        .map(String::trim)
                        .filter(part -> !part.isEmpty())
                        .count();
                return new ReadOnlyStringWrapper(String.valueOf(partsCount));
            }
        });
    }

    @FXML
    void handleButtonServiceAddAction() {
        Workshop selectedWorkshop = comboBoxWorkshop.getSelectionModel().getSelectedItem();
        Vehicle selectedVehicle = comboBoxVehicle.getSelectionModel().getSelectedItem();
        LocalDate date = datePicker.getValue();

        // Check if any of the essential fields are null
        if (selectedWorkshop == null || selectedVehicle == null || date == null) {
            if (selectedWorkshop == null) {
                serviceInfoLabel.setText("No workshop selected for the service.");
            } else if (selectedVehicle == null) {
                serviceInfoLabel.setText("No vehicle selected for the service.");
            } else {
                serviceInfoLabel.setText("No date selected for the service.");
            }
            return;
        }

        // Check if the vehicle type is "Large Truck" and the selected workshop is
        // internal
        if ("Large Truck".equals(selectedVehicle.getVehicleType()) && selectedWorkshop.isInternal()) {
            showDecommissionAlert3(selectedVehicle);
            return; // Exit the method early
        }

        // if service cost is negative
        if (Double.parseDouble(textFieldServiceCost.getText()) < 0) {
            serviceInfoLabel.setText("Error: Service cost cannot be negative.");
            return; // Exit the method early
        }

        try {
            String serviceDescription = textAreaServiceDescription.getText();
            double serviceCost = Double.parseDouble(textFieldServiceCost.getText());
            String serviceStatus = textFieldServiceStatus.getText();
            String partsReplaced = textFieldPartsReplaced.getText();

            ServiceActivity newServiceActivity = new ServiceActivity(
                    selectedWorkshop,
                    selectedVehicle,
                    selectedVehicle.getServiceHistory(),
                    date,
                    serviceDescription,
                    serviceCost,
                    serviceStatus,
                    partsReplaced);

            selectedVehicle.getServiceHistory().addServiceActivity(newServiceActivity);
            sharedDataModel.addServiceActivity(newServiceActivity);

            if (!selectedVehicle.getVisitedWorkshops().contains(selectedWorkshop)) {
                selectedVehicle.addVisitedWorkshop(selectedWorkshop);
            } else {
                selectedVehicle.getVisitedWorkshops().remove(selectedWorkshop);
                selectedVehicle.addVisitedWorkshop(selectedWorkshop);
            }

            // add the cost of the service to the total cost of the vehicle and recalculate
            // the total cost
            selectedVehicle.setTotalServiceCost(selectedVehicle.getTotalServiceCost() + serviceCost);
            selectedVehicle.recalculateTotalServiceCost();

            if (selectedVehicle.getTotalServiceCost() > 100000) {
                showDecommissionAlert2(selectedVehicle);
            }

            // add to workshop handled activities
            selectedWorkshop.addServiceActivity(newServiceActivity);

            serviceInfoLabel.setText("Service added successfully.");
        } catch (NumberFormatException e) {
            serviceInfoLabel.setText("Invalid format for service cost.");
        } catch (Exception e) {
            serviceInfoLabel.setText("Error: " + e.getMessage());
        }

        // calculate total parts replaced
        int totalPartsReplaced = calculateTotalPartsReplaced(selectedVehicle);

        if (totalPartsReplaced > 100) {
            showDecommissionAlert(selectedVehicle);
        }

        // Refresh the table view
        // clear the fields
        comboBoxWorkshop.getSelectionModel().clearSelection();
        comboBoxVehicle.getSelectionModel().clearSelection();
        datePicker.setValue(null);
        textAreaServiceDescription.clear();
        textFieldServiceCost.clear();
        textFieldServiceStatus.clear();
        textFieldPartsReplaced.clear();
        serviceTableView.refresh();
    }

    @FXML
    private void handleButtonEditActivityAction(ActionEvent event) {
        ServiceActivity selectedActivity = serviceTableView.getSelectionModel().getSelectedItem();
        if (selectedActivity != null) {
            Dialog<ButtonType> dialog = new Dialog<>();
            Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
            Image applicationIcon = new Image(getClass().getResourceAsStream("/vikingexpresslogo.PNG"));
            dialogStage.getIcons().add(applicationIcon);
            dialog.setTitle("Edit Service Activity");

            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(20, 150, 10, 10));

            ComboBox<String> comboBoxVehicle = new ComboBox<>();
            comboBoxVehicle.setItems(FXCollections.observableArrayList(
                    sharedDataModel.getVehicles().stream().map(Vehicle::getName).collect(Collectors.toList())));
            comboBoxVehicle.setValue(selectedActivity.getServiceForVehicle().getName());

            ComboBox<String> comboBoxWorkshop = new ComboBox<>();
            comboBoxWorkshop.setItems(FXCollections.observableArrayList(
                    sharedDataModel.getWorkshops().stream().map(Workshop::getName).collect(Collectors.toList())));
            comboBoxWorkshop.setValue(selectedActivity.getWorkshop().getName());

            TextField serviceDescriptionField = new TextField(selectedActivity.getServiceDescription());
            TextField serviceCostField = new TextField(String.valueOf(selectedActivity.getServiceCost()));
            TextField serviceStatusField = new TextField(selectedActivity.getServiceStatus());
            TextField partsReplacedField = new TextField(selectedActivity.getPartsReplaced());
            DatePicker serviceDatePicker = new DatePicker(selectedActivity.getDateOfService());

            gridPane.add(new Label("Vehicle:"), 0, 0);
            gridPane.add(comboBoxVehicle, 1, 0);
            gridPane.add(new Label("Workshop:"), 0, 1);
            gridPane.add(comboBoxWorkshop, 1, 1);
            gridPane.add(new Label("Service Description:"), 0, 2);
            gridPane.add(serviceDescriptionField, 1, 2);
            gridPane.add(new Label("Service Cost:"), 0, 3);
            gridPane.add(serviceCostField, 1, 3);
            gridPane.add(new Label("Service Status:"), 0, 4);
            gridPane.add(serviceStatusField, 1, 4);
            gridPane.add(new Label("Parts Replaced:"), 0, 5);
            gridPane.add(partsReplacedField, 1, 5);
            gridPane.add(new Label("Date of Service:"), 0, 6);
            gridPane.add(serviceDatePicker, 1, 6);

            dialog.getDialogPane().setContent(gridPane);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            dialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        Vehicle updatedVehicle = sharedDataModel.getVehicles().stream()
                                .filter(v -> v.getName().equals(comboBoxVehicle.getValue()))
                                .findFirst()
                                .orElse(null);
                        Workshop updatedWorkshop = sharedDataModel.getWorkshops().stream()
                                .filter(w -> w.getName().equals(comboBoxWorkshop.getValue()))
                                .findFirst()
                                .orElse(null);

                        if ("Large Truck".equals(updatedVehicle.getVehicleType()) && updatedWorkshop.isInternal()) {
                            serviceInfoLabel.setText("Error: Internal workshops cannot service large trucks.");
                            return; // Exit the method early
                        }

                        // check if cost is negative
                        if (Double.parseDouble(serviceCostField.getText()) < 0) {
                            serviceInfoLabel.setText("Error: Service cost cannot be negative.");
                            return; // Exit the method early
                        }

                        selectedActivity.setServiceForVehicle(updatedVehicle);
                        selectedActivity.setWorkshop(updatedWorkshop);
                        selectedActivity.setServiceDescription(serviceDescriptionField.getText());
                        selectedActivity.setServiceCost(Double.parseDouble(serviceCostField.getText()));
                        selectedActivity.setServiceStatus(serviceStatusField.getText());
                        selectedActivity.setPartsReplaced(partsReplacedField.getText());
                        selectedActivity.setDateOfService(serviceDatePicker.getValue());

                        updatedVehicle.recalculateTotalServiceCost();

                        setupTableColumns();
                        serviceTableView.setItems(sharedDataModel.getServiceActivities());
                        serviceTableView.refresh();
                        serviceInfoLabel.setText("Service activity updated successfully!");
                    } catch (NumberFormatException e) {
                        serviceInfoLabel.setText("Error: Invalid input format for service cost.");
                    } catch (Exception e) {
                        serviceInfoLabel.setText("Error: " + e.getMessage());
                    }
                }
            });
        } else {
            serviceInfoLabel.setText("No service activity selected for editing.");
        }

        serviceTableView.refresh();
    }

    @FXML
    void handleButtonRemoveActivityAction(ActionEvent event) {
        ServiceActivity selectedActivity = serviceTableView.getSelectionModel().getSelectedItem();
        if (selectedActivity != null) {
            Vehicle associatedVehicle = selectedActivity.getServiceForVehicle();
            associatedVehicle.getServiceHistory().removeServiceActivity(selectedActivity);
            sharedDataModel.removeServiceActivity(selectedActivity);
            serviceInfoLabel.setText("Service activity removed successfully.");
            // remove the cost of the service from the total cost of the vehicle and
            // recalculate the total cost

            // remove it from the workshop handled activities
            selectedActivity.getWorkshop().removeServiceActivity(selectedActivity);

            associatedVehicle
                    .setTotalServiceCost(associatedVehicle.getTotalServiceCost() - selectedActivity.getServiceCost());
            associatedVehicle.recalculateTotalServiceCost();
        } else {
            serviceInfoLabel.setText("No service activity selected.");
        }
    }

    @FXML
    void handleButtonViewHistoryAction() {
        ServiceActivity selectedActivity = serviceTableView.getSelectionModel().getSelectedItem();

        if (selectedActivity != null) {
            Vehicle associatedVehicle = selectedActivity.getServiceForVehicle();

            // Create a new dialog
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("Service History for " + associatedVehicle.getName());

            // Create a TextArea for displaying the service activities
            TextArea serviceHistoryTextArea = new TextArea();
            serviceHistoryTextArea.setEditable(false);

            StringBuilder serviceHistoryContent = new StringBuilder();
            int activityNumber = 1; // Start numbering from 1
            for (ServiceActivity activity : associatedVehicle.getServiceHistory().getServiceActivities()) {
                serviceHistoryContent.append("Activity #")
                        .append(activityNumber++)
                        .append(": ")
                        .append(activity.toString())
                        .append("\n\n");
            }

            serviceHistoryTextArea.setText(serviceHistoryContent.toString());

            // Apply a fixed width and height to the TextArea
            serviceHistoryTextArea.setPrefSize(600, 400);

            VBox vBox = new VBox(serviceHistoryTextArea);
            vBox.setPadding(new Insets(0)); 
            vBox.setSpacing(10); // Spacing between controls in VBox

            // Additional components like labels
            int totalPartsReplaced = calculateTotalPartsReplaced(associatedVehicle);
            Label totalPartsReplacedLabel = new Label("Total Parts Replaced: " + totalPartsReplaced);
            Label totalServiceCostLabel = new Label("Total Service Cost: " + associatedVehicle.getTotalServiceCost());

            vBox.getChildren().addAll(totalPartsReplacedLabel, totalServiceCostLabel);

            // Set dialog properties
            dialog.getDialogPane().setContent(vBox);
            dialog.getDialogPane().setPrefWidth(serviceHistoryTextArea.getPrefWidth());
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.setResizable(true);
            dialog.getDialogPane().setPadding(new Insets(10)); // Some padding around VBox
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/vikingexpresslogo.PNG")));
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

            // Show the dialog
            dialog.showAndWait();
        } else {
            serviceInfoLabel.setText("No service activity selected.");
        }
    }

    private int calculateTotalPartsReplaced(Vehicle vehicle) {
        int totalPartsReplaced = 0;
        for (ServiceActivity activity : vehicle.getServiceHistory().getServiceActivities()) {
            String partsReplaced = activity.getPartsReplaced();
            if (partsReplaced != null && !partsReplaced.isEmpty()) {
                totalPartsReplaced += partsReplaced.split(",").length;
            }
        }
        return totalPartsReplaced;
    }

    private void showDecommissionAlert(Vehicle vehicle) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Vehicle Decommissioned!");
        alert.setHeaderText("Warning");

        String contentText = vehicle.getName()
                + " has had more than 100 parts replaced. This vehicle is now decommissioned. ";

        // Wrap the text and set a preferred width
        Label label = new Label(contentText);
        label.setWrapText(true);
        label.setMaxWidth(400); // Set to an appropriate value based on the expected text length

        // Create a custom layout for the alert dialog
        VBox dialogPaneContent = new VBox(label);
        dialogPaneContent.setSpacing(10);

        // Set the custom layout to the alert dialog
        alert.getDialogPane().setContent(dialogPaneContent);

        // Set the alert dialog to be resizable
        alert.setResizable(true);

        // Update the vehicle's name to indicate it is decommissioned
        vehicle.setName("{Decommissioned} " + vehicle.getName());

        // Set the icon
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/vikingexpresslogo.PNG")));

        // Show the alert dialog
        alert.showAndWait();
    }

    private void showDecommissionAlert2(Vehicle vehicle) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Warning! Expensive Vehicle!");
        alert.setHeaderText("Warning");

        String contentText = vehicle.getName()
                + " has now cost more than 100000 in total service costs, consider decommissioning!";

        // Wrap the text and set a preferred width
        Label label = new Label(contentText);
        label.setWrapText(true);
        label.setMaxWidth(400); // Set to an appropriate value based on the expected text length

        // Create a custom layout for the alert dialog
        VBox dialogPaneContent = new VBox(label);
        dialogPaneContent.setSpacing(10); // You can adjust the spacing

        // Set the custom layout to the alert dialog
        alert.getDialogPane().setContent(dialogPaneContent);

        // Set the alert dialog to be resizable
        alert.setResizable(true);

        // Set the icon
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/vikingexpresslogo.PNG")));

        // Show the alert dialog
        alert.showAndWait();
    }

    private void showDecommissionAlert3(Vehicle vehicle) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Warning! Cannot Service Vehicle!");
        alert.setHeaderText("Service Warning!");

        String contentText = vehicle.getName()
                + " is a large truck and cannot be serviced at an internal workshop!";

        // Wrap the text and set a preferred width
        Label label = new Label(contentText);
        label.setWrapText(true);
        label.setMaxWidth(400); // Set to an appropriate value based on the expected text length

        // Create a custom layout for the alert dialog
        VBox dialogPaneContent = new VBox(label);
        dialogPaneContent.setSpacing(10); // You can adjust the spacing

        // Set the custom layout to the alert dialog
        alert.getDialogPane().setContent(dialogPaneContent);

        // Set the alert dialog to be resizable
        alert.setResizable(true);

        // Set the icon
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/vikingexpresslogo.PNG")));

        // Show the alert dialog
        alert.showAndWait();
    }

    private void initializeSearchFunctionality() {
        textFieldSearchService.textProperty().addListener((observable, oldValue, newValue) -> {
            filterServiceTable(newValue);
        });
    }

    private void filterServiceTable(String searchQuery) {
        if (searchQuery != null && !searchQuery.isEmpty()) {
            // Convert the search query to lower case for case-insensitive comparison
            String lowerCaseSearchQuery = searchQuery.toLowerCase();

            ObservableList<ServiceActivity> filteredList = FXCollections.observableArrayList();

            for (ServiceActivity serviceActivity : sharedDataModel.getServiceActivities()) {
                // Convert the names to lower case before comparing
                String vehicleName = serviceActivity.getServiceForVehicle().getName().toLowerCase();
                String workshopName = serviceActivity.getWorkshop().getName().toLowerCase();

                if (vehicleName.contains(lowerCaseSearchQuery) || workshopName.contains(lowerCaseSearchQuery)) {
                    filteredList.add(serviceActivity);
                }
            }
            serviceTableView.setItems(filteredList);
        } else {
            serviceTableView.setItems(sharedDataModel.getServiceActivities());
        }
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
