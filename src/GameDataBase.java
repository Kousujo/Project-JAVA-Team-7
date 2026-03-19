import java.sql.*;
import java.util.ArrayList;

public class GameDataBase {
    private String url = "jdbc:sqlserver://localhost:1433;databaseName=";
    private String user = "sa";
    private String pass = "";

    public void saveScore(String name, int score, String mode) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(url, user, pass);

            String sql = "";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, score);
            ps.setString(3, mode);

            ps.executeUpdate()
            
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String[] getLastSession() {
        String[] data = {"Unknown", "0", "Chưa rõ"};
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(url, user, pass);

            String sql = "";
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                data[0] = rs.getString("PlayerName");
                data[1] = String.valueOf(rs.getInt("Score"));
                data[2] = rs.getString("Mode");
            }

            rs.close(); st.close(); conn.close();
        } catch (Exception e) { e.printStackTrace(); }
        return data;
    }
}