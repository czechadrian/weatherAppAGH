package com.agh.weather.app.controllers;

import com.agh.weather.app.api.models.WeatherManager;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private WeatherManager weatherManager;
    private String citySet;
    private Label city;
    private Label temperature;
    private Label day;

    public Controller() {
        this.citySet = "Pune".toUpperCase();
    }

    private void showWeather() {
        weatherManager.getWeather();
        city.setText(weatherManager.getCity().toUpperCase());
        temperature.setText(weatherManager.getTemperature().toString() + "*C");
        day.setText(weatherManager.getDay().toUpperCase());
    }

    public void initialize(URL location, ResourceBundle resources) {

        weatherManager = new WeatherManager(citySet);

        try {
            showWeather();
        } catch (Exception e) {
            city.setText("Error!! - No Internet");
            city.setTextFill(Color.TOMATO);
        }
    }
}
