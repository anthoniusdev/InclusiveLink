package dao;

import model.Comentario;

import java.sql.*;
import java.util.ArrayList;

public class ComentarioDAO {
    private Connection conectar() {
        Conexao conexao = new Conexao();
        return conexao.conectar();
    }

    public Comentario criarComentario(int idPublicacao, String texto, int idAutor) {
        try (Connection con = conectar()) {
            String create = "insert into publicacao_comentario(idPublicacao, texto, id_autor) values(?, ?, ?)";
            try (PreparedStatement preparedStatement = con.prepareStatement(create, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, idPublicacao);
                preparedStatement.setString(2, texto);
                preparedStatement.setInt(3, idAutor);
                int linhas_afetadas = preparedStatement.executeUpdate();
                if (linhas_afetadas > 0) {
                    try (ResultSet idGerado = preparedStatement.getGeneratedKeys()) {
                        if (idGerado.next()) {
                            return retornaComentario(idGerado.getInt(1));
                        }
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Comentario retornaComentario(int idComentario) {
        String read = "select * from publicacao_comentario where idComentario = ?";
        Comentario comentario = new Comentario();
        try (Connection con = conectar()) {
            PreparedStatement preparedStatement = con.prepareStatement(read);
            preparedStatement.setInt(1, idComentario);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                comentario.setIdComentario(rs.getInt(1));
                comentario.setIdPublicacao(rs.getInt(2));
                comentario.setTexto(rs.getString(3));
                comentario.setMidia(rs.getString(4));
                comentario.setAutor(new MembroDAO().retornaMembro(rs.getInt(5)));
                comentario.getAutor().setSenha(null);
                comentario.setData(rs.getString(6));
                comentario.setHora(rs.getString(7));
                comentario.setCurtidas(curtidas(comentario.getIdComentario()));
                comentario.setNumeroCurtidas(comentario.getCurtidas().size());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return comentario;
    }

    public ArrayList<Integer> curtidas(int idComentario) {
        String read = "select idMembro from comentario_curtida where idcomentario = ?";
        ArrayList<Integer> membros = new ArrayList<>();
        try (Connection con = conectar()) {
            PreparedStatement preparedStatement = con.prepareStatement(read);
            preparedStatement.setInt(1, idComentario);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                membros.add(rs.getInt(1));
            }
            return membros;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Comentario> comentarios(int idPublicacao, int indice_inicial, int quantidade_publicacoes) {
        try (Connection con = conectar()) {
            String read = "SELECT idComentario FROM publicacao_comentario WHERE idPublicacao = ? ORDER BY data DESC, hora DESC LIMIT ?, ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(read)) {
                preparedStatement.setInt(1, idPublicacao);
                preparedStatement.setInt(2, indice_inicial);
                preparedStatement.setInt(3, quantidade_publicacoes);
                ResultSet rs = preparedStatement.executeQuery();
                ArrayList<Comentario> comentarios = new ArrayList<>();
                while (rs.next()){
                    comentarios.add(retornaComentario(rs.getInt(1)));
                }
                return comentarios;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void curtirComentario(int idComentario, int idMembro){
        try(Connection con = conectar()){
            String read = "INSERT INTO comentario_curtida VALUES (?, ?)";
            try(PreparedStatement preparedStatement = con.prepareStatement(read)){
                preparedStatement.setInt(1, idComentario);
                preparedStatement.setInt(2, idMembro);
                preparedStatement.executeUpdate();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void descurtirComentario(int idComentario, int idMembro){
        try (Connection con = conectar()){
            String delete = "DELETE FROM comentario_curtida WHERE idMembro = ? AND idComentario = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(delete)){
                preparedStatement.setInt(1, idMembro);
                preparedStatement.setInt(2, idComentario);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
