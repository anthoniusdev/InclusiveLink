package model;

import dao.PublicacaoDAO;
import org.apache.commons.fileupload.FileItem;
import util.ObterData;
import util.ObterExtensaoArquivo;
import util.ObterURL;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Publicacao implements Serializable {
    private int idPublicacao;
    private String texto;
    private String midia;
    private Membro autor;
    private int numeroCurtidas;
    private ArrayList<Integer> curtidas;
    private int numeroComentarios;
    private ArrayList<Integer> idComentarios;
    private String data;
    private String hora;

    public Publicacao(int idPublicacao, String texto, String midia, Membro autor, int numeroCurtidas, ArrayList<Integer> curtidas, int numeroComentarios, ArrayList<Integer> comentarios, String data, String hora) {
        this.idPublicacao = idPublicacao;
        this.texto = texto;
        this.midia = midia;
        this.autor = autor;
        this.numeroCurtidas = numeroCurtidas;
        this.curtidas = curtidas;
        this.numeroComentarios = numeroComentarios;
        this.idComentarios = comentarios;
        this.data = data;
        this.hora = hora;
    }

    public Publicacao(String texto, String midia, Membro autor) {
        try {
            Publicacao novaPublicacao;
            setTexto(texto);
            setMidia(midia);
            numeroCurtidas = 0;
            curtidas = new ArrayList<>();
            numeroComentarios = 0;
            idComentarios = new ArrayList<Integer>();
            setAutor(autor);
            PublicacaoDAO publicacaoDAO = new PublicacaoDAO();
            novaPublicacao = publicacaoDAO.novaPublicacao(this);
            this.setIdPublicacao(novaPublicacao.getIdPublicacao());
            this.setData(novaPublicacao.getData());
            this.setHora(novaPublicacao.getHora());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Publicacao(int idPublicacao) {
        try {
            Publicacao publicacao = new PublicacaoDAO().retornaPublicacao(idPublicacao);
            this.setIdPublicacao(idPublicacao);
            this.setCurtidas(publicacao.getCurtidas());
            this.setComentarios(publicacao.getComentarios());
            this.setAutor(publicacao.getAutor());
            this.setTexto(publicacao.getTexto());
            this.setNumeroComentarios(publicacao.getNumeroComentarios());
            this.setNumeroCurtidas(publicacao.getNumeroCurtidas());
            this.setMidia(publicacao.getMidia());
            try {
                // Setando data no formato correto
                String dataString = publicacao.getData();
                SimpleDateFormat formatoOriginal = new SimpleDateFormat("yyyy-MM-dd");
                Date date = formatoOriginal.parse(dataString);
                SimpleDateFormat novoFormato = new SimpleDateFormat("dd/MM/yyyy");
                String stringDataConvertida = novoFormato.format(date);
                this.setData(stringDataConvertida);

                // Setando a hora no formato correto
                String horaString = publicacao.getHora();
                SimpleDateFormat formatoOriginalH = new SimpleDateFormat("HH:mm:ss");
                Date hora = formatoOriginalH.parse(horaString);
                SimpleDateFormat novoFormatoH = new SimpleDateFormat("HH:mm");
                String stringHoraConvertida = novoFormatoH.format(hora);
                this.setHora(stringHoraConvertida);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Publicacao() {
    }
    public void novaPublicacao(ArrayList<FileItem> items, Membro autor) throws Exception {
        String inputTexto = null;
        FileItem inputMidia = null;
        for (FileItem item : items) {
            if (item.isFormField() && "inputTexto".equals(item.getFieldName())) {
                inputTexto = item.getString("UTF-8");
            } else {
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
                    this.setMidia(caminho);
                }
            }
        }
        this.setTexto(inputTexto);
        this.setAutor(autor);
        new PublicacaoDAO().novaPublicacao(this);
        this.setIdPublicacao(new PublicacaoDAO().novaPublicacao(this).getIdPublicacao());
    }
    public void setIdPublicacao(int idPublicacao) {
        this.idPublicacao = idPublicacao;
    }

    public int getIdPublicacao() {
        return idPublicacao;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getMidia() {
        return midia;
    }

    public void setMidia(String midia) {
        this.midia = midia;
    }

    public Membro getAutor() {
        return autor;
    }

    public void setAutor(Membro autor) {
        this.autor = autor;
    }

    public int getNumeroCurtidas() {
        return numeroCurtidas;
    }

    public boolean jaCurtiu(int idMembro) {
        for (int membroCurtiu : getCurtidas()) {
            if (membroCurtiu == idMembro) {
                return true;
            }
        }
        return false;
    }

    public void curtir(int idMembro) {
        try {
            PublicacaoDAO publicacaoDAO = new PublicacaoDAO();
            publicacaoDAO.curtirPublicacao(getIdPublicacao(), idMembro);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void descurtir(int idMembro) {
        try {
            PublicacaoDAO publicacaoDAO = new PublicacaoDAO();
            publicacaoDAO.descurtirPublicacao(getIdPublicacao(), idMembro);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Integer> getCurtidas() {
        return curtidas;
    }

    public void setNumeroCurtidas(int numeroCurtidas) {
        this.numeroCurtidas = numeroCurtidas;
    }

    public void setNumeroComentarios(int numeroComentarios) {
        this.numeroComentarios = numeroComentarios;
    }

    public void setComentarios(ArrayList<Integer> idComentarios) {
        this.idComentarios = idComentarios;
    }

    public void setCurtidas(ArrayList<Integer> curtidas) {
        this.curtidas = curtidas;
    }

    public ArrayList<Integer> getComentarios() {
        return idComentarios;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public ArrayList<Publicacao> listarPublicacoes(int idMembro) {
        return new PublicacaoDAO().feed(idMembro);
    }

    public ArrayList<Publicacao> feedMembro(int idMembro, int intervalo_inicial, int quantidade_publicacoes) {
        return new PublicacaoDAO().feed(idMembro, intervalo_inicial, quantidade_publicacoes);
    }

    public ArrayList<Publicacao> feedComunidade(int idComunidade, int intervalo_inicial, int quantidade_publicacoes) {
        return new PublicacaoDAO().feedComunidade(idComunidade, intervalo_inicial, quantidade_publicacoes);
    }

    public void excluirPublicacao() {
        new PublicacaoDAO().excluirPublicacao(this.idPublicacao);
    }

    public int getNumeroComentarios() {
        return numeroComentarios;
    }

    public ArrayList<Publicacao> perfilUsuario(int idUsuario, int intervalo, int quantidade) {
        return new PublicacaoDAO().perfilMembro(idUsuario, intervalo, quantidade);
    }
}
