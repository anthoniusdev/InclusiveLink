package model;

import java.util.ArrayList;

public class ModeradorComunidade extends ParticipanteComunidade{

    private ArrayList<ParticipanteComunidade> participantes = new ArrayList<ParticipanteComunidade>();

    private ArrayList<ModeradorComunidade> moderadores = new ArrayList<ModeradorComunidade>();


    public ModeradorComunidade(int idPessoa, String nome, String dataNascimento, String email, String senha) {
        super(idPessoa, nome, dataNascimento, email, senha);
    }



}
