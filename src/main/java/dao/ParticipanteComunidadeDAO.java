package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParticipanteComunidadeDAO {
    public Connection conectar(){
        return new Conexao().conectar();
    }
    public boolean sairComunidade(int idComunidade, int idParticipante){
        try (Connection con = conectar()){
            String delete = "DELETE FROM participante_comunidade WHERE idComunidade = ? AND idParticipante = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(delete)){
                preparedStatement.setInt(1, idComunidade);
                preparedStatement.setInt(2, idParticipante);
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
