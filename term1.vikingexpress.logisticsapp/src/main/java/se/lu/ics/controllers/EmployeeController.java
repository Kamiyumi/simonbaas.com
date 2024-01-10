package se.lu.ics.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import se.lu.ics.models.Employee;
import se.lu.ics.data.SharedVehicleDataModel;
import se.lu.ics.models.Vehicle;

public class EmployeeController {

    private SharedVehicleDataModel sharedDataModel = new SharedVehicleDataModel();

    @FXML
    private Label employeeInfoLabel;

    @FXML
    private Button buttonAddEmployee;

    @FXML
    private Button buttonEditEmployee;

    @FXML
    private Button buttonRemoveEmployee;

    @FXML
    private TableView<Employee> employeeTableView;

    @FXML
    private TableColumn<Employee, String> columnEmployeeName;

    @FXML
    private TableColumn<Employee, String> columnEmployeeSsNo;

    @FXML
    private TableColumn<Employee, String> columnEmployeeAddress;

    @FXML
    private TableColumn<Employee, String> columnEmployeePhoneNumber;

    @FXML
    private TableColumn<Employee, String> columnEmployeeType;

    @FXML
    private TableColumn<Employee, String> columnEmployeeDriverOf;

    @FXML
    private Label labelEdit;

    @FXML
    private Button buttonAssign;

    @FXML
    private ComboBox<Vehicle> comboBoxAssignVehicle;

    @FXML
    private Label labelRemove;

    @FXML
    private Label labelSearchEmployee;

    @FXML
    private TextField textFieldEmployeeAddress;

    @FXML
    private TextField textFieldEmployeeName;

    @FXML
    private TextField textFieldEmployeePhoneNumber;

    @FXML
    private TextField textFieldEmployeeSearch;

    @FXML
    private TextField textFieldEmployeeSsNo;

    @FXML
    private TextField textFieldEmployeeType;

    @FXML
    private Button addEmployeeButton;

    @FXML
    private Button removeEmployeeButton;

    @FXML
    private Text selectEmployeeText;

    public void setSharedDataModel(SharedVehicleDataModel sharedDataModel) {
        this.sharedDataModel = sharedDataModel;
        loadDataIntoUI();
    }

    private void loadDataIntoUI() {
        if (sharedDataModel != null) {
            employeeTableView.setItems(sharedDataModel.getEmployees());
            comboBoxAssignVehicle.setItems(FXCollections.observableArrayList(sharedDataModel.getVehicles()));
        }
    }
     //Filter by the employee's name
    private void filterEmployeeTable(String searchQuery) {
        if (searchQuery != null && !searchQuery.isEmpty()) {
            ObservableList<Employee> filteredList = FXCollections.observableArrayList();

            for (Employee employee : sharedDataModel.getEmployees()) {
               
                String employeeName = employee.getName();

                // Check if the employee's name contains the search query, case insensitive
                if (employeeName.toLowerCase().contains(searchQuery.toLowerCase())) {
                    filteredList.add(employee);
                }
            }
            employeeTableView.setItems(filteredList); // Set the filtered list as the items for the TableView
        } else {
            // If the search query is empty, show all employees
            employeeTableView.setItems(sharedDataModel.getEmployees());
        }
    }

    private void initializeSearchFunctionality() {
        textFieldEmployeeSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filterEmployeeTable(newValue);
        });
    }

    @FXML
    public void initialize() {
        // Validate sharedDataModel and data retrieval
        setupTableColumns();

        setupComboBoxConverter();

        initializeSearchFunctionality();
        
        //tooltips for textFields
        applyDynamicTooltip(textFieldEmployeeName, "Enter the employee's name");
        applyDynamicTooltip(textFieldEmployeeSsNo, "Enter the employee's SSN");
        applyDynamicTooltip(textFieldEmployeeAddress, "Enter the employee's address");
        applyDynamicTooltip(textFieldEmployeePhoneNumber, "Enter the employee's phone number");
        applyDynamicTooltip(textFieldEmployeeType, "Enter the employee's title");
        //tooltips for buttons
        applyDynamicTooltip(buttonAddEmployee, "Add a new employee");
        applyDynamicTooltip(buttonEditEmployee, "Edit the selected employee");
        applyDynamicTooltip(buttonRemoveEmployee, "Remove the selected employee");
        applyDynamicTooltip(buttonAssign, "Assign a vehicle to the selected employee");
        applyDynamicTooltip(comboBoxAssignVehicle, "Select a vehicle to assign to the employee");
        applyDynamicTooltip(textFieldEmployeeSearch, "Search employees by SSN, name, or other details");
    }

    private void setupComboBoxConverter() {
        comboBoxAssignVehicle.setConverter(new StringConverter<Vehicle>() {
            @Override
            public String toString(Vehicle vehicle) {
                return vehicle != null ? vehicle.getName() : "";
            }

            @Override
            public Vehicle fromString(String string) {
                return null; // No conversion from String to Vehicle needed
            }
        });
    }

    private void setupTableColumns() {
        columnEmployeeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnEmployeeSsNo.setCellValueFactory(new PropertyValueFactory<>("ssNo"));
        columnEmployeeAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        columnEmployeePhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        columnEmployeeType.setCellValueFactory(new PropertyValueFactory<>("employeeType"));

        // Adjust the cellValueFactory for the vehicle name column
        columnEmployeeDriverOf.setCellValueFactory(cellData -> {
            Employee employee = cellData.getValue();
            Vehicle vehicle = employee.getDriverOf();
            String vehicleName = vehicle != null ? vehicle.getName() : "None";
            return new SimpleStringProperty(vehicleName);
        });
    }

    @FXML
    private void handleButtonAddEmployeeAction(ActionEvent event) {
        try {
            String name = textFieldEmployeeName.getText();
            String ssNo = textFieldEmployeeSsNo.getText();
            String address = textFieldEmployeeAddress.getText();
            String phoneNumber = textFieldEmployeePhoneNumber.getText();
            String employeeType = textFieldEmployeeType.getText();

            if (name.isEmpty() || ssNo.isEmpty() || address.isEmpty() || phoneNumber.isEmpty() || employeeType.isEmpty()) {
                employeeInfoLabel.setText("Please fill in all fields.");
                return; 
            }

            Employee employee = new Employee(name, ssNo, address, phoneNumber, employeeType);
            sharedDataModel.getEmployees().add(employee);
            employeeTableView.refresh();
            employeeInfoLabel.setText("Employee added successfully.");

        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }


    @FXML
    private void handleButtonEditEmployeeAction(ActionEvent event) {
        Employee selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();

        if (selectedEmployee != null) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Edit Employee");

           
            Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
            Image applicationIcon = new Image(getClass().getResourceAsStream("/vikingexpresslogo.PNG"));
            dialogStage.getIcons().add(applicationIcon);

          
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(20, 150, 10, 10));

         
            TextField nameField = new TextField(selectedEmployee.getName());
            TextField addressField = new TextField(selectedEmployee.getAddress());
            TextField phoneNumberField = new TextField(selectedEmployee.getPhoneNumber());
            TextField ssNoField = new TextField(selectedEmployee.getSsNo());
            TextField employeeTypeField = new TextField(selectedEmployee.getEmployeeType());
           

           
            nameField.setPrefWidth(300);
            addressField.setPrefWidth(300);
            phoneNumberField.setPrefWidth(300);
            ssNoField.setPrefWidth(300);
            employeeTypeField.setPrefWidth(300);

            
            gridPane.add(new Label("Name:"), 0, 0);
            gridPane.add(nameField, 1, 0);
            gridPane.add(new Label("SSN:"), 0, 1);
            gridPane.add(ssNoField, 1, 1);
            gridPane.add(new Label("Address:"), 0, 2);
            gridPane.add(addressField, 1, 2);
            gridPane.add(new Label("Phone Number:"), 0, 3);
            gridPane.add(phoneNumberField, 1, 3);
            gridPane.add(new Label("Employee Type:"), 0, 4);
            gridPane.add(employeeTypeField, 1, 4);
           
            dialog.getDialogPane().setContent(gridPane);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            dialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        // Update the selected employee with the new data
                        selectedEmployee.setName(nameField.getText());
                        selectedEmployee.setSsNo(ssNoField.getText());
                        selectedEmployee.setAddress(addressField.getText());
                        selectedEmployee.setPhoneNumber(phoneNumberField.getText());
                        
                        // Validate and parse the phone number
                        try {
                                String phoneNumber = phoneNumberField.getText();
                                if (!phoneNumber.matches("\\d+")) {
                                    throw new NumberFormatException("Invalid input. Phone number must be a number.");
                                }
                                    selectedEmployee.setPhoneNumber(phoneNumber);
                            } catch (NumberFormatException e) {
                                        employeeInfoLabel.setText("Error: " + e.getMessage());
                                        return;
                            }
                                    
                        selectedEmployee.setEmployeeType(employeeTypeField.getText());
                    
                        employeeTableView.refresh();
                        employeeInfoLabel.setText("Employee edited successfully.");
                    } catch (Exception e) {
                        employeeInfoLabel.setText("Error: " + e.getMessage());
                    }
                }
            });
        } else {
            employeeInfoLabel.setText("No employee selected for editing.");
        }
    }

    @FXML
    void handleButtonRemoveEmployeeAction(ActionEvent event) {
        Employee selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            // Remove the employee from the shared data model's list of employees
            sharedDataModel.getEmployees().remove(selectedEmployee);

            // Also remove the employee from any vehicles' list of responsible drivers
            for (Vehicle vehicle : sharedDataModel.getVehicles()) {
                // Assuming Vehicle has a method to remove a driver, and getResponsibleDrivers
                // returns a list of drivers
                vehicle.getResponsibleDrivers().remove(selectedEmployee);
            }

            // Refresh the employee table view to reflect the removal
            employeeTableView.refresh();

            // Update the info label to indicate success
            employeeInfoLabel.setText("Employee removed: " + selectedEmployee.getName());

            // Additional UI updates or logic can be added here if necessary
        } else {
            employeeInfoLabel.setText("Please select an employee to remove.");
        }
    }

    @FXML
    void handleButtonAssignVehicleAction(ActionEvent event) {
        Employee selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();
        Vehicle selectedVehicle = comboBoxAssignVehicle.getSelectionModel().getSelectedItem();

        if (selectedEmployee != null && selectedVehicle != null) {
            // Assign the vehicle to the employee
            selectedEmployee.setDriverOf(selectedVehicle);

            // Check if the driver isn't already in the list of drivers, add the driver to the list
            if (!selectedVehicle.getResponsibleDrivers().contains(selectedEmployee)) {
                selectedVehicle.addResponsibleDriver(selectedEmployee);
                employeeInfoLabel
                        .setText(selectedEmployee.getName() + " is now assigned to " + selectedVehicle.getName());
            } else {
                // Handle the case where the driver is already in the list of drivers
                employeeInfoLabel
                        .setText(selectedEmployee.getName() + " is already assigned to " + selectedVehicle.getName());
            }

            employeeTableView.refresh();

        } else {
            // Handle the case where either the selected employee or vehicle is null
            employeeInfoLabel.setText("Please select both an employee and a vehicle.");
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