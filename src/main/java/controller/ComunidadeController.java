package controller;

import com.google.gson.Gson;
import dao.ComunidadeDAO;
import model.Comunidade;
import model.Membro;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;
import util.ObterData;
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

@WebServlet(urlPatterns = {"/criarComunidade", "/obterComunidades", "/formNovaComunidade", "/verComunidades", "/sairComunidade"})
public class ComunidadeController extends HttpServlet {
    private final ComunidadeDAO comunidadeDAO = new ComunidadeDAO();
    private final Comunidade comunidade = new Comunidade();

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
            case "/sairComunidade" -> sairComunidade(request, response);
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

    private void criarComunidade(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("veio p ca");

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
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
            // Cria o caminho completo para o diretório de fotos de perfil
            String urlCaminho = new ObterURL().getUrl();
            String urlFotoPerfil = "arquivosEstaticos" + File.separator + "fotoPerfilComunidade" + File.separator + anoAtual + File.separator + mesAtual + File.separator + diaAtual + File.separator;
            String urlFotoFundo = "arquivosEstaticos" + File.separator + "fotoFundoComunidade"+ File.separator + anoAtual + File.separator + mesAtual + File.separator + diaAtual + File.separator;
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
                    fotoPerfil.write(new File(diretorioFotoPerfil, ("img-fotoperfil" + nomeComunidade + randomName + "." + obterExtensaoArquivo(fotoPerfil.getName()))));
                    comunidade.setFotoPerfil(urlFotoPerfil + "img-fotoperfil" + nomeComunidade + randomName + "." + obterExtensaoArquivo(fotoPerfil.getName()));
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
                    fotoFundo.write(new File(diretorioFotoFundo, ("img-fotofundo" + nomeComunidade + randomName + "." + obterExtensaoArquivo(fotoFundo.getName()))));
                    comunidade.setFotoFundo(urlFotoFundo + "img-fotofundo" + nomeComunidade + randomName + "." + obterExtensaoArquivo(fotoFundo.getName()));
                }
            } else {
                System.out.println("DIRETORIO NAO ENCONTRADO");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if (comunidade.criarComunidade()) {
            System.out.println("retornou");
            response.getWriter().write("sucesso");
        }
    }    private void obterComunidades(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Comunidade> comunidades = comunidadeDAO.listarComunidades(2);
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
            ArrayList<Comunidade> comunidades_usuario = comunidadeDAO.listarComunidadesUsuario(membro.getIdPessoa());
            httpSession.setAttribute("comunidades-participantes-usuario", comunidades_usuario);
            response.sendRedirect("VerComunidadesParticipante.jsp");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    private void sairComunidade(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession httpSession = request.getSession(false);
            if (httpSession == null || httpSession.getAttribute("authenticated") == null){
                response.sendRedirect("index.html");
                return;
            }
            Membro membro = (Membro) httpSession.getAttribute("usuario");
            int idComunidade = Integer.parseInt(request.getParameter("id"));
            System.out.println("idComunidade = " + idComunidade);
            response.setContentType("application/json");

            JSONObject jsonResponse = new JSONObject();

            if (comunidadeDAO.sairComunidade(idComunidade, membro.getIdPessoa())) {
                System.out.println("retornou que removeu");
                jsonResponse.put("status", "success");
                jsonResponse.put("message", "Usuário removido da comunidade com sucesso.");
            } else {
                System.out.println("retornou poha nenhuma");
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "Falha ao remover o usuário da comunidade.");
            }

            response.getWriter().println(jsonResponse.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private String obterExtensaoArquivo(String nomeArquivo) {
        int pontofinal = nomeArquivo.lastIndexOf(".");
        if (pontofinal != -1) {
            return nomeArquivo.substring(pontofinal + 1);
        }
        return null;
    }
}
