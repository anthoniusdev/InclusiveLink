package dao;

import model.Pessoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public Pessoa retornaPessoa(int idPessoa){
        try (Connection con = conectar()){
            String read = "SELECT * FROM pessoa WHERE idPessoa = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(read)){
                preparedStatement.setInt(1, idPessoa);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()){
                    return new Pessoa(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    protected void editarNome(int idPessoa, String nome){
        try(Connection con = conectar()){
            String update = "UPDATE pessoa SET nome = ? WHERE idPessoa = ?";
            try(PreparedStatement preparedStatement = con.prepareStatement(update)){
                preparedStatement.setString(1, nome);
                preparedStatement.setInt(2, idPessoa);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
