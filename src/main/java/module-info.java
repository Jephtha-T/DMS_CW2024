module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires javafx.media;
    requires org.junit.jupiter.api;


    opens com.example.demo to javafx.fxml;
    opens com.example.demo.levels to javafx.fxml;
    opens com.example.demo.actors to javafx.fxml;
    opens com.example.demo.imagedisplay to javafx.fxml;
    opens com.example.demo.controller to javafx.fxml; // Allow access to the controller package if needed
    exports com.example.demo;
    exports com.example.demo.controller;
    exports com.example.demo.levels;
    exports com.example.demo.actors;
    exports com.example.demo.imagedisplay;
}