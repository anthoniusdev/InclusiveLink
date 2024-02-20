package model;

import dao.PublicacaoDAO;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        Publicacao novaPublicacao;
        setTexto(texto);
        setMidia(midia);
        numeroCurtidas = 0;
        curtidas = new ArrayList<>();
        numeroComentarios = 0;
        idComentarios = new ArrayList<Integer>();
        setAutor(autor);
        try {
            PublicacaoDAO publicacaoDAO = new PublicacaoDAO();
            novaPublicacao = publicacaoDAO.novaPublicacao(this);
            this.setIdPublicacao(novaPublicacao.getIdPublicacao());
            this.setData(novaPublicacao.getData());
            this.setHora(novaPublicacao.getHora());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

//    public Publicacao(String texto, String midia, ParticipanteComunidade autor, Comunidade comunidade) {
//        Publicacao novaPublicacao;
//        if (autor.isParticipante(comunidade) || autor.isParticipante(comunidade)) {
//            try {
//                autor = new ParticipanteComunidade(autor);
//                setTexto(texto);
//                setMidia(midia);
//                setAutor(autor);
//                PublicacaoDAO publicacaoDAO = new PublicacaoDAO();
//                comunidade.criarPublicacao(this);
//                novaPublicacao = publicacaoDAO.novaPublicacao(this);
//                if (novaPublicacao != null) {
//                    this.setIdPublicacao(novaPublicacao.getIdPublicacao());
//                    this.setData(novaPublicacao.getData());
//                    this.setHora(novaPublicacao.getHora());
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw new RuntimeException(e);
//            }
//        }
//    }

    public Publicacao(int idPublicacao){
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

    public void excluirPublicacao() {
        new PublicacaoDAO().excluirPublicacao(this.idPublicacao);
    }

    public int getNumeroComentarios() {
        return numeroComentarios;
    }
    public ArrayList<Publicacao> perfilUsuario(int idUsuario, int intervalo, int quantidade){
        return new PublicacaoDAO().perfilMembro(idUsuario, intervalo, quantidade);
    }
}
