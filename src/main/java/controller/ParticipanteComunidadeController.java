package controller;

import dao.ComunidadeDAO;
import model.Comunidade;
import model.Membro;
import model.ParticipanteComunidade;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/sairComunidade"})
public class ParticipanteComunidadeController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Served at: " + request.getContextPath() + request.getServletPath());
        String action = request.getServletPath();
        System.out.println(action);
        switch (action){
            case "/sairComunidade" -> sairComunidade(request, response);
        }
    }
    private void sairComunidade(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ComunidadeDAO comunidadeDAO = new ComunidadeDAO();
            HttpSession httpSession = request.getSession(false);
            if (httpSession == null || httpSession.getAttribute("authenticated") == null) {
                response.sendRedirect("index.html");
                return;
            }
            Membro membro = (Membro) httpSession.getAttribute("usuario");
            int idComunidade = Integer.parseInt(request.getParameter("id"));
            System.out.println("idComunidade = " + idComunidade);
            response.setContentType("application/json");

            JSONObject jsonResponse = new JSONObject();
            ParticipanteComunidade participanteComunidade = new ParticipanteComunidade(membro.getIdPessoa(), idComunidade);
            if (participanteComunidade.sairComunidade()) {
                System.out.println("retornou que removeu");
                jsonResponse.put("status", "success");
                jsonResponse.put("message", "Usuário removido da comunidade com sucesso.");
                ArrayList<Comunidade> comunidades_usuario = comunidadeDAO.listarComunidadesUsuario(membro.getIdPessoa());
                httpSession.setAttribute("comunidades-participantes-usuario", comunidades_usuario);
            } else {
                System.out.println("retornou poha nenhuma");
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "Falha ao remover o usuário da comunidade.");
            }

            response.getWriter().println(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
