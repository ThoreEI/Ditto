package com.thore.bot.io.database;
import java.sql.*;
import java.util.Set;

public class DBHandler {
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/database/playerStats.sqlite";

    public DBHandler() throws SQLException {
        createNewTable();
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private void createNewTable() throws SQLException {
        this.getConnection().createStatement().execute(
                "CREATE TABLE IF NOT EXISTS t_player ("
                        + "playerID integer PRIMARY KEY NOT NULL,"
                        + "coins INTEGER DEFAULT 0 NOT NULL,"
                        + "games INTEGER DEFAULT 0 NOT NULL,"
                        + "wins INTEGER DEFAULT 0 NOT NULL,"
                        + "pushes INTEGER DEFAULT 0 NOT NULL,"
                        + "defeats INTEGER DEFAULT 0 NOT NULL"
                        + ");"
        );
        // draws can be calculated by games - wins - defeats
    }

    public boolean playerExists(int playerID) throws SQLException {
        ResultSet rs = this.getConnection().createStatement().executeQuery(
                "SELECT playerID FROM t_player WHERE playerID = " + playerID + ";"
        );
        return rs.next();
    }

    public void insertNewPlayer(int playerID) throws SQLException {
        this.getConnection().createStatement().execute(
                "INSERT INTO t_player (playerID) VALUES (" + playerID + ");"
        );
    }

    public void updatePlayerPoints(int playerID, int points) throws SQLException {
        this.getConnection().createStatement().execute(
                "UPDATE t_player SET points = " + points + " WHERE playerID = " + playerID + ";"
        );
    }

    private void addGame(int playerID, String value) throws SQLException {
        if (Set.of("wins", "defeats").contains(value)) {
            this.getConnection().createStatement().execute(
                    "UPDATE t_player SET " + value + " = " + value + " + 1, games = games +1 WHERE playerID= " + playerID + ";"
            );
        } else { // draw
            this.getConnection().createStatement().execute(
                    "UPDATE t_player SET games=games+1 WHERE playerID = " + playerID + ";"
            );
        }
    }

    public void addWin(int playerID) throws SQLException {
        addGame(playerID, "wins");
    }

    public void addDefeat(int playerID) throws SQLException {
        addGame(playerID, "defeats");
    }

    public void addPush(int playerID) throws SQLException {
        addGame(playerID, "push's");
    }

    public static void main(String[] args) throws SQLException {
        new DBHandler();
    }
}
