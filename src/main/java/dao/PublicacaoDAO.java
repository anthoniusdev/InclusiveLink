package dao;

import model.Publicacao;

import java.sql.*;
import java.util.ArrayList;

public class PublicacaoDAO {
    private Connection conectar() {
        Conexao conexao = new Conexao();
        return conexao.conectar();
    }

    // CRUD - - - CREATE - - -
    // <-- Criando uma nova publicacao DE PERFIL no banco de dados -->
    public Publicacao novaPublicacao(Publicacao publicacao) {
        int idPublicacao = 0;
        try (Connection con = conectar()) {
            String createPublicacao = "insert into publicacao(texto, midia, id_autor) values (?, ?, ?)";
            try (PreparedStatement preparedStatementPublicacao = con.prepareStatement(createPublicacao, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatementPublicacao.setString(1, publicacao.getTexto());
                preparedStatementPublicacao.setString(2, publicacao.getMidia());
                preparedStatementPublicacao.setInt(3, publicacao.getAutor().getIdPessoa());
                int linhasAfetadas = preparedStatementPublicacao.executeUpdate();
                if (linhasAfetadas > 0) {
                    try (ResultSet idGerado = preparedStatementPublicacao.getGeneratedKeys()) {
                        if (idGerado.next()) {
                            idPublicacao = idGerado.getInt(1);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        if (verificaPublicacao(idPublicacao)) {
            return retornaPublicacao(idPublicacao);
        } else {
            return null;
        }
    }

    // CRUD - - - READ - - -
    // <-- Retorna um objeto da classe Publicacao para utilização de serviço futuro -->
    public Publicacao retornaPublicacao(int idPublicacao) {
        String read = "select * from publicacao where idpublicacao = ?";
        Publicacao publicacaoRetornada = new Publicacao();
        try (Connection con = conectar()) {
            PreparedStatement preparedStatement = con.prepareStatement(read);
            preparedStatement.setInt(1, idPublicacao);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                publicacaoRetornada.setIdPublicacao(rs.getInt(1));
                publicacaoRetornada.setTexto(rs.getString(2));
                publicacaoRetornada.setMidia(rs.getString(3));
                publicacaoRetornada.setAutor(new MembroDAO().retornaMembro(rs.getInt(4)));
                publicacaoRetornada.getAutor().setSenha(null);
                publicacaoRetornada.setData(rs.getString(5));
                publicacaoRetornada.setHora(rs.getString(6));
                publicacaoRetornada.setCurtidas(curtidas(idPublicacao));
                publicacaoRetornada.setComentarios(comentarios(idPublicacao));
                publicacaoRetornada.setNumeroComentarios(publicacaoRetornada.getComentarios().size());
                publicacaoRetornada.setNumeroCurtidas(publicacaoRetornada.getCurtidas().size());
            }
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return publicacaoRetornada;
    }

    // CREAD - - - READ - - -
    // <-- Verifica se existe a específica -->
    public boolean verificaPublicacao(int idPublicacao) {
        String read = "select * from publicacao where idpublicacao = ?";
        try (Connection con = conectar()) {
            PreparedStatement preparedStatement = con.prepareStatement(read);
            preparedStatement.setInt(1, idPublicacao);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // CRUD - - - READ - - -
    // <-- Armaneza as curtidas de alguma publicação específica e retorna a ArrayList -->
    public ArrayList<Integer> curtidas(int idPublicacao) {
        String read = "SELECT idMembro FROM publicacao_curtida pc WHERE pc.idPublicacao = ?";
        ArrayList<Integer> membros = new ArrayList<>();
        try (Connection con = conectar()) {
            PreparedStatement preparedStatement = con.prepareStatement(read);
            preparedStatement.setInt(1, idPublicacao);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                membros.add(rs.getInt(1));
            }
            return membros;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // CRUD - - - CREATE - - -
    // <-- Insere um registro de uma curtida nova na tabela publicacao_curtida -->
    public void curtirPublicacao(int idPublicacao, int idMembro) {
        String create = "insert into publicacao_curtida values (?, ?)";
        try (Connection con = conectar()) {
            PreparedStatement preparedStatement = con.prepareStatement(create);
            preparedStatement.setInt(1, idPublicacao);
            preparedStatement.setInt(2, idMembro);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // CRUD - - - DELETE - - -
    // <-- Deleta uma publicacao específica -->
    public void excluirPublicacao(int idPublicacao) {
        try (Connection con = conectar()) {
            String delete = "DELETE FROM publicacao WHERE idPublicacao = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(delete)) {
                preparedStatement.setInt(1, idPublicacao);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Publicacao> feed(int idUsuario, int indice_inicial, int quantidade_publicacoes) {
        try (Connection con = conectar()) {
            String read = "SELECT idPublicacao FROM publicacao WHERE id_autor IN (SELECT idSeguindo FROM membro_seguindo WHERE idMembro = ?) OR id_autor = ? ORDER BY data DESC, hora DESC LIMIT ?, ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(read)) {
                preparedStatement.setInt(1, idUsuario);
                preparedStatement.setInt(2, idUsuario);
                preparedStatement.setInt(3, indice_inicial);
                preparedStatement.setInt(4, quantidade_publicacoes);
                ResultSet rs = preparedStatement.executeQuery();
                ArrayList<Publicacao> feed = new ArrayList<>();
                while (rs.next()) {
                    feed.add(retornaPublicacao(rs.getInt(1)));
                }
                return feed;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Publicacao> feed(int idUsuario) {
        try (Connection con = conectar()) {
            String read = "SELECT idPublicacao FROM publicacao WHERE id_autor IN (SELECT idSeguindo FROM membro_seguindo WHERE idMembro = ?) OR id_autor = ? ORDER BY CONCAT(data, ' ', hora) DESC";
            try (PreparedStatement preparedStatement = con.prepareStatement(read)) {
                preparedStatement.setInt(1, idUsuario);
                preparedStatement.setInt(2, idUsuario);
                ResultSet rs = preparedStatement.executeQuery();
                ArrayList<Publicacao> feed = new ArrayList<>();
                while (rs.next()) {
                    feed.add(retornaPublicacao(rs.getInt(1)));
                }
                return feed;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void descurtirPublicacao(int idPublicacao, int idMembro) {
        try (Connection con = conectar()) {
            String delete = "DELETE FROM publicacao_curtida WHERE idPublicacao = ? AND idMembro = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(delete)) {
                preparedStatement.setInt(1, idPublicacao);
                preparedStatement.setInt(2, idMembro);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Integer> comentarios(int idPublicacao) {
        try (Connection con = conectar()) {
            String read = "SELECT idComentario FROM publicacao_comentario WHERE idPublicacao = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(read)) {
                preparedStatement.setInt(1, idPublicacao);
                ResultSet rs = preparedStatement.executeQuery();
                ArrayList<Integer> comentarios = new ArrayList<>();
                while (rs.next()) {
                    comentarios.add(rs.getInt(1));
                }
                return comentarios;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Publicacao> perfilMembro(int idMebro, int indice_inicial, int quantidade_publicacoes) {
        try (Connection con = conectar()) {
            String read = "SELECT idPublicacao FROM publicacao WHERE id_autor = ? ORDER BY data DESC, hora DESC LIMIT ?, ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(read)) {
                preparedStatement.setInt(1, idMebro);
                preparedStatement.setInt(2, indice_inicial);
                preparedStatement.setInt(3, quantidade_publicacoes);
                ResultSet rs = preparedStatement.executeQuery();
                ArrayList<Publicacao> feed = new ArrayList<>();
                while (rs.next()) {
                    feed.add(retornaPublicacao(rs.getInt(1)));
                }
                return feed;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }}
