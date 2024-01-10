package controller;

import dao.MembroDAO;
import model.Membro;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/RealizarCadastro", "/Cadastrar"})
public class MembroController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.service(request, response);
    }
    public MembroController(){
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        System.out.println("Servedt at: " + action);
        if (action.equals("/RealizarCadastro")) {
            response.sendRedirect("RealizarCadastro.jsp");
        } else {
            response.sendRedirect("index.html");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
        String action = request.getServletPath();
        System.out.println(action);
        if (action.equals("/Cadastrar")){
            String nome = request.getParameter("nome") + " " + request.getParameter("sobrenome");
            String nomeUsuario = request.getParameter("nome_usuario");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            String dia = request.getParameter("dia");
            String mes = request.getParameter("mes");
            String ano = request.getParameter("ano");
            String dataNascimento = dataNascimentoToString(mes, dia, ano);
            Membro membro = new Membro(nome, dataNascimento, nomeUsuario,email, senha);
            if(membro.realizarCadastro()){
                response.sendRedirect("teste.html");
            }
        }
    }

    private String dataNascimentoToString(String mes, String dia, String ano) {
        int numeroMes = 0;
        switch (mes){
            case "Janeiro":
                numeroMes = 1;
                break;
            case "Fevereiro":
                numeroMes = 2;
                break;
            case "Março":
                numeroMes = 3;
                break;
            case "Abril":
                numeroMes = 4;
                break;
            case "Maio":
                numeroMes = 5;
                break;
            case "Junho":
                numeroMes = 6;
                break;
            case "Julho":
                numeroMes = 7;
                break;
            case "Agosto":
                numeroMes = 8;
                break;
            case "Setembro":
                numeroMes = 9;
                break;
            case "Outubro":
                numeroMes = 10;
                break;
            case "Novembro":
                numeroMes = 11;
                break;
            case "Dezembro":
                numeroMes = 12;
                break;
        }
        return dia + "-" + numeroMes + "-" + ano;
    }

}
