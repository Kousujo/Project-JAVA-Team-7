package database;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class GameDAO {
    private List <Object[]> list; 

    public int getHighestScore() {
        String sql = "SELECT MAX(score) AS max_score FROM high_scores";
        
        try (Connection conn = ConnectionDB.connectToDatabase();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("max_score");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy kỷ lục: " + e.getMessage());
        }
        return 0;
    }

    public List <Object[]> getTopScores() {
        list = new ArrayList<>();
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
            System.err.println("Lỗi load bảng xếp hạng: " + e.getMessage());
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
            System.err.println("Lỗi khi lưu kết quả game: " + e.getMessage());
            return false;
        }
    }
}