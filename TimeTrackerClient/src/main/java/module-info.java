module com.fhtw.taskmanagerclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires xstream;


    opens com.fhtw.taskmanagerclient.controller to javafx.fxml;
    opens com.fhtw.taskmanagerclient.model.dto to xstream, javafx.base;
    opens com.fhtw.taskmanagerclient.helpers to javafx.base;
    exports com.fhtw.taskmanagerclient;
}