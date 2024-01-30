package controller;

import com.google.gson.Gson;
import dao.MembroDAO;
import model.Membro;
import util.ServicoAutenticacao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

@WebServlet(urlPatterns = {"/RealizarCadastro", "/Cadastrar", "/Login", "/seguirMembro", "/pesquisarPerfil", "/paginaInicial"})
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
