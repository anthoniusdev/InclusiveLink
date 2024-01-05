package dao;

import model.Publicacao;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PublicacaoDAO {
    private Connection conectar(){
        Conexao conexao = new Conexao();
        return conexao.conectar();
    }
    // Criando uma nova publicacao DE PERFIL no banco de dados
    public int novaPublicacao(Publicacao publicacao){
        String createPublicacao = "insert into publicacao(texto, midia, id_autor) values (?, ?, ?)";
        String createPublicacaoCurtida = "insert into publicacao_curtida values (?,?)";
        int idPublicacao = 0;
        try (Connection con = conectar()) {
            PreparedStatement preparedStatementPublicacao = con.prepareStatement(createPublicacao);
            PreparedStatement preparedStatementPublicacaoCurtida = con.prepareStatement(createPublicacaoCurtida);
            preparedStatementPublicacao.setString(1, publicacao.getTexto());
            preparedStatementPublicacao.setString(2, publicacao.getMidia());
            preparedStatementPublicacao.setInt(3, publicacao.getAutor().getIdPessoa());
            preparedStatementPublicacao.executeUpdate();
            ResultSet idGerado = preparedStatementPublicacao.getGeneratedKeys();
            if (idGerado.next()){
                idPublicacao = idGerado.getInt(1);
            }
            /*preparedStatementPublicacaoCurtida.setInt(1, idPublicacao);
            preparedStatementPublicacaoCurtida.setInt(2, );*/
        } catch (Exception e) {
            System.out.println(e);
        }
        return buscaPublicacao(idPublicacao).getIdPublicacao();
    }
    // Buscando uma publicação
    public Publicacao buscaPublicacao(int id){
        String read = "select * from publicacao where idpublicacao = ?";
        Publicacao publicacaoRetornada = new Publicacao();
        try (Connection con = conectar()){
            PreparedStatement preparedStatement = con.prepareStatement(read);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                publicacaoRetornada.setIdPublicacao(rs.getInt(1));
                publicacaoRetornada.setTexto(rs.getString(2));
                publicacaoRetornada.setMidia(rs.getString(3));
                publicacaoRetornada.setData(rs.getString(5));
                publicacaoRetornada.setHora(rs.getString(6));
                // publicacaoRetornada.setNumeroCurtidas(); --> Depois que criar o curtidasDAO, modificar.
                // publicacaoRetornada.setAutor(); --> Depois que criar o MembroDAO, modificar.
                // publicacaoRetornada.setNumeroComentarios(); --> Depois que criar o ComentarioDAO, modificar.
            }
            preparedStatement.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return publicacaoRetornada;
    }

}
