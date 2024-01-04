package model;

public class SeguidorComunidade extends Membro{
    public SeguidorComunidade(Membro  membroParaSeguidor){
        super(membroParaSeguidor.getIdPessoa(), membroParaSeguidor.getNome(), membroParaSeguidor.getDataNascimento(), membroParaSeguidor.getEmail(), membroParaSeguidor.getSenha());
    }
}
