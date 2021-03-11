package gexabyte.test.java.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBuilder {
    private static String URL="jdbc:postgresql://localhost:5432/test";
    private static String USER="postgres";
    private static String PASSWORD="mira1418";

    public static Connection build() throws SQLException {
        Connection connection=null;
        try {
            connection=DriverManager.getConnection(URL, USER, PASSWORD);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return connection;
    }
}
