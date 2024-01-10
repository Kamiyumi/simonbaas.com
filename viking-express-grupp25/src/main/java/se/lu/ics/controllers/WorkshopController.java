package se.lu.ics.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.Duration;
import se.lu.ics.data.SharedVehicleDataModel;
import se.lu.ics.models.ServiceActivity;
import se.lu.ics.models.Workshop;

public class WorkshopController {

    private SharedVehicleDataModel sharedDataModel;

    @FXML
    private TableView<Workshop> workshopsTableView;

    @FXML
    private TableColumn<Workshop, String> columnName;

    @FXML
    private TableColumn<Workshop, String> columnAddress;

    @FXML
    private TableColumn<Workshop, String> columnIsInternal;

    @FXML
    private TextField textFieldSearchName;

    @FXML
    private TextField textFieldWorkshopName;

    @FXML
    private TextField textFieldAddress;

    @FXML
    private Button buttonAddWorkshop;

    @FXML
    private Button buttonEditWorkshop;

    @FXML
    private Button buttonRemoveWorkshop;

    @FXML
    private Button buttonDisplayServivceHistoryWorkshop;

    @FXML
    private CheckBox checkBoxIsInternal;

    @FXML
    private Label workshopInfoLabel;

    @FXML
    public void initialize() {
        setupTableColumns();
        applyDynamicTooltip(textFieldSearchName, "Search workshops by name");
        applyDynamicTooltip(textFieldWorkshopName, "Enter the name of the workshop");
        applyDynamicTooltip(textFieldAddress, "Enter the address of the workshop");
        applyDynamicTooltip(checkBoxIsInternal, "Check if the workshop is internal");
        applyDynamicTooltip(workshopsTableView, "Select a workshop to edit or remove it");
        applyDynamicTooltip(buttonAddWorkshop, "Add a new workshop");
        applyDynamicTooltip(buttonEditWorkshop, "Edit the selected workshop");
        applyDynamicTooltip(buttonRemoveWorkshop, "Remove the selected workshop");

        // Check if sharedDataModel is already set
        if (sharedDataModel != null) {
            workshopsTableView.setItems(sharedDataModel.getWorkshops());
        }
        initializeSearchFunctionality();
    }

    private void setupTableColumns() {
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        columnIsInternal.setCellValueFactory(cellData -> {
            // if isInternal is true write "Yes" else "No
            Boolean isInternal = cellData.getValue().isInternal();

            if (isInternal) {
                return new ReadOnlyObjectWrapper<>("Yes");
            } else {
                return new ReadOnlyObjectWrapper<>("No");
            }
        });
    }

    @FXML
    private void handleButtonAddWorkshopAction() {
        String name = textFieldWorkshopName.getText();
        String address = textFieldAddress.getText();
        boolean isInternal = checkBoxIsInternal.isSelected();

        if (sharedDataModel != null) {
            // Check if the name and address fields are not empty
            if (name != null && !name.trim().isEmpty() && address != null && !address.trim().isEmpty()) {
                Workshop newWorkshop = new Workshop(name, address, isInternal);
                sharedDataModel.addWorkshop(newWorkshop);
                clearInputFields();
            } else {
                System.err.println("Invalid input: Name and address fields must not be empty.");
                String errorMessage = "Invalid input: Name and address fields must not be empty.";
                System.err.println(errorMessage);
                workshopInfoLabel.setText(errorMessage);
            }
        } else {
            System.err.println("sharedDataModel is not initialized. Cannot add workshop.");

        }
    }

    @FXML
    private void handleButtonEditWorkshopAction() {
        Workshop selectedWorkshop = workshopsTableView.getSelectionModel().getSelectedItem();
        if (selectedWorkshop != null) {
            Dialog<ButtonType> dialog = new Dialog<>();

            Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
            Image applicationIcon = new Image(getClass().getResourceAsStream("/vikingexpresslogo.PNG"));
            dialogStage.getIcons().add(applicationIcon);
            dialog.setTitle("Edit Workshop");

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

            // Name Label
            Label nameLabel = new Label("Name:");
            TextField nameField = new TextField(selectedWorkshop.getName());

            // Address Label
            Label addressLabel = new Label("Address:");
            TextField addressField = new TextField(selectedWorkshop.getAddress());

            // Internal CheckBox
            CheckBox internalCheckBox = new CheckBox("Is Internal");
            internalCheckBox.setSelected(selectedWorkshop.isInternal());

            // Add fields to gridPane in the specified order
            gridPane.add(nameLabel, 0, 0);
            gridPane.add(nameField, 1, 0);
            gridPane.add(addressLabel, 0, 1);
            gridPane.add(addressField, 1, 1);
            gridPane.add(internalCheckBox, 0, 2, 2, 1); // Span 2 columns

            // Add gridPane to dialog
            dialog.getDialogPane().setContent(gridPane);

            // Add confirm and cancel buttons
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // Show dialog and wait for response
            dialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Update Workshop object with new values from the text fields and CheckBox
                    selectedWorkshop.setName(nameField.getText());
                    selectedWorkshop.setAddress(addressField.getText());
                    selectedWorkshop.setInternal(internalCheckBox.isSelected());

                    // Refresh the TableView
                    workshopsTableView.refresh();
                }
            });
        } else {
            System.err.println("No workshop selected for editing.");
          
        }
    }

    @FXML
    private void handleButtonRemoveWorkshopAction() {
        Workshop selectedWorkshop = workshopsTableView.getSelectionModel().getSelectedItem();
        if (selectedWorkshop != null && sharedDataModel != null) {
            sharedDataModel.removeWorkshop(selectedWorkshop);
        } else {
            System.err.println("No workshop selected or sharedDataModel is not initialized.");
          
        }
    }

    private void clearInputFields() {
        textFieldWorkshopName.clear();
        textFieldAddress.clear();
        checkBoxIsInternal.setSelected(false);
    }

    public void setSharedDataModel(SharedVehicleDataModel sharedDataModel) {
        this.sharedDataModel = sharedDataModel;
        // Reload workshop data when sharedDataModel is set
        if (this.sharedDataModel != null) {
            workshopsTableView.setItems(this.sharedDataModel.getWorkshops());
        }
    }

    private void initializeSearchFunctionality() {
        textFieldSearchName.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTable(newValue);
        });
    }

    private void filterTable(String searchQuery) {
        if (searchQuery.isEmpty()) {
            workshopsTableView.setItems(sharedDataModel.getWorkshops()); // Set all items if search is empty
        } else {
            ObservableList<Workshop> filteredList = FXCollections.observableArrayList();
            for (Workshop workshop : sharedDataModel.getWorkshops()) {
                if (workshop.getName().toLowerCase().contains(searchQuery.toLowerCase())) {
                    filteredList.add(workshop);
                }
            }
            workshopsTableView.setItems(filteredList); // Set filtered items
        }
        workshopsTableView.refresh();
    }

    @FXML
    void handleButtonDisplayServiceHistoryWorkshopAction(ActionEvent event) {
        Workshop selectedWorkshop = workshopsTableView.getSelectionModel().getSelectedItem();
        if (selectedWorkshop != null) {
            Dialog<ButtonType> dialog = new Dialog<>();
            Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
            Image applicationIcon = new Image(getClass().getResourceAsStream("/vikingexpresslogo.PNG"));
            dialogStage.getIcons().add(applicationIcon);
            dialog.setTitle("Service History");
            dialog.setHeaderText("Service Activities for " + selectedWorkshop.getName());

            // Create a TextArea for service activity details
            TextArea serviceActivitiesDetails = new TextArea();
            serviceActivitiesDetails.setEditable(false);
            serviceActivitiesDetails.setWrapText(true);

            // Build a string with details of each service activity
            StringBuilder details = new StringBuilder();
            for (ServiceActivity activity : selectedWorkshop.getServiceActivitiesHandeled()) {
                details.append("Date of Service: ").append(activity.getDateOfService())
                        .append("\nDescription: ").append(activity.getServiceDescription())
                        .append("\nCost: ").append(activity.getServiceCost())
                        .append("\nStatus: ").append(activity.getServiceStatus())
                        .append("\nVehicle: ").append(activity.getServiceForVehicle().getName())
                        .append("\nParts Replaced: ").append(activity.getPartsReplaced())
                        .append("\n\n");
            }

            // Set text in TextArea
            serviceActivitiesDetails.setText(details.toString());

            // Add TextArea to dialog
            dialog.getDialogPane().setContent(serviceActivitiesDetails);

            // Add a close button
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

            // Show the dialog
            dialog.showAndWait();
        } else {
            System.err.println("No workshop selected.");
            
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
