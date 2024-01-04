package model;

public class ParticipanteComunidade extends SeguidorComunidade{
    /*
    public ParticipanteComunidade(int idPessoa, String nome, String dataNascimento, String email, String senha) {
        super(idPessoa, nome, dataNascimento, email, senha);
    }*/

    public ParticipanteComunidade(SeguidorComunidade  seguidorComunidade){
        super(seguidorComunidade.getIdPessoa(), seguidorComunidade.getNome(), seguidorComunidade.getDataNascimento(), seguidorComunidade.getEmail(), seguidorComunidade.getSenha());

    }
}
