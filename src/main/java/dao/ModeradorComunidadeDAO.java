package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ModeradorComunidadeDAO {
    public Connection conectar() {
        return new Conexao().conectar();
    }

    public void novoModerador(int idModerador, int idComunidade) {
        try (Connection con = conectar()) {
            String create = "INSERT INTO moderador_comunidade(idModerador, idComunidade) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = con.prepareStatement(create)) {
                preparedStatement.setInt(1, idModerador);
                preparedStatement.setInt(2, idComunidade);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void excluirComunidade(int idComunidade) {
        try (Connection con = conectar()) {
            String delete = "DELETE FROM comunidade WHERE idComunidade = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(delete)) {
                preparedStatement.setInt(1, idComunidade);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void excluirParticipante(int idParticipante) {
        try (Connection con = conectar()) {
            String delete = "DELETE FROM comunidade WHERE idParticipante = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(delete)) {
                preparedStatement.setInt(1, idParticipante);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
