package se.lu.ics.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import se.lu.ics.models.Vehicle;
import se.lu.ics.data.SharedVehicleDataModel;
import se.lu.ics.models.MaintenanceSchedule;
import se.lu.ics.models.Workshop;
import javafx.beans.property.SimpleObjectProperty;

public class MaintenanceController {

    @FXML
    private Button buttonAddMaintenance;

    @FXML
    private Button buttonEditMaintenance;

    @FXML
    private Button buttonRemoveMaintenance;

    @FXML
    private ComboBox<Workshop> comboBoxWorkshop;

    @FXML
    private TableColumn<MaintenanceSchedule, String> columnResponsibleWorkshop;

    @FXML
    private TableColumn<MaintenanceSchedule, String> columnName;

    @FXML
    private TableColumn<MaintenanceSchedule, LocalDate> columnScheduledMaintenance;

    @FXML
    private TableColumn<MaintenanceSchedule, String> columnWorkshopAddress;

    @FXML
    private TableColumn<MaintenanceSchedule, String> columnVin;

    @FXML
    private ComboBox<Vehicle> comboBoxStoredInVehicle;

    @FXML
    private Label labelEdit;

    @FXML
    private Label labelRemove;

    @FXML
    private Label labelSearchVin;

    @FXML
    private Label maintenanceInfoLabel;

    @FXML
    private TableView<MaintenanceSchedule> maintenanceTableView;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField textFieldDescription;

    @FXML
    private TextField textFieldMaintenanceSearch;

    private SharedVehicleDataModel sharedDataModel;

    public void setSharedDataModel(SharedVehicleDataModel sharedDataModel) {
        this.sharedDataModel = sharedDataModel;
        initializeTableColumns();
        populateMaintenanceTableView();
        updateComboBoxItems(); 
    }

    private void updateComboBoxItems() {
        if (sharedDataModel != null) {
            comboBoxStoredInVehicle.setItems(sharedDataModel.getVehicles());
            comboBoxWorkshop.setItems(sharedDataModel.getWorkshops());
        }
    }

    private void populateMaintenanceTableView() {
        if (sharedDataModel != null) {
            maintenanceTableView.setItems(sharedDataModel.getMaintenanceSchedules());
        } else {
            System.out.println("");
        }
    }

    private void initializeTableColumns() {
        columnVin.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.valueOf(cellData.getValue().getVehicle().getVin())));

        columnName.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getVehicle().getName()));

        columnWorkshopAddress.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getWorkshop().getAddress()));

        columnResponsibleWorkshop.setCellValueFactory(cellData -> {
            Workshop workshop = cellData.getValue().getWorkshop();
            String workshopName = workshop.getName();

            // Check if the workshop is internal
            if (workshop.isInternal()) {
                workshopName += " (Internal)";
            } else {
                workshopName += " (External)";
            }

            return new SimpleStringProperty(workshopName);
        });

        columnScheduledMaintenance.setCellValueFactory(cellData -> new SimpleObjectProperty<>(
                cellData.getValue().getScheduledMaintenanceDate()));
    }

    public void initialize() {

        initializeTableColumns();
        updateComboBoxItems();
        populateMaintenanceTableView(); 

        applyDynamicTooltip(buttonAddMaintenance, "Add new maintenance record");
        applyDynamicTooltip(buttonEditMaintenance, "Edit selected maintenance record");
        applyDynamicTooltip(buttonRemoveMaintenance, "Remove selected maintenance record");
        applyDynamicTooltip(comboBoxWorkshop, "Select a workshop for maintenance");
        applyDynamicTooltip(comboBoxStoredInVehicle, "Select a vehicle for maintenance");
        applyDynamicTooltip(datePicker, "Pick a date for the maintenance schedule");
        applyDynamicTooltip(textFieldMaintenanceSearch, "Search maintenance records by VIN");

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

        // Set the converter for displaying workshop names

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

        initializeSearchFunctionality();
    }

    
    //search function
    private void initializeSearchFunctionality() {
        textFieldMaintenanceSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filterMaintenanceTable(newValue);
        });
    }

    private void filterMaintenanceTable(String searchQuery) {
        if (searchQuery != null && !searchQuery.isEmpty()) {
            ObservableList<MaintenanceSchedule> filteredList = FXCollections.observableArrayList();

            for (MaintenanceSchedule maintenanceSchedule : sharedDataModel.getMaintenanceSchedules()) {
                int vehicleVIN = maintenanceSchedule.getVehicle().getVin(); // Assuming VIN is an int

                // Convert the VIN to a string
                String vinAsString = String.valueOf(vehicleVIN);

                // Check if the string representation of the VIN contains the search query
                if (vinAsString.contains(searchQuery)) {
                    filteredList.add(maintenanceSchedule);
                }
            }
            maintenanceTableView.setItems(filteredList); // Set the filtered list as the items
        } else {
            // If the search query is empty, show all maintenance schedules
            maintenanceTableView.setItems(sharedDataModel.getMaintenanceSchedules());
        }
    }



    @FXML
    public void handleButtonMaintenanceAddAction(ActionEvent event) {
        Vehicle selectedVehicle = comboBoxStoredInVehicle.getSelectionModel().getSelectedItem();
        Workshop selectedWorkshop = comboBoxWorkshop.getSelectionModel().getSelectedItem();

        if (selectedVehicle != null && selectedWorkshop != null) {
            try {
                LocalDate date = datePicker.getValue();
                Workshop workshop = comboBoxWorkshop.getSelectionModel().getSelectedItem();
                
                // Create a new MaintenanceSchedule with the selected vehicle and date
                MaintenanceSchedule newMaintenance = new MaintenanceSchedule(selectedVehicle, date);
                newMaintenance.setWorkshop(workshop);
                // Set the selected workshop to the new maintenance schedule
                newMaintenance.setWorkshop(selectedWorkshop);

                // Add the new maintenance schedule to your data model
                sharedDataModel.addMaintenanceSchedule(newMaintenance);

                maintenanceInfoLabel.setText("Maintenance added successfully.");
            } catch (DateTimeParseException e) {
                maintenanceInfoLabel.setText("The date format is invalid. Please use the format YYYY-MM-DD.");
            } catch (Exception e) {
                maintenanceInfoLabel.setText("Error: " + e.getMessage());
            }
        } else

        {
            if (selectedVehicle == null) {
                maintenanceInfoLabel.setText("No vehicle selected for adding maintenance.");
            } else {
                maintenanceInfoLabel.setText("No workshop selected for the maintenance.");
            }
        }
    }

    @FXML
    private void handleButtonMaintenanceRemoveAction(ActionEvent event) {
        MaintenanceSchedule selectedMaintenance = maintenanceTableView.getSelectionModel().getSelectedItem();
        if (selectedMaintenance != null) {
            sharedDataModel.removeMaintenanceSchedule(selectedMaintenance); // Remove the selected maintenance
            maintenanceInfoLabel.setText("Maintenance removed successfully.");
        } else {
            maintenanceInfoLabel.setText("No maintenance selected for removal.");
        }
    }

    @FXML
    private void handleButtonMaintenanceEditAction(ActionEvent event) {
        MaintenanceSchedule selectedMaintenance = maintenanceTableView.getSelectionModel().getSelectedItem();
        Vehicle selectedVehicle = selectedMaintenance != null ? selectedMaintenance.getVehicle() : null;

        if (selectedVehicle != null) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Edit Maintenance");

            // add icon viking-express-icon.png
            Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
            Image applicationIcon = new Image(getClass().getResourceAsStream("/vikingexpresslogo.PNG"));
            dialogStage.getIcons().add(applicationIcon);

            // Set up the GridPane layout
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(20, 150, 10, 10));

            // Create and populate fields with the selected vehicle's current data
            DatePicker datePicker = new DatePicker(); // Use a DatePicker for selecting the date
            ComboBox<Workshop> workshopComboBox = new ComboBox<>(); // ComboBox for selecting the workshop

            // Populate the workshopComboBox with available workshops
            workshopComboBox.setItems(sharedDataModel.getWorkshops());

            //set the combobox to show the name only

            workshopComboBox.setConverter(new StringConverter<Workshop>() {
                @Override
                public String toString(Workshop workshop) {
                    return workshop != null ? workshop.getName() : "";
                }

                @Override
                public Workshop fromString(String string) {
                    return null; // Not needed for this use case
                }

            });

            // Set preferred width for controls
            datePicker.setPrefWidth(300);
            workshopComboBox.setPrefWidth(300);

            // Add controls to gridPane
            gridPane.add(new Label("Date:"), 0, 0);
            gridPane.add(datePicker, 1, 0);
            gridPane.add(new Label("Workshop:"), 0, 1);
            gridPane.add(workshopComboBox, 1, 1);

            // Add gridPane to dialog
            dialog.getDialogPane().setContent(gridPane);

            // Add confirm and cancel buttons
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // Show dialog and wait for response
            dialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        LocalDate date = datePicker.getValue();
                        Workshop selectedWorkshop = workshopComboBox.getSelectionModel().getSelectedItem();

                        if (date != null && selectedWorkshop != null) {
                            // Update the selected maintenance with the new date and workshop
                            selectedMaintenance.setDate(date);
                            selectedMaintenance.setWorkshop(selectedWorkshop);

                            maintenanceTableView.refresh();
                            maintenanceInfoLabel.setText("Maintenance edited successfully.");
                        } else {
                            maintenanceInfoLabel.setText("Please select a date and workshop.");
                        }
                    } catch (Exception e) {
                        maintenanceInfoLabel.setText("Error: " + e.getMessage());
                    }
                }
            });
        } else {
            maintenanceInfoLabel.setText("No vehicle selected for editing maintenance.");
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