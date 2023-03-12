package com.thore.bot.database;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class PlayerStats {
    private static Connection connection;
    private static Statement statement;

    public PlayerStats(){
        setConnection();
        createTablePlayerStats();
    }

    public void setConnection() {
        connection = null;
        try {
            File database = new File("playerStats.db");
            if (!database.exists())
                if(!database.createNewFile())
                    System.err.println("Couldn't create a database file.");
            String url = "jdbc:sqlite:" + database.getPath();
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
        } catch (IOException e) {
            System.err.println("Database not found.");
        } catch (SQLException e) {
            System.err.println("An error occurred while connecting to the database.");
        }
    }

    public void disconnect() {
        if (connection == null)
            return;
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("An error occurred while disconnecting from the database.");
        }
    }

    public static ResultSet onQuery(String sql) {
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static void onUpdate(String sql) {
        try {
            statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTablePlayerStats(){
        String sql = "CREATE TABLE IF NOT EXISTS t_playerStats(playerID INTEGER AUTO_INCREMENT PRIMARY KEY, name VARCHAR NOT NULL, wins INTEGER NOT NULL, pushes INTEGER NOT NULL, loses INTEGER NOT NULL);";
        onUpdate(sql);
    }
        public static void main(String[] args) {
        new PlayerStats();

    }
}
