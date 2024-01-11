package dao;

import model.Comentario;
import model.Membro;
import model.Publicacao;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumSet;

public class PublicacaoDAO {
    private Connection conectar() {
        Conexao conexao = new Conexao();
        return conexao.conectar();
    }

    // CRUD - - - CREATE - - -
    // <-- Criando uma nova publicacao DE PERFIL no banco de dados -->
    public Publicacao novaPublicacao(Publicacao publicacao) {
        String createPublicacao = "insert into publicacao(texto, midia, id_autor) values (?, ?, ?)";
        int idPublicacao = 0;
        try (Connection con = conectar()) {
            PreparedStatement preparedStatementPublicacao = con.prepareStatement(createPublicacao);
            preparedStatementPublicacao.setString(1, publicacao.getTexto());
            preparedStatementPublicacao.setString(2, publicacao.getMidia());
            preparedStatementPublicacao.setInt(3, publicacao.getAutor().getIdPessoa());
            preparedStatementPublicacao.executeUpdate();
            ResultSet idGerado = preparedStatementPublicacao.getGeneratedKeys();
            if (idGerado.next()) {
                idPublicacao = idGerado.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
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
                MembroDAO membroDAO = new MembroDAO();
                ComentarioDAO comentarioDAO = new ComentarioDAO();
                while (rs.next()) {
                    publicacaoRetornada.setIdPublicacao(rs.getInt(1));
                    publicacaoRetornada.setTexto(rs.getString(2));
                    publicacaoRetornada.setMidia(rs.getString(3));
                    publicacaoRetornada.setAutor(membroDAO.retornaMembro(rs.getInt(4)));
                    publicacaoRetornada.setData(rs.getString(5));
                    publicacaoRetornada.setHora(rs.getString(6));
                    publicacaoRetornada.setCurtidas(curtidas(idPublicacao));
                    publicacaoRetornada.setComentarios(comentarioDAO.comentarios(idPublicacao));
                    // publicacaoRetornada.setNumeroComentarios(); --> Verificar depois
                    // publicacaoRetornada.setNumeroCurtidas(); --> Verificar depois
                }
            }
            preparedStatement.close();
        } catch (Exception e) {
            System.out.println(e);
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
            System.out.println(e);
            return false;
        }
    }

    // CRUD - - - READ - - -
    // <-- Armaneza as curtidas de alguma publicação específica e retorna a ArrayList -->
    public ArrayList<Membro> curtidas(int idPublicacao) {
        String read = "SELECT idMembro FROM publicacao_curtida pc WHERE pc.idPublicacao = ?";
        ArrayList<Membro> membros = new ArrayList<>();
        try (Connection con = conectar()) {
            PreparedStatement preparedStatement = con.prepareStatement(read);
            preparedStatement.setInt(1, idPublicacao);
            ResultSet rs = preparedStatement.executeQuery();
            MembroDAO membroDAO = new MembroDAO();
            while (rs.next()) {
                membros.add(membroDAO.retornaMembro(rs.getInt(1)));
            }
            return membros;
        } catch (Exception e) {
            System.out.println(e);
            return null;
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
            System.out.println(e);
        }
    }

    // CRUD - - - DELETE - - -
    // <-- Deleta uma publicacao específica -->
    public void excluirPublicacao(int idPublicacao) {
        if (verificaPublicacao(idPublicacao)) {
            String delete = "delete from publicacao where idpublicacao = ?";
            try (Connection con = conectar()) {
                PreparedStatement preparedStatement = con.prepareStatement(delete);
                preparedStatement.setInt(1, idPublicacao);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
