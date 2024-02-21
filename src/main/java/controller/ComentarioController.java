package controller;

import com.google.gson.Gson;
import dao.ComentarioDAO;
import model.Comentario;
import model.Membro;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/novoComentario", "/obterComentarios"})
public class ComentarioController extends HttpServlet implements Serializable {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        switch (action) {
            case "/novoComentario" -> novoComentario(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        switch (action) {
            case "/obterComentarios" -> obterComentarios(request, response);
        }
    }

    private void novoComentario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String texto = request.getParameter("inputTexto");
        HttpSession httpSession = request.getSession(false);
        int idPublicacao = Integer.parseInt(request.getParameter("idPublicacao"));
        Membro membro = (Membro) httpSession.getAttribute("usuario");
        new Comentario(idPublicacao, texto, membro.getIdPessoa());
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
    }

    private void obterComentarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idPublicacao = Integer.parseInt(request.getParameter("idPublicacao"));
        int contagem_inicial = Integer.parseInt(request.getParameter("intervalo"));
        ArrayList<Comentario> comentarios = new ComentarioDAO().comentarios(idPublicacao, contagem_inicial, 5);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String jsonResponse = new Gson().toJson(comentarios);
        response.getWriter().write(jsonResponse);
    }
}
