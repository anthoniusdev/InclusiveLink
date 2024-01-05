package model;

import java.util.ArrayList;

public class SeguidorComunidade extends Membro{
    private ArrayList<Comunidade> seguindoComunidades = new ArrayList<Comunidade>();
    public SeguidorComunidade(int idPessoa, String nome, String dataNascimento, String email, String senha) {
        super(idPessoa, nome, dataNascimento, email, senha);
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
