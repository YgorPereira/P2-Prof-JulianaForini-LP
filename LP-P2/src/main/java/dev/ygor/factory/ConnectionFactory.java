package dev.ygor.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    String url = "jdbc:mysql://localhost:3306/alunoDAO";
    String user = "root";
    String password = "ygor2006";

    public Connection getConnections(){
        try {
            System.out.println("Se conectando ao banco de dados...");
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão bem-sucedida!");
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível se conectar ao banco de dados...");
        }
    }
}
