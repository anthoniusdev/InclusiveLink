package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PessoaDAO {
    private Connection conectar() {
        Conexao conexao = new Conexao();
        return conexao.conectar();
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
