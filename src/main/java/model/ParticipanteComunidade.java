package model;

import dao.ComunidadeDAO;
import dao.ParticipanteComunidadeDAO;
import org.apache.commons.fileupload.FileItem;
import util.ObterData;
import util.ObterExtensaoArquivo;
import util.ObterURL;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class ParticipanteComunidade extends Membro{
    private int idComunidade;
    public ParticipanteComunidade(int idParticipante, int idComunidade) {
        setIdPessoa(idParticipante);
        setIdComunidade(idComunidade);
    }

    public ParticipanteComunidade(int idMembro) {
        super(idMembro);
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
    public void publicarComunidade(ArrayList<FileItem> items){
        try {
            String inputTexto = null;
            FileItem inputMidia = null;
            for (FileItem item : items) {
                if (item.isFormField() && "inputTexto".equals(item.getFieldName())) {
                    inputTexto = item.getString("UTF-8");
                } else if (item.isFormField() && "idComunidade".equals(item.getFieldName())) {
                    setIdComunidade(Integer.parseInt(item.getString()));
                }else{
                    inputMidia = item;
                }
            }
            if (inputMidia != null) {
                String urlCaminho = new ObterURL().getUrl();
                ObterData obterData = new ObterData();
                int anoAtual = obterData.getAnoAtual();
                int mesAtual = obterData.getMesAtual();
                int diaAtual = obterData.getDiaAtual();
                String urlFotosPublicacao = "arquivosEstaticos" + File.separator + "fotosPublicacoes" + File.separator + anoAtual + File.separator + mesAtual + File.separator + diaAtual + File.separator;
                String diretorioFotosPublicacao = urlCaminho + File.separator + urlFotosPublicacao;
                File diretorioFile = new File(diretorioFotosPublicacao);
                if (!diretorioFile.exists()) {
                    diretorioFile.mkdirs();
                }
                if (diretorioFile.exists()) {
                    UUID randomName = UUID.randomUUID();
                    String caminho = null;
                    if (new ObterExtensaoArquivo().get(inputMidia.getName()) != null) {
                        String nomeArquivo = randomName.toString() + "." + new ObterExtensaoArquivo().get(inputMidia.getName());
                        File arquivoImagem = new File(diretorioFile, nomeArquivo);
                        inputMidia.write(arquivoImagem);
                        caminho = urlFotosPublicacao + nomeArquivo;
                    }
                    Publicacao publicacao = new Publicacao(inputTexto, caminho, this);
                    new ParticipanteComunidadeDAO().publicarComunidade(this.getIdComunidade(), publicacao.getIdPublicacao());
                }
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}