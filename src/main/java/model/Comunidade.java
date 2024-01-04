package model;

import java.util.*;

public class Comunidade {
    private int id;
    private String nome;
    private Membro membro;
    private ModeradorComunidade criador; // -> Por ser criador, dá para passar o criador logo como ModeradorComunidade
    private ModeradorComunidade moderador;
    private String fotoPerfil;
    private String fotoFundo;
    private String descricao;
    private int idComunidade;
    private ArrayList<Publicacao> publicacoes = new ArrayList<>();
    private Publicacao publicacao;
    private ArrayList<ModeradorComunidade> moderadores = new ArrayList<ModeradorComunidade>();
    private ArrayList<ParticipanteComunidade> participantes = new ArrayList<ParticipanteComunidade>();
    private ArrayList<SeguidorComunidade> seguidores = new ArrayList<SeguidorComunidade>();



    public Comunidade(String nome, Membro criador, String fotoPerfil, String fotoFundo, String descricao, int idComunidade) {
        this.nome = nome;
        this.criador = definirModerador(participarComunidade(criador)); // -> Define o criador como moderador, o transformando em participante primeiro e depois em moderador.
        this.fotoPerfil = fotoPerfil;
        this.fotoFundo = fotoFundo;
        this.descricao = descricao;
        this.participantes.size();
        this.idComunidade = idComunidade;

    }

    public Comunidade(String nome, Membro criador, String fotoPerfil, String fotoFundo, String descricao, ArrayList<Publicacao> publicacoes, ArrayList<ModeradorComunidade> moderadores, int numeroParticipantes, ArrayList<ParticipanteComunidade> participantes, int numeroSeguidores, ArrayList<SeguidorComunidade> seguidor) {
        this.nome = nome;
        this.fotoPerfil = fotoPerfil;
        this.fotoFundo = fotoFundo;
        this.descricao = descricao;
        this.publicacoes = publicacoes;
        this.moderadores = moderadores;
        this.participantes.size();
        this.participantes = participantes;
        this.seguidores.size();
        this.seguidores = seguidor;
    }

    public void setCriador(ModeradorComunidade criador) {
        this.criador = criador;
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

    public Membro getCriador() {
        return criador;
    }

    public String getFotoFundo() {
        return fotoFundo;
    }

    public void setFotoFundo(String fotoFundo) {
        this.fotoFundo = fotoFundo;
    }

    public ArrayList<Publicacao> getPublicacoes() {
        return publicacoes;
    }

    public void setPublicacoes(ArrayList<Publicacao> publicacoes) {
        this.publicacoes = publicacoes;
    }

    public ArrayList<ModeradorComunidade> getModeradores() {
        return moderadores;
    }

    public void setModeradores(ArrayList<ModeradorComunidade> moderadores) {
        this.moderadores = moderadores;
    }

    public ArrayList<ParticipanteComunidade> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(ArrayList<ParticipanteComunidade> participantes) {
        this.participantes = participantes;
    }


    public ArrayList<SeguidorComunidade> getSeguidor() {
        return seguidores;
    }

    public void setSeguidor(ArrayList<SeguidorComunidade> seguidor) {
        this.seguidores = seguidor;
    }

<<<<<<< HEAD
    public boolean seguirComunidade(Membro membro) {
=======
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public boolean seguirComunidade(){
>>>>>>> Abimael
        return true;
    }


    public ParticipanteComunidade participarComunidade(Membro membro) {
        // Na minha opinião esta á lógica mais correta
        if (membro.isSeguidor(idComunidade)) {
            ParticipanteComunidade novoParticipante = new ParticipanteComunidade(membro.getIdPessoa(), membro.getNome(), membro.getDataNascimento(), membro.getEmail(), membro.getSenha());
            participantes.add(novoParticipante);
            return novoParticipante;
        } else {
            return null;
        }

    }

    public ModeradorComunidade definirModerador(ParticipanteComunidade participanteComunidade) {
        ModeradorComunidade novoModerador = null;
        try {
            novoModerador = new ModeradorComunidade(moderador.getIdPessoa(), moderador.getNome(), moderador.getDataNascimento(), moderador.getEmail(), moderador.getSenha());
            moderadores.add(novoModerador); // adiciona o novo moderador no ArrayList de moderadores
        } catch (Exception e) {
            System.out.println(e);
            novoModerador = null;
        }

        return novoModerador;

    }

    public void excluirComunidade(Comunidade comunidade) {
        comunidade = null;
    }

    public void removerParticipanteComunidade(ParticipanteComunidade participante) {
        participantes.remove(participante);
    }

    public void excluirPublicacao(Publicacao publicacao) {
        publicacoes.remove(publicacao);
    }

    public void criarPublicacao() {
        Publicacao novaPublicacao = null;

        try{
            novaPublicacao = new Publicacao(publicacao.getTexto(), publicacao.getMidia(), publicacao.getAutor());
            publicacoes.add(novaPublicacao);

        }catch (Exception e){
            System.out.println(e);
            novaPublicacao= null;
        }

    }

    public void removerSeguidorComunidade(SeguidorComunidade seguidor) {
        seguidores.remove(seguidor);
    }

}
