package database;

import java.sql.*;
import java.util.Vector;

public class GameDAO {

    public boolean checkLogin(String username, String password) {
        String sql = "..."; // TODO : soạn lệnh SQL
        
        try (Connection conn = ConnectionDB.connectToDatabase();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Vector<Vector<Object>> getTopPlayers() {
        Vector<Vector<Object>> data = new Vector<>();
        String sql = "..."; // TODO : soạn lệnh SQL

        try (Connection conn = ConnectionDB.connectToDatabase();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
}