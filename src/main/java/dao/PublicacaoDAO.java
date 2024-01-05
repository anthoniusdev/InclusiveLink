package dao;

import model.Membro;
import model.Publicacao;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PublicacaoDAO {
    private Connection conectar() {
        Conexao conexao = new Conexao();
        return conexao.conectar();
    }

    // Criando uma nova publicacao DE PERFIL no banco de dados
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
        if (verificaPublicacao(publicacao)) {
            return retornaPublicacao(idPublicacao);
        } else {
            return null;
        }
    }

    // Instanciando uma publicação para retornar
    public Publicacao retornaPublicacao(int idPublicacao) {
        String read = "select * from publicacao where idpublicacao = ?";
        Publicacao publicacaoRetornada = new Publicacao();
        try (Connection con = conectar()) {
            PreparedStatement preparedStatement = con.prepareStatement(read);
            preparedStatement.setInt(1, idPublicacao);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                publicacaoRetornada.setIdPublicacao(rs.getInt(1));
                publicacaoRetornada.setTexto(rs.getString(2));
                publicacaoRetornada.setMidia(rs.getString(3));
                publicacaoRetornada.setData(rs.getString(5));
                publicacaoRetornada.setHora(rs.getString(6));
                publicacaoRetornada.setNumeroCurtidas(numeroCurtidas(idPublicacao));
                publicacaoRetornada.setAutor(autorPublicacao(idPublicacao));
                // publicacaoRetornada.setNumeroComentarios(); --> Depois que criar o ComentarioDAO, modificar.
            }
            preparedStatement.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return publicacaoRetornada;
    }

    // Verifica se existe a publicação em questão
    public boolean verificaPublicacao(Publicacao publicacao) {
        String read = "select * from publicacao where idpublicacao = ?";
        try (Connection con = conectar()) {
            PreparedStatement preparedStatement = con.prepareStatement(read);
            preparedStatement.setInt(1, publicacao.getIdPublicacao());
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    // Retorna todos os membros que curtiram a publicacao
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

    public int numeroCurtidas(int idPublicacao) {
        ArrayList<Membro> curtidas = curtidas(idPublicacao);
        return curtidas.size();
    }

    public Membro autorPublicacao(int idPublicacao) {
        String read = "select idPessoa from membro inner join publicacao on membro.idPessoa = publicacao.id_autor where idpublicacao = ?";
        try (Connection con = conectar()) {
            PreparedStatement preparedStatement = con.prepareStatement(read);
            preparedStatement.setInt(1, idPublicacao);
            ResultSet rs = preparedStatement.executeQuery();
            MembroDAO membroDAO = new MembroDAO();
            return membroDAO.retornaMembro(rs.getInt(1));
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
