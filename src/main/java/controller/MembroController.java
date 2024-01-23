package controller;

import dao.MembroDAO;
import model.Membro;
import util.ServicoAutenticacao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.UUID;

@WebServlet(urlPatterns = {"/RealizarCadastro", "/Cadastrar", "/Login", "/seguirMembro"})
public class MembroController extends HttpServlet {
    private final MembroDAO membroDAO = new MembroDAO();

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
            Membro membro = membroDAO.retornaMembro(id);
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
                    response.sendRedirect("index.html?erro=1");
                }
            } else {
                response.sendRedirect("index.html?erro=1");
            }
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

        if (membroDAO.seguirMembro(idMembro, idSeguindo)) {
            response.getWriter().write("Usuário seguido com sucesso");
            System.out.println(membroDAO.retornaMembro(idMembro).getMembrosSeguindo().size());
            atualizarDadosMembro(request, idMembro);
        } else {
            response.getWriter().write("Usuário não foi seguido");
        }
    }

    private String dataNascimentoToString(String mes, String dia, String ano) {
        int numeroMes = 0;
        switch (mes) {
            case "Janeiro":
                numeroMes = 1;
                break;
            case "Fevereiro":
                numeroMes = 2;
                break;
            case "Março":
                numeroMes = 3;
                break;
            case "Abril":
                numeroMes = 4;
                break;
            case "Maio":
                numeroMes = 5;
                break;
            case "Junho":
                numeroMes = 6;
                break;
            case "Julho":
                numeroMes = 7;
                break;
            case "Agosto":
                numeroMes = 8;
                break;
            case "Setembro":
                numeroMes = 9;
                break;
            case "Outubro":
                numeroMes = 10;
                break;
            case "Novembro":
                numeroMes = 11;
                break;
            case "Dezembro":
                numeroMes = 12;
                break;
        }
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
}
