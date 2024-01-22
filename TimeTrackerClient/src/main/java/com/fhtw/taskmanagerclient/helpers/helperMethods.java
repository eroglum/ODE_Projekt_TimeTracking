package com.fhtw.taskmanagerclient.helpers;

import com.fhtw.taskmanagerclient.ClientApplication;
import com.fhtw.taskmanagerclient.controller.UpdatePasswordController;
import com.fhtw.taskmanagerclient.model.dto.AssociateDto;
import com.fhtw.taskmanagerclient.model.dto.GetAllAssociatesByManagerIdResponse;
import com.fhtw.taskmanagerclient.model.dto.TaskDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 A helper class that provides methods to facilitate different tasks.
 */
public class helperMethods {
    /**

     Creates an observable list of associateTaskListItem objects from a GetAllAssociatesByManagerIdResponse object.
     @param associates The GetAllAssociatesByManagerIdResponse object from which to create the observable list.
     @return An observable list of associateTaskListItem objects created from the associates in the GetAllAssociatesByManagerIdResponse object.
     */
    public static ObservableList<associateTaskListItem> associateTaskListFactory(GetAllAssociatesByManagerIdResponse associates){

        ObservableList<associateTaskListItem> list = FXCollections.observableArrayList();

        for (AssociateDto associate:associates.getAssociates()) {
            for (TaskDto task:associate.getTasks()) {
                list.add(new associateTaskListItem(
                        associate.getAssociateName(),
                        task.getEmployeeTask(),
                        task.getEmployeeDateFrom(),
                        task.getEmployeeHoursSpent()));
            }
        }

        return list;

    }
    /**
     * Shows a Java FX Alert Dialog with a given message that can be closed by clicking its "OK" button
     * @param message the message that the alert window will show
     * @throws IOException if an error occurs while communicating with the server.
     */
    public static void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            alert.close();
        }
    }
    /**

     This method opens a new view window in the application.
     @param viewName The name of the FXML file that represents the view.
     @param dialogTitle The title of the window.
     @param controller The controller class that handles the events of the view.
     @throws IOException if the FXML file cannot be found or loaded.
     */
    public static void openView(String viewName, String dialogTitle, Object controller) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource(viewName));
        fxmlLoader.setControllerFactory(controllerClass -> controller);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(dialogTitle);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }
    /**

     This method opens a new view window in the application.
     @param viewName The name of the FXML file that represents the view.
     @param dialogTitle The title of the window.
     @param controller The controller class that handles the events of the view.
     @param stage The stage of the view.
     @throws IOException if the FXML file cannot be found or loaded.
     */
    public static void openView(String viewName, String dialogTitle, Object controller, Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource(viewName));
        fxmlLoader.setControllerFactory(controllerClass -> controller);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(dialogTitle);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
