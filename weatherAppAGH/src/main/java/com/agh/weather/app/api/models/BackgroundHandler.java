package com.agh.weather.app.api.models;

public class BackgroundHandler {

    public static String getImage(String icon) {
        switch (icon) {
            case "01d":
                return "/images/clearsky.jpg";
            case "01n":
                return "/images/clearskynight.jpeg";
            case "02d":
                return "/images/fewclouds.jpeg";
            case "02n":
                return "/images/fewcloudsnight.jpg";
            case "03d":
                return "/images/scatteredclouds.jpg";
            case "03n":
                return "/images/scatteredcloudsnight.jpg";
            case "04d":
                return "/images/brokenclouds.png";
            case "04n":
                return "/images/brokencloudsnight.jpg";
            case "09d":
            case "10d":
                return "/images/rain.jpg";
            case "09n":
            case "10n":
                return "/images/rainnight.jpg";
            case "11n":
            case "11d":
                return "/images/thunderstorm.jpg";
            case "13d":
            case "13n":
                return "/images/snow.jpeg";
            case "50d":
                return "/images/fog.jpeg";
            case "50n":
                return "/images/mistnight.jpeg";
        }
        return "/images/bg.jpg";
    }
}
