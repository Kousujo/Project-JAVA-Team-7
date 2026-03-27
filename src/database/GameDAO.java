package database;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class GameDAO { 

    public int getHighestScore() {
        String sql = "SELECT MAX(score) AS max_score FROM high_scores";
        
        try (Connection conn = ConnectionDB.connectToDatabase();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("max_score");
            }
        } catch (SQLException e) {
            System.err.println("Loi khi lay data ky luc: " + e.getMessage());
        }
        return 0;
    }

    public List <Object[]> getTopScores() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT TOP 10 score, mode, secret_number, attempts_used, time_spent " +
                    "FROM high_scores ORDER BY score DESC, time_spent ASC";

        try (Connection conn = ConnectionDB.connectToDatabase();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            int rank = 1;
            while (rs.next()) {
                list.add(new Object[]{
                    rank++,                         
                    rs.getInt("score"),             
                    rs.getString("mode"),           
                    rs.getInt("time_spent"),        
                    rs.getInt("attempts_used"),     
                    rs.getInt("secret_number")      
                });
            }
        } catch (SQLException e) {
            System.err.println("Loi load bang xep hang: " + e.getMessage());
        }
        return list;
    }

    public boolean insertGameResult(int score, String mode, int secret, int attempts, int time) {
        String sql = "INSERT INTO high_scores (score, mode, secret_number, attempts_used, time_spent) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnectionDB.connectToDatabase();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, score);
            ps.setString(2, mode);
            ps.setInt(3, secret);
            ps.setInt(4, attempts);
            ps.setInt(5, time);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Loi khi luu ket qua game: " + e.getMessage());
            return false;
        }
    }
}