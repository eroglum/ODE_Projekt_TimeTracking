package com.fhtw.taskmanagerclient.controller;

import com.fhtw.taskmanagerclient.model.Client;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static com.fhtw.taskmanagerclient.helpers.helperMethods.showAlert;

/**
 * The controller class for the update password view.
 */
public class UpdatePasswordController {
    /**
     * The client object used to communicate with the server.
     */
    Client client;
    Stage stage;
    @FXML private TextField newPasswordTextField;
    @FXML private TextField repeatNewPasswordTextField;
    @FXML private Button submitButton;
    /**
     * The constructor for the update password controller
     * @param client the client object used to communicate with the server
     * @param stage the stage of the update password view
     */
    public UpdatePasswordController(Client client, Stage stage){
        this.client = client;
        this.stage = stage;
    }

    /**
     * Handles the click event for the submit button.
     * It sends the new password to the server and closes the update password window.
     * If passwords do not match or a server error occurs, an alert message is shown with a warning message.
     * @param event the event that triggered the method.
     * @throws IOException if an error occurs while communicating with the server.
     */
    @FXML
    void onSubmitButtonClick(ActionEvent event) throws IOException {
        if (newPasswordTextField.getText()==null||repeatNewPasswordTextField.getText()==null){
            showAlert("Passwords fields must not be empty!");
        }
        if(this.client.updatePassword(newPasswordTextField.getText(), repeatNewPasswordTextField.getText()))
        {
            stage.close();
            return;
        }
            showAlert("Passwords do not match or Server is down! \n Try Again!");
    }

}