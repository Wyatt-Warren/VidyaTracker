module com.example.vidyatracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens com.example.vidyatracker11 to javafx.fxml;
    exports com.example.vidyatracker11;
}