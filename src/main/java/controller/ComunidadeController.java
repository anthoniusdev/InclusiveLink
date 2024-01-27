package controller;

import com.google.gson.Gson;
import dao.ComunidadeDAO;
import model.Comunidade;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import util.ObterData;
import util.ObterURL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

@WebServlet(urlPatterns = {"/criarComunidade", "/obterComunidades", "/formNovaComunidade"})
public class ComunidadeController extends HttpServlet {
    private ComunidadeDAO comunidadeDAO = new ComunidadeDAO();
    private Comunidade comunidade = new Comunidade();

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
            String diretorioFotoPerfil = urlCaminho + File.separator + "arquivosEstaticos" + File.separator + "fotoPerfilComunidade" + File.separator + anoAtual + File.separator + mesAtual + File.separator + diaAtual + File.separator;
            String diretorioFotoFundo = urlCaminho +  File.separator + "arquivosEstaticos" + File.separator + "fotoFundoComunidade"+ File.separator + anoAtual + File.separator + mesAtual + File.separator + diaAtual + File.separator;
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
                    comunidade.setFotoPerfil(diretorioFotoPerfil + "img-fotoperfil" + nomeComunidade + randomName + "." + obterExtensaoArquivo(fotoPerfil.getName()));
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
                    comunidade.setFotoFundo(diretorioFotoFundo + "img-fotofundo" + nomeComunidade + randomName + "." + obterExtensaoArquivo(fotoFundo.getName()));
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
            String urlFotoPerfil = request.getContextPath() + comunidade.getFotoPerfil();
            String urlFotoFundo = request.getContextPath() + comunidade.getFotoFundo();
            response.getWriter().write(urlFotoPerfil);
            response.getWriter().write(urlFotoFundo);
        }
    }

    private void obterComunidades(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Comunidade> comunidades = comunidadeDAO.listarComunidades(2);
        String jsonResponse = new Gson().toJson(comunidades);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }

    private void redNovaComunidade(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("");
    }

    private String obterExtensaoArquivo(String nomeArquivo) {
        int pontofinal = nomeArquivo.lastIndexOf(".");
        if (pontofinal != -1) {
            return nomeArquivo.substring(pontofinal + 1);
        }
        return null;
    }
}
