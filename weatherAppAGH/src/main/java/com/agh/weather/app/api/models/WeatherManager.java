package com.agh.weather.app.api.models;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class WeatherManager {
    private String city;
    private String day;
    private Integer temperature;
    private String description;

    public WeatherManager(String city) {
        this.city = city;
    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public void getWeather() {
        JSONObject json;
        JSONObject jsonSpecificData;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        Calendar c = Calendar.getInstance();

        try {
            json = readJsonFromUrl("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=6058c674105590adedec19807619c608&lang=eng&units=metric");
        } catch (IOException e) {
            return;
        }

        jsonSpecificData = json.getJSONObject("main");
        this.temperature = jsonSpecificData.getInt("temp");
        c.add(Calendar.DATE, 0);
        this.day = simpleDateFormat.format(c.getTime());

        jsonSpecificData = json.getJSONArray("weather").getJSONObject(0);
        this.description = jsonSpecificData.get("description").toString();
        System.out.println("temp: " + this.temperature + " day: " + this.day + " desc: " + this.description);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
