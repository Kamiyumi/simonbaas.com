package se.lu.ics.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import se.lu.ics.models.Cargo;
import se.lu.ics.models.Vehicle;
import se.lu.ics.data.SharedVehicleDataModel;
import se.lu.ics.models.Factory;

public class CargoController {

    @FXML
    private Button buttonEditCargo;

    @FXML
    private Label labelEdit;

    @FXML
    private Button buttonRemoveCargo;

    @FXML
    private TextField textFieldCargoSearch;

    @FXML
    private Button buttonAddCargo;

    @FXML
    private Label labelSearchEan;

    @FXML
    private Label cargoInfoLabel;

    @FXML
    private TableView<Cargo> cargoTableView;

    @FXML
    private TableColumn<Cargo, String> columnCargoName;

    @FXML
    private TableColumn<Cargo, Double> columnCargoWeight;

    @FXML
    private TableColumn<Cargo, Integer> columnCargoAmount;

    @FXML
    private TableColumn<Cargo, Integer> columnEan;

    @FXML
    private TableColumn<Cargo, Double> columnCargoTotalWeight;

    @FXML
    private TableColumn<Cargo, String> columnCargoStoredInVehicle; 

    @FXML
    private TextField textFieldCargoAmount;

    @FXML
    private TextField textFieldCargoName;

    @FXML
    private TextField textFieldCargoWeight;

    @FXML
    private TextField textFieldEan;

    @FXML
    private TextField textFieldStoredInVehicle;

    @FXML
    private ComboBox<Vehicle> comboBoxStoredInVehicle;

    private SharedVehicleDataModel sharedDataModel;

    public void setSharedDataModel(SharedVehicleDataModel sharedDataModel) {
        this.sharedDataModel = sharedDataModel;
        initializeTableColumns();
        populateCargoTableView();
        updateComboBoxItems(); 
    }

    private void updateComboBoxItems() {
        if (sharedDataModel != null) {
            comboBoxStoredInVehicle.setItems(sharedDataModel.getVehicles());
        }
    }

    private void initializeTableColumns() {
        columnCargoName.setCellValueFactory(new PropertyValueFactory<>("cargoName"));
        columnCargoWeight.setCellValueFactory(
                cellData -> new SimpleDoubleProperty(cellData.getValue().getCargoWeight()).asObject());
        columnCargoAmount.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getCargoAmount()).asObject());
        columnEan.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEan()).asObject());
        columnCargoStoredInVehicle
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVehicleName()));
        columnCargoTotalWeight.setCellValueFactory(
                cellData -> new SimpleDoubleProperty(cellData.getValue().getCargoTotalWeight()).asObject());
    }

    public void populateCargoTableView() {
        cargoTableView.setItems(sharedDataModel.getAllCargo());
    }

    public void initialize() {
        initializeTableColumns();
        updateComboBoxItems(); // Ensure ComboBox is updated on initialization

        applyDynamicTooltip(textFieldCargoSearch, "Enter search query to filter cargos by EAN");
        applyDynamicTooltip(textFieldCargoName, "Enter the name of the cargo");
        applyDynamicTooltip(textFieldCargoWeight, "Enter the weight of the cargo in kilograms");
        applyDynamicTooltip(textFieldCargoAmount, "Enter the amount of cargo units");
        applyDynamicTooltip(textFieldEan, "Enter the EAN code of the cargo");
        applyDynamicTooltip(comboBoxStoredInVehicle, "Select a vehicle to store the cargo");
        applyDynamicTooltip(cargoTableView, "View and manage cargo details");
        applyDynamicTooltip(buttonEditCargo, "Edit cargo details");
        applyDynamicTooltip(buttonRemoveCargo, "Remove cargo from the system");
        applyDynamicTooltip(buttonAddCargo, "Add cargo to the system");

        // Set the converter for displaying vehicle names
        comboBoxStoredInVehicle.setConverter(new StringConverter<Vehicle>() {
            @Override
            public String toString(Vehicle vehicle) {
                return vehicle != null ? vehicle.getName() : "";
            }

            @Override
            public Vehicle fromString(String string) {
                return null; 
            }
        });
        initializeSearchFunctionality();
    }

    private void initializeSearchFunctionality() {
        textFieldCargoSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filterCargoTable(newValue);
        });
    }

    private void filterCargoTable(String searchQuery) {
        if (searchQuery.isEmpty()) {
            cargoTableView.setItems(sharedDataModel.getAllCargo()); // Set all items if search is empty
        } else {
            ObservableList<Cargo> filteredList = FXCollections.observableArrayList();
            for (Cargo cargo : sharedDataModel.getAllCargo()) {
                if (String.valueOf(cargo.getEan()).contains(searchQuery)) {
                    filteredList.add(cargo);
                }
            }
            cargoTableView.setItems(filteredList); // Set filtered items
        }
        cargoTableView.refresh();
    }

    public void handleButtonCargoAddAction(ActionEvent event) {
        Vehicle selectedVehicle = comboBoxStoredInVehicle.getValue();

        if (selectedVehicle == null) {
            cargoInfoLabel.setText("Please select a vehicle before adding cargo.");
            return;
        }

        Factory factory = new Factory();

        try {
            String cargoName = textFieldCargoName.getText();
            if (cargoName.isEmpty()) {
                cargoInfoLabel.setText("Error: Cargo name cannot be empty.");
                return;
            }

            double cargoWeight;
            try {
                cargoWeight = Double.parseDouble(textFieldCargoWeight.getText());
                if (cargoWeight <= 0) {
                    cargoInfoLabel.setText("Error: Cargo weight must be a positive number.");
                    return;
                }
            } catch (NumberFormatException e) {
                cargoInfoLabel.setText("Error: Invalid input format for cargo weight.");
                return;
            }

            int cargoAmount;
            try {
                cargoAmount = Integer.parseInt(textFieldCargoAmount.getText());
                if (cargoAmount <= 0) {
                    cargoInfoLabel.setText("Error: Cargo amount must be a positive number.");
                    return;
                }
            } catch (NumberFormatException e) {
                cargoInfoLabel.setText("Error: Invalid input format for cargo amount.");
                return;
            }

            int ean;
            try {
                ean = Integer.parseInt(textFieldEan.getText());
            } catch (NumberFormatException e) {
                cargoInfoLabel.setText("Error: Invalid input format for EAN code.");
                return;
            }

            // Calculate total weight of the new cargo
            double totalNewCargoWeight = cargoWeight * cargoAmount;

            // Check if adding the new cargo exceeds vehicle's weight capacity
            if (totalNewCargoWeight + selectedVehicle.getCargoWeight() > selectedVehicle.getWeightCapacity()) {
                cargoInfoLabel.setText(String.format(
                        "Error: Total cargo weight exceeds vehicle capacity. Expected Total: %.2f, Vehicle Capacity: %.2f",
                        totalNewCargoWeight + selectedVehicle.getCargoWeight(),
                        selectedVehicle.getWeightCapacity()));
                return;
            }

            // Create and add new cargo
            Cargo newCargo = factory.createCargo(cargoName, cargoWeight, cargoAmount, ean);
            newCargo.setVehicle(selectedVehicle);
            selectedVehicle.addCargo(newCargo);

            // Recalculate total cargo weight
            selectedVehicle.recalculateTotalCargoWeight();

            // Refresh TableView
            ObservableList<Cargo> cargoList = cargoTableView.getItems();
            cargoList.add(newCargo);
            cargoTableView.setItems(cargoList);
            cargoTableView.refresh();

            // Clear input fields and update info label
            clearCargoInputFields();
            cargoInfoLabel.setText("Cargo added to " + selectedVehicle.getName());

        } catch (Exception e) {
            cargoInfoLabel.setText("Unexpected error: " + e.getMessage());
        }
    }

    private void clearCargoInputFields() {
        textFieldCargoName.clear();
        textFieldCargoWeight.clear();
        textFieldCargoAmount.clear();
        textFieldEan.clear();
    }

    // Optional: method to add a cargo item
    public void addCargo(Cargo cargo) {
        cargoTableView.getItems().add(cargo);
    }

    // Optional: method to remove a cargo item
    public void removeCargo(Cargo cargo) {
        cargoTableView.getItems().remove(cargo);
    }

    @FXML
    private void handleButtonCargoRemoveAction(ActionEvent event) {
        // Get the selected cargo from the TableView
        Cargo selectedCargo = cargoTableView.getSelectionModel().getSelectedItem();

        if (selectedCargo != null) {
            Vehicle vehicle = selectedCargo.getVehicle();

            // Remove the selected cargo from the shared data model's cargo list
            sharedDataModel.getAllCargo().remove(selectedCargo);

           /* Remove the cargo from the vehicle's cargo list, if it's associated with a
             vehicle */
            if (vehicle != null) {
                vehicle.removeCargo(selectedCargo);
                vehicle.recalculateTotalCargoWeight(); // Update the vehicle's total cargo weight
            }

            // Update the UI to reflect the removal
            cargoTableView.getItems().remove(selectedCargo);

            // Update the information label
            cargoInfoLabel.setText("Cargo removed: " + selectedCargo.getCargoName());

            //refresh
            cargoTableView.refresh();
        } else {
            // Handle the case where no cargo is selected
            cargoInfoLabel.setText("No cargo selected for removal.");
        }
    }

    @FXML
    private void handleButtonCargoEditAction(ActionEvent event) {
        Cargo selectedCargo = cargoTableView.getSelectionModel().getSelectedItem();
        if (selectedCargo != null) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Edit Cargo");

            // Add icon to dialog
            Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
            Image applicationIcon = new Image(getClass().getResourceAsStream("/vikingexpresslogo.PNG"));
            dialogStage.getIcons().add(applicationIcon);

            // Set up the GridPane layout
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(20, 150, 10, 10));

            // Create and populate fields with the selected cargo's current data
            TextField cargoNameField = new TextField(selectedCargo.getCargoName());
            TextField cargoWeightField = new TextField(String.valueOf(selectedCargo.getCargoWeight()));
            TextField cargoAmountField = new TextField(String.valueOf(selectedCargo.getCargoAmount()));
            TextField eanField = new TextField(String.valueOf(selectedCargo.getEan()));

            // Create ComboBox for vehicle selection
            ComboBox<Vehicle> vehicleComboBox = new ComboBox<>();
            vehicleComboBox.setItems(sharedDataModel.getVehicles()); // Set the list of vehicles
            vehicleComboBox.setConverter(new StringConverter<Vehicle>() {
                @Override
                public String toString(Vehicle vehicle) {
                    return vehicle != null ? vehicle.getName() : "";
                }

                @Override
                public Vehicle fromString(String string) {
                    return null; // Not required
                }
            });
            vehicleComboBox.setValue(selectedCargo.getVehicle()); // Set the current vehicle as default selection

            // Add fields to gridPane
            gridPane.add(new Label("Cargo Name:"), 0, 0);
            gridPane.add(cargoNameField, 1, 0);
            gridPane.add(new Label("Cargo Weight:"), 0, 1);
            gridPane.add(cargoWeightField, 1, 1);
            gridPane.add(new Label("Cargo Amount:"), 0, 2);
            gridPane.add(cargoAmountField, 1, 2);
            gridPane.add(new Label("EAN:"), 0, 3);
            gridPane.add(eanField, 1, 3);
            gridPane.add(new Label("Stored in Vehicle:"), 0, 4);
            gridPane.add(vehicleComboBox, 1, 4);

            // Add gridPane to dialog
            dialog.getDialogPane().setContent(gridPane);

            // Add confirm and cancel buttons
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // Show dialog and wait for response
            dialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        String newCargoName = cargoNameField.getText();
                        double newCargoWeight = Double.parseDouble(cargoWeightField.getText());
                        int newCargoAmount = Integer.parseInt(cargoAmountField.getText());
                        int newEan = Integer.parseInt(eanField.getText());
                        Vehicle newVehicle = vehicleComboBox.getValue();

                        double originalTotalWeight = selectedCargo.getCargoWeight() * selectedCargo.getCargoAmount();
                        double newTotalWeight = newCargoWeight * newCargoAmount;

                        double totalVehicleCargoWeight = (newVehicle != null ? newVehicle.getCargoWeight() : 0)
                                - originalTotalWeight + newTotalWeight;
                        sharedDataModel.updateCargoVehicle(selectedCargo, newVehicle);

                        if (newVehicle != null && totalVehicleCargoWeight > newVehicle.getWeightCapacity()) {
                            cargoInfoLabel.setText(String.format(
                                    "Error: Total cargo weight exceeds vehicle capacity. Current Total: %.2f, Capacity: %.2f",
                                    totalVehicleCargoWeight, newVehicle.getWeightCapacity()));
                            sharedDataModel.updateCargoVehicle(selectedCargo, newVehicle);
                            return;
                        }

                        selectedCargo.setCargoName(newCargoName);
                        selectedCargo.setCargoWeight(newCargoWeight);
                        selectedCargo.setCargoAmount(newCargoAmount);
                        selectedCargo.setEan(newEan);
                        selectedCargo.setVehicle(newVehicle);

                        if (newVehicle != null) {
                            newVehicle.recalculateTotalCargoWeight();
                        }

                        cargoTableView.refresh();
                        cargoInfoLabel.setText("Cargo details updated successfully.");
                    } catch (NumberFormatException e) {
                        cargoInfoLabel.setText("Error: Invalid input format.");
                    } catch (Exception e) {
                        cargoInfoLabel.setText("Error: " + e.getMessage());
                    }
                }
            });
        } else {
            cargoInfoLabel.setText("No cargo selected for editing.");
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
