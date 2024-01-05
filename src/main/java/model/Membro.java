package model;

import java.util.ArrayList;

public class Membro extends Pessoa {
    private boolean perfilVisivel = true;
    private String fotoPerfiil;
    private String fotoFundo;
    private String descricao;
    private ArrayList<Membro> listaSeguidores;
    private ArrayList<Membro> membrosSeguindo;
    private ArrayList<Publicacao> publicacoes;
    private ArrayList<Publicacao> curtidas;
    private ArrayList<Comentario> comentarios;

    public Membro(int idPessoa, String nome, String dataNascimento, String email, String senha, String fotoPerfiil, String fotoFundo, String descricao) {
        super(idPessoa, nome, dataNascimento, email, senha);
        this.fotoPerfiil = fotoPerfiil;
        this.fotoFundo = fotoFundo;
        this.descricao = descricao;
    }

    public boolean realizarCadastro(String email, String senha) {
        //VERIFICAR SE AS INFORMAÇÕES JA ESTÃO CADASTRADAS
        //CASO NÃO MANDAR INFORMAÇÕES PARA O BD
        return true;
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
                this.membrosSeguindo.add(membroSeguido);
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
        return membrosSeguindo;
    }

    // --------------------------------------------------------------------------------------------------------
    public boolean seguirComunidade(Comunidade comunidadeSeguida) {
        SeguidorComunidade seguidorComunidade = new SeguidorComunidade(this);
        boolean comunidadeJaSeguida = false;
        try {
            for (Comunidade comunidade : seguidorComunidade.getSeguindoComunidades()) {
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

    public boolean curtir(Publicacao publicacaoCurtida) {
        boolean verificacaoCurtida = true;
        for (Publicacao publicCurtidaExiste : curtidas) {
            if (publicacaoCurtida.getIdPublicacao() == publicCurtidaExiste.getIdPublicacao()) {
                verificacaoCurtida = false;
            }
        }
        if (verificacaoCurtida) {
            curtidas.add(publicacaoCurtida);
            return true;
        } else {
            return false;
        }
    }

    public void excluirSeguidor(Membro seguidorExcluido) {
        listaSeguidores.remove(seguidorExcluido);
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

    public int numeroSeguidores() {
        return listaSeguidores.size();
    }

    public int numeroSeguindo() {
        return listaSeguindo1.size() + listaSeguindo2.size();
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

}
