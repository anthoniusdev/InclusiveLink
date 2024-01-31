package model;

import dao.ParticipanteComunidadeDAO;

public class ParticipanteComunidade extends SeguidorComunidade{
    public ParticipanteComunidade(int idParticipante, int idComunidade) {
        setIdPessoa(idParticipante);
        setIdComunidade(idComunidade);
    }

    public ParticipanteComunidade(SeguidorComunidade seguidorParaParticipante) {
        super(seguidorParaParticipante);
    }
    public boolean sairComunidade(){
        try {
            ParticipanteComunidadeDAO participanteComunidadeDAO = new ParticipanteComunidadeDAO();
            participanteComunidadeDAO.sairComunidade(getIdComunidade(), getIdPessoa());
            return true;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
