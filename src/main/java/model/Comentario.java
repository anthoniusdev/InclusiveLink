package model;

import dao.ComentarioDAO;

public class Comentario extends Publicacao{

    private int idComentario;

    public Comentario(String texto, String midia, Membro autor, Publicacao publicacao) {
        super(texto, midia, autor);
        this.setNumeroComentarios(this.getComentarios().size());
        this.setNumeroCurtidas(this.getCurtidas().size());
        comentarPublicacao(publicacao);
    }
    public Comentario(){}

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public int getIdComentario() {
        return idComentario;
    }
    public void comentarPublicacao(Publicacao publicacao){
        try {
            setIdPublicacao(publicacao.getIdPublicacao());
            ComentarioDAO comentarioDAO = new ComentarioDAO();
            comentarioDAO.criarComentario(this);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
