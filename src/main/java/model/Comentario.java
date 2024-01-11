package model;
import dao.ComentarioDAO;

public class Comentario extends Publicacao {

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
}
