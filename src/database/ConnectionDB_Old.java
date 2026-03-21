package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDB_Old {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=...;encrypt=false;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASS = "123456";

    public static Connection getConnection() throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(URL, USER, PASS);
    }
}