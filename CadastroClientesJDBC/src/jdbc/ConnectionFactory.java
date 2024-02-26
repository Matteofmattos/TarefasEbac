package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection connection;

    public ConnectionFactory() { }

    public static Connection getConnection(){
        return (connection==null)? initConnection(): connection;
    }

    private static Connection initConnection() {
        try {
           return DriverManager.getConnection("jdbc:postgresql://" +
                   "localhost:5432/vendas_online","postgres","139746");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
