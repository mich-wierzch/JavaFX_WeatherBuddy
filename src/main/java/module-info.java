module com.example.javafx_weatherbuddy {
    requires javafx.controls;
    requires javafx.fxml;
    requires openweathermap.api;
    requires json.simple;



    opens com.example.javafx_weatherbuddy to javafx.fxml;
    exports com.example.javafx_weatherbuddy;
}