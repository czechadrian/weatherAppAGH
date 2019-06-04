package com.agh.weather.app;

import com.agh.weather.app.api.models.WeatherManager;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage stage) {
        WeatherManager weatherManager = new WeatherManager("Cracow");
        weatherManager.getWeather();

        Scene scene = new Scene(new Group(), 1250, 650);
        stage.setTitle("Weather App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
