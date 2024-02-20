package controller;

import model.Membro;
import model.ParticipanteComunidade;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/sairComunidade", "/participarComunidade", "/novaPublicacaoComunidade"})
public class ParticipanteComunidadeController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Served at: " + request.getContextPath() + request.getServletPath());
        String action = request.getServletPath();
        System.out.println(action);
        switch (action) {
            case "/sairComunidade" -> sairComunidade(request, response);
            case "/participarComunidade" -> participarComunidade(request, response);
            case "/novaPublicacaoComunidade" -> novaPublicacaoComunidade(request, response);
        }
    }
    private void sairComunidade(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession httpSession = request.getSession(false);
            Membro membro = (Membro) httpSession.getAttribute("usuario");
            new ParticipanteComunidade(membro.getIdPessoa(), Integer.parseInt(request.getParameter("idComunidade"))).sairComunidade();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void participarComunidade(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession httpSession = request.getSession(false);
            Membro membro = (Membro) httpSession.getAttribute("usuario");
            new ParticipanteComunidade(membro.getIdPessoa(), Integer.parseInt(request.getParameter("idComunidade"))).participarComunidade();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void novaPublicacaoComunidade(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Membro membro = (Membro) request.getSession(false).getAttribute("usuario");
            ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
            ArrayList<FileItem> items = (ArrayList<FileItem>) upload.parseRequest(request);
            new ParticipanteComunidade(membro.getIdPessoa()).publicarComunidade(items);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
