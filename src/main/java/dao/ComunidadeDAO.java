package dao;

import model.Comunidade;

import java.sql.*;
import java.util.ArrayList;

public class ComunidadeDAO {
    private int idComunidade = 0;

    private Connection conectar() {
        Conexao conexao = new Conexao();
        return conexao.conectar();
    }

    public Comunidade criarComunidade(Comunidade comunidade) {
        try (Connection con = conectar()) {
            con.setAutoCommit(false); // Desabilita o commit automático para gerenciar transações manualmente
            String create = "INSERT INTO comunidade(nome, idCriador, fotoPerfil, fotoFundo, descricao) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = con.prepareStatement(create, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, comunidade.getNome());
                preparedStatement.setInt(2, comunidade.getIdCriador());
                preparedStatement.setString(3, comunidade.getFotoPerfil());
                preparedStatement.setString(4, comunidade.getFotoFundo());
                preparedStatement.setString(5, comunidade.getDescricao());
                int linhasAfetadas = preparedStatement.executeUpdate();
                if (linhasAfetadas > 0) {
                    try (ResultSet chavesGeradas = preparedStatement.getGeneratedKeys()) {
                        if (chavesGeradas.next()) {
                            idComunidade = chavesGeradas.getInt(1);
                            String create2 = "INSERT INTO participante_comunidade(idParticipante, idComunidade) VALUES (?, ?)";
                            try (PreparedStatement preparedStatement2 = con.prepareStatement(create2)) {
                                preparedStatement2.setInt(1, comunidade.getIdCriador());
                                preparedStatement2.setInt(2, idComunidade);
                                preparedStatement2.executeUpdate();
                                String create3 = "INSERT INTO moderador_comunidade(idModerador, idComunidade) VALUES (?, ?)";
                                try (PreparedStatement preparedStatement3 = con.prepareStatement(create3)) {
                                    preparedStatement3.setInt(1, comunidade.getIdCriador());
                                    preparedStatement3.setInt(2, idComunidade);
                                    preparedStatement3.executeUpdate();
                                    con.commit();
                                    con.setAutoCommit(true);
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return retornaComunidade(idComunidade);
    }

    public Comunidade retornaComunidade(int idComunidade) {
        try (Connection con = conectar()) {
            String read = "SELECT * FROM comunidade WHERE idComunidade = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(read)) {
                preparedStatement.setInt(1, idComunidade);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    idComunidade = rs.getInt(1);
                    return new Comunidade(
                            idComunidade,
                            rs.getString(2),
                            rs.getInt(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            publicacoes(idComunidade),
                            moderadores(idComunidade),
                            participantes(idComunidade),
                            seguidores(idComunidade)
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public ArrayList<Integer> publicacoes(int idComunidade) {
        try (Connection con = conectar()) {
            String read = "SELECT idPublicacao FROM publicacao_comunidade WHERE idComunidade = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(read)) {
                preparedStatement.setInt(1, idComunidade);
                ArrayList<Integer> publicacoes = new ArrayList<>();
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    publicacoes.add(rs.getInt(1));
                }
                return publicacoes;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Integer> moderadores(int idComunidade) {
        try (Connection con = conectar()) {
            String read = "SELECT idModerador FROM moderador_comunidade WHERE idComunidade = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(read)) {
                preparedStatement.setInt(1, idComunidade);
                ArrayList<Integer> moderadores = new ArrayList<>();
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    moderadores.add(rs.getInt(1));
                }
                return moderadores;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Integer> participantes(int idComunidade) {
        try (Connection con = conectar()) {
            String read = "SELECT idParticipante FROM participante_comunidade WHERE idComunidade = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(read)) {
                preparedStatement.setInt(1, idComunidade);
                ArrayList<Integer> participantes = new ArrayList<>();
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    participantes.add(rs.getInt(1));
                }
                return participantes;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Integer> seguidores(int idComunidade) {
        try (Connection con = conectar()) {
            String read = "SELECT idSeguidor FROM seguidor_comunidade WHERE idComunidade = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(read)) {
                preparedStatement.setInt(1, idComunidade);
                ArrayList<Integer> seguidores = new ArrayList<>();
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    seguidores.add(rs.getInt(1));
                }
                return seguidores;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean verificaComunidade (int idComunidade){
        try (Connection con = conectar()) {
            String read = "SELECT idComunidade FROM comunidade WHERE idComunidade = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(read)) {
                preparedStatement.setInt(1, idComunidade);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()){
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public ArrayList<Comunidade> listarComunidades(int limite){
        try (Connection con = conectar()){
            String read = "SELECT idComunidade FROM comunidade ORDER BY comunidade.idComunidade DESC LIMIT ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(read)){
                preparedStatement.setInt(1, limite);
                ResultSet rs = preparedStatement.executeQuery();
                ArrayList<Comunidade> comunidades = new ArrayList<>();
                while (rs.next()){
                    comunidades.add(retornaComunidade(rs.getInt(1)));
                }
                return comunidades;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Comunidade> listarComunidades(){
        try (Connection con = conectar()){
            String read = "SELECT idComunidade FROM comunidade ORDER BY comunidade.idComunidade DESC";
            try (PreparedStatement preparedStatement = con.prepareStatement(read)){
                ResultSet rs = preparedStatement.executeQuery();
                ArrayList<Comunidade> comunidades = new ArrayList<>();
                while (rs.next()){
                    comunidades.add(retornaComunidade(rs.getInt(1)));
                }
                return comunidades;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Comunidade> listarComunidadesUsuario(int idUsuario){
        try (Connection con = conectar()){
            String read = "SELECT idComunidade FROM participante_comunidade WHERE idParticipante = ? ORDER BY idComunidade DESC";
            try (PreparedStatement preparedStatement = con.prepareStatement(read)){
                preparedStatement.setInt(1, idUsuario);
                ResultSet rs = preparedStatement.executeQuery();
                ArrayList<Comunidade> comunidades = new ArrayList<>();
                while (rs.next()){
                    int idComunidade = rs.getInt(1);
                    System.out.println(idComunidade);
                    comunidades.add(retornaComunidade(idComunidade));
                }
                return comunidades;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Comunidade> pesquisarComunidade(String query){
        try(Connection con = conectar()){
            String read = "SELECT idComunidade FROM comunidade WHERE nome LIKE ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(read)){
                preparedStatement.setString(1, "%" + query + "%");
                try(ResultSet rs = preparedStatement.executeQuery()){
                    ArrayList<Comunidade> comunidades = new ArrayList<>();
                    while (rs.next()){
                        comunidades.add(retornaComunidade(rs.getInt(1)));
                    }
                    return comunidades;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void selecionarComunidade(Comunidade comunidade){
        String read = "select * from comunidade where idComunidade = ?";
        try(Connection con = conectar()) {
            PreparedStatement preparedStatement = con.prepareStatement(read);
            preparedStatement.setString(1, String.valueOf(comunidade.getIdComunidade()));
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                comunidade.setIdComunidade(Integer.parseInt(rs.getString(1)));
                comunidade.setNome(rs.getString(2));
                comunidade.setIdCriador(Integer.parseInt(rs.getString(3)));
                comunidade.setFotoPerfil(rs.getString(4));
                comunidade.setFotoFundo(rs.getString(5));
                comunidade.setDescricao(rs.getString(6));
            }
            con.close();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Comunidade> comunidades(){
        try (Connection con = conectar()){
            String read = "SELECT idComunidade FROM comunidade";
            try (PreparedStatement preparedStatement = con.prepareStatement(read)){
                ResultSet rs = preparedStatement.executeQuery();
                ArrayList<Comunidade> comunidades = new ArrayList<>();
                while (rs.next()){
                    comunidades.add(retornaComunidade(rs.getInt(1)));
                }
                return comunidades;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}