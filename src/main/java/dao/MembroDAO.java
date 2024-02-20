package dao;

import model.Membro;
import util.ServicoAutenticacao;

import java.sql.*;
import java.util.ArrayList;

public class MembroDAO {
    private int idPessoa = 0;

    private Connection conectar() {
        Conexao conexao = new Conexao();
        return conexao.conectar();
    }

    // CRUD - - - CREATE - - -
    // <-- Realizando cadastro de uma nova pessoa e membro no banco de dados-->
    public Membro realizarCadastro(Membro membro) {

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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // CRUD - - - READ - - -
    // <-- Retorna um objeto da classe Membro para utilização de serviço futuro -->
    public Membro retornaMembro(int id) {
        int idPessoa;
        String nome, dataNascimento, email, senha, fotoPerfil, fotoFundo, nomeUsuario, descricao;
        boolean perfilVisivel = false;
        String read = "select * FROM pessoa p INNER JOIN membro m ON p.idPessoa = m.idPessoa WHERE p.idPessoa = ?";
        try (Connection con = conectar()) {
            try (PreparedStatement preparedStatement = con.prepareStatement(read)) {
                preparedStatement.setInt(1, id);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    idPessoa = rs.getInt(1);
                    nome = rs.getString(2);
                    dataNascimento = rs.getString(3);
                    email = rs.getString(4);
                    senha = rs.getString(5);
                    fotoPerfil = rs.getString(8);
                    fotoFundo = rs.getString(9);
                    nomeUsuario = rs.getString(10);
                    descricao = rs.getString(11);
                    return new Membro(idPessoa, nome, dataNascimento, email, senha, fotoPerfil, fotoFundo, nomeUsuario, membrosSeguidores(idPessoa), membrosSeguindos(idPessoa), comunidadesParticipantes(idPessoa), descricao, publicacoesCurtidas(idPessoa), publicacoes(idPessoa), perfilVisivel, comentarios(idPessoa));
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // CRUD - - - READ - - -
    // <-- Armaneza as curtidas de algum membro específico e retorna a ArrayList -->
    public ArrayList<Integer> publicacoesCurtidas(int idPessoa) {
        try (Connection con = conectar()) {
            String read = "select idPublicacao from publicacao_curtida where idMembro = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(read)) {
                preparedStatement.setInt(1, idPessoa);
                ResultSet rs = preparedStatement.executeQuery();
                ArrayList<Integer> publicacoesCurtidas = new ArrayList<>();
                while (rs.next()) {
                    publicacoesCurtidas.add(rs.getInt(1));
                }
                return publicacoesCurtidas;
            }
        } catch (SQLException e) {
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
                } else {
                    return null;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Integer> membrosSeguidores(int idPessoa) {
        try (Connection con = conectar()) {
            String read = "select membro_seguidor.idSeguidor from membro_seguidor inner join membro m on membro_seguidor.idMembro = m.idPessoa where m.idPessoa = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(read)) {
                preparedStatement.setInt(1, idPessoa);
                ResultSet rs = preparedStatement.executeQuery();
                ArrayList<Integer> membrosSeguidores = new ArrayList<>();
                while (rs.next()) {
                    membrosSeguidores.add(rs.getInt(1));
                }
                return membrosSeguidores;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Integer> membrosSeguindos(int idPessoa) {
        try (Connection con = conectar()) {
            String read = "select membro_seguindo.idSeguindo from membro_seguindo inner join membro m on membro_seguindo.idMembro = m.idPessoa where m.idPessoa = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(read)) {
                preparedStatement.setInt(1, idPessoa);
                ResultSet rs = preparedStatement.executeQuery();
                ArrayList<Integer> membrosSeguindos = new ArrayList<>();
                while (rs.next()) {
                    membrosSeguindos.add(rs.getInt(1));
                }
                return membrosSeguindos;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Integer> comunidadesParticipantes(int idPessoa) {
        try (Connection con = conectar()) {
            String read = "select participante_comunidade.idComunidade from participante_comunidade inner join membro m on participante_comunidade.idParticipante = m.idPessoa where m.idPessoa = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(read)) {
                preparedStatement.setInt(1, idPessoa);
                ResultSet rs = preparedStatement.executeQuery();
                ArrayList<Integer> comunidadesParticipantes = new ArrayList<>();
                while (rs.next()) {
                    comunidadesParticipantes.add(rs.getInt(1));
                }
                return comunidadesParticipantes;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Integer> publicacoes(int idPessoa) {
        try (Connection con = conectar()) {
            String read = "select idPublicacao from publicacao where id_autor = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(read)) {
                preparedStatement.setInt(1, idPessoa);
                ResultSet rs = preparedStatement.executeQuery();
                ArrayList<Integer> publicacoes = new ArrayList<>();
                while (rs.next()) {
                    publicacoes.add(rs.getInt(1));
                }
                return publicacoes;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Integer> comentarios(int idPessoa) {
        try (Connection con = conectar()) {
            String read = "select idComentario from publicacao_comentario where id_autor = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(read)) {
                preparedStatement.setInt(1, idPessoa);
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

    public int verificaId(String nomeUsuario) {
        try (Connection con = conectar()) {
            String read = "select pessoa.idPessoa from pessoa inner join membro on pessoa.idPessoa = membro.idPessoa where membro.nomeUsuario = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(read)) {
                preparedStatement.setString(1, nomeUsuario);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
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

    public boolean isNomeUsuarioUnique(String nomeUsuario) {
        return !nomesUsuario().contains(nomeUsuario);
    }

    public ArrayList<Membro> listarMembros() {
        try (Connection con = conectar()) {
            String read = "select idPessoa from membro order by dataCriacao desc";
            try (PreparedStatement preparedStatement = con.prepareStatement(read)) {
                ResultSet rs = preparedStatement.executeQuery();
                ArrayList<Membro> membros = new ArrayList<>();
                while (rs.next()) {
                    membros.add(retornaMembro(rs.getInt(1)));
                }
                return membros;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Membro> listarMembros(int quantidade) {
        try (Connection con = conectar()) {
            String read = "select idPessoa from membro order by dataCriacao desc limit ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(read)) {
                preparedStatement.setInt(1, quantidade);
                ResultSet rs = preparedStatement.executeQuery();
                ArrayList<Membro> membros = new ArrayList<>();
                while (rs.next()) {
                    membros.add(retornaMembro(rs.getInt(1)));
                }
                return membros;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Membro> listarMembros(int quantidade, int idPessoa) {
        try (Connection con = conectar()) {
            String read = "select idPessoa from membro where idPessoa <> ? and idPessoa not in (SELECT idMembro from membro_seguidor where idSeguidor = ?) order by dataCriacao desc limit ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(read)) {
                preparedStatement.setInt(1, idPessoa);
                preparedStatement.setInt(2, idPessoa);
                preparedStatement.setInt(3, quantidade);
                ResultSet rs = preparedStatement.executeQuery();
                ArrayList<Membro> membros = new ArrayList<>();
                while (rs.next()) {
                    membros.add(retornaMembro(rs.getInt(1)));
                }
                return membros;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean seguirMembro(int idMembro, int idSeguindo) {
        try (Connection con = conectar()) {
            String create = "INSERT INTO membro_seguindo(idMembro, idSeguindo) VALUES (?,?)";
            try (PreparedStatement preparedStatement = con.prepareStatement(create)) {
                preparedStatement.setInt(1, idMembro);
                preparedStatement.setInt(2, idSeguindo);
                preparedStatement.executeUpdate();
                String create1 = "INSERT INTO membro_seguidor(idMembro, idSeguidor) VALUES (?,?)";
                try (PreparedStatement preparedStatement1 = con.prepareStatement(create1)) {
                    preparedStatement1.setInt(1, idSeguindo);
                    preparedStatement1.setInt(2, idMembro);
                    preparedStatement1.executeUpdate();
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Membro> pesquisarPerfil(String query, int id) {
        try (Connection con = conectar()) {
            String read = "SELECT idPessoa FROM pessoa WHERE nome LIKE ? AND idPessoa <> ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(read)) {
                preparedStatement.setString(1, "%" + query + "%");
                preparedStatement.setInt(2, id);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    ArrayList<Membro> membros = new ArrayList<>();
                    while (rs.next()) {
                        membros.add(retornaMembro(rs.getInt(1)));
                    }
                    return membros;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean editarPerfil(int idMembro, String nome, String descricao, String urlFotoPerfil, String urlFotoFundo) {
        try (Connection con = conectar()) {
            new PessoaDAO().editarNome(idMembro, nome);
            String update = "UPDATE membro SET descricao = ?, fotoPerfil = ?, fotoFundo = ? WHERE idPessoa = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(update)) {
                preparedStatement.setString(1, descricao);
                preparedStatement.setString(2, urlFotoPerfil);
                preparedStatement.setString(3, urlFotoFundo);
                preparedStatement.setInt(4, idMembro);
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void pararSeguir(int idUsuario, int idSeguindo) {
        try (Connection con = conectar()) {
            String delete = "DELETE FROM membro_seguindo WHERE idMembro = ? AND idSeguindo = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(delete)) {
                preparedStatement.setInt(1, idUsuario);
                preparedStatement.setInt(2, idSeguindo);
                preparedStatement.executeUpdate();
                String delete1 = "DELETE FROM membro_seguidor WHERE idMembro = ? AND idSeguidor = ?";
                try (PreparedStatement preparedStatement1 = con.prepareStatement(delete1)) {
                    preparedStatement1.setInt(1, idSeguindo);
                    preparedStatement1.setInt(2, idUsuario);
                    preparedStatement1.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
