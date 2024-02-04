package controller;

import com.google.gson.JsonObject;
import model.Membro;
import model.Pessoa;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(urlPatterns = {"/verificarUnicidade"})
public class UnicidadeController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        boolean unico = false;
        String tipo = request.getParameter("tipo");
        String valor = request.getParameter("valor");
        JsonObject jsonObject = new JsonObject();
        try {
            if (tipo.equals("nomeUsuario")) {
                unico = new Membro().isNomeDeUsuarioUnique(valor);
                jsonObject.addProperty("tipo", "nomeUsuario");
                jsonObject.addProperty("valor", valor);
                jsonObject.addProperty("unico", unico);
            } else if (tipo.equals("email")) {
                unico = new Pessoa().isEmailUnique(valor);
                jsonObject.addProperty("tipo", "email");
                jsonObject.addProperty("valor", valor);
                jsonObject.addProperty("unico", unico);
            } else {
                throw new IllegalArgumentException("Tipo de verificação desconhecido: " + tipo);
            }
        } catch (Exception e) {
            jsonObject.addProperty("erro", "Ocorreu um erro ao verificar a unicidade.");
            jsonObject.addProperty("detalhesErro", e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        out.println(jsonObject.toString());
        out.flush();
    }

}

