package model;
import java.util.ArrayList;

public class Membro extends Pessoa{
    private boolean perfilVisivel;
    private String fotoPerfiil;
    private String fotoFundo;
    private String descricao;
    private ArrayList<Membro> listaSeguidores = new ArrayList<>();
    private ArrayList<Membro> listaSeguindo1 = new ArrayList<>();
    private ArrayList<Comunidade> listaSeguindo2 = new ArrayList<>();
    private ArrayList<Publicacao> listaPublicacoes = new ArrayList<>();
    private ArrayList<Publicacao> listaCurtidas = new ArrayList<>();
    private ArrayList<Comentario> listaComentarios = new ArrayList<>();

    public Membro(int idPessoa, String nome, String dataNascimento, String email, String senha) {
        super(idPessoa, nome, dataNascimento, email, senha);
    }

    public boolean realizarCadastro(String email, String senha){
        //VERIFICAR SE AS INFORMAÇÕES JA ESTÃO CADASTRADAS
        //CASO NÃO MANDAR INFORMAÇÕES PARA O BD
        return true;
    }
    public boolean seguirMembro(Membro membroSeguido){
        boolean verificacaoSeguindo=true;
        for(Membro membroSeguidoExiste : listaSeguindo1){
            if(membroSeguido.getIdPessoa() == membroSeguidoExiste.getIdPessoa()){
                verificacaoSeguindo=false;
            }
        }
        if(verificacaoSeguindo){
            listaSeguindo1.add(membroSeguido);
            return true;
        }else{
            return false;
        }
    }
    public boolean seguirComunidade(Comunidade comunidadeSeguida){
        boolean verificacaoSeguindo=true;
        for(Comunidade comunidadeSeguidoExiste : listaSeguindo2){
            if(comunidadeSeguidoExiste.getId() == comunidadeSeguida.getId()){
                verificacaoSeguindo=false;
            }
        }
        if(verificacaoSeguindo){
            listaSeguindo2.add(comunidadeSeguida);
            return true;
        }else{
            return false;
        }
    }
    public boolean aceitarSolicitacao(){
        //NÃO SEI COMO FUNCIONARIA ESSE MÉTODO
        return true;
    }
    public boolean excluirPublicacao(Publicacao publicacaoParaExcluir){
        listaPublicacoes.remove(publicacaoParaExcluir);
        return true;
    }
    public void curtir(Publicacao publicacaoCurtida){
        boolean verificacaoCurtida=true;
        for(){

        }

        listaCurtidas.add(publicacaoCurtida);
    }
    public void excluirSeguidor(Membro seguidorExcluido){}
    public void pesquisarComunidade(){}
    public void pesquisarMembro(){}
    public void mudarVisibilidade(){}
    public void editarPerfil(){}
    public void excluirComentario(Comentario comentarioParaExcluir){}
    public boolean isPaticipante(){return true;}
    public boolean isSeguidor(){return true;}
    public boolean isModerador(){return true;}

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

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int numeroSeguidores(){
        return listaSeguidores.size();
    }
    public int numeroSeguindo(){return listaSeguindo.size();}
}
