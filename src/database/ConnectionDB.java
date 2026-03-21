package database;

import java.sql.Connection;
import java.sql.SQLException;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class ConnectionDB {
    private static SQLServerDataSource ds = new SQLServerDataSource();

    static {
        ds.setUser("sa");
        ds.setPassword("123456");
        ds.setServerName("ADMIN-Kousujo");
        ds.setPortNumber(1433);
        ds.setDatabaseName("..."); 
        ds.setEncrypt(false);
        ds.setTrustServerCertificate(true);
    }

    public static Connection connectToDatabase() {
        try {
            Connection conn = ds.getConnection();
            if (conn != null) {
                System.out.println("Kết nối SQL Server thành công!");
                return conn;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối: " + e.getMessage());
        }
        return null;
    }
}