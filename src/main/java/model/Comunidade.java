package model;

import java.util.*;

public class Comunidade {
    private String nome;
    private ModeradorComunidade criador; // -> Por ser criador, dá para passar o criador logo como ModeradorComunidade
    private String fotoPerfil;
    private String fotoFundo;
    private String descricao;
    private ArrayList<Publicacao> publicacoes = new ArrayList<>();
    private ArrayList<ModeradorComunidade> moderadores = new ArrayList<ModeradorComunidade>();
    private int numeroParticipantes;
    private ArrayList<ParticipanteComunidade> participantes = new ArrayList<ParticipanteComunidade>();
    private int numeroSeguidores;
    private ArrayList<SeguidorComunidade> seguidores = new ArrayList<SeguidorComunidade>();
    private boolean participanteAceito;


    public Comunidade(String nome, Membro criador, String fotoPerfil, String fotoFundo, String descricao) {
        this.nome = nome;
        this.criador = definirModerador(participarComunidade(criador)); // -> Define o criador como moderador, o transformando em participante primeiro e depois em moderador.
        this.fotoPerfil = fotoPerfil;
        this.fotoFundo = fotoFundo;
        this.descricao = descricao;
        this.numeroParticipantes = 1;

    }

    public Comunidade(String nome, Membro criador, String fotoPerfil, String fotoFundo, String descricao, ArrayList<Publicacao> publicacoes, ArrayList<ModeradorComunidade> moderadores, int numeroParticipantes, ArrayList<ParticipanteComunidade> participantes, int numeroSeguidores, ArrayList<SeguidorComunidade> seguidor) {
        this.nome = nome;
        this.criador = criador; // -> Se aceitar minhas sugestões esse código dará erro
        this.fotoPerfil = fotoPerfil;
        this.fotoFundo = fotoFundo;
        this.descricao = descricao;
        this.publicacoes = publicacoes;
        this.moderadores = moderadores;
        this.numeroParticipantes = numeroParticipantes;
        this.participantes = participantes;
        this.numeroSeguidores = numeroSeguidores;
        this.seguidores = seguidor;
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

    public void setCriador(Membro criador) {
        this.criador = criador;
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

    public int getNumeroParticipantes() {
        return numeroParticipantes;
    }

    public void setNumeroParticipantes(int numeroParticipantes) {
        this.numeroParticipantes = numeroParticipantes;
    }

    public ArrayList<ParticipanteComunidade> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(ArrayList<ParticipanteComunidade> participantes) {
        this.participantes = participantes;
    }

    public int getNumeroSeguidores() {
        return numeroSeguidores;
    }

    public void setNumeroSeguidores(int numeroSeguidores) {
        this.numeroSeguidores = numeroSeguidores;
    }

    public ArrayList<SeguidorComunidade> getSeguidor() {
        return seguidores;
    }

    public void setSeguidor(ArrayList<SeguidorComunidade> seguidor) {
        this.seguidores = seguidor;
    }

    public boolean seguirComunidade() {
        return true;
    }

    //     Sugestão de Alteração
    //              |
    //              |
    //              V
    public ParticipanteComunidade participarComunidade(Membro membro) {
        // Na minha opinião esta á lógica mais correta
        if (membro.isSeguidor(this)) {
            ParticipanteComunidade novoParticipante = new ParticipanteComunidade(membro);
            participantes.add(novoParticipante);
            return novoParticipante;
        } else {
            return null;
        }
        //ParticipanteComunidade participante = new participante(seguidor);//puxar do seguidor as informações

        //participantes.add(participante);//adicionar seguidor em participantes

        /*System.out.println(seguidor + " É o novo participante da comunidade");*/ // ->> Sysout faz a classe perder a autonomia

    }
    //     Sugestão de Alteração
    //              |
    //              |
    //              V

    public ModeradorComunidade definirModerador(ParticipanteComunidade participanteComunidade) {
        //ModeradorComunidade moderador = new moderador(participante)//Instanciar participante
        //moderadores.add(moderador);//adicionar participante em moderadores

        // Na minha opinião, o seguinte código está com a lógica mais correta |
        //                                                                    |
        //                                                                    V
        ModeradorComunidade novoModerador = null;
        try {
            novoModerador = new ModeradorComunidade(participanteComunidade);
            moderadores.add(novoModerador); // adiciona o novo moderador no ArrayList de moderadores
        } catch (Exception e) {
            System.out.println(e);
            novoModerador = null;
        }

        return novoModerador;

        /*System.out.println(seguidores + " É o novo moderador da comunidade");*/ // ->> Sysout faz a classe perder a autonomia
    }

    public void excluirComunidade(Comunidade comunidade) {
        comunidade = null;
        System.out.println("A comunidade foi apagada!");// ->> Sysout faz a classe perder a autonomia
    }

    public void removerParticipanteComunidade(ParticipanteComunidade participante) {
        participantes.remove(participante);
    }

    public void excluirPublicacao(Publicacao publicacao) {
        publicacoes.remove(publicacao);
    }

    public void criarPublicacao() {
        //Criar construtor Publicacao e instanciar o objeto para ser adicionado em publicacoes
    }

    public void removerSeguidorComunidade(SeguidorComunidade seguidor) {
        seguidores.remove(seguidor);
    }

}
