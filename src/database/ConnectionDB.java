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
        ds.setDatabaseName("the_chosen_number"); 
        ds.setEncrypt(false);
        ds.setTrustServerCertificate(true);
    }

    public static Connection connectToDatabase() {
        try {
            Connection conn = ds.getConnection();
            if (conn != null) {
                System.out.println("Ket noi SQL Server thanh cong!");
                return conn;
            }
        } catch (SQLException e) {
            System.err.println("Loi ket noi: " + e.getMessage());
        }
        return null;
    }
}