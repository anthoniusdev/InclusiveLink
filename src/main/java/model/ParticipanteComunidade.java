package model;

import dao.ComunidadeDAO;
import dao.ParticipanteComunidadeDAO;

public class ParticipanteComunidade extends Membro{
    private int idComunidade;
    public ParticipanteComunidade(int idParticipante, int idComunidade) {
        setIdPessoa(idParticipante);
        setIdComunidade(idComunidade);
    }

    public int getIdComunidade() {
        return idComunidade;
    }

    public void setIdComunidade(int idComunidade) {
        this.idComunidade = idComunidade;
    }
    public void participarComunidade(){
        Comunidade comunidade = new Comunidade(this.getIdComunidade());
        if (!comunidade.getIdParticipantes().contains(this.getIdPessoa())){
            new ParticipanteComunidadeDAO().participarComunidade(this.getIdComunidade(), this.getIdPessoa());
        }
    }
    public void sairComunidade(){
        Comunidade comunidade = new Comunidade(this.getIdComunidade());
        if (comunidade.getIdParticipantes().contains(this.getIdPessoa())) {
            if (comunidade.getIdParticipantes().size() == 1) {
                new ComunidadeDAO().excluirComunidade(this.getIdComunidade());
            } else if (comunidade.getIdParticipantes().size() > 1) {
                new ParticipanteComunidadeDAO().sairComunidade(this.getIdComunidade(), this.getIdPessoa());
            }
        }
    }
}