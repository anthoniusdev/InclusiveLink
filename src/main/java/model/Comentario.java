package model;

import dao.ComentarioDAO;

import java.util.ArrayList;

public class Comentario extends Publicacao {

    private int idComentario;

    public Comentario(int idPublicacao, String texto, int idAutor) {
    }

    public Comentario(int idComentario) {
        try {
            this.setIdComentario(idComentario);
            Comentario comentario = new ComentarioDAO().retornaComentario(idComentario);
            this.setIdPublicacao(comentario.getIdPublicacao());
            this.setCurtidas(comentario.getCurtidas());
            this.setComentarios(comentario.getComentarios());
            this.setAutor(comentario.getAutor());
            this.setTexto(comentario.getTexto());
            this.setNumeroComentarios(comentario.getNumeroComentarios());
            this.setNumeroCurtidas(comentario.getNumeroCurtidas());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Comentario() {
    }

    public void novoComentario(int idPublicacao, String texto, int idAutor) {
        try {
            Comentario comentario;
            this.setIdPublicacao(idPublicacao);
            this.setTexto(texto);
            this.setAutor(new Membro(idAutor));
            ComentarioDAO comentarioDAO = new ComentarioDAO();
            comentario = comentarioDAO.criarComentario(idPublicacao, texto, this.getAutor().getIdPessoa());
            this.setIdComentario(comentario.getIdComentario());
            this.setData(comentario.getData());
            this.setHora(comentario.getHora());
            this.setCurtidas(comentario.getCurtidas());
            this.setNumeroCurtidas(getCurtidas().size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public void curtirComentario(int idMembro) {
        new ComentarioDAO().curtirComentario(this.getIdComentario(), idMembro);
    }

    public void descurtirComentario(int idMembro) {
        new ComentarioDAO().descurtirComentario(this.getIdComentario(), idMembro);
    }

    public ArrayList<Comentario> obterComentarios(int idPublicacao, int intervalo, int quantidadeComentarios) {
        try {
            return new ComentarioDAO().comentarios(idPublicacao, intervalo, quantidadeComentarios);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
