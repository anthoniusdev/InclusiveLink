package model;
<<<<<<< HEAD
public class Comentario extends Publicacao{

=======

public class Comentario extends Publicacao{
    private int idComentario;

    public Comentario(String texto, String midia, Membro autor, Publicacao publicacao) {
        super(texto, midia, autor);
        this.setNumeroComentarios(this.getComentarios().size());
        this.setNumeroCurtidas(this.getCurtidas().size());
        comentarPublicacao(publicacao);
    }

    public int getIdComentario() {
        return idComentario;
    }
    public void comentarPublicacao(Publicacao publicacao){
        try {
            ComentarioDAO comentarioDAO = new ComentarioDAO();
            comentarioDAO.criarComentario(this, publicacao);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
>>>>>>> 55a84b35ab5013b178158265fae3591b37e69f37
}