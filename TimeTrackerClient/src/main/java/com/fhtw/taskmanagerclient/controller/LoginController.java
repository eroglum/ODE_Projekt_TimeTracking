package com.fhtw.taskmanagerclient.controller;
import com.fhtw.taskmanagerclient.model.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import static com.fhtw.taskmanagerclient.helpers.helperMethods.openView;
import static com.fhtw.taskmanagerclient.helpers.helperMethods.showAlert;

/**
 * The controller for the login view. It handles the interaction between the view and the model.
 * It implements the signInButtonClick method to handle the sign-in button click event.
 */
public class LoginController{

    private Stage stage;
    private Client client;
    private String hostname = "localhost";
    private int port = 8888;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordPasswordField;
    @FXML
    private Button signInButton;

    /**
     * Creates a new instance of the login controller.
     *
     * @param stage the primary stage of the application.
     */

    public LoginController(Stage stage){
        this.stage = stage;
    }

    /**
     * Establishes a connection with the server and handles sign-in.
     * It checks if the connection and login are successful and based on the role of the user
     * it loads the appropriate view and sets it as the scene for the primary stage or shows a warning dialog in the case of an error.
     *
     * @param event the action event of the button click.
     * @throws IOException if there is a problem loading the fxml file.
     */
    @FXML
    void signInButtonClick(ActionEvent event) throws IOException {
        try {
            client =  new Client(this.hostname, this.port);
        } catch (IOException e) {
            showAlert("No server running on " + this.hostname + ": " + this.port);
            return;
        } catch (NumberFormatException e){
            showAlert("Port must be a whole number!");
            return;
        }
        if(client.login(usernameTextField.getText(), passwordPasswordField.getText()))
        {
            if(client.getUserRole().equals("manager")){
                openView("manager-view.fxml", "Manager Panel", new ManagerPanelController(this.client));
                this.stage.close();
                return;
            }
            openView("client-view.fxml", "Task Manager", new TaskManagerController(this.client));
            this.stage.close();
        }else{
            showAlert("Wrong Credentials! \n Try again! ");
        }

    }

}
