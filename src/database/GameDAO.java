package database;

import java.sql.*;

public class GameDAO {

    /**
     * Lấy điểm số cao nhất từ bảng high_scores
     */
    public int getHighestScore() {
        String sql = "SELECT MAX(score) AS max_score FROM high_scores";
        
        // Sử dụng try-with-resources để tự động đóng kết nối
        try (Connection conn = ConnectionDB.connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("max_score");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy kỷ lục: " + e.getMessage());
        }
        return 0; // Trả về 0 nếu lỗi hoặc không có dữ liệu
    }

    // Thêm vào file GameDAO.java
    public java.util.List<Object[]> getTopScores() {
        java.util.List<Object[]> list = new java.util.ArrayList<>();
        // SQL Server: Lấy Top 10, sắp xếp điểm giảm dần, thời gian tăng dần (ai nhanh hơn xếp trên)
        String sql = "SELECT TOP 10 score, mode, secret_number, attempts_used, time_spent " +
                    "FROM high_scores ORDER BY score DESC, time_spent ASC";

        try (Connection conn = ConnectionDB.connectToDatabase();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            int rank = 1;
            while (rs.next()) {
                list.add(new Object[]{
                    rank++,                         // Cột HẠNG
                    rs.getInt("score"),             // Cột ĐIỂM
                    rs.getString("mode"),           // Cột CHẾ ĐỘ
                    rs.getInt("time_spent"),        // Ẩn: TIME_HIDDEN
                    rs.getInt("attempts_used"),     // Ẩn: TURNS_HIDDEN
                    rs.getInt("secret_number")      // Ẩn: SECRET_HIDDEN
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