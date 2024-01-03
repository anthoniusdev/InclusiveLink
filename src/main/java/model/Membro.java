package model;
import java.util.ArrayList;

public class Membro {
    private boolean perfilVisivel;
    private String fotoPerfiil;
    private String fotoFundo;
    private String nome;
    private int numeroSeguindo;
    private int numeroSeguidores;
    private String descricao;
    private ArrayList<Membro> listaSeguidores = new ArrayList<>();
    private ArrayList<Membro> listaSeguindo = new ArrayList<>();
    //private ArrayList<Publicacao> listaPublicacoes = new ArrayList<>();
    //private ArrayList<Publicacao> listaCurtidas = new ArrayList<>();
    //private ArrayList<Comentario> listaComentarios = new ArrayList<>();

    //public void criarComunidade(){}?
    public boolean realizarCadastro(){return true;}
    public boolean seguirMembro(){return true;}
    public boolean seguirComunidade(){return true;}
    public boolean aceitarSolicitacao(){return true;}
    public boolean criarPublicacao(){return true;}
    public boolean excluirPublicacao(){return true;}
    public void curtir(){}
    public void excluirSeguidor(){}
    public void pesquisarComunidade(){}
    public void pesquisarMembro(){}
    public void mudarVisibilidade(){}
    public void editarPerfil(){}
    public void comentarPublicao(){}
    public void excluirComentario(){}

    public boolean isPerfilVisivel() {
        return perfilVisivel;
    }
    public void setPerfilVisivel(boolean perfilVisivel) {
        this.perfilVisivel = perfilVisivel;
    }

    public String getFotoPerfiil() {
        return fotoPerfiil;
    }
    public void setFotoPerfiil(String fotoPerfiil) {
        this.fotoPerfiil = fotoPerfiil;
    }

    public String getFotoFundo() {
        return fotoFundo;
    }
    public void setFotoFundo(String fotoFundo) {
        this.fotoFundo = fotoFundo;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumeroSeguindo() {
        return numeroSeguindo;
    }
    public void setNumeroSeguindo(int numeroSeguindo) {
        this.numeroSeguindo = numeroSeguindo;
    }

    public int getNumeroSeguidores() {
        return numeroSeguidores;
    }
    public void setNumeroSeguidores(int numeroSeguidores) {
        this.numeroSeguidores = numeroSeguidores;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
