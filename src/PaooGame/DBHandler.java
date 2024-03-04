package PaooGame;

import java.io.File;
import java.sql.*;

public class DBHandler {
    private static Connection connection = null;
    private static Statement statement = null;
    private static final String DB_PATH = "DataBase.db";

    //Constructorul este privat pentru a previne initializarea
    private DBHandler() {
    }

    //Singleton
    public static void getInstance() {
        if (connection == null) {
            try {
                File file = new File(DB_PATH);
                if (file.exists()) {
                    file.delete();
                }

                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
                statement = connection.createStatement();

                String sql = "CREATE TABLE IF NOT EXISTS SAVE" +
                        "(ID INT PRIMARY KEY NOT NULL," +
                        "SCORE INT NOT NULL," +
                        "PLAYER_ID INT NOT NULL)";
                statement.execute(sql);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //return connection;
    }


    public static void reset() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection = null;
    }


    public static void updateHighestScore(int score, int playerId) throws SQLException {
        try {


            getInstance();
            statement = connection.createStatement();

            String sql = "INSERT OR REPLACE INTO SAVE (ID, SCORE, PLAYER_ID) VALUES (1, " + score + "," + playerId + ")";
            statement.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
