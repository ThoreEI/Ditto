package com.thore.bot.io;

import java.sql.*;
import java.util.Set;

public class DBHandler {
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/db/Ditto.sqlite";

    public DBHandler() {
        try {
            getConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public void createNewTable() {
        try {
            this.getConnection().createStatement().execute(
                    "CREATE TABLE IF NOT EXISTS users ("
                            + "	playerId integer PRIMARY KEY NOT NULL,"
                            + "	games integer default 0 NOT NULL"
                            + "	wins integer default 0 NOT NULL"
                            + "	defeats integer default 0 NOT NULL"
                            + "	points integer default 0 NOT NULL"
                            + ");"
            );
            // draws can be calculated by games - wins - defeats
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public boolean playerExists(int playerId) {
        try {
            ResultSet rs = this.getConnection().createStatement().executeQuery(
                    "SELECT playerId FROM users WHERE playerId = " + playerId + ";"
            );
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void insertNewPlayer(int playerId) {
        try {
            this.getConnection().createStatement().execute(
                    "INSERT INTO users (playerId) VALUES (" + playerId + ");"
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updatePlayerPoints(int playerId, int points) {
        try {
            this.getConnection().createStatement().execute(
                    "UPDATE users SET points = " + points + " WHERE playerId = " + playerId + ";"
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private void addGame(int playerId, String value) throws SQLException {
        if (Set.of("wins", "defeats").contains(value)){
            this.getConnection().createStatement().execute(
                    "UPDATE users SET " + value + " = " + value + " + 1, games = games +1 WHERE playerId = " + playerId + ";"
            );
        }
        else { // draw
            this.getConnection().createStatement().execute(
                    "UPDATE users SET games = games +1 WHERE playerId = " + playerId + ";"
            );
        }
    }
    public void addWin(int playerId) throws SQLException {
        addGame(playerId, "wins");
    }
    public void addDefeat(int playerId) throws SQLException {
        addGame(playerId, "defeats");
    }
    public void addDraw(int playerId) throws SQLException {
        addGame(playerId, "draws");
    }

    public static void main(String[] args) {
        DBHandler dbHandler = new DBHandler();
        dbHandler.createNewTable();
    }

}
