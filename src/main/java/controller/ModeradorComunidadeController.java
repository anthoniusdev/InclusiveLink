package controller;

import model.Membro;
import model.ModeradorComunidade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/adicionarModerador", "/excluirComunidade"})
public class ModeradorComunidadeController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Served at: " + request.getContextPath() + request.getServletPath());
        String action = request.getServletPath();
        System.out.println(action);
        switch (action) {
            case "/adicionarModerador" -> adicionarModerador(request, response);
            case "/excluirComunidade" -> excluirComunidade(request, response);
        }
    }

    private void adicionarModerador(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int idModerador = Integer.parseInt(request.getParameter("idModerador"));
            int idComunidade = Integer.parseInt(request.getParameter("idComunidade"));
            new ModeradorComunidade(idModerador, idComunidade).novoModerador();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void excluirComunidade(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession httpSession = request.getSession(false);
            Membro membro = (Membro) httpSession.getAttribute("usuario");
            int idComunidade = Integer.parseInt(request.getParameter("idComunidade"));
            new ModeradorComunidade(membro.getIdPessoa(), idComunidade).excluirComunidade();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}