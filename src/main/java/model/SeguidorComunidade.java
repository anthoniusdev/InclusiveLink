package model;

import java.util.ArrayList;

public class SeguidorComunidade extends Membro{
<<<<<<< HEAD
    public SeguidorComunidade(Membro  membroParaSeguidor){
        super(membroParaSeguidor.getIdPessoa(), membroParaSeguidor.getNome(), membroParaSeguidor.getDataNascimento(), membroParaSeguidor.getEmail(), membroParaSeguidor.getSenha());
=======
    private ArrayList<Comunidade> seguindoComunidades = new ArrayList<Comunidade>();
    public SeguidorComunidade(int idPessoa, String nome, String dataNascimento, String email, String senha) {
        super(idPessoa, nome, dataNascimento, email, senha);
>>>>>>> 55a84b35ab5013b178158265fae3591b37e69f37
    }
    public SeguidorComunidade(Membro membro){
        super(membro.getIdPessoa(), membro.getNome(), membro.getDataNascimento(), membro.getEmail(), membro.getSenha());
    }

    public ArrayList<Comunidade> getSeguindoComunidades() {
        return seguindoComunidades;
    }
    public boolean seguirComunidade(Comunidade comunidade){
        try {
            seguindoComunidades.add(comunidade);
        }catch (Exception e){
            System.out.println(e);
        }
        return verificaSeguindoComunidade(comunidade);
    }
    public boolean verificaSeguindoComunidade(Comunidade comunidade){
        boolean verificaSeguindoComunidade = false;
        for (Comunidade comunidade1: getSeguindoComunidades()){
            if (comunidade1.getIdComunidade() == comunidade.getIdComunidade()) {
                verificaSeguindoComunidade = true;
                break;
            }
        }
        return verificaSeguindoComunidade;
    }
}
