package controller;

import com.google.gson.Gson;
import model.Membro;
import model.Publicacao;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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

@WebServlet(urlPatterns = {"/novaPublicacao", "/verPublicacoes", "/excluirPublicacao"})
public class PublicacaoController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Served at: " + request.getContextPath() + request.getServletPath());
        String action = request.getServletPath();
        switch (action) {
            case "/novaPublicacao" -> novaPublicacao(request, response);
            case "/excluirPublicacao" -> excluirPublicacao(request);
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
            ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
            ArrayList<FileItem> items = (ArrayList<FileItem>) upload.parseRequest(request);
            String inputTexto = null;
            FileItem inputMidia = null;
            for (FileItem item : items) {
                if (item.isFormField() && "inputTexto".equals(item.getFieldName())) {
                    inputTexto = item.getString("UTF-8");
                } else {
                    inputMidia = item;
                }
            }
            if (inputMidia != null) {
                String urlCaminho = new ObterURL().getUrl();
                ObterData obterData = new ObterData();
                int anoAtual = obterData.getAnoAtual();
                int mesAtual = obterData.getMesAtual();
                int diaAtual = obterData.getDiaAtual();
                String urlFotosPublicacao = "arquivosEstaticos" + File.separator + "fotosPublicacoes" + File.separator + anoAtual + File.separator + mesAtual + File.separator + diaAtual + File.separator;
                String diretorioFotosPublicacao = urlCaminho + File.separator + urlFotosPublicacao;
                File diretorioFile = new File(diretorioFotosPublicacao);
                if (!diretorioFile.exists()) {
                    if (diretorioFile.mkdirs()) {
                        System.out.println("CRIOU A PASTA DE DIRETORIO DE FOTOS");
                    }
                }
                if (diretorioFile.exists()) {
                    UUID randomName = UUID.randomUUID();
                    String caminho = null;
                    if (new ObterExtensaoArquivo().get(inputMidia.getName()) != null) {
                        String nomeArquivo = randomName.toString() + "." + new ObterExtensaoArquivo().get(inputMidia.getName());
                        File arquivoImagem = new File(diretorioFile, nomeArquivo);
                        inputMidia.write(arquivoImagem);
                        caminho = urlFotosPublicacao + nomeArquivo;
                    }
                    HttpSession httpSession = request.getSession();
                    Membro membro = (Membro) httpSession.getAttribute("usuario");
                    System.out.println("NOME DO MEMBRO: " + membro.getNome());
                    System.out.println("TEXTO DA PUBLICACAO: " + inputTexto);
                    new Publicacao(inputTexto, caminho, membro);
                    response.setContentType("text/plain");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write("Publicação criada com sucesso!");
                }
            }
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

    private void excluirPublicacao(HttpServletRequest request) throws ServletException, IOException {
        new Publicacao(Integer.parseInt(request.getParameter("idPublicacao"))).excluirPublicacao();
    }
}
