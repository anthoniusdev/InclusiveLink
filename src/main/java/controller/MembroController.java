package controller;

import com.google.gson.Gson;
import model.Membro;
import model.Publicacao;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

@WebServlet(urlPatterns = {"/Cadastrar", "/Login", "/logout", "/home", "/perfil", "/seguirMembro", "/pesquisarPerfil", "/paginaInicial", "/curtirPublicacao", "/obterUsuarioAutenticado", "/curtirComentario", "/editarPerfil", "/removerSeguidor"})
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
        String action = request.getServletPath();
        switch (action) {
            case "/Cadastrar" -> realizarCadastro(request, response);
            case "/Login" -> realizarLogin(request, response);
            case "/seguirMembro" -> seguirMembro(request, response);
            case "/curtirPublicacao" -> curtirPublicacao(request, response);
            case "/curtirComentario" -> curtirComentario(request, response);
            case "/editarPerfil" -> editarPerfil(request, response);
            case "/removerSeguidor" -> removerSeguidor(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        switch (action) {
            case "/home", "/paginaInicial" -> paginaInicial(request, response);
            case "/perfil" -> perfil(request, response);
            case "/pesquisarPerfil" -> pesquisarPerfil(request, response);
            case "/obterUsuarioAutenticado" -> obterUsuarioAutenticado(request, response);
            case "/logout" -> logout(request, response);
        }
    }

    private String gerarTokenSessao() {
        return UUID.randomUUID().toString();
    }

    private void realizarLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String nomeUsuario = request.getParameter("nomeUsuario");
            String senha = request.getParameter("senha");
            Membro membro = new Membro(nomeUsuario);
            if (membro.realizarLogin(nomeUsuario, senha)) {
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
                response.sendRedirect("home");
            } else {
                response.sendRedirect(request.getContextPath() + "/index.html?erro=1");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
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
        Membro membro = new Membro().realizarCadastro(nome, mes, dia, ano, nomeUsuario, email, senha);
        if (membro != null) {
            String sessionID = gerarTokenSessao();
            int maxAge = 24 * 60 * 60;
            Cookie cookie = new Cookie("sessionID", sessionID);
            cookie.setMaxAge(maxAge);
            response.addCookie(cookie);
            HttpSession session = request.getSession();
            session.setAttribute("authenticated", true);
            session.setAttribute("usuario", membro);
            session.setAttribute("perfis", membro.listarMembros(3));
            response.sendRedirect("perfil?nome_usuario=" + membro.getNomeUsuario());
        }
    }

    private void seguirMembro(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            HttpSession httpSession = request.getSession(false);
            Membro membro = (Membro) httpSession.getAttribute("usuario");
            membro.seguirMembro(Integer.parseInt(request.getParameter("idSeguindo")));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        String jsonResponse;
        jsonResponse = new Gson().toJson(membro);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }

    private void curtirComentario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(false);
        Membro membro = (Membro) httpSession.getAttribute("usuario");
        membro.curtirComentario(Integer.parseInt(request.getParameter("idComentario")));
    }

    private void perfil(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Membro membro = new Membro(request.getParameter("nome_usuario"));
        request.setAttribute("perfilVisitado", membro);
        RequestDispatcher rd = request.getRequestDispatcher("perfil.jsp");
        rd.forward(request, response);
    }

    private void editarPerfil(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
            ArrayList<FileItem> items = (ArrayList<FileItem>) upload.parseRequest(request);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            JSONObject jsonResponse = new JSONObject();
            if (new Membro().editarPerfil(items)) {
                jsonResponse.put("success", true);
                jsonResponse.put("message", "Perfil editado com sucesso.");
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Erro ao editar o comunidade.");
            }
            response.getWriter().write(jsonResponse.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void removerSeguidor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(false);
        Membro usuario = (Membro) httpSession.getAttribute("usuario");
        usuario.removerSeguidor(Integer.parseInt(request.getParameter("idSeguidor")));
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        session.invalidate();
        response.sendRedirect("index.html");
    }
}
