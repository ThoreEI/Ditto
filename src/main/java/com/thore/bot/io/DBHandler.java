package com.thore.bot.io;

import java.sql.*;
import java.util.Set;

public class DBHandler {
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/db/Ditto.sqlite";

    public DBHandler() throws SQLException {
        createNewTable();
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private void createNewTable() throws SQLException {
        this.getConnection().createStatement().execute(
                "CREATE TABLE IF NOT EXISTS users ("
                        + "playerId integer PRIMARY KEY NOT NULL,"
                        + "games integer default 0 NOT NULL"
                        + "wins integer default 0 NOT NULL"
                        + "	defeats integer default 0 NOT NULL"
                        + "points integer default 0 NOT NULL"
                        + ");"
        );
        // draws can be calculated by games - wins - defeats
    }

    public boolean playerExists(int playerId) throws SQLException {
        ResultSet rs = this.getConnection().createStatement().executeQuery(
                "SELECT playerId FROM users WHERE playerId = " + playerId + ";"
        );
        return rs.next();
    }

    public void insertNewPlayer(int playerId) throws SQLException {
        this.getConnection().createStatement().execute(
                "INSERT INTO users (playerId) VALUES (" + playerId + ");"
        );
    }

    public void updatePlayerPoints(int playerId, int points) throws SQLException {
        this.getConnection().createStatement().execute(
                "UPDATE users SET points = " + points + " WHERE playerId = " + playerId + ";"
        );
    }

    private void addGame(int playerId, String value) throws SQLException {
        if (Set.of("wins", "defeats").contains(value)) {
            this.getConnection().createStatement().execute(
                    "UPDATE users SET " + value + " = " + value + " + 1, games = games +1 WHERE playerId = " + playerId + ";"
            );
        } else { // draw
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

}
