package com.thore.bot.io.database;
import net.dv8tion.jda.api.entities.User;

import java.sql.*;
import java.util.Set;

public class DatabaseHandler {
    private static final String DATABASE_URL = "jdbc:sqlite:src/main/resources/database/playerstats.sqlite";

    public DatabaseHandler() throws SQLException {
        createNewTable();
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    private void createNewTable() throws SQLException {
        this.getConnection().createStatement().execute(
                "CREATE TABLE IF NOT EXISTS t_blackJackPlayer ("
                        + "PlayerID BIGINT AUTO_INCREMENT PRIMARY KEY,"
                        + "Name VARCHAR (100) NOT NULL,"
                        + "Coins INTEGER DEFAULT 0 NOT NULL,"
                        + "Games INTEGER DEFAULT 0 NOT NULL,"
                        + "Wins INTEGER DEFAULT 0 NOT NULL,"
                        + "Pushes INTEGER DEFAULT 0 NOT NULL,"
                        + "Defeats INTEGER DEFAULT 0 NOT NULL"
                        + ");"
        );
        // games can be calculated by wins + pushes + defeats   // TODO
    }

    public boolean playerExists(long playerID) throws SQLException {
        ResultSet resultSet = this.getConnection().createStatement().executeQuery(
                "SELECT PlayerID FROM t_blackJackPlayer WHERE PlayerID = " + playerID + ";"
        );
        return resultSet.next();
    }

    public void insertNewPlayer(User player) throws SQLException {
        this.getConnection().createStatement().execute(
                "INSERT INTO t_blackJackPlayer (PlayerID, Name) VALUES (" + player.getIdLong() + ", " +player.getName()+");"
        );
    }

    public void updatePlayerPoints(int playerID, int points) throws SQLException {
        this.getConnection().createStatement().execute(
                "UPDATE t_player SET Coins = " + points + " WHERE PlayerID = " + playerID + ";"
        );
    }

    private void addGame(int playerID, String value) throws SQLException {
        if (Set.of("wins", "defeats").contains(value)) {
            this.getConnection().createStatement().execute(
                    "UPDATE t_player SET " + value + " = " + value + " + 1, Games = Games +1 WHERE playerID= " + playerID + ";"
            );
        } else { // draw
            this.getConnection().createStatement().execute(
                    "UPDATE t_player SET Games=Games+1 WHERE playerID = " + playerID + ";"
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
        addGame(playerID, "pushes");
    }
}
