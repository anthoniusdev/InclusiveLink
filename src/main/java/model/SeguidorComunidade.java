package model;

import java.util.ArrayList;

public class SeguidorComunidade extends Membro{
    private ArrayList<Comunidade> seguindoComunidades = new ArrayList<Comunidade>();

    public SeguidorComunidade(Membro membro){
        super(membro);
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
