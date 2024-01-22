package com.fhtw.taskmanagerclient.controller;

import com.fhtw.taskmanagerclient.helpers.DateTimeHelper;
import com.fhtw.taskmanagerclient.helpers.associateTaskListItem;
import com.fhtw.taskmanagerclient.helpers.helperMethods;
import com.fhtw.taskmanagerclient.model.Client;
import com.fhtw.taskmanagerclient.model.dto.GetAllAssociatesByManagerIdResponse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

import static com.fhtw.taskmanagerclient.helpers.helperMethods.openView;
import static com.fhtw.taskmanagerclient.helpers.helperMethods.showAlert;

/**
 * The ManagerPanelController class is the controller for the Manager Panel view.
 * It is responsible for handling the events and logic of the view and communicating with the client model.
 *
 */
public class ManagerPanelController implements Initializable {
    /**
     * The entriesList variable is an observable list that holds the tasks of the associates
     */
    private ObservableList<associateTaskListItem> entriesList = FXCollections.observableArrayList();
    /**
     * The client object used to communicate with the server.
     */
    private Client client;

    /**
     * The employees variable holds the response from the getAllAssociatesByManagerId method
     */
    private GetAllAssociatesByManagerIdResponse employees;

    @FXML private TableColumn<associateTaskListItem, String> employeeTaskColumn;
    @FXML private TableColumn<associateTaskListItem, String> employeeDateFromColumn;
    @FXML private TableColumn<associateTaskListItem, Double> employeeHoursSpentColumn;
    @FXML private TableColumn<associateTaskListItem, String> employeeNameTableColumn;
    /**
     * The table view object, representing the table where entries are displayed.
     */
    @FXML private TableView<associateTaskListItem> entriesTableView;
    @FXML private Button updateEntriesButton;
    @FXML private Button updatePasswordButton;

    /**
     * The current state start date. Initializes with the first date of this week.
     */
    String currentStartDate = DateTimeHelper.getWeekStartDate();
    /**
     * The current state end date. Initializes with the last date of this week.
     */
    String currentEndDate = DateTimeHelper.getWeekEndDate();

    public ManagerPanelController(Client client) {
        this.client = client;
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded. It sets up the TableView with the
     * appropriate columns and calls the updateEntriesList method to populate
     * the TableView with data.
     *
     * @param location
     *            the location used to resolve relative paths for the root
     *            object, or null if the location is not known
     * @param resources
     *            the resources used to localize the root object, or null if
     *            the root object was not localized
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateEntriesList();
        employeeNameTableColumn.setCellValueFactory(new PropertyValueFactory<associateTaskListItem, String>("associateName"));
        employeeTaskColumn.setCellValueFactory(new PropertyValueFactory<associateTaskListItem, String>("employeeTask"));
        employeeDateFromColumn.setCellValueFactory(new PropertyValueFactory<associateTaskListItem, String>("employeeDateFrom"));
        employeeHoursSpentColumn.setCellValueFactory(new PropertyValueFactory<associateTaskListItem, Double>("employeeHoursSpent"));
        this.entriesTableView.setItems(entriesList);
    }
    /**
     * This method is called when the update password button is clicked. It opens a new stage
     * for the user to update their password

     * @throws IOException
     *            if there is an error loading the update password view
     */
    @FXML
    void onUpdatePasswordButtonClick() throws IOException {
        //passing stage to make the dialog self-closeable
        Stage stage = new Stage();
        openView("update_password-view.fxml", "Update Password", new UpdatePasswordController(client, stage), stage);
    }
    /**
     * This method is called when the update entries button is clicked.
     * It updates the entriesList field and populates the TableView with the data
     * If the request fails, it shows a warning dialog.
     */
    @FXML
    private void updateEntriesList() {
        try {
            employees = client.getAllManagersEmployeesTasks();
            if(!employees.isRequestSucceeded()){
                showAlert("Failed to get employee information.");
                return;
            }
            entriesList = helperMethods.associateTaskListFactory(employees);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }



}
