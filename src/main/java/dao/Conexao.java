package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
    // Criando uma conexão com o banco de dados
    protected Connection conectar() {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
            String url = "jdbc:mysql://localhost:3308/db_infinitylink";
            String user = "root";
            String password = "988689092";
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
