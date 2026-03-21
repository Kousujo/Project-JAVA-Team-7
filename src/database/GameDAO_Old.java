package database;

import java.sql.*;
import java.util.Vector;

public class GameDAO_Old {

    public boolean checkLogin(String username, String password) {
        String sql = "...";
        try (Connection conn = ConnectionDB_Old.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, username);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            return rs.next(); 
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void saveGameHistory(String username, String mode, int score, int result, int timeSpent) {
        String getUserIdSql = "...";
        String insertSql = "...";
        
        try (Connection conn = ConnectionDB_Old.getConnection()) {
            PreparedStatement psGet = conn.prepareStatement(getUserIdSql);
            psGet.setString(1, username);
            ResultSet rs = psGet.executeQuery();
            
            if (rs.next()) {
                int userId = rs.getInt("UserID");
                PreparedStatement psInsert = conn.prepareStatement(insertSql);
                psInsert.setInt(1, userId);
                psInsert.setString(2, mode);
                psInsert.setInt(3, score);
                psInsert.setInt(4, result);
                psInsert.setInt(5, timeSpent);
                
                psInsert.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Vector<Vector<Object>> getTopPlayers() {
        Vector<Vector<Object>> data = new Vector<>();
        String sql = "...";
        
        try (Connection conn = ConnectionDB_Old.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql)) {
            
            int rank = 1;
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rank++);
                row.add(rs.getString("DisplayName"));
                row.add(rs.getInt("Score"));
                row.add(rs.getString("Mode"));
                data.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}