package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;


public class HighScores {

    private final PreparedStatement insertStatement;
    private final PreparedStatement deleteStatement;
    private final Connection connection;

    public HighScores() throws SQLException {
        Properties connectionProps = new Properties();
        
        
        connectionProps.put("user", "root");
        connectionProps.put("password", "Roni2009david.");
        connectionProps.put("serverTimezone", "UTC");
        String dbURL = "jdbc:mysql://localhost:3306/Tron?autoReconnect=true&useSSL=false";
        connection = DriverManager.getConnection(dbURL, connectionProps);
        
        
        String insertQuery = "INSERT INTO HIGHSCORES (TIMESTAMP, NAME, SCORE) VALUES (?, ?, ?)";
        insertStatement = connection.prepareStatement(insertQuery);
        String deleteQuery = "DELETE FROM HIGHSCORES WHERE NAME=?";
        deleteStatement = connection.prepareStatement(deleteQuery);
    }

    public ArrayList<HighScore> getHighScores() throws SQLException {
        String query = "SELECT * FROM HIGHSCORES";
        ArrayList<HighScore> highScores = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet results = stmt.executeQuery(query);
        while (results.next()) {
            String name = results.getString("NAME");
            int score = results.getInt("SCORE");
            highScores.add(new HighScore(name, score));
        }
        sortHighScores(highScores);
        return highScores;
    }

    public void putHighScore(String name, int score) throws SQLException {
        ArrayList<HighScore> highScores = getHighScores();
        int pos = -1;
        for(int i = 0; i < highScores.size(); i++) {
            if(highScores.get(i).getName().equals(name)) {
                pos = i;
                break;
            }
        }
        if(pos != -1) {
            if(highScores.get(pos).getScore() < score) {
                deleteScores(highScores.get(pos).getName());
                insertScore(name,score);
            }
        } else {
            insertScore(name,score);
        }
    }

    
    private void sortHighScores(ArrayList<HighScore> highScores) {
        Collections.sort(highScores, new Comparator<HighScore>() {
            @Override
            public int compare(HighScore t, HighScore t1) {
                return t1.getScore() - t.getScore();
            }
        });
    }

    private void insertScore(String name, int score) throws SQLException {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        insertStatement.setTimestamp(1, ts);
        insertStatement.setString(2, name);
        insertStatement.setInt(3, score);
        insertStatement.executeUpdate();
    }

    
    private void deleteScores(String name) throws SQLException {
        deleteStatement.setString(1, name);
        deleteStatement.executeUpdate();
    }
}
