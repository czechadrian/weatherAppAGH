package com.agh.weather.app.connection;


import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionClass {
    public Connection connection;
    public  Connection getConnection(){

        String userName="adminfranek";
        String password="korelacja";

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            connection= DriverManager.getConnection("jdbc:mysql://servicestationdbdev.czntefjg545i.eu-west-2.rds.amazonaws.com:3306/serviceStationDbDev",userName,password);


        } catch (Exception e) {
            e.printStackTrace();
        }


        return connection;
    }

}