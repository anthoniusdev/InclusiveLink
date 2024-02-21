package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParticipanteComunidadeDAO {
    public Connection conectar(){
        return new Conexao().conectar();
    }
    public void participarComunidade(int idComunidade, int idParticipante) {
        try (Connection con = conectar()) {
            String create = "INSERT INTO participante_comunidade(idParticipante, idComunidade) VALUES (?, ?);";
            try (PreparedStatement preparedStatement = con.prepareStatement(create)) {
                preparedStatement.setInt(1, idParticipante);
                preparedStatement.setInt(2, idComunidade);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void sairComunidade(int idComunidade, int idParticipante){
        try (Connection con = conectar()){
            String delete = "DELETE FROM participante_comunidade WHERE idComunidade = ? AND idParticipante = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(delete)){
                preparedStatement.setInt(1, idComunidade);
                preparedStatement.setInt(2, idParticipante);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void publicarComunidade(int idComunidade, int idPublicacao){
        try (Connection con = conectar()) {
            String create = "INSERT INTO publicacao_comunidade(idComunidade, idPublicacao) VALUES (?, ?);";
            try (PreparedStatement preparedStatement = con.prepareStatement(create)) {
                preparedStatement.setInt(1, idComunidade);
                preparedStatement.setInt(2, idPublicacao);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}