package model;

import dao.MembroDAO;
import org.apache.commons.fileupload.FileItem;
import util.ObterData;
import util.ObterExtensaoArquivo;
import util.ObterURL;
import util.ServicoAutenticacao;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Membro extends Pessoa implements Serializable {
    private String fotoPerfil;
    private String fotoFundo;
    private String nomeUsuario;
    private ArrayList<Integer> idMembrosSeguidores;
    private ArrayList<Integer> idMembrosSeguindos;
    private ArrayList<Integer> idComunidadesParticipantes;
    private String descricao;
    private ArrayList<Integer> idPublicacaoCurtidas;
    private ArrayList<Integer> idPublicacoes;
    private ArrayList<Integer> idComentarios;

    public Membro(int idPessoa, String nome, String dataNascimento, String nomeUsuario, String email, String senha, String fotoPerfil, String fotoFundo, String descricao, ArrayList<Integer> curtidas) {
        this.setIdPessoa(idPessoa);
        this.setNome(nome);
        this.setSenha(senha);
        this.setDataNascimento(dataNascimento);
        this.setEmail(email);
        this.fotoPerfil = fotoPerfil;
        this.fotoFundo = fotoFundo;
        this.descricao = descricao;
        this.nomeUsuario = nomeUsuario;
        this.idPublicacaoCurtidas = curtidas;
        this.idPublicacoes = null;
        this.idMembrosSeguindos = null;
        this.idMembrosSeguidores = null;
        this.idComunidadesParticipantes = null;
        this.idComentarios = null;
    }

    public Membro(int idPessoa, String nome, String dataNascimento, String email, String senha, String fotoPerfil, String fotoFundo, String nomeUsuario, ArrayList<Integer> idMembrosSeguidores, ArrayList<Integer> idMembrosSeguindos, ArrayList<Integer> idComunidadesParticipantes, String descricao, ArrayList<Integer> idPublicacaoCurtidas, ArrayList<Integer> idPublicacoes, ArrayList<Integer> idComentarios) {
        this.setIdPessoa(idPessoa);
        this.setNome(nome);
        this.setSenha(senha);
        this.setDataNascimento(dataNascimento);
        this.setEmail(email);
        this.fotoPerfil = fotoPerfil;
        this.fotoFundo = fotoFundo;
        this.nomeUsuario = nomeUsuario;
        this.idMembrosSeguidores = idMembrosSeguidores;
        this.idMembrosSeguindos = idMembrosSeguindos;
        this.idComunidadesParticipantes = idComunidadesParticipantes;
        this.descricao = descricao;
        this.idPublicacaoCurtidas = idPublicacaoCurtidas;
        this.idPublicacoes = idPublicacoes;
        this.idComentarios = idComentarios;
    }

    public Membro(Membro membro) {
        this.setNome(membro.getNome());
        this.setSenha(membro.getSenha());
        this.setDataNascimento(membro.getDataNascimento());
        this.setEmail(membro.getEmail());
        this.fotoPerfil = membro.getFotoPerfil();
        this.fotoFundo = membro.getFotoFundo();
        this.descricao = membro.getDescricao();
        this.nomeUsuario = membro.getNomeUsuario();
        this.idPublicacaoCurtidas = membro.getCurtidas();
        this.idPublicacoes = membro.getPublicacoes();
        this.idMembrosSeguindos = membro.getMembrosSeguindo();
        this.idMembrosSeguidores = membro.getMembrosSeguidores();
        this.idComunidadesParticipantes = membro.getComunidadesParticipantes();
        this.idComentarios = membro.getComentarios();
    }

    public Membro(int idPessoa, String nome, String dataNascimento, String email, String senha) {
        setIdPessoa(idPessoa);
        setNome(nome);
        setDataNascimento(dataNascimento);
        setEmail(email);
        setSenha(senha);
    }

    public Membro() {
    }

    public Membro(int idMembro) {
        Membro membro = new MembroDAO().retornaMembro(idMembro);
        this.setIdPessoa(membro.getIdPessoa());
        this.setNomeUsuario(membro.getNomeUsuario());
        this.setNome(membro.getNome());
        this.setSenha(membro.getSenha());
        this.setDataNascimento(membro.getDataNascimento());
        this.setEmail(membro.getEmail());
        this.fotoPerfil = membro.getFotoPerfil();
        this.fotoFundo = membro.getFotoFundo();
        this.descricao = membro.getDescricao();
        this.nomeUsuario = membro.getNomeUsuario();
        this.idPublicacaoCurtidas = membro.getCurtidas();
        this.idPublicacoes = membro.getPublicacoes();
        this.idMembrosSeguindos = membro.getMembrosSeguindo();
        this.idMembrosSeguidores = membro.getMembrosSeguidores();
        this.idComunidadesParticipantes = membro.getComunidadesParticipantes();
        this.idComentarios = membro.getComentarios();
    }

    public Membro(String nomeUsuario) {
        this.setIdPessoa(this.verificaId(nomeUsuario));
        Membro membro = new MembroDAO().retornaMembro(this.getIdPessoa());
        this.setNomeUsuario(nomeUsuario);
        this.setNome(membro.getNome());
        this.setSenha(membro.getSenha());
        this.setDataNascimento(membro.getDataNascimento());
        this.setEmail(membro.getEmail());
        this.setFotoPerfil(membro.getFotoPerfil());
        this.fotoFundo = membro.getFotoFundo();
        this.descricao = membro.getDescricao();
        this.nomeUsuario = membro.getNomeUsuario();
        this.idPublicacaoCurtidas = membro.getCurtidas();
        this.idPublicacoes = membro.getPublicacoes();
        this.idMembrosSeguindos = membro.getMembrosSeguindo();
        this.idMembrosSeguidores = membro.getMembrosSeguidores();
        this.idComunidadesParticipantes = membro.getComunidadesParticipantes();
        this.idComentarios = membro.getComentarios();
    }

    public String getFotoPerfil() {
        return this.fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getFotoFundo() {
        return this.fotoFundo;
    }

    public void setFotoFundo(String fotoFundo) {
        this.fotoFundo = fotoFundo;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public ArrayList<Integer> getMembrosSeguidores() {
        return idMembrosSeguidores;
    }

    public ArrayList<Integer> getComunidadesParticipantes() {
        return idComunidadesParticipantes;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ArrayList<Integer> getCurtidas() {
        return idPublicacaoCurtidas;
    }

    public ArrayList<Integer> getPublicacoes() {
        return idPublicacoes;
    }

    public ArrayList<Integer> getComentarios() {
        return idComentarios;
    }

    public void setComentarios(ArrayList<Integer> comentarios) {
        this.idComentarios = comentarios;
    }

    public Membro realizarCadastro(String nome, String mes, String dia, String ano, String nomeUsuario, String email, String senha) {
        try {
            Membro novoMembro;
            this.setNome(nome);
            this.setEmail(email);
            this.setDataNascimento(dataNascimentoToString(mes, dia, ano));
            this.setNomeUsuario(nomeUsuario);
            this.setSenha(senha);
            MembroDAO membroDAO = new MembroDAO();
            novoMembro = membroDAO.realizarCadastro(this);
            this.setIdPessoa(novoMembro.getIdPessoa());
            return this;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private String dataNascimentoToString(String mes, String dia, String ano) {
        int numeroMes = switch (mes) {
            case "Janeiro" -> 1;
            case "Fevereiro" -> 2;
            case "Março" -> 3;
            case "Abril" -> 4;
            case "Maio" -> 5;
            case "Junho" -> 6;
            case "Julho" -> 7;
            case "Agosto" -> 8;
            case "Setembro" -> 9;
            case "Outubro" -> 10;
            case "Novembro" -> 11;
            case "Dezembro" -> 12;
            default -> 0;
        };
        return dia + "-" + numeroMes + "-" + ano;
    }

    // Metodo para realizar login e retorna se o login é válido
    public boolean realizarLogin(String nomeUsuario, String senha) {
        try {
            Membro membro = new Membro(nomeUsuario);
            membro.setEmail(nomeUsuario);
            String senhaArmazenada = membro.getHashSenha();
            int id = membro.verificaId(nomeUsuario);
            if (id != 0) {
                if (senhaArmazenada != null) {
                    return ServicoAutenticacao.autentica(senha, senhaArmazenada);
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Metodo que retorna se o email é único
    @Override
    public boolean isEmailUnique(String email) {
        return new MembroDAO().isEmailUnique(email);
    }

    public ArrayList<Integer> getMembrosSeguindo() {
        return idMembrosSeguindos;
    }


    public String getHashSenha() {
        MembroDAO membroDAO = new MembroDAO();
        String hash = membroDAO.retornaHashSenha(this.getNomeUsuario());
        if (hash == null) {
            hash = membroDAO.retornaHashSenha(this.getEmail());
        }
        return hash;
    }

    public void pararSeguir(int idSeguindo) {
        try {
            new MembroDAO().pararSeguir(this.getIdPessoa(), idSeguindo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void seguir(int id) {
        try {
            new MembroDAO().seguirMembro(this.getIdPessoa(), id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void seguirMembro(int id) {
        try {
            if (segue(id)) {
                pararSeguir(id);
            } else {
                seguir(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    private void curtir(int idPublicacao) {
        try {
            new Publicacao(idPublicacao).curtir(this.getIdPessoa());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void curtirPublicacao(int idPublicacao) {
        try {
            if (new Publicacao(idPublicacao).jaCurtiu(this.getIdPessoa())) {
                descurtirPublicacao(idPublicacao);
            } else {
                curtir(idPublicacao);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void curtirComentario(int idComentario) {
        try {
            Comentario comentario = new Comentario(idComentario);
            if (comentario.jaCurtiu(this.getIdPessoa())) {
                descurtirComentario(idComentario);
            } else {
                comentario.curtirComentario(this.getIdPessoa());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void descurtirPublicacao(int idPublicacao) {
        try {
            new Publicacao(idPublicacao).descurtir(this.getIdPessoa());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void descurtirComentario(int idComentario) {
        try {
            new Comentario(idComentario).descurtirComentario(this.getIdPessoa());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Membro> listarMembros(int quantidade) {
        return new MembroDAO().listarMembros(quantidade, this.getIdPessoa());
    }

    public ArrayList<Membro> pesquisarPerfil(String query) {
        return new MembroDAO().pesquisarPerfil(query, this.getIdPessoa());
    }

    public boolean isNomeDeUsuarioUnique(String nomeUsuario) {
        return new MembroDAO().isNomeUsuarioUnique(nomeUsuario);
    }

    public boolean editarPerfil(ArrayList<FileItem> items) {
        try {
            String nome = null;
            String descricao = null;
            int idPerfil = 0;
            FileItem fotoPerfil = null;
            FileItem fotoFundo = null;
            for (FileItem item : items) {
                if (item.isFormField()) {
                    switch (item.getFieldName()) {
                        case "nome" -> nome = item.getString("UTF-8");
                        case "descricao" -> descricao = item.getString("UTF-8");
                        case "idUsuario" -> idPerfil = Integer.parseInt(item.getString());
                    }
                } else {
                    switch (item.getFieldName()) {
                        case "fotoPerfil" -> fotoPerfil = item;
                        case "fotoFundo" -> fotoFundo = item;
                    }
                }
            }
            Membro membro = new Membro(idPerfil);
            membro.setNome(nome);
            membro.setDescricao(descricao);
            ObterData obterData = new ObterData();
            int anoAtual = obterData.getAnoAtual();
            int mesAtual = obterData.getMesAtual();
            int diaAtual = obterData.getDiaAtual();
            String urlCaminho = new ObterURL().getUrl();
            String urlFotoPerfil = "arquivosEstaticos" + File.separator + "fotoPerfilUsuario" + File.separator + anoAtual + File.separator + mesAtual + File.separator + diaAtual + File.separator;
            String urlFotoFundo = "arquivosEstaticos" + File.separator + "fotoFundoUsuario" + File.separator + anoAtual + File.separator + mesAtual + File.separator + diaAtual + File.separator;
            String diretorioFotoPerfil = urlCaminho + File.separator + urlFotoPerfil;
            String diretorioFotoFundo = urlCaminho + File.separator + urlFotoFundo;
            File diretorioFileFotoPerfil = new File(diretorioFotoPerfil);
            if (!diretorioFileFotoPerfil.exists()) diretorioFileFotoPerfil.mkdirs();
            if (diretorioFileFotoPerfil.exists()) {
                UUID randomName = UUID.randomUUID();
                if (fotoPerfil != null) {
                    fotoPerfil.write(new File(diretorioFotoPerfil, ("img-fotoperfil" + membro.getNomeUsuario() + randomName + "." + new ObterExtensaoArquivo().get(fotoPerfil.getName()))));
                    membro.setFotoPerfil(urlFotoPerfil + "img-fotoperfil" + membro.getNomeUsuario() + randomName + "." + new ObterExtensaoArquivo().get(fotoPerfil.getName()));
                }
            }
            File diretorioFileFotoFundo = new File(diretorioFotoFundo);
            if (!diretorioFileFotoFundo.exists()) diretorioFileFotoFundo.mkdirs();
            if (diretorioFileFotoFundo.exists()) {
                UUID randomName = UUID.randomUUID();
                if (fotoFundo != null) {
                    fotoFundo.write(new File(diretorioFotoFundo, ("img-fotofundo" + membro.getNomeUsuario() + randomName + "." + new ObterExtensaoArquivo().get(fotoFundo.getName()))));
                    membro.setFotoFundo(urlFotoFundo + "img-fotofundo" + membro.getNomeUsuario() + randomName + "." + new ObterExtensaoArquivo().get(fotoFundo.getName()));
                }
            }
            return new MembroDAO().editarPerfil(membro.getIdPessoa(), membro.getNome(), membro.getDescricao(), membro.getFotoPerfil(), membro.getFotoFundo());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public boolean segue(int id) {
        return new MembroDAO().membrosSeguindos(this.getIdPessoa()).contains(id);
    }

    public void removerSeguidor(int idSeguidor) {
        try {
            if (this.getMembrosSeguidores().contains(idSeguidor)) {
                new MembroDAO().pararSeguir(idSeguidor, this.getIdPessoa());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Metodo para verificar se o usuario existe, se ele existir, retorna o Id, senão, retorna 0
    public int verificaId(String nomeUsuario) {
        return new MembroDAO().verificaId(nomeUsuario);
    }
}
