package model;

import dao.MembroDAO;
import java.util.ArrayList;

public class Membro extends Pessoa {
    private String fotoPerfil;
    private String fotoFundo;
    private String nomeUsuario;
    private ArrayList<Integer> idMembrosSeguidores;
    private ArrayList<Integer> idMembrosSeguindos;
    private ArrayList<Integer> idComunidadesSeguindos;
    private ArrayList<Integer> idComunidadesParticipantes;
    private String descricao;
    private ArrayList<Integer> idPublicacaoCurtidas;
    private ArrayList<Integer> idPublicacoes;
    private boolean perfilVisivel;
    private ArrayList<Integer> idComentarios;

    public Membro(int idPessoa, String nome, String dataNascimento, String nomeUsuario, String email, String senha, String fotoPerfil, String fotoFundo, String descricao, ArrayList<Integer> curtidas) {
        super(idPessoa, nome, dataNascimento, email, senha);
        this.fotoPerfil = fotoPerfil;
        this.fotoFundo = fotoFundo;
        this.descricao = descricao;
        this.perfilVisivel = true;
        this.nomeUsuario = nomeUsuario;
        this.idPublicacaoCurtidas = curtidas;
        this.idPublicacoes = null;
        this.idMembrosSeguindos = null;
        this.idMembrosSeguidores = null;
        this.idComunidadesParticipantes = null;
        this.idComunidadesSeguindos = null;
        this.idComentarios = null;
    }

    public Membro(int idPessoa, String nome, String dataNascimento, String email, String senha, String fotoPerfil, String fotoFundo, String nomeUsuario, ArrayList<Integer> idMembrosSeguidores, ArrayList<Integer> idMembrosSeguindos, ArrayList<Integer> idComunidadesSeguindos, ArrayList<Integer> idComunidadesParticipantes, String descricao, ArrayList<Integer> idPublicacaoCurtidas, ArrayList<Integer> idPublicacoes, boolean perfilVisivel, ArrayList<Integer> idComentarios) {
        super(idPessoa, nome, dataNascimento, email, senha);
        this.fotoPerfil = fotoPerfil;
        this.fotoFundo = fotoFundo;
        this.nomeUsuario = nomeUsuario;
        this.idMembrosSeguidores = idMembrosSeguidores;
        this.idMembrosSeguindos = idMembrosSeguindos;
        this.idComunidadesSeguindos = idComunidadesSeguindos;
        this.idComunidadesParticipantes = idComunidadesParticipantes;
        this.descricao = descricao;
        this.idPublicacaoCurtidas = idPublicacaoCurtidas;
        this.idPublicacoes = idPublicacoes;
        this.perfilVisivel = perfilVisivel;
        this.idComentarios = idComentarios;
    }

    public Membro(String nome, String dataNascimento, String nomeUsuario, String email, String senha){
        super(nome, dataNascimento, email, senha);
        setNomeUsuario(nomeUsuario);
        setFotoPerfil(null);
        setFotoFundo(null);
        setDescricao(null);
        setPerfilVisivel(true);
        setCurtidas(null);
        setPublicacoes(null);
        setMembrosSeguidores(null);
        setMembrosSeguindos(null);
        setComunidadesParticipantes(null);
        setComunidadesSeguindos(null);
        setComentarios(null);
    }
    public Membro(Membro membro) {
        super(membro.getIdPessoa(), membro.getNome(), membro.getDataNascimento(), membro.getEmail(), membro.getSenha());
        this.fotoPerfil = membro.getFotoPerfil();
        this.fotoFundo = membro.getFotoFundo();
        this.descricao = membro.getDescricao();
        this.perfilVisivel = membro.isPerfilVisivel();
        this.nomeUsuario = membro.getNomeUsuario();
        this.idPublicacaoCurtidas = membro.getCurtidas();
        this.idPublicacoes = null;
        this.idMembrosSeguindos = null;
        this.idMembrosSeguidores = null;
        this.idComunidadesParticipantes = null;
        this.idComunidadesSeguindos = null;
        this.idComentarios = null;
    }

    public Membro(){}
    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getFotoFundo() {
        return fotoFundo;
    }

    public void setFotoFundo(String fotoFundo) {
        this.fotoFundo = fotoFundo;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public ArrayList<Integer> getMembrosSeguidores() {
        return idMembrosSeguidores;
    }

    public void setMembrosSeguidores(ArrayList<Integer> membrosSeguidores) {
        this.idMembrosSeguidores = membrosSeguidores;
    }

    public ArrayList<Integer> getMembrosSeguindos() {
        return idMembrosSeguindos;
    }

    public void setMembrosSeguindos(ArrayList<Integer> membrosSeguindos) {
        this.idMembrosSeguindos = membrosSeguindos;
    }

    public ArrayList<Integer> getComunidadesSeguindos() {
        return idComunidadesSeguindos;
    }

    public void setComunidadesSeguindos(ArrayList<Integer> comunidadesSeguindos) {
        this.idComunidadesSeguindos = comunidadesSeguindos;
    }

    public ArrayList<Integer> getComunidadesParticipantes() {
        return idComunidadesParticipantes;
    }

    public void setComunidadesParticipantes(ArrayList<Integer> comunidadesParticipantes) {
        this.idComunidadesParticipantes = comunidadesParticipantes;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ArrayList<Integer> getCurtidas() {
        return idPublicacaoCurtidas;
    }

    public void setCurtidas(ArrayList<Integer> curtidas) {
        this.idPublicacaoCurtidas = curtidas;
    }

    public ArrayList<Integer> getPublicacoes() {
        return idPublicacoes;
    }

    public void setPublicacoes(ArrayList<Integer> publicacoes) {
        this.idPublicacoes = publicacoes;
    }

    public boolean isPerfilVisivel() {
        return perfilVisivel;
    }

    public void setPerfilVisivel(boolean perfilVisivel) {
        this.perfilVisivel = perfilVisivel;
    }

    public ArrayList<Integer> getComentarios() {
        return idComentarios;
    }

    public void setComentarios(ArrayList<Integer> comentarios) {
        this.idComentarios = comentarios;
    }

    /* <-- Metodo pronto --> */
    public boolean realizarCadastro() {
        Membro novoMembro;
        try {
            MembroDAO membroDAO = new MembroDAO();
            if (!membroDAO.verificaMembro(this)) {

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
            for (int id : getMembrosSeguindo()) {
                if (membroSeguido.getIdPessoa() == id) {
                    membroJaSeguido = true;
                    break;
                }
            }
            if (!membroJaSeguido) {
                this.idMembrosSeguindos.add(membroSeguido.getIdPessoa());
                membroSeguido.getMembrosSeguindo().add(this.getIdPessoa());
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
    public ArrayList<Integer> getMembrosSeguindo() {
        return idMembrosSeguindos;
    }

    // --------------------------------------------------------------------------------------------------------
    public boolean seguirComunidade(Comunidade comunidadeSeguida) {
        SeguidorComunidade seguidorComunidade = new SeguidorComunidade(this);
        boolean comunidadeJaSeguida = false;
        try {
            for (Integer idComunidade : seguidorComunidade.getComunidadesSeguindos()) {
                if (comunidadeSeguida.getIdComunidade() == idComunidade) {
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

    public void excluirPublicacao(Publicacao publicacaoParaExcluir) {
        try {
            publicacaoParaExcluir.excluirPublicacao();
            idPublicacoes.remove(publicacaoParaExcluir);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void curtirPublicacao(Publicacao publicacao) {
        boolean publicacaoCurtida = false;
        for (int idPublicCurtidaExiste : idPublicacaoCurtidas) {
            if (publicacao.getIdPublicacao() == idPublicCurtidaExiste) {
                publicacaoCurtida = true;
                break;
            }
        }
        if (!publicacaoCurtida) {
            MembroDAO membroDAO = new MembroDAO();
            idPublicacaoCurtidas.add(publicacao.getIdPublicacao());
        }
    }

    public void excluirSeguidor(Membro seguidorExcluido) {
        idMembrosSeguidores.remove(seguidorExcluido);
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
        setFotoPerfil(fotoPerfiilEdicao);
        setFotoFundo(fotoFundoEdicao);
        setDescricao(descricaoEdicao);
    }

    public void excluirComentario(Comentario comentarioParaExcluir) {
        idComentarios.remove(comentarioParaExcluir.getIdComentario());
    }


    public int getNumeroSeguidores() {
        return idMembrosSeguidores.size() + idMembrosSeguidores.size();
    }

    public int getNumeroSeguindos() {
        return idMembrosSeguindos.size() + idComunidadesSeguindos.size();
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

    @Override
    public boolean fazerLogin() {
        return false;
    }
}
