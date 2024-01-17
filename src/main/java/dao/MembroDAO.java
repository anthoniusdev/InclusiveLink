package dao;

import model.Membro;
import model.Publicacao;
import util.ServicoAutenticacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MembroDAO {
    int idPessoa = 0;

    private Connection conectar() {
        Conexao conexao = new Conexao();
        return conexao.conectar();
    }

    // CRUD - - - CREATE - - -
    // <-- Realizando cadastro de uma nova pessoa e membro no banco de dados-->
    public Membro realizarCadastro(Membro membro) {

        String dataNascimentoSQL = membro.getDataNascimento();
        String createPessoa = "insert into pessoa(nome, dataNascimento, email, senha) values (?,STR_TO_DATE(?, '%d-%m-%Y'),?,?)";
        String createMembro = "insert into membro(idPessoa, fotoPerfil, fotoFundo, nomeUsuario, descricao, perfilVisivel) values (?, ?, ?, ?, ?, ?)";
        try (Connection con = conectar()) {
            con.setAutoCommit(false);  // Desabilita o commit automático para gerenciar transações manualmente

            try (PreparedStatement preparedStatementPessoa = con.prepareStatement(createPessoa, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement preparedStatementMembro = con.prepareStatement(createMembro)) {

                preparedStatementPessoa.setString(1, membro.getNome());
                preparedStatementPessoa.setString(2, membro.getDataNascimento());
                preparedStatementPessoa.setString(3, membro.getEmail());
                membro.setSenha(ServicoAutenticacao.hashSenha(membro.getSenha()));
                preparedStatementPessoa.setString(4, membro.getSenha());

                int linhasAfetadas = preparedStatementPessoa.executeUpdate();

                if (linhasAfetadas > 0) {
                    try (ResultSet chavesGeradas = preparedStatementPessoa.getGeneratedKeys()) {
                        if (chavesGeradas.next()) {
                            idPessoa = chavesGeradas.getInt(1);
                        }
                    }

                    preparedStatementMembro.setInt(1, idPessoa);
                    preparedStatementMembro.setString(2, membro.getFotoPerfil());
                    preparedStatementMembro.setString(3, membro.getFotoFundo());
                    preparedStatementMembro.setString(4, membro.getNomeUsuario());
                    preparedStatementMembro.setString(5, membro.getDescricao());
                    preparedStatementMembro.setBoolean(6, membro.isPerfilVisivel());

                    preparedStatementMembro.executeUpdate();
                }

                con.commit();  // Confirma a transação
                con.setAutoCommit(true);  // Restaura o modo de commit automático

                return new Membro(idPessoa, membro.getNome(), membro.getDataNascimento(), membro.getNomeUsuario(), membro.getEmail(), membro.getSenha(), membro.getFotoPerfil(), membro.getFotoFundo(), membro.getDescricao(), membro.getCurtidas());
            } catch (Exception e) {
                con.rollback();  // Desfaz a transação em caso de erro
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // CRUD - - - READ - - -
    // <-- Verificando se um membro existe -->
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

    // CRUD - - - READ - - -
    // <-- Retorna um objeto da classe Membro para utilização de serviço futuro -->
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
            preparedStatement.close();
            return new Membro(idPessoa, nome, dataNascimento, email, senha, fotoPerfil, fotoFundo, nomeUsuario, descricao, publicacoesCurtidas(idPessoa));
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    // CRUD - - - READ - - -
    // <-- Armaneza as curtidas de algum membro específico e retorna a ArrayList -->
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
            preparedStatement.close();
            return publicacoes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String retornaHashSenha(String string) {
        String read = "select pessoa.senha from pessoa inner join membro on pessoa.idpessoa = membro.idpessoa where pessoa.email = ? or membro.nomeUsuario = ?";
        try (Connection con = conectar()) {
            try (PreparedStatement preparedStatement = con.prepareStatement(read)) {
                preparedStatement.setString(1, string);
                preparedStatement.setString(2, string);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getString(1);
                }else {
                    return null;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> nomesUsuario() {
        ArrayList<String> nomesUsuario = new ArrayList<>();
        String read = "select nomeUsuario from membro";
        try (Connection con = conectar()) {
            try (PreparedStatement preparedStatement = con.prepareStatement(read)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    nomesUsuario.add(resultSet.getString(1));
                }
                return nomesUsuario;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public boolean isNomeUsuarioUnique(String nomeUsuario){
        return !nomesUsuario().contains(nomeUsuario);
    }
}
