package controller;

import com.google.gson.Gson;
import model.Comunidade;
import model.Membro;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;
import util.ObterData;
import util.ObterExtensaoArquivo;
import util.ObterURL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

@WebServlet(urlPatterns = {"/obterComunidades", "/formNovaComunidade", "/verComunidades", "/criarComunidade"})
public class ComunidadeController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Served at: " + request.getContextPath() + request.getServletPath());
        String action = request.getServletPath();
        System.out.println(action);
        switch (action) {
            case "/criarComunidade" -> criarComunidade(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Served at: " + request.getContextPath() + request.getServletPath());
        String action = request.getServletPath();
        System.out.println(action);
        switch (action) {
            case "/obterComunidades" -> obterComunidades(request, response);
            case "/verComunidades" -> verComunidades(request, response);
        }
    }


    private void obterComunidades(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Membro membro = (Membro) session.getAttribute("usuario");
        ArrayList<Comunidade> comunidades = new Comunidade().listarComunidadesParticipantes(membro.getIdPessoa());
        String jsonResponse = new Gson().toJson(comunidades);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }

    private void verComunidades(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession httpSession = request.getSession(false);
            if (httpSession == null || httpSession.getAttribute("authenticated") == null) {
                response.sendRedirect("index.html");
                return;
            }
            Membro membro = (Membro) httpSession.getAttribute("usuario");
            ArrayList<Comunidade> comunidades_usuario = new Comunidade().listarComunidadesParticipantes(membro.getIdPessoa());
            httpSession.setAttribute("comunidades-participantes-usuario", comunidades_usuario);
            response.sendRedirect("VerComunidadesParticipante.jsp");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void criarComunidade(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("veio p ca");
        Comunidade comunidade = new Comunidade();
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        HttpSession httpSession = request.getSession(false);
        try {
            ArrayList<FileItem> items = (ArrayList<FileItem>) upload.parseRequest(request);
            String nomeComunidade = null;
            String descricaoComunidade = null;
            int idAutor = 0;
            FileItem fotoPerfil = null;
            FileItem fotoFundo = null;
            for (FileItem item : items) {
                if (item.isFormField()) {
                    if ("nomeComunidade".equals(item.getFieldName())) {
                        nomeComunidade = item.getString();
                    } else if ("descricaoComunidade".equals(item.getFieldName())) {
                        descricaoComunidade = item.getString();
                    } else if ("idAutor".equals(item.getFieldName())) {
                        idAutor = Integer.parseInt(item.getString());
                    }
                } else {
                    if ("fotoPerfil".equals(item.getFieldName())) {
                        fotoPerfil = item;
                    } else if ("fotoFundo".equals(item.getFieldName())) {
                        fotoFundo = item;
                    }
                }
            }
            comunidade.setIdCriador(idAutor);
            comunidade.setNome(nomeComunidade);
            comunidade.setDescricao(descricaoComunidade);
            ObterData obterData = new ObterData();
            int anoAtual = obterData.getAnoAtual();
            int mesAtual = obterData.getMesAtual();
            int diaAtual = obterData.getDiaAtual();
            String urlCaminho = new ObterURL().getUrl();
            String urlFotoPerfil = "arquivosEstaticos" + File.separator + "fotoPerfilComunidade" + File.separator + anoAtual + File.separator + mesAtual + File.separator + diaAtual + File.separator;
            String urlFotoFundo = "arquivosEstaticos" + File.separator + "fotoFundoComunidade" + File.separator + anoAtual + File.separator + mesAtual + File.separator + diaAtual + File.separator;
            String diretorioFotoPerfil = urlCaminho + File.separator + urlFotoPerfil;
            String diretorioFotoFundo = urlCaminho + File.separator + urlFotoFundo;
            System.out.println(fotoPerfil);
            System.out.println(fotoFundo);
            File diretorioFileFotoPerfil = new File(diretorioFotoPerfil);
            if (!diretorioFileFotoPerfil.exists()) {
                if (diretorioFileFotoPerfil.mkdirs()) {
                    System.out.println("criou diretorio ftper");
                }
            }
            if (diretorioFileFotoPerfil.exists()) {
                UUID randomName = UUID.randomUUID();
                if (fotoPerfil != null) {
                    fotoPerfil.write(new File(diretorioFotoPerfil, ("img-fotoperfil" + nomeComunidade + randomName + "." + new ObterExtensaoArquivo().get(fotoPerfil.getName()))));
                    comunidade.setFotoPerfil(urlFotoPerfil + "img-fotoperfil" + nomeComunidade + randomName + "." + new ObterExtensaoArquivo().get(fotoPerfil.getName()));
                }
            } else {
                System.out.println("DIRETORIO NAO ENCONTRADO");
            }
            File diretorioFileFotoFundo = new File(diretorioFotoFundo);
            if (!diretorioFileFotoFundo.exists()) {
                if (diretorioFileFotoFundo.mkdirs()) {
                    System.out.println("criou diretorio ftfun");
                }
            }
            if (diretorioFileFotoFundo.exists()) {
                UUID randomName = UUID.randomUUID();
                if (fotoFundo != null) {
                    fotoFundo.write(new File(diretorioFotoFundo, ("img-fotofundo" + nomeComunidade + randomName + "." + new ObterExtensaoArquivo().get(fotoFundo.getName()))));
                    comunidade.setFotoFundo(urlFotoFundo + "img-fotofundo" + nomeComunidade + randomName + "." + new ObterExtensaoArquivo().get(fotoFundo.getName()));
                }
            } else {
                System.out.println("DIRETORIO NAO ENCONTRADO");
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject jsonResponse = new JSONObject();
        Membro membro = (Membro) httpSession.getAttribute("usuario");
        if (comunidade.criarComunidade()) {
            System.out.println("retornou");
            jsonResponse.put("success", true);
            jsonResponse.put("message", "Comunidade criada com sucesso.");
            ArrayList<Comunidade> comunidades_usuario = comunidade.listarComunidadesParticipantes(membro.getIdPessoa());
            httpSession.setAttribute("comunidades-participantes-usuario", comunidades_usuario);
        } else {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Erro ao criar a comunidade.");
        }
        response.getWriter().write(jsonResponse.toString());
    }
}