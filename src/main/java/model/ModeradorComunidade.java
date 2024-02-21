package model;

import dao.ModeradorComunidadeDAO;

import java.util.ArrayList;

public class ModeradorComunidade extends ParticipanteComunidade {
    public ModeradorComunidade(int idMembro, int idComunidade) {
        super(idMembro, idComunidade);
    }

    public void novoModerador() {
        Comunidade comunidade = new Comunidade(this.getIdComunidade());
        if (comunidade.getIdModeradores().contains(this.getIdPessoa())) {
            new ModeradorComunidadeDAO().novoModerador(this.getIdPessoa(), this.getIdComunidade());
        }
    }

    public void excluirComunidade() {
        Comunidade comunidade = new Comunidade(this.getIdComunidade());
        if (comunidade.getIdModeradores().contains(this.getIdPessoa())) {
            new ModeradorComunidadeDAO().excluirComunidade(this.getIdComunidade());
        }
    }

    public void excluirParticipante(int idParticipante, int idComunidade) {

        Comunidade comunidade = new Comunidade(idComunidade);
        ArrayList<Integer> idModeradores = comunidade.getIdModeradores();
        if (!idModeradores.isEmpty()) {
            ArrayList<Integer> idParticipantes = comunidade.getIdParticipantes();
            if (idParticipantes.contains(idParticipante)) {
                idModeradores.remove(Integer.valueOf(idParticipante));
                comunidade.setIdParticipantes(idParticipantes);
            }
        }

    }
}