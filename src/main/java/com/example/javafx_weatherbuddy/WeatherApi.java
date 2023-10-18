package com.example.javafx_weatherbuddy;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.weather.Weather;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class WeatherApi {


    private double temperature;
    private String city;
    private long humidity;
    private double windspeed;
    private long pressure;

    private String weatherCondition;


    private long responseStatus;




    private final static OpenWeatherMapClient openWeatherMapClient = new OpenWeatherMapClient("c3f43823b4b5a61f58dbf5e8008325e8");
    private JSONObject fetchCurrentWeatherData(String location){
        final String weatherData = openWeatherMapClient
                .currentWeather()
                .single()
                .byCityName(location)
                .language(Language.ENGLISH)
                .unitSystem(UnitSystem.METRIC)
                .retrieve()
                .asJSON();
        JSONParser parser = new JSONParser();
        JSONObject json;
        try {
            json = (JSONObject) parser.parse(weatherData);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        System.out.println(json);
        return json;
    }


    public void extractWeatherData(String location){
        JSONObject weatherData = fetchCurrentWeatherData(location);
        JSONObject mainObject = (JSONObject) weatherData.get("main");
        JSONObject windObject = (JSONObject) weatherData.get("wind");
        JSONArray weatherArray = (JSONArray) weatherData.get("weather");
        JSONObject weatherObject = (JSONObject) weatherArray.get(0);


        temperature = (double) mainObject.get("temp");
        city = (String) weatherData.get("name");
        humidity = (Long) mainObject.get("humidity");
        windspeed = (double) windObject.get("speed");
        pressure = (Long) mainObject.get("pressure");
        weatherCondition = (String) weatherObject.get("main");
        responseStatus = (Long) weatherData.get("cod");






    }

    public String getCurrentTime(String location){
        final Weather weather = openWeatherMapClient
                .currentWeather()
                .single()
                .byCityName(location)
                .language(Language.ENGLISH)
                .unitSystem(UnitSystem.METRIC)
                .retrieve()
                .asJava();

        String currentTime = String.valueOf(weather.getCalculationTime());
        return currentTime.substring(currentTime.indexOf('T')+ 1);
    }


    public double getTemperature() {
        return temperature;
    }

    public String getCity() {
        return city;
    }

    public long getHumidity() {
        return humidity;
    }

    public double getWindspeed() {
        return windspeed;
    }

    public long getPressure() {
        return pressure;
    }
    public String getWeatherCondition() {
        return weatherCondition;
    }
    public long getResponseStatus() {
        return responseStatus;
    }









}
