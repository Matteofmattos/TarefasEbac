package JDBC;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
    private static Connection connection;
    public ConnectionFactory() { }
    public static Connection getConnection() {
        try {
            if ( connection == null || connection.isClosed()){
                return DriverManager.getConnection("jdbc:postgresql://" +
                        "localhost:5432/Vendas","postgres","139746");
            } else {
                return connection;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Erro: "+e.getMessage());
            return null;
        }
    }
}
