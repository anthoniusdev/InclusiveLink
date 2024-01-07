package dao;

import model.Membro;
import model.Publicacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MembroDAO {
    private Connection conectar() {
        Conexao conexao = new Conexao();
        return conexao.conectar();
    }

    public Membro realizarCadastro(Membro membro) {
        String createPessoa = "insert into pessoa(nome, dataNascimento, email, senha) values (?,?,?,?)";
        String createMembro = "insert into membro(idPessoa, fotoPerfil, fotoFundo, nomeUsuario, descricao, perfilVisivel) values (?, ?, ?, ?, ?, ?)";
        int idPessoa = 0;
        try (Connection con = conectar()) {
            PreparedStatement preparedStatementPessoa = con.prepareStatement(createPessoa);
            PreparedStatement preparedStatementMembro = con.prepareStatement(createMembro);
            preparedStatementPessoa.setString(1, membro.getNome());
            preparedStatementPessoa.setString(2, membro.getDataNascimento());
            preparedStatementPessoa.setString(3, membro.getEmail());
            preparedStatementPessoa.setString(4, membro.getSenha());
            preparedStatementPessoa.executeUpdate();
            ResultSet idGerado = preparedStatementPessoa.getGeneratedKeys();
            if (idGerado.next()) {
                idPessoa = idGerado.getInt(1);
            }
            preparedStatementMembro.setInt(1, idPessoa);
            preparedStatementMembro.setString(2, membro.getFotoPerfil());
            preparedStatementMembro.setString(3, membro.getFotoFundo());
            preparedStatementMembro.setString(4, membro.getNomeUsuario());
            preparedStatementMembro.setString(5, membro.getDescricao());
            preparedStatementMembro.setBoolean(6, membro.isPerfilVisivel());
            preparedStatementMembro.executeUpdate();
            return new Membro(idPessoa, membro.getNome(), membro.getDataNascimento(), membro.getNomeUsuario(), membro.getEmail(), membro.getSenha(), membro.getFotoPerfil(), membro.getFotoFundo(), membro.getDescricao(), membro.getCurtidas());
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public boolean verificaMembro(Membro membro) {
        String read = "select * from membro where idpessoa = ?";
        try (Connection con = conectar()) {
            PreparedStatement preparedStatement = con.prepareStatement(read);
            preparedStatement.setInt(1, membro.getIdPessoa());
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public Membro retornaMembro(int id) {
        int idPessoa;
        String nome, dataNascimento, email, senha, fotoPerfil, fotoFundo, nomeUsuario, descricao;
        boolean perfilVisivel;
        String read = "select p.iDPessoa, p.nome, p.dataNascimento, p.email, p.senha, m.fotoPerfil, m.fotoFundo, m.nomeUsuario, m.descricao, m.perfilVisivel FROM pessoa p INNER JOIN membro m ON p.idPessoa = m.idPessoa WHERE p.idPessoa = ?";
        try (Connection con = conectar()) {
            PreparedStatement preparedStatement = con.prepareStatement(read);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            idPessoa = rs.getInt(1);
            nome = rs.getString(2);
            dataNascimento = rs.getString(3);
            email = rs.getString(4);
            senha = rs.getString(5);
            fotoPerfil = rs.getString(6);
            fotoFundo = rs.getString(7);
            nomeUsuario = rs.getString(8);
            descricao = rs.getString(9);
            return new Membro(idPessoa, nome, dataNascimento, email, senha, fotoPerfil, fotoFundo, nomeUsuario, descricao, publicacoesCurtidas(idPessoa));
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public ArrayList<Publicacao> publicacoesCurtidas(int idMembro) {
        String read = "select idPublicacao from publicacao_curtida where idMembro = ?";
        ArrayList<Publicacao> publicacoes = new ArrayList<>();
        try (Connection con = conectar()) {
            PreparedStatement preparedStatement = con.prepareStatement(read);
            preparedStatement.setInt(1, idMembro);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                PublicacaoDAO publicacaoDAO = new PublicacaoDAO();
                while (rs.next()) {
                    publicacoes.add(publicacaoDAO.retornaPublicacao(rs.getInt(1)));
                }
            }
            return publicacoes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
