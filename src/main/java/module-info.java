module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires javafx.media;


    opens com.example.demo to javafx.fxml;
    opens com.example.demo.controller to javafx.fxml; // Allow access to the controller package if needed
    exports com.example.demo;
    exports com.example.demo.controller;
    opens com.example.demo.LevelControl to javafx.fxml;
    opens com.example.demo.Actors to javafx.fxml;
    opens com.example.demo.ImageDisplays to javafx.fxml;
}