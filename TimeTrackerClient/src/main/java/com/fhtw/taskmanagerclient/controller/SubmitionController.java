package com.fhtw.taskmanagerclient.controller;

import com.fhtw.taskmanagerclient.model.Client;
import com.fhtw.taskmanagerclient.model.dto.TaskDto;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.text.ParseException;

import static com.fhtw.taskmanagerclient.helpers.helperMethods.showAlert;


/**
 * The controller class for the submission view.
 */
public class SubmitionController{
    /**
     * The client object used to communicate with the server.
     */
    private Client client;
    private Stage stage;
    @FXML
    private TextField durationTextField;
    @FXML
    private DatePicker taskDateDatePicker;
    @FXML
    private TextField taskTextField;

    /**
     * The constructor for the submission controller
     * @param client the client object used to communicate with the server
     * @param stage the stage of the submission view
     */
    public SubmitionController(Client client, Stage stage) {
        this.client = client;
        this.stage = stage;
    }

/**
 * Handles the click event for the submit button.
 * It sends the new task to the server and closes the submission window.
 * If any NumberFormatException is caught, it shows an alert message asking the user to enter a valid number of hours.
 * @throws IOException if an error occurs while communicating with the server.
 * @throws ParseException if the task date cannot be parsed.
 */
    @FXML
    protected void onSubmitButtonClick() throws IOException, ParseException {
        try {
            TaskDto task = new TaskDto(
                    taskTextField.getText(),
                    taskDateDatePicker.getValue().toString(),
                    Double.parseDouble(durationTextField.getText()));
            if(!this.client.addTask(task)){
                showAlert("Server Error!");
                return;
            }
            stage.close();
        }catch(NumberFormatException|NullPointerException n){
            showAlert("Please enter a valid number of hours!");
        }
    }
}
