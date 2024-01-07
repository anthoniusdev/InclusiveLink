package model;

import dao.MembroDAO;

import java.util.ArrayList;

public class Membro extends Pessoa {
    private boolean perfilVisivel;
    private String nomeUsuario;
    private String fotoPerfil;
    private String fotoFundo;
    private String descricao;
    private ArrayList<Membro> membrosSeguidores;
    private ArrayList<Membro> membrosSeguindos;
    private ArrayList<Comunidade> comunidadesSeguindos;
    private ArrayList<Comunidade> comunidadesParticipantes;
    private ArrayList<Publicacao> publicacoes;
    private ArrayList<Publicacao> curtidas;
    private ArrayList<Comentario> comentarios;

    public Membro(int idPessoa, String nome, String dataNascimento, String nomeUsuario, String email, String senha, String fotoPerfil, String fotoFundo, String descricao, ArrayList<Publicacao> curtidas) {
        super(idPessoa, nome, dataNascimento, email, senha);
        this.fotoPerfil = fotoPerfil;
        this.fotoFundo = fotoFundo;
        this.descricao = descricao;
        this.perfilVisivel = true;
        this.nomeUsuario = nomeUsuario;
        this.curtidas = curtidas;
        this.publicacoes = null;
        this.membrosSeguindos = null;
        this.membrosSeguidores = null;
        this.comunidadesParticipantes = null;
        this.comunidadesSeguindos = null;
        this.comentarios = null;
    }
    public Membro(Membro membro){
        super(membro.getIdPessoa(), membro.getNome(), membro.getDataNascimento(), membro.getEmail(), membro.getSenha());
        this.fotoPerfil = membro.getFotoPerfil();
        this.fotoFundo = membro.getFotoFundo();
        this.descricao = membro.getDescricao();
        this.perfilVisivel = membro.isPerfilVisivel();
        this.nomeUsuario = membro.getNomeUsuario();
        this.curtidas = membro.getCurtidas();
        this.publicacoes = membro.getPublicacoes();
        this.membrosSeguindos = membro.getMembrosSeguindo();
        this.membrosSeguidores = membro.getMembrosSeguidores();
        this.comunidadesParticipantes = membro.getComunidadesParticipantes();
        this.comunidadesSeguindos = membro.getComunidadesSeguindos();
        this.comentarios = membro.getComentarios();
    }
    public ArrayList<Comunidade> getComunidadesSeguindos() {
        return comunidadesSeguindos;
    }

    public ArrayList<Comunidade> getComunidadesParticipantes() {
        return comunidadesParticipantes;
    }

    /* <-- Metodo pronto --> */
    public boolean realizarCadastro() {
        Membro novoMembro;
        try {
            MembroDAO membroDAO = new MembroDAO();
            if(!membroDAO.verificaMembro(this)){
                novoMembro = membroDAO.realizarCadastro(this);
                this.setIdPessoa(novoMembro.getIdPessoa());
            }
            return membroDAO.verificaMembro(this);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    // --------------------------------------------------------------------------------------------------------
    public boolean seguirMembro(Membro membroSeguido) {
        boolean membroJaSeguido = false;
        try {
            for (Membro membro : getMembrosSeguindo()) {
                if (membroSeguido.getIdPessoa() == membro.getIdPessoa()) {
                    membroJaSeguido = true;
                    break;
                }
            }
            if (!membroJaSeguido) {
                this.membrosSeguindos.add(membroSeguido);
                membroSeguido.getMembrosSeguindo().add(this);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    // --------------------------------------------------------------------------------------------------------
    public ArrayList<Membro> getMembrosSeguindo() {
        return membrosSeguindos;
    }

    // --------------------------------------------------------------------------------------------------------
    public boolean seguirComunidade(Comunidade comunidadeSeguida) {
        SeguidorComunidade seguidorComunidade = new SeguidorComunidade(this);
        boolean comunidadeJaSeguida = false;
        try {
            for (Comunidade comunidade : seguidorComunidade.getComunidadesSeguindos()) {
                if (comunidadeSeguida.getIdComunidade() == comunidade.getIdComunidade()) {
                    comunidadeJaSeguida = true;
                    break;
                }
            }
            if (!comunidadeJaSeguida) {
                comunidadeSeguida.seguirComunidade(this);
                return seguidorComunidade.seguirComunidade(comunidadeSeguida);
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
// --------------------------------------------------------------------------------------------------------

    public boolean aceitarSolicitacao() {
        //NÃO SEI COMO FUNCIONARIA ESSE MÉTODO
        return true;
    }

    public boolean excluirPublicacao(Publicacao publicacaoParaExcluir) {
        publicacoes.remove(publicacaoParaExcluir);
        return true;
    }

    public void curtirPublicacao(Publicacao publicacao) {
        boolean publicacaoCurtida = false;
        for (Publicacao publicCurtidaExiste : curtidas) {
            if (publicacao.getIdPublicacao() == publicCurtidaExiste.getIdPublicacao()) {
                publicacaoCurtida = true;
                break;
            }
        }
        if (!publicacaoCurtida) {
            MembroDAO membroDAO = new MembroDAO();
            curtidas.add(publicacao);
        }
    }

    public void excluirSeguidor(Membro seguidorExcluido) {
        membrosSeguidores.remove(seguidorExcluido);
    }

    public void pesquisarComunidade() {
        //NÃO SEI COMO FUNCIONARIA ESSE MÉTODO - PROVAVELMENTE ENVOLVE O BD
    }

    public void pesquisarMembro() {
        //NÃO SEI COMO FUNCIONARIA ESSE MÉTODO - PROVAVELMENTE ENVOLVE O BD
    }

    public void mudarVisibilidade() {
        perfilVisivel = !perfilVisivel;
    }

    public void editarPerfil(String nomeEdicao, String fotoPerfiilEdicao, String fotoFundoEdicao, String descricaoEdicao) {
        setNome(nomeEdicao);
        setFotoPerfiil(fotoPerfiilEdicao);
        setFotoFundo(fotoFundoEdicao);
        setDescricao(descricaoEdicao);
    }

    public void excluirComentario(Comentario comentarioParaExcluir) {
        comentarios.remove(comentarioParaExcluir);
    }

    public boolean isPerfilVisivel() {
        return perfilVisivel;
    }

    public void setPerfilVisivel(boolean perfilVisivel) {
        this.perfilVisivel = perfilVisivel;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfiil(String fotoPerfiil) {
        this.fotoPerfil = fotoPerfil;
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

    public int getNumeroSeguidores() {
        return membrosSeguidores.size() + membrosSeguidores.size();
    }

    public int getNumeroSeguindos() {
        return membrosSeguindos.size() + comunidadesSeguindos.size();
    }

    public boolean isParticipante(Comunidade comunidade) {
        boolean isParticipante = false;
        for (ParticipanteComunidade participanteComunidade : comunidade.getParticipantes()) {
            if (participanteComunidade.getIdPessoa() == this.getIdPessoa()) {
                isParticipante = true;
                break;
            }
        }
        return isParticipante;
    }

    public boolean isSeguidor(Comunidade comunidade) {
        boolean isSeguidor = false;
        for (SeguidorComunidade seguidorComunidade : comunidade.getSeguidores()) {
            if (seguidorComunidade.getIdPessoa() == this.getIdPessoa()) {
                isSeguidor = true;
                break;
            }
        }
        return isSeguidor;
    }

    public boolean isModerador(Comunidade comunidade) {
        boolean isModerador = false;
        for (ModeradorComunidade moderadorComunidade : comunidade.getModeradores()) {
            if (moderadorComunidade.getIdPessoa() == this.getIdPessoa()) {
                isModerador = true;
                break;
            }
        }
        return isModerador;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public ArrayList<Publicacao> getCurtidas() {
        return curtidas;
    }

    public ArrayList<Comentario> getComentarios() {
        return comentarios;
    }

    public ArrayList<Membro> getMembrosSeguidores() {
        return membrosSeguidores;
    }

    public ArrayList<Membro> getMembrosSeguindos() {
        return membrosSeguindos;
    }

    public ArrayList<Publicacao> getPublicacoes() {
        return publicacoes;
    }
}
