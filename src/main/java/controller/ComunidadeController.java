package controller;

import com.google.gson.Gson;
import model.Comunidade;
import model.Membro;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/obterComunidades", "/verComunidades", "/criarComunidade", "/pesquisarComunidade", "/comunidade", "/comunidades", "/editarComunidade"})
public class ComunidadeController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        switch (action) {
            case "/criarComunidade" -> criarComunidade(request, response);
            case "/editarComunidade" -> editarComunidade(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        switch (action) {
            case "/obterComunidades" -> obterComunidades(request, response);
            case "/verComunidades" -> verComunidades(request, response);
            case "/pesquisarComunidade" -> pesquisarComunidade(request, response);
            case "/comunidade" -> comunidade(request, response);
            case "/comunidades" -> todasComunidades(request, response);
        }
    }

    // Metodo para obter as comunidades do usuario
    private void obterComunidades(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            Membro membro = (Membro) session.getAttribute("usuario");
            ArrayList<Comunidade> comunidades = new Comunidade().listarComunidadesParticipantes(membro.getIdPessoa());
            String jsonResponse = new Gson().toJson(comunidades);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Metodo para obter e ir até a página de ver as comunidades que o usuário participa
    private void verComunidades(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession httpSession = request.getSession(false);
            Membro membro = (Membro) httpSession.getAttribute("usuario");
            ArrayList<Comunidade> comunidades_usuario = new Comunidade().listarComunidadesParticipantes(membro.getIdPessoa());
            httpSession.setAttribute("comunidades-participantes-usuario", comunidades_usuario);
            RequestDispatcher rd = request.getRequestDispatcher("VerComunidadesParticipante.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Metodo para criar uma nova comunidade
    private void criarComunidade(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Comunidade comunidade = new Comunidade();
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            HttpSession httpSession = request.getSession(false);
            ArrayList<FileItem> items = (ArrayList<FileItem>) upload.parseRequest(request);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            JSONObject jsonResponse = new JSONObject();
            Membro membro = (Membro) httpSession.getAttribute("usuario");
            if (comunidade.criarComunidade(items)) {
                jsonResponse.put("success", true);
                jsonResponse.put("message", "Comunidade criada com sucesso.");
                ArrayList<Comunidade> comunidades_usuario = comunidade.listarComunidadesParticipantes(membro.getIdPessoa());
                httpSession.setAttribute("comunidades-participantes-usuario", comunidades_usuario);
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Erro ao criar a comunidade.");
            }
            response.getWriter().write(jsonResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Metodo para pesquisar comunidade
    private void pesquisarComunidade(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String query = request.getParameter("query");
            String jsonResponse = new Gson().toJson(new Comunidade().pesquisarComunidade(query));
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Metodo para visitar a pagina de uma comunidade especifica
    private void comunidade(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int idComunidade = Integer.parseInt(request.getParameter("idComunidade"));
            if (new Comunidade().verificaId(idComunidade)) {
                Comunidade comunidade = new Comunidade(Integer.parseInt(request.getParameter("idComunidade")));
                request.setAttribute("comunidade", comunidade);
                RequestDispatcher rd = request.getRequestDispatcher("Comunidade.jsp");
                rd.forward(request, response);
            } else {
                response.sendRedirect("verComunidades");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Metodo para visitar a pagina que possibilita ver todas as comunidades
    private void todasComunidades(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession httpSession = request.getSession(false);
            httpSession.setAttribute("allComunidades", new Comunidade().obterTodasComunidades());
            RequestDispatcher rd = request.getRequestDispatcher("VerTodasAsComunidades.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Metodo para editar uma comunidade especifica
    private void editarComunidade(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
            ArrayList<FileItem> items = (ArrayList<FileItem>) upload.parseRequest(request);
            int idComunidade = Integer.parseInt(request.getParameter("idComunidade"));
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            JSONObject jsonResponse = new JSONObject();
            if (new Comunidade(idComunidade).editarComunidade(items)) {
                jsonResponse.put("success", true);
                jsonResponse.put("message", "Comunidade editada com sucesso.");
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Erro ao editar a comunidade.");
            }
            response.getWriter().write(jsonResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
