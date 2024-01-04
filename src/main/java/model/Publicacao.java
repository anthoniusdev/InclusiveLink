package model;

import java.util.ArrayList;
import java.util.Comparator;

public class Publicacao {
    private int idPublicacao;
    private String texto;
    private String midia;
    private Membro autor;
    private int numeroCurtidas;
    private ArrayList<Membro> curtidas;
    private int numeroComentarios;
    private ArrayList<Comentario> comentarios;
    private String data;
    private String hora;

    public Publicacao(String texto, String midia, Membro autor) {
        setTexto(texto);
        setMidia(midia);
        numeroCurtidas = 0;
        curtidas = new ArrayList<Membro>();
        numeroComentarios = 0;
        comentarios = new ArrayList<Comentario>();
        setAutor(autor);
        try {
            PublicacaoDAO publicacaoDAO = new PublicacaoDAO();
            this = publicacaoDAO.novaPublicacao(this);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Publicacao(String texto, String midia, Membro autor, Comunidade comunidade) {
        if (autor.isParticipante(comunidade) || autor.isParticipante(comunidade)) {
            try {
                autor = new ParticipanteComunidade(autor);
                criarPublicacao(texto, midia);
                setAutor(autor);
                PublicacaoDAO publicacaoDAO = new PublicacaoDAO();
                comunidade.criarPublicacao(this);
                this = publicacaoDAO.novaPublicacao(this);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    public Publicacao(){}
    public int getIdPublicacao() {
        return idPublicacao;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getMidia() {
        return midia;
    }

    public void setMidia(String midia) {
        this.midia = midia;
    }

    public Membro getAutor() {
        return autor;
    }

    public void setAutor(Membro autor) {
        this.autor = autor;
    }

    public int getNumeroCurtidas() {
        return numeroCurtidas;
    }

    public boolean curtir(Membro membro) {
        try {
            PublicacaoDAO publicacaoDAO = new PublicacaoDAO();
            publicacaoDAO.curtir(this, membro);
            numeroCurtidas++;
            membro.curtirPublicacao(this);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public ArrayList<Membro> getCurtidas() {
        return curtidas;
    }

    public ArrayList<Comentario> getComentarios() {
        return comentarios;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }


    public void excluirPublicacao(Membro autor) {
        try {
            PublicacaoDAO publicacaoDAO = new PublicacaoDAO();
            publicacaoDAO.excluirPublicacao(this);
            autor.excluirPublicacao(this);
            this.getCurtidas().clear(); // Excluir curtidas no DAO -> ArrayList
            this.getComentarios().clear(); // Excluir comentarios no DAO -> ArrayList
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void excluirPublicacao(Membro autor, Comunidade comunidade) {
        if (autor.isParticipante(comunidade) || autor.isModerador(comunidade)){
            try {
                PublicacaoDAO publicacaoDAO = new PublicacaoDAO();
                publicacaoDAO.excluirPublicacao(this);
                autor.excluirPublicacao(this);
                this.getCurtidas().clear(); // Excluir curtidas no DAO -> ArrayList
                this.getComentarios().clear(); // Excluir comentarios no DAO -> ArrayList
                comunidade.excluirPublicacao(this);
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }
    // metodos que eu acho que deveria estar na classe comentario:
    /*public void adicionarComentario(Comentario comentario, Comunidade comunidade){
        if (comentario.getAutor().isParticipante(comunidade) || comentario.getAutor().isModerador(comunidade)){
            try {
                PublicacaoDAO publicacaoDAO = new PublicacaoDAO();
                publicacaoDAO.adicionarComentario(comentario);
                comentarios.add(comentario);
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }
    public void adicionarComentario(Comentario comentario){
        try {
            PublicacaoDAO publicacaoDAO = new PublicacaoDAO();
            publicacaoDAO.adicionarComentario(comentario, comentario.getAutor());
            comentarios.add(comentario);
        }catch (Exception e){
            System.out.println(e);
        }
    }*/
}
