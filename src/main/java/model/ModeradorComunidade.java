package model;

import dao.ModeradorComunidadeDAO;

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
}