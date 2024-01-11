package model;
<<<<<<< HEAD
<<<<<<< HEAD
public class Comentario extends Publicacao{

=======
=======

import dao.ComentarioDAO;

public class Comentario extends Publicacao {
>>>>>>> 21032bec43c5c09874469b309445397d72670e0e

    private int idComentario;

    public Comentario(String texto, String midia, Membro autor, Publicacao publicacao) {
        super(texto, midia, autor);
        this.setNumeroComentarios(this.getComentarios().size());
        this.setNumeroCurtidas(this.getCurtidas().size());
        comentarPublicacao(publicacao);
    }

    public Comentario() {
    }

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public void comentarPublicacao(Publicacao publicacao) {
        try {
            setIdPublicacao(publicacao.getIdPublicacao());
            ComentarioDAO comentarioDAO = new ComentarioDAO();
            Comentario comentario = comentarioDAO.criarComentario(this);
            this.setIdComentario(comentario.getIdComentario());
            this.setData(comentario.getData());
            this.setHora(comentario.getHora());
            this.setCurtidas(comentario.getCurtidas());
            this.setComentarios(comentario.getComentarios());
            this.setNumeroCurtidas(getCurtidas().size());
            this.setNumeroComentarios(getComentarios().size());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
<<<<<<< HEAD
>>>>>>> 55a84b35ab5013b178158265fae3591b37e69f37
=======
>>>>>>> 21032bec43c5c09874469b309445397d72670e0e
}
