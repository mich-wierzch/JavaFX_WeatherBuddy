package com.example.javafx_weatherbuddy;

import com.github.prominence.openweathermap.api.exception.NoDataFoundException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ForecastSceneController {
    @FXML
    private TextField searchTextField;
    @FXML
    private TextArea forecastValue;


    private final WeatherApi weatherApi = new WeatherApi();
    public void showForecastScene(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("forecastScene.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add("application.css");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void onSearchButtonClick(ActionEvent actionEvent) throws IOException{
        try {
            String locationInput = searchTextField.getText();

            if (locationInput.replaceAll("\\s", "").length() <= 0 || locationInput.equals("")) {
                forecastValue.setText("Please enter correct location");
                return;
            }


            ArrayList<String> weatherForecast = weatherApi.getFiveDayForecast(searchTextField.getText());



            forecastValue.setText(String.join("\n\n", weatherForecast));
        } catch (NoDataFoundException e){
            forecastValue.setText("No data available");
        }

    }
    public void onGoBackButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("application.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add("application.css");
        stage.setScene(scene);
        stage.show();
    }


}
