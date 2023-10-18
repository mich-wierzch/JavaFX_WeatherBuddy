package com.example.javafx_weatherbuddy;

import com.github.prominence.openweathermap.api.exception.NoDataFoundException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class Controller {

    @FXML
    private TextField searchTextField;
    @FXML
    private Label tempText;
    @FXML
    private ImageView weatherConditionImage;
    @FXML
    private Label weatherCondDescription;
    @FXML
    private Label locationText;
    @FXML
    private Label humidityValue;
    @FXML
    private Label windspeedValue;
    @FXML
    private Label pressureValue;
    @FXML
    private Label errorText;
    @FXML
    private Label apiFailureText;
    @FXML
    private Label noDataFoundText;
    @FXML
    private Label clockValue;
    private final WeatherApi weatherApi = new WeatherApi();
    private final InformationSceneController informationSceneController = new InformationSceneController();
    @FXML
    protected void onSearchButtonClick(ActionEvent actionEvent){
        try {
            errorText.setVisible(false);
            apiFailureText.setVisible(false);
            noDataFoundText.setVisible(false);


            String locationInput = searchTextField.getText();
            searchTextField.clear();

            if (locationInput.replaceAll("\\s", "").length() <= 0 || locationInput.equals("")) {
                errorText.setVisible(true);
                return;
            }

            weatherApi.extractWeatherData(locationInput);


            clockValue.setText(weatherApi.getCurrentTime(locationInput));

            if (!String.valueOf(weatherApi.getResponseStatus()).equals("200")) {
                apiFailureText.setVisible(true);
                return;
            }

            String weatherCondition = weatherApi.getWeatherCondition();

            switch (weatherCondition) {
                case "Thunderstorm" -> weatherConditionImage.setImage(new Image("thunderstormIcon.png"));
                case "Drizzle" -> weatherConditionImage.setImage(new Image("drizzleIcon.png"));
                case "Rain" -> weatherConditionImage.setImage(new Image("rainIcon.png"));
                case "Snow" -> weatherConditionImage.setImage(new Image("snowIcon.png"));
                case "Mist", "Sand", "Smoke", "Haze", "Dust",
                        "Fog", "Ash", "Squall" -> weatherConditionImage.setImage(new Image("mistIcon.png"));
                case "Tornado" -> weatherConditionImage.setImage(new Image("tornadoIcon.png"));
                case "Clear" -> weatherConditionImage.setImage(new Image("clearskyIcon.png"));
                case "Clouds" -> weatherConditionImage.setImage(new Image("cloudyWeatherIcon.png"));
            }

            tempText.setText(String.valueOf(weatherApi.getTemperature()).substring(0, 4) + " C");
            tempText.setText(Math.round(weatherApi.getTemperature() * 10) / 10.0 + " C");
            weatherCondDescription.setText(weatherCondition);
            locationText.setText(weatherApi.getCity());
            humidityValue.setText(weatherApi.getHumidity() + "%");
            windspeedValue.setText(weatherApi.getWindspeed() + "km/h");
            pressureValue.setText(weatherApi.getPressure() + " hPa");
        } catch (NoDataFoundException e){
            noDataFoundText.setVisible(true);
        }
    }

    public void onInformationButtonClick(ActionEvent actionEvent) throws IOException {
        informationSceneController.showInformationScene(actionEvent);
    }

}