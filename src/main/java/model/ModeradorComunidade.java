package model;

import dao.ModeradorComunidadeDAO;

public class ModeradorComunidade extends ParticipanteComunidade {
    public ModeradorComunidade(int idMembro, int idComunidade) {
        super(idMembro, idComunidade);
    }

    public void novoModerador() {
        try {
            Comunidade comunidade = new Comunidade(this.getIdComunidade());
            if (!comunidade.getIdModeradores().contains(this.getIdPessoa())) {
                new ModeradorComunidadeDAO().novoModerador(this.getIdPessoa(), this.getIdComunidade());
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void excluirComunidade() {
        try {
            Comunidade comunidade = new Comunidade(this.getIdComunidade());
            if (comunidade.getIdModeradores().contains(this.getIdPessoa())) {
                new ModeradorComunidadeDAO().excluirComunidade(this.getIdComunidade());
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void excluirParticipante(int idParticipante) {
        try {
            Comunidade comunidade = new Comunidade(this.getIdComunidade());
            if (comunidade.getIdModeradores().contains(this.getIdPessoa())) {
                if (comunidade.getIdParticipantes().contains(idParticipante)) {
                    new ModeradorComunidadeDAO().excluirParticipante(this.getIdComunidade(), idParticipante);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}