package com.fhtw.taskmanagerclient.controller;

import com.fhtw.taskmanagerclient.ClientApplication;
import com.fhtw.taskmanagerclient.helpers.DateTimeHelper;
import com.fhtw.taskmanagerclient.model.Client;
import com.fhtw.taskmanagerclient.model.dto.GetAssociateTasksResponse;
import com.fhtw.taskmanagerclient.model.dto.TaskDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

import static com.fhtw.taskmanagerclient.helpers.helperMethods.openView;

/**
 * The controller for the task manager view.
 * It handles the interaction between the view and the model.
 * It implements the Initializable interface to initialize the client object
 * and handle the events for the buttons and date picker.
 */
public class TaskManagerController implements Initializable {

    private Client client;
    @FXML private Button newEntryButton;
    @FXML private TableView<TaskDto> entriesTableView;
    @FXML private TableColumn<TaskDto, String> employeeTaskColumn;
    @FXML private TableColumn<TaskDto, String> employeeDateFromColumn;
    @FXML private TableColumn<TaskDto, Double> employeeHoursSpentColumn;
    @FXML private Button monthlyViewButton;
    @FXML private Button weeklyViewButton;

    /**
     * The date picker for the update entries single-date server request.
     */
    @FXML private DatePicker dateViewDatePicker;
    @FXML private Button updatePasswordButton;
    /**
     * The tasks response from the server.
     */
    private GetAssociateTasksResponse tasks;

    /**
     * The observable list for the entries of the TableView.
     */
    private ObservableList<TaskDto> entriesList = FXCollections.observableArrayList();
    /**
     * The current state start date.
     */
    String currentStartDate = DateTimeHelper.getWeekStartDate();
    /**
     * The current state end date.
     */
    String currentEndDate = DateTimeHelper.getWeekEndDate();

    /**
     * The constructor of the controller that set the client field
     * @param client
     */
    public TaskManagerController(Client client) {
        this.client = client;
    }
    /**
     * Handles the click event for the new entry button.
     * It opens a new window for the new entry submission.
     * @param event the click event.
     * @throws IOException if the fxml file couldn't be loaded.
     */
    @FXML
    void onNewEntryButtonClick(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setOnHidden(e->{this.updateEntriesList();});
        openView("new_entry-view.fxml", "New Entry Submission", new SubmitionController(client, stage), stage);
    }
    /**
     * Handles the click event for the update password button.
     * It opens a new window for the update password.
     * @throws IOException if the fxml file couldn't be loaded.
     */

    @FXML
    void onUpdatePasswordButtonClick() throws IOException {
        //passing stage to make the dialog self-closeable
        Stage stage = new Stage();
        openView("update_password-view.fxml", "Update Password", new UpdatePasswordController(client, stage), stage);
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * Sets up the correct values to the column objects of the "entriesTableView" object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateEntriesList();
        employeeTaskColumn.setCellValueFactory(new PropertyValueFactory<TaskDto, String>("employeeTask"));
        employeeDateFromColumn.setCellValueFactory(new PropertyValueFactory<TaskDto, String>("employeeDateFrom"));
        employeeHoursSpentColumn.setCellValueFactory(new PropertyValueFactory<TaskDto, Double>("employeeHoursSpent"));
        this.entriesTableView.setItems(entriesList);
    }

    /**
     * Fetches the tasks from the server for the given start and end date and updates the entries list.
     */
    private void updateEntriesList(){
        try {
            tasks = client.getTasksInAnInterval(currentStartDate, currentEndDate);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        try {
            entriesList.setAll(tasks.getTasks());
        }catch (NullPointerException n){
            System.out.println("No Entries in this period");
        }

    }

    /**
     * Handles the change event for the date picker.
     * Updates the current start and end date and updates the entries list.
     */
    @FXML
    void onDateViewDatePickerChanged() {
        currentStartDate = DateTimeHelper.getFormattedDateString(dateViewDatePicker.getValue());
        currentEndDate = DateTimeHelper.getFormattedDateString(dateViewDatePicker.getValue());
        updateEntriesList();
    }
    /**
     * Handles the click event for the monthly view button.
     * It updates the current start and end date with the start and end of the current month
     * and updates the entries list.
     * @param event the click event.
     */
    @FXML
    void onMonthlyViewButtonButtonClick(ActionEvent event) {
        currentStartDate = DateTimeHelper.getMonthStartDate();
        currentEndDate = DateTimeHelper.getMonthEndDate();
        updateEntriesList();
    }

    /*@FXML
    void onDeleteTask(ActionEvent event) {
        TaskDto selectedTask = entriesTableView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            try {
                client.deleteTask(selectedTask.getTaskId());
                entriesList.remove(selectedTask);
            } catch (IOException e) {
                // Fehlerbehandlung
            }
        }
    }*/

    /**
     * Handles the click event for the weekly view button.
     * It updates the current start and end date with the start and end of the current week
     * and updates the entries list.
     * @param event the click event.
     */
    @FXML
    void onWeeklyViewButtonButtonClick(ActionEvent event) {
        currentStartDate = DateTimeHelper.getWeekStartDate();
        currentEndDate = DateTimeHelper.getWeekEndDate();
        updateEntriesList();
    }
}