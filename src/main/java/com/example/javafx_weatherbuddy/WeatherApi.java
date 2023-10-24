package com.example.javafx_weatherbuddy;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.forecast.Forecast;
import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import com.github.prominence.openweathermap.api.model.weather.Weather;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Iterator;

public class WeatherApi {


    private Double temperature;
    private String city;
    private Long humidity;
    private Double windspeed;
    private Long pressure;

    private String weatherCondition;


    private Long responseStatus;




    private final static OpenWeatherMapClient openWeatherMapClient = new OpenWeatherMapClient("YOUR API KEY HERE");
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


        temperature = (Double) mainObject.get("temp");
        city = (String) weatherData.get("name");
        humidity = (Long) mainObject.get("humidity");

        Object windSpeedValue = windObject.get("speed");
        if (windSpeedValue instanceof Double){
            windspeed = (Double) windSpeedValue;
        } else if (windSpeedValue instanceof Long){
            windspeed = ((Long) windSpeedValue).doubleValue();
        }


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

    public ArrayList<String> getFiveDayForecast(String location){

        Forecast weather = openWeatherMapClient.forecast5Day3HourStep()
                .byCityName(location)
                .language(Language.ENGLISH)
                .unitSystem(UnitSystem.METRIC)
                .retrieve()
                .asJava();

        Iterator<WeatherForecast> it = weather.getWeatherForecasts().iterator();

        ArrayList<String> weatherForecast = new ArrayList<>();
        while(it.hasNext()){
            weatherForecast.add(it.next().getTemperature().toString() + " " + it.next().getForecastTimeISO());
        }
        return weatherForecast;



    }



    public Double getTemperature() {
        return temperature;
    }

    public String getCity() {
        return city;
    }

    public Long getHumidity() {
        return humidity;
    }

    public Double getWindspeed() {
        return windspeed;
    }

    public Long getPressure() {
        return pressure;
    }
    public String getWeatherCondition() {
        return weatherCondition;
    }
    public Long getResponseStatus() {
        return responseStatus;
    }









}
