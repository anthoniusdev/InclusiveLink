package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PessoaDAO {
    private Connection conectar() {
        Conexao conexao = new Conexao();
        return conexao.conectar();
    }
    public ArrayList<String> emails(){
        String read = "select email from pessoa";
        ArrayList<String> emails = new ArrayList<>();
        try(Connection con = conectar()){
            try(PreparedStatement preparedStatement = con.prepareStatement(read)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    emails.add(resultSet.getString(1));
                }
                return emails;
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public boolean isEmailUnique(String email){
        return !emails().contains(email);
    }
}
