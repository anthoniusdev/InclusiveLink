package controller;

import com.google.gson.Gson;
import model.Membro;
import model.Publicacao;
import util.ServicoAutenticacao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

@WebServlet(urlPatterns = {"/RealizarCadastro", "/Cadastrar", "/Login", "/seguirMembro", "/pesquisarPerfil", "/paginaInicial", "/curtirPublicacao", "/obterUsuarioAutenticado"})
public class MembroController extends HttpServlet {

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
            case "/curtirPublicacao" -> curtirPublicacao(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Served at: " + request.getContextPath() + request.getServletPath());
        String action = request.getServletPath();
        System.out.println(action);
        switch (action) {
            case "/pesquisarPerfil" -> pesquisarPerfil(request, response);
            case "/paginaInicial" -> paginaInicial(request, response);
            case "/obterUsuarioAutenticado" -> obterUsuarioAutenticado(request, response);
        }
    }

    private String gerarTokenSessao() {
        return UUID.randomUUID().toString();
    }

    private void realizarLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String nomeUsuario = request.getParameter("nomeUsuario");
            String senha = request.getParameter("senha");
            Membro membro = new Membro();
            membro.setEmail(nomeUsuario);
            membro.setNomeUsuario(nomeUsuario);
            String senhaArmazenada = membro.getHashSenha();
            int id = membro.retornaIdPorNomeUser();
            if (id != 0) {
                System.out.println(id);
                membro = new Membro(id);
                System.out.println("Nome: " + membro.getNome());
                System.out.println("Nome de usuário: " + membro.getNomeUsuario());
                System.out.println("Email: " + membro.getEmail());
                if (senhaArmazenada != null) {
                    if (ServicoAutenticacao.autentica(senha, senhaArmazenada)) {
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
                        session.setAttribute("perfis", membro.listarMembros(3));
                        session.setAttribute("feed", new Publicacao().listarPublicacoes(membro.getIdPessoa()));
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
        } catch (Exception e) {
            e.printStackTrace();
        }

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
            Membro membro = new Membro(idMembro);
            if (membro.seguirMembro(idSeguindo)) {
                response.getWriter().write("Usuário seguido com sucesso");
                System.out.println(membro.getMembrosSeguindo().size());
//                atualizarDadosMembro(request, idMembro);
            } else {
                response.getWriter().write("Usuário não foi seguido");
            }
        } catch (Exception e) {
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

    private void pesquisarPerfil(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String query = request.getParameter("query");
        HttpSession httpSession = request.getSession(false);
        Membro membro = (Membro) httpSession.getAttribute("usuario");
        ArrayList<Membro> membros = membro.pesquisarPerfil(query);
        String jsonResponse = new Gson().toJson(membros);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }

    private void paginaInicial(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("authenticated") == null) {
            response.sendRedirect("index.html");
            return;
        }
        Membro membro = (Membro) session.getAttribute("usuario");
        session.setAttribute("perfis", membro.listarMembros(3));
        session.setAttribute("feed", new Publicacao().listarPublicacoes(membro.getIdPessoa()));
        request.getRequestDispatcher("PaginaInicial.jsp").forward(request, response);
    }

    private void curtirPublicacao(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        Membro membro = (Membro) session.getAttribute("usuario");
        membro.curtirPublicacao(Integer.parseInt(request.getParameter("idPublicacao")));
    }
    private void obterUsuarioAutenticado(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        Membro membro = (Membro) session.getAttribute("usuario");
        String jsonResponse = new Gson().toJson(membro.getIdPessoa());
        // Até o momento só preciso do ID, se precisar de mais alguma tem que adicionar
        System.out.println(jsonResponse);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }
}
