package com.agh.weather.app.controllers;

import com.agh.weather.app.api.models.ImageHandler;
import com.agh.weather.app.api.models.WeatherManager;
import com.agh.weather.app.connection.ConnectionClass;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private WeatherManager weatherManager;
    private String citySet;

    @FXML
    public ImageView img;
    @FXML
    public JFXButton change;
    public JFXButton set;
    public JFXButton cancel;
    @FXML
    private JFXTextField cityName;
    public JFXTextField invis;
    @FXML
    private Label city;
    public Label temperature;
    public Label day;
    public Label desc;
    public Label errors;
    public Label windSpeed;
    public Label cloudiness;
    public Label pressure;
    public Label humidity;

    //Constructor to set the initial city to Kraków
    public Controller() {
        this.citySet = "Kraków".toUpperCase();
    }

    //Event Handler for each button
    @FXML
    private void handleButtonClicks(javafx.event.ActionEvent ae) {
        String initialCity = city.getText(); //stores the last searched city-name


        if (ae.getSource() == change) {
            cityName.setText("");
            bottomSet(true);
            cityName.requestFocus();
        } else if (ae.getSource() == set) {
            setPressed();
        } else if (ae.getSource() == cancel) {
            cityName.setText(initialCity);
            bottomSet(false);
            invis.requestFocus();
        }
    }

    //method to clear all the fields
    private void reset() {
        bottomSet(false);
        day.setText("");
        temperature.setText("");
        desc.setText("");
        windSpeed.setText("");
        cloudiness.setText("");
        pressure.setText("");
        humidity.setText("");
        img.setImage(null);
    }

    //method to set the new entered city
    private void setPressed() {

        //if user enters nothing into cityName field
        if (cityName.getText().equals("")) {
            showToast("City Name cannot be blank");
            return;
        } else {
            try {
                errors.setText("");
                this.citySet = cityName.getText().trim();
                cityName.setText((cityName.getText().trim()).toUpperCase());
                weatherManager = new WeatherManager(citySet);

                showWeather();
                insertToDatabase();
                addSuffix();

                bottomSet(false);
                invis.requestFocus();


            } catch (Exception e) {
                city.setText("Error!!");
                city.setTextFill(Color.TOMATO);
                showToast("City with the given name was not found.");
                reset();
                invis.requestFocus();
            }
        }
    }

    //method to handle nodes at botton part of the scene
    private void bottomSet(boolean statement) {
        cityName.setDisable(!statement);
        set.setVisible(statement);
        change.setVisible(!statement);
        cancel.setVisible(statement);
    }

    //method to show error messages
    private void showToast(String message) {
        errors.setText(message);
        errors.setTextFill(Color.TOMATO);
        errors.setStyle("-fx-background-color: #fff; -fx-background-radius: 50px;");

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), errors);
        fadeIn.setToValue(1);
        fadeIn.setFromValue(0);
        fadeIn.play();

        fadeIn.setOnFinished(event -> {
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.play();
            pause.setOnFinished(event2 -> {
                FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), errors);
                fadeOut.setToValue(0);
                fadeOut.setFromValue(1);
                fadeOut.play();
            });
        });
    }

    //actual method to call and get the weather and populate the scene
    private void showWeather() {
        weatherManager.getWeather();
        city.setText(weatherManager.getCity());
        temperature.setText(weatherManager.getTemperature().toString());
        day.setText(weatherManager.getDay().toUpperCase());
        desc.setText(weatherManager.getDescription().toUpperCase());
        img.setImage(new Image(ImageHandler.getImage(weatherManager.getIcon())));
        windSpeed.setText(weatherManager.getWindSpeed());
        cloudiness.setText(weatherManager.getCloudiness());
        pressure.setText(weatherManager.getPressure());
        humidity.setText(weatherManager.getHumidity());
    }

    private void addSuffix() {
        city.setText(weatherManager.getCity().toUpperCase());
        temperature.setText(weatherManager.getTemperature().toString() + "°C");
        windSpeed.setText(weatherManager.getWindSpeed() + " m/s");
        cloudiness.setText(weatherManager.getCloudiness() + "%");
        pressure.setText(weatherManager.getPressure() + " hpa");
        humidity.setText(weatherManager.getHumidity() + "%");
    }

    public void initialize(URL location, ResourceBundle resources) {
        cityName.setText(citySet);
        cityName.setDisable(true);
        set.setVisible(false);
        cancel.setVisible(false);
        errors.setText("");
        weatherManager = new WeatherManager(citySet);
        invis.requestFocus();

        //try catch block to see if there is internet and disabling ecery field
        try {
            showWeather();
            addSuffix();
        } catch (Exception e) {
            city.setText("Error!! - No Internet");
            city.setTextFill(Color.TOMATO);
            showToast("Internet Down. Please Connect");
            reset();
            change.setDisable(true);
            cityName.setText("");
        }

        //Set the city entered into textField on pressing enter on Keyboard
        cityName.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                setPressed();
            }
        });
    }

    public void insertToDatabase() {
        ConnectionClass connectionClass=new ConnectionClass();
        Connection connection=connectionClass.getConnection();

        try {
            Statement statement = connection.createStatement();

            String sql = "INSERT INTO WEATHER (date, time, city, temperature, wind, cloudiness, pressure, HUMIDITY) VALUES " +
                    "( CURRENT_DATE(), CURRENT_TIME(), \"" +
                    city.getText() + "\", " +
                    temperature.getText() + ", " +
                    windSpeed.getText() + ", " +
                    cloudiness.getText() + ", " +
                    pressure.getText() + ", " +
                    humidity.getText() + ")";

            statement.executeUpdate(sql);

            sql = "SELECT * FROM WEATHER";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                if ( resultSet.isLast() ) {
                    System.out.print("Date:" + resultSet.getString(2));
                    System.out.print(" Time:" + resultSet.getString(3));
                    System.out.print(" City:" + resultSet.getString(4));
                    System.out.print(" Temperature:" + resultSet.getString(5) + "°C");
                    System.out.print(" Wind Speed:" + resultSet.getString(6) + "m/s");
                    System.out.print(" Cloudiness:" + resultSet.getString(7) + "%");
                    System.out.print(" Pressure:" + resultSet.getString(8) + "hpa");
                    System.out.print(" Humidity:" + resultSet.getString(9) + "%");
                    System.out.println();
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
