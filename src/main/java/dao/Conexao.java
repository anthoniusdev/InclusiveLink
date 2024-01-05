package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
    protected Connection conectar() {
        Connection con = null;
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
            String url = "jdbc:mysql://localhost:3306/db_infinitelink";
            String user = "root";
            String password = "#Tony17#";
            con = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }
}
