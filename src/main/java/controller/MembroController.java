package controller;

import com.google.gson.Gson;
import dao.ComunidadeDAO;
import dao.MembroDAO;
import dao.PublicacaoDAO;
import model.Comunidade;
import model.Membro;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;
import util.ObterData;
import util.ObterExtensaoArquivo;
import util.ObterURL;
import util.ServicoAutenticacao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

@WebServlet(urlPatterns = {"/RealizarCadastro", "/Cadastrar", "/Login", "/seguirMembro", "/criarComunidade", "/pesquisarPerfil", "/paginaInicial"})
public class MembroController extends HttpServlet {
    private final MembroDAO membroDAO = new MembroDAO();
    private Membro membro = new Membro();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.service(request, response);
    }

    public MembroController() {
        super();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Served at: " + request.getContextPath() + request.getServletPath());
        String action = request.getServletPath();
        System.out.println(action);
        switch (action) {
            case "/Cadastrar" -> realizarCadastro(request, response);
            case "/Login" -> realizarLogin(request, response);
            case "/seguirMembro" -> seguirMembro(request, response);
            case "/criarComunidade" -> criarComunidade(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Served at: " + request.getContextPath() + request.getServletPath());
        String action = request.getServletPath();
        System.out.println(action);
        switch (action) {
            case "/pesquisarPerfil" -> pesquisarPerfil(request, response);
            case "/paginaInicial" -> request.getRequestDispatcher("PaginaInicial.jsp").forward(request, response);
        }
    }

    private String gerarTokenSessao() {
        return UUID.randomUUID().toString();
    }

    private void realizarLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String nomeUsuario = request.getParameter("nomeUsuario");
            String senha = request.getParameter("senha");
            String senhaArmazenada = membroDAO.retornaHashSenha(nomeUsuario);
            int id = membroDAO.verificaId(nomeUsuario);
            if (id != 0) {
                System.out.println(id);
                membro = membroDAO.retornaMembro(id);
                System.out.println("Nome: " + membro.getNome());
                System.out.println("Nome de usuário: " + membro.getNomeUsuario());
                System.out.println("Email: " + membro.getEmail());
                if (senhaArmazenada != null) {
                    if (ServicoAutenticacao.autentica(senha, membroDAO.retornaHashSenha(nomeUsuario))) {
                        boolean remember = request.getParameter("remember") != null;

                        String sessionID = gerarTokenSessao();

                        int maxAge = 24 * 60 * 60;
                        if (remember) {
                            maxAge *= 30;
                        }

                        Cookie cookie = new Cookie("sessionID", sessionID);
                        cookie.setMaxAge(maxAge);
                        response.addCookie(cookie);
                        HttpSession session = request.getSession();
                        session.setAttribute("authenticated", true);
                        session.setAttribute("usuario", membro);
                        session.setAttribute("perfis", membroDAO.listarMembros(3, membro.getIdPessoa()));
                        PublicacaoDAO publicacaoDAO = new PublicacaoDAO();
                        session.setAttribute("feed", publicacaoDAO.feed(membro.getIdPessoa()));
                        response.sendRedirect("PaginaInicial.jsp");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/index.html?erro=1");
                    }

                } else {
                    response.sendRedirect(request.getContextPath() + "/index.html?erro=1");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/index.html?erro=1");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void criarComunidade(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("veio p ca");
        Comunidade comunidade = new Comunidade();
        ComunidadeDAO comunidadeDAO = new ComunidadeDAO();
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        HttpSession httpSession = request.getSession(false);
        try {
            ArrayList<FileItem> items = (ArrayList<FileItem>) upload.parseRequest(request);
            String nomeComunidade = null;
            String descricaoComunidade = null;
            int idAutor = 0;
            FileItem fotoPerfil = null;
            FileItem fotoFundo = null;
            for (FileItem item : items) {
                if (item.isFormField()) {
                    if ("nomeComunidade".equals(item.getFieldName())) {
                        nomeComunidade = item.getString();
                    } else if ("descricaoComunidade".equals(item.getFieldName())) {
                        descricaoComunidade = item.getString();
                    } else if ("idAutor".equals(item.getFieldName())) {
                        idAutor = Integer.parseInt(item.getString());
                    }
                } else {
                    if ("fotoPerfil".equals(item.getFieldName())) {
                        fotoPerfil = item;
                    } else if ("fotoFundo".equals(item.getFieldName())) {
                        fotoFundo = item;
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
            System.out.println(fotoPerfil);
            System.out.println(fotoFundo);
            File diretorioFileFotoPerfil = new File(diretorioFotoPerfil);
            if (!diretorioFileFotoPerfil.exists()) {
                if (diretorioFileFotoPerfil.mkdirs()) {
                    System.out.println("criou diretorio ftper");
                }
            }
            if (diretorioFileFotoPerfil.exists()) {
                UUID randomName = UUID.randomUUID();
                if (fotoPerfil != null) {
                    fotoPerfil.write(new File(diretorioFotoPerfil, ("img-fotoperfil" + nomeComunidade + randomName + "." + new ObterExtensaoArquivo().get(fotoPerfil.getName()))));
                    comunidade.setFotoPerfil(urlFotoPerfil + "img-fotoperfil" + nomeComunidade + randomName + "." + new ObterExtensaoArquivo().get(fotoPerfil.getName()));
                }
            } else {
                System.out.println("DIRETORIO NAO ENCONTRADO");
            }
            File diretorioFileFotoFundo = new File(diretorioFotoFundo);
            if (!diretorioFileFotoFundo.exists()) {
                if (diretorioFileFotoFundo.mkdirs()) {
                    System.out.println("criou diretorio ftfun");
                }
            }
            if (diretorioFileFotoFundo.exists()) {
                UUID randomName = UUID.randomUUID();
                if (fotoFundo != null) {
                    fotoFundo.write(new File(diretorioFotoFundo, ("img-fotofundo" + nomeComunidade + randomName + "." + new ObterExtensaoArquivo().get(fotoFundo.getName()))));
                    comunidade.setFotoFundo(urlFotoFundo + "img-fotofundo" + nomeComunidade + randomName + "." + new ObterExtensaoArquivo().get(fotoFundo.getName()));
                }
            } else {
                System.out.println("DIRETORIO NAO ENCONTRADO");
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject jsonResponse = new JSONObject();

        if (comunidade.criarComunidade()) {
            System.out.println("retornou");
            jsonResponse.put("success", true);
            jsonResponse.put("message", "Comunidade criada com sucesso.");
            ArrayList<Comunidade> comunidades_usuario = comunidadeDAO.listarComunidadesUsuario(membro.getIdPessoa());
            httpSession.setAttribute("comunidades-participantes-usuario", comunidades_usuario);
        } else {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Erro ao criar a comunidade.");
        }
        response.getWriter().write(jsonResponse.toString());
    }

    private void realizarCadastro(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nome = request.getParameter("nome") + " " + request.getParameter("sobrenome");
        String nomeUsuario = request.getParameter("nome_usuario");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String dia = request.getParameter("dia");
        String mes = request.getParameter("mes");
        String ano = request.getParameter("ano");
        String dataNascimento = dataNascimentoToString(mes, dia, ano);
        Membro membro = new Membro(nome, dataNascimento, nomeUsuario, email, senha);
        if (membro.realizarCadastro()) {
            response.sendRedirect("index.html");
        }
    }

    private void seguirMembro(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int idMembro = Integer.parseInt(request.getParameter("idMembro"));
            int idSeguindo = Integer.parseInt(request.getParameter("idSeguindo"));
            membro = membroDAO.retornaMembro(idMembro);
            if (membro.seguirMembro(idSeguindo)) {
                response.getWriter().write("Usuário seguido com sucesso");
                System.out.println(membro.getMembrosSeguindo().size());
                atualizarDadosMembro(request, idMembro);
            } else {
                response.getWriter().write("Usuário não foi seguido");
            }
        }catch (Exception e){
            e.printStackTrace();
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

    private void atualizarDadosMembro(HttpServletRequest request, int idMembro) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Membro membroAtualizado = membroDAO.retornaMembro(idMembro);
            session.setAttribute("usuario", membroAtualizado);
            session.setAttribute("perfis", membroDAO.listarMembros(3, membroAtualizado.getIdPessoa()));
        }
    }

    private void pesquisarPerfil(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String query = request.getParameter("query");
        ArrayList<Membro> membros = membroDAO.pesquisarPerfil(query, membro.getIdPessoa());
        String jsonResponse = new Gson().toJson(membros);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }
}
