package model;

import dao.ComunidadeDAO;

import java.io.Serializable;
import java.util.ArrayList;

public class Comunidade implements Serializable {
    private int idComunidade;
    private String nome;
    private int idCriador;
    private String fotoPerfil;
    private String fotoFundo;
    private String descricao;
    private ArrayList<Integer> idPublicacoes = new ArrayList<>();
    private ArrayList<Integer> idModeradores = new ArrayList<>();
    private ArrayList<Integer> idParticipantes = new ArrayList<>();
    private ArrayList<Integer> idSeguidores = new ArrayList<>();

    public Comunidade(int idComunidade, String nome, int idCriador, String fotoPerfil, String fotoFundo, String descricao, ArrayList<Integer> idPublicacoes, ArrayList<Integer> idModeradores, ArrayList<Integer> idParticipantes, ArrayList<Integer> idSeguidores) {
        this.idComunidade = idComunidade;
        this.nome = nome;
        this.idCriador = idCriador;
        this.fotoPerfil = fotoPerfil;
        this.fotoFundo = fotoFundo;
        this.descricao = descricao;
        this.idPublicacoes = idPublicacoes;
        this.idModeradores = idModeradores;
        this.idParticipantes = idParticipantes;
        this.idSeguidores = idSeguidores;
    }

    public Comunidade() {
    }

    public ParticipanteComunidade participarComunidade(Membro membro) {
        if (membro.isSeguidor(this)) {
            ParticipanteComunidade novoParticipante = new ParticipanteComunidade((SeguidorComunidade) membro);
            idParticipantes.add(novoParticipante.getIdPessoa());
            return novoParticipante;
        } else {
            return null;
        }

    }

    public ModeradorComunidade definirModerador(ParticipanteComunidade participanteComunidade) {
        ModeradorComunidade novoModerador = null;
        try {
            novoModerador = new ModeradorComunidade(participanteComunidade);
            idModeradores.add(novoModerador.getIdPessoa());
        } catch (Exception e) {
            System.out.println(e);
            novoModerador = null;
        }

        return novoModerador;

    }


    public void setIdCriador(int idCriador) {
        this.idCriador = idCriador;
    }

    public int getIdComunidade() {
        return idComunidade;
    }

    public void setIdComunidade(int idComunidade) {
        this.idComunidade = idComunidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public int getIdCriador() {
        return idCriador;
    }

    public String getFotoFundo() {
        return fotoFundo;
    }

    public void setFotoFundo(String fotoFundo) {
        this.fotoFundo = fotoFundo;
    }

    public ArrayList<Integer> getIdPublicacoes() {
        return idPublicacoes;
    }

    public void setPublicacoes(ArrayList<Integer> publicacoes) {
        this.idPublicacoes = publicacoes;
    }

    public ArrayList<Integer> getIdModeradores() {
        return idModeradores;
    }

    public void setModeradores(ArrayList<Integer> moderadores) {
        this.idModeradores = moderadores;
    }

    public ArrayList<Integer> getIdParticipantes() {
        return idParticipantes;
    }

    public void setParticipantes(ArrayList<Integer> idParticipantes) {
        this.idParticipantes = idParticipantes;
    }

    public ArrayList<Integer> getIdSeguidores() {
        return idSeguidores;
    }

    public void setSeguidor(ArrayList<Integer> seguidor) {
        this.idSeguidores = seguidor;
    }

    public boolean criarComunidade() {
        Comunidade novaComunidade;
        try {
            ComunidadeDAO comunidadeDAO = new ComunidadeDAO();
            if (!comunidadeDAO.verificaComunidade(this.getIdComunidade())) {
                System.out.println("verificou que a comunidade nao existe");
                System.out.println("id do autor: " + this.getIdCriador());
                novaComunidade = comunidadeDAO.criarComunidade(this);
                this.setIdComunidade(novaComunidade.getIdComunidade());
                System.out.println("TUDO CERTO");
                return true;
            } else {
                System.out.println("comunidade ja existe");
            }
            // ALterar logica para retornar caso a comunidade já exista
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean seguirComunidade(Membro membroSeguir) {
        boolean verificacaoSeguindo = true;
        for (int idMembroSeguindo : idSeguidores) {
            if (idMembroSeguindo == membroSeguir.getIdPessoa()) {
                verificacaoSeguindo = false;
                break;
            }
        }
        if (verificacaoSeguindo) {
            idSeguidores.add(membroSeguir.getIdPessoa());
            return true;
        }
        return false;

    }

    public ArrayList<Comunidade> listarComunidadesParticipantes(int idMembro) {
        return new ComunidadeDAO().listarComunidadesUsuario(idMembro);
    }

    public ArrayList<Comunidade> listarComunidadesParticipantes(int idMembro, int limit) {
        return new ComunidadeDAO().listarComunidades(limit);
    }

    public void excluirComunidade(model.Comunidade comunidade) {
        comunidade = null;
    }

    public void removerParticipanteComunidade(ParticipanteComunidade participante) {
        idParticipantes.remove(participante.getIdPessoa());
    }

    public void excluirPublicacao(Publicacao publicacao) {
        idPublicacoes.remove(publicacao.getIdPublicacao());
    }

    public void criarPublicacao(Publicacao publicacao) {
        Publicacao novaPublicacao = null;

        try {
            novaPublicacao = new Publicacao();
            idPublicacoes.add(novaPublicacao.getIdPublicacao());
        } catch (Exception e) {
            System.out.println(e);
            novaPublicacao = null;
        }

    }

    public void removerSeguidorComunidade(SeguidorComunidade seguidor) {
        idSeguidores.remove(seguidor.getIdPessoa());
    }

    public ArrayList<Comunidade> pesquisarComunidade(String query) {
        return new ComunidadeDAO().pesquisarComunidade(query);
    }
}