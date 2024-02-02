package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import model.Membro;
import model.Publicacao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@WebServlet(urlPatterns = {"/novaPublicacao", "/verPublicacoes"})
public class PublicacaoController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Served at: " + request.getContextPath() + request.getServletPath());
        String action = request.getServletPath();
        switch (action) {
            case "/novaPublicacao" -> novaPublicacao(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Served at: " + request.getContextPath() + request.getServletPath());
        String action = request.getServletPath();
        switch (action) {
            case "/verPublicacoes" -> verPublicacoes(request, response);
        }
    }


    private void novaPublicacao(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            BufferedReader reader = request.getReader();
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> dados = objectMapper.readValue(jsonBuilder.toString(), new TypeReference<Map<String, String>>() {
            });
            String inputTexto = dados.get("inputTexto");
            String inputMidia = dados.get("inputMidia");
            HttpSession httpSession = request.getSession();
            Membro membro = (Membro) httpSession.getAttribute("usuario");
            System.out.println("NOME DO MEMBRO: " + membro.getNome());
            System.out.println("TEXTO DA PUBLICACAO: " + inputTexto);
            new Publicacao(inputTexto, inputMidia, membro);
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("Publicação criada com sucesso!");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erro interno do servidor");
            throw new RuntimeException(e);
        }
    }

    private void verPublicacoes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(false);
        Membro membro = (Membro) httpSession.getAttribute("usuario");
        int intervalo = Integer.parseInt(request.getParameter("intervalo"));
        ArrayList<Publicacao> publicacaos = new Publicacao().feedMembro(membro.getIdPessoa(), intervalo, 5);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String json = new Gson().toJson(publicacaos);
        response.getWriter().write(json);
    }
}
