package com.JavaFinal.ToiletsFinder;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SQLOperatrions {
    public static List<Location> GetAll() {
        Properties properties = new Properties();
        try (InputStream input = ToiletUpdateUpload.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find db.properties");
                return null;
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        String jdbcUrl = properties.getProperty("jdbc.url");
        String user = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");

        Connection connection = null;
        Statement statement = null;

        try {
            // 加载JDBC驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 建立连接
            connection = DriverManager.getConnection(jdbcUrl, user, password);
            statement = connection.createStatement();

            // 使用数据库
            String useDatabaseSQL = "USE toilets";
            statement.executeUpdate(useDatabaseSQL);
            System.out.println("Using database...");


            //Extract All Data
            List<Location> retLocations = new ArrayList<>();
            String selectDataSQL = "SELECT * FROM locations";
            ResultSet resultSet = statement.executeQuery(selectDataSQL);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                double longitude = resultSet.getDouble("longitude");
                double latitude = resultSet.getDouble("latitude");
                String name = resultSet.getString("name");
                String comment = resultSet.getString("comment");
                boolean isFree = resultSet.getBoolean("isFree");
                int floor = resultSet.getInt("floor");
                boolean accessibility = resultSet.getBoolean("accessibility");
                boolean isGenderFriendly = resultSet.getBoolean("isGenderFriendly");
                boolean isDisabledFriendly = resultSet.getBoolean("isDisabledFriendly");

                Location l = new Location(longitude, latitude, name, comment, isFree, floor, accessibility, isGenderFriendly, isDisabledFriendly);
                retLocations.add(l);
            }
            return retLocations;



        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return null;
    }
}
