package com.JavaFinal.ToiletsFinder;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class ToiletUpdateUpload {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try (InputStream input = ToiletUpdateUpload.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find db.properties");
                return;
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
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

            // 创建数据库
            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS toilets";
            statement.executeUpdate(createDatabaseSQL);
            System.out.println("Database created successfully...");

            // 使用数据库
            String useDatabaseSQL = "USE toilets";
            statement.executeUpdate(useDatabaseSQL);
            System.out.println("Using database...");

            // 创建表
            String createTableSQL = "CREATE TABLE IF NOT EXISTS locations ("
                    + "id INT PRIMARY KEY AUTO_INCREMENT, "
                    + "longitude DOUBLE, "
                    + "latitude DOUBLE, "
                    + "name VARCHAR(255), "
                    + "comment VARCHAR(255), "
                    + "isFree BOOLEAN, "
                    + "floor INT, "
                    + "accessibility BOOLEAN, "
                    + "isGenderFriendly BOOLEAN, "
                    + "isDisabledFriendly BOOLEAN"
                    + ")";

            statement.executeUpdate(createTableSQL);
            System.out.println("Table created successfully...");

            // 插入数据（示例）
            String insertDataSQL = "INSERT INTO locations (longitude, latitude, name, comment, isFree, floor, accessibility, isGenderFriendly, isDisabledFriendly) "
                    + "VALUES (121.5, 31.2, 'test', 'test', true, 1, true, true, true)";
            statement.executeUpdate(insertDataSQL);
            System.out.println("Data inserted successfully...");


            //display data
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

                System.out.printf("ID: %d, Longitude: %f, Latitude: %f, Name: %s, Comment: %s, Is Free: %b, Floor: %d, Accessibility: %b, Is Gender Friendly: %b, Is Disabled Friendly: %b%n",
                        id, longitude, latitude, name, comment, isFree, floor, accessibility, isGenderFriendly, isDisabledFriendly);
            }
            System.out.println("Data displayed successfully...");



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
    }

//    public static void update (int id, Location locatino) {
//        String updateDataSQL = "UPDATE locations SET longitude = ?, latitude = ?, name = ?, comment = ?, isFree = ?, floor = ?, accessibility = ?, isGenderFriendly = ?, isDisabledFriendly = ? WHERE id = ?";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(updateDataSQL)) {
//            preparedStatement.setDouble(1, location.getLongitude());
//            preparedStatement.setDouble(2, location.getLatitude());
//            preparedStatement.setString(3, location.getName());
//            preparedStatement.setString(4, location.getComment());
//            preparedStatement.setBoolean(5, location.isFree());
//            preparedStatement.setInt(6, location.getFloor());
//            preparedStatement.setBoolean(7, location.isAccessibility());
//            preparedStatement.setBoolean(8, location.isGenderFriendly());
//            preparedStatement.setBoolean(9, location.isDisabledFriendly());
//            preparedStatement.setInt(10, id);
//
//            preparedStatement.executeUpdate();
//            System.out.println("Data updated successfully...");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void upload (int id, Location location) {
//        String uploadDataSQL = "INSERT INTO locations (longitude, latitude, name, comment, isFree, floor, accessibility, isGenderFriendly, isDisabledFriendly) "
//                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(uploadDataSQL)) {
//            preparedStatement.setDouble(1, location.getLongitude());
//            preparedStatement.setDouble(2, location.getLatitude());
//            preparedStatement.setString(3, location.getName());
//            preparedStatement.setString(4, location.getComment());
//            preparedStatement.setBoolean(5, location.isFree());
//            preparedStatement.setInt(6, location.getFloor());
//            preparedStatement.setBoolean(7, location.isAccessibility());
//            preparedStatement.setBoolean(8, location.isGenderFriendly());
//            preparedStatement.setBoolean(9, location.isDisabledFriendly());
//
//            preparedStatement.executeUpdate();
//            System.out.println("Data uploaded successfully...");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void delete (int id) {
//        String deleteDataSQL = "DELETE FROM locations WHERE id = ?";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteDataSQL)) {
//            preparedStatement.setInt(1, id);
//
//            preparedStatement.executeUpdate();
//            System.out.println("Data deleted successfully...");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

}
