package model;

import dao.ComentarioDAO;

public class Comentario extends Publicacao {

    private int idComentario;

    public Comentario(int idPublicacao, String texto, int idAutor) {
        super(idPublicacao);
        Comentario comentario;
        this.setTexto(texto);
        this.setAutor(new Membro(idAutor));
        try {
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

    public Comentario() {
    }

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }
}
