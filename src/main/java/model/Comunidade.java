package model;

import dao.ComunidadeDAO;
import org.apache.commons.fileupload.FileItem;
import util.ObterData;
import util.ObterExtensaoArquivo;
import util.ObterURL;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Comunidade implements Serializable {
    private int idComunidade;
    private String nome;
    private int idCriador;
    private String fotoPerfil;
    private String fotoFundo;
    private String descricao;
    private ArrayList<Integer> idPublicacoes = new ArrayList<>();
    private ArrayList<Integer> idModeradores = new ArrayList<>();
    private ArrayList<Integer> idParticipantes = new ArrayList<>();

    public Comunidade(int idComunidade, String nome, int idCriador, String fotoPerfil, String fotoFundo, String descricao, ArrayList<Integer> idPublicacoes, ArrayList<Integer> idModeradores, ArrayList<Integer> idParticipantes) {
        this.idComunidade = idComunidade;
        this.nome = nome;
        this.idCriador = idCriador;
        this.fotoPerfil = fotoPerfil;
        this.fotoFundo = fotoFundo;
        this.descricao = descricao;
        this.idPublicacoes = idPublicacoes;
        this.idModeradores = idModeradores;
        this.idParticipantes = idParticipantes;
    }

    public Comunidade() {
    }

    public Comunidade(int idComunidade) {
        Comunidade comunidade = new ComunidadeDAO().retornaComunidade(idComunidade);
        this.setIdComunidade(idComunidade);
        this.nome = comunidade.getNome();
        this.idCriador = comunidade.getIdCriador();
        this.fotoPerfil = comunidade.getFotoPerfil();
        this.fotoFundo = comunidade.getFotoFundo();
        this.descricao = comunidade.getDescricao();
        this.idPublicacoes = comunidade.getIdPublicacoes();
        this.idModeradores = comunidade.getIdModeradores();
        this.idParticipantes = comunidade.getIdParticipantes();
    }


    public void setIdCriador(int idCriador) {
        this.idCriador = idCriador;
    }

    public int getIdComunidade() {
        return idComunidade;
    }

    public void setIdComunidade(int idComunidade) {
        this.idComunidade = idComunidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public int getIdCriador() {
        return idCriador;
    }

    public String getFotoFundo() {
        return fotoFundo;
    }

    public void setFotoFundo(String fotoFundo) {
        this.fotoFundo = fotoFundo;
    }

    public ArrayList<Integer> getIdPublicacoes() {
        return idPublicacoes;
    }

    public ArrayList<Integer> getIdModeradores() {
        return idModeradores;
    }

    public ArrayList<Integer> getIdParticipantes() {
        return idParticipantes;
    }

    public void setParticipantes(ArrayList<Integer> idParticipantes) {
        this.idParticipantes = idParticipantes;
    }

    public boolean criarComunidade(ArrayList<FileItem> items) throws Exception {
        try {
            Comunidade comunidade = new Comunidade();
            String nomeComunidade = null;
            String descricaoComunidade = null;
            int idAutor = 0;
            FileItem fotoPerfil = null;
            FileItem fotoFundo = null;
            for (FileItem item : items) {
                if (item.isFormField()) {
                    switch (item.getFieldName()) {
                        case "nomeComunidade" -> nomeComunidade = item.getString();
                        case "descricaoComunidade" -> descricaoComunidade = item.getString();
                        case "idAutor" -> idAutor = Integer.parseInt(item.getString());
                    }
                } else {
                    switch (item.getFieldName()) {
                        case "fotoPerfil" -> fotoPerfil = item;
                        case "fotoFundo" -> fotoFundo = item;
                    }
                }
            }
            comunidade.setIdCriador(idAutor);
            comunidade.setNome(nomeComunidade);
            comunidade.setDescricao(descricaoComunidade);
            ObterData obterData = new ObterData();
            int anoAtual = obterData.getAnoAtual();
            int mesAtual = obterData.getMesAtual();
            int diaAtual = obterData.getDiaAtual();
            String urlCaminho = new ObterURL().getUrl();
            String urlFotoPerfil = "arquivosEstaticos" + File.separator + "fotoPerfilComunidade" + File.separator + anoAtual + File.separator + mesAtual + File.separator + diaAtual + File.separator;
            String urlFotoFundo = "arquivosEstaticos" + File.separator + "fotoFundoComunidade" + File.separator + anoAtual + File.separator + mesAtual + File.separator + diaAtual + File.separator;
            String diretorioFotoPerfil = urlCaminho + File.separator + urlFotoPerfil;
            String diretorioFotoFundo = urlCaminho + File.separator + urlFotoFundo;
            File diretorioFileFotoPerfil = new File(diretorioFotoPerfil);
            if (!diretorioFileFotoPerfil.exists()) diretorioFileFotoPerfil.mkdirs();
            if (diretorioFileFotoPerfil.exists()) {
                UUID randomName = UUID.randomUUID();
                if (fotoPerfil != null) {
                    fotoPerfil.write(new File(diretorioFotoPerfil, ("img-fotoperfil" + nomeComunidade + randomName + "." + new ObterExtensaoArquivo().get(fotoPerfil.getName()))));
                    comunidade.setFotoPerfil(urlFotoPerfil + "img-fotoperfil" + nomeComunidade + randomName + "." + new ObterExtensaoArquivo().get(fotoPerfil.getName()));
                }
            }
            File diretorioFileFotoFundo = new File(diretorioFotoFundo);
            if (!diretorioFileFotoFundo.exists()) diretorioFileFotoFundo.mkdirs();
            if (diretorioFileFotoFundo.exists()) {
                UUID randomName = UUID.randomUUID();
                if (fotoFundo != null) {
                    fotoFundo.write(new File(diretorioFotoFundo, ("img-fotofundo" + nomeComunidade + randomName + "." + new ObterExtensaoArquivo().get(fotoFundo.getName()))));
                    comunidade.setFotoFundo(urlFotoFundo + "img-fotofundo" + nomeComunidade + randomName + "." + new ObterExtensaoArquivo().get(fotoFundo.getName()));
                }
            }
            ComunidadeDAO comunidadeDAO = new ComunidadeDAO();
            Comunidade novaComunidade = comunidadeDAO.criarComunidade(comunidade);
            this.setIdComunidade(novaComunidade.getIdComunidade());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public ArrayList<Comunidade> listarComunidadesParticipantes(int idMembro) {
        return new ComunidadeDAO().listarComunidadesUsuario(idMembro);
    }

    public ArrayList<Comunidade> pesquisarComunidade(String query) {
        return new ComunidadeDAO().pesquisarComunidade(query);
    }

    public ArrayList<Comunidade> obterTodasComunidades() {
        return new ComunidadeDAO().comunidades();
    }

    public boolean editarComunidade(ArrayList<FileItem> items) {
        try {
            String nome = null;
            String descricao = null;
            int idComunidade = 0;
            FileItem fotoPerfil = null;
            FileItem fotoFundo = null;
            for (FileItem item : items) {
                if (item.isFormField()) {
                    switch (item.getFieldName()) {
                        case "nome" -> nome = item.getString("UTF-8");
                        case "descricao" -> descricao = item.getString("UTF-8");
                        case "idComunidade" -> idComunidade = Integer.parseInt(item.getString());
                    }
                } else {
                    switch (item.getFieldName()) {
                        case "fotoPerfil" -> fotoPerfil = item;
                        case "fotoFundo" -> fotoFundo = item;
                    }
                }
            }
            Comunidade comunidade = new Comunidade(idComunidade);
            comunidade.setNome(nome);
            comunidade.setDescricao(descricao);
            ObterData obterData = new ObterData();
            int anoAtual = obterData.getAnoAtual();
            int mesAtual = obterData.getMesAtual();
            int diaAtual = obterData.getDiaAtual();
            String urlCaminho = new ObterURL().getUrl();
            String urlFotoPerfil = "arquivosEstaticos" + File.separator + "fotoPerfilComunidade" + File.separator + anoAtual + File.separator + mesAtual + File.separator + diaAtual + File.separator;
            String urlFotoFundo = "arquivosEstaticos" + File.separator + "fotoFundoComunidade" + File.separator + anoAtual + File.separator + mesAtual + File.separator + diaAtual + File.separator;
            String diretorioFotoPerfil = urlCaminho + File.separator + urlFotoPerfil;
            String diretorioFotoFundo = urlCaminho + File.separator + urlFotoFundo;
            File diretorioFileFotoPerfil = new File(diretorioFotoPerfil);
            if (!diretorioFileFotoPerfil.exists()) diretorioFileFotoPerfil.mkdirs();
            if (diretorioFileFotoPerfil.exists()) {
                UUID randomName = UUID.randomUUID();
                if (fotoPerfil != null) {
                    fotoPerfil.write(new File(diretorioFotoPerfil, ("img-fotoperfil" + comunidade.getIdComunidade() + randomName + "." + new ObterExtensaoArquivo().get(fotoPerfil.getName()))));
                    comunidade.setFotoPerfil(urlFotoPerfil + "img-fotoperfil" + comunidade.getIdComunidade() + randomName + "." + new ObterExtensaoArquivo().get(fotoPerfil.getName()));
                }
            }
            File diretorioFileFotoFundo = new File(diretorioFotoFundo);
            if (!diretorioFileFotoFundo.exists()) diretorioFileFotoFundo.mkdirs();
            if (diretorioFileFotoFundo.exists()) {
                UUID randomName = UUID.randomUUID();
                if (fotoFundo != null) {
                    fotoFundo.write(new File(diretorioFotoFundo, ("img-fotofundo" + comunidade.getIdComunidade() + randomName + "." + new ObterExtensaoArquivo().get(fotoFundo.getName()))));
                    comunidade.setFotoFundo(urlFotoFundo + "img-fotofundo" + comunidade.getIdComunidade() + randomName + "." + new ObterExtensaoArquivo().get(fotoFundo.getName()));
                }
            }
            return new ComunidadeDAO().editar(comunidade.getIdComunidade(), comunidade.getNome(), comunidade.getDescricao(), comunidade.getFotoPerfil(), comunidade.getFotoFundo());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public boolean verificaId(int idComunidade) {
        return new ComunidadeDAO().verificaComunidade(idComunidade);
    }
}