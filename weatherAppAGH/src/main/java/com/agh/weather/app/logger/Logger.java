package com.agh.weather.app.logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static Logger instance;

    private Logger() {
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void info(String info) {
        System.out.println(getNow() + " INFO: " + info);
    }

    public void error(String error) {
        System.out.println(getNow() + " ERROR: " + error);
    }

    private String getNow() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return dateTime.format(format);
    }

}
