package dao;

import model.Comentario;
import model.Membro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ComentarioDAO {
    private Connection conectar(){
        Conexao conexao = new Conexao();
        return conexao.conectar();
    }
    public Comentario criarComentario(Comentario comentario){

    }
    public Comentario retornaComentario(int idComentario){
        String read = "select * from publicacao_comentario where idComentario = ?";
        Membro autor;
        Comentario comentario = new Comentario();
        try (Connection con = conectar()){
            PreparedStatement preparedStatement = con.prepareStatement(read);
            preparedStatement.setInt(1, idComentario);
            ResultSet rs = preparedStatement.executeQuery();
            // Pega o resultado da consulta ao banco de dados  |
            //                                                 v
            comentario.setIdComentario(rs.getInt(1));
            comentario.setIdPublicacao(rs.getInt(2));
            comentario.setTexto(rs.getString(3));
            comentario.setMidia(rs.getString(4));
            MembroDAO membroDAO = new MembroDAO();
            comentario.setAutor(membroDAO.retornaMembro(rs.getInt(5)));
            comentario.setData(rs.getString(6));
            comentario.setHora(rs.getString(7));
            comentario.setCurtidas(curtidas(comentario.getIdComentario()));
            comentario.setNumeroCurtidas(comentario.getCurtidas().size());
            // comentario.setNumeroComentarios(); --> Verificar depois
            // comentario.setComentarios(); --> Verificar depois
            return comentario;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Membro> curtidas(int idComentario){
        String read = "select idMembro from comentario_curtida where idcomentario = ?";
        ArrayList<Membro> membros = new ArrayList<>();
        try (Connection con = conectar()){
            PreparedStatement preparedStatement = con.prepareStatement(read);
            preparedStatement.setInt(1, idComentario);
            ResultSet rs = preparedStatement.executeQuery();
            MembroDAO membroDAO = new MembroDAO();
            while (rs.next()){
                membros.add(membroDAO.retornaMembro(rs.getInt(1)));
            }
            return membros;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Comentario> comentarios(int idPublicacao) {
        String read = "select idComentario from publicacao_comentario where idPublicacao = ?";
        ArrayList<Comentario> comentarios = new ArrayList<>();
        try (Connection con = conectar()) {
            PreparedStatement preparedStatement = con.prepareStatement(read);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                comentarios.add(retornaComentario(rs.getInt(1)));
            }
            return comentarios;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
