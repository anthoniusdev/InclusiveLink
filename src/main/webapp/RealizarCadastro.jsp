<<<<<<< HEAD
<%--
  Created by IntelliJ IDEA.
  User: antho
  Date: 08/01/2024
  Time: 12:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.Calendar" %>
<<<<<<< HEAD

<html>
=======
<html lang="pt-BR">
>>>>>>> f8ac4e5a76177b7c39ed2d3e7db4febd3fe2a028
<head>
    <title>Inclusive Link</title>
    <link rel="stylesheet" href="styles/RealizarCadastro.css">
    <meta charset="UTF-8">
</head>
<body>
<%
    Calendar calendar = Calendar.getInstance();
    int anoAtual = calendar.get(Calendar.YEAR);
%>
<div class="container">
    <form id="formCadastro" action="Cadastrar" method="post" name="frmCadastro">
        <div class="lado-a-lado">
            <div class="form-control">
                <label for="nome">
                    <input type="text" placeholder="Nome" name="nome" id="nome">
                </label>
                <i><img src="" alt=""></i>
                <small>Error messager</small>
            </div>
            <div class="form-control">
                <label for="sobrenome">
                    <input type="text" placeholder="Sobrenome" name="sobrenome" id="sobrenome">
                </label>
                <i><img src="" alt=""></i>
                <small>Error messager</small>
            </div>
        </div>
        <div class="form-control">
            <label for="nome_usuario">
                <input type="text" placeholder="Nome de usuário" name="nome_usuario" id="nome_usuario">
            </label>
            <i><img src="" alt=""></i>
            <small>Error messager</small>
        </div>
        <div class="form-control">
            <label for="email">
                <input type="email" placeholder="Email" name="email" id="email">
            </label>
            <i><img src="" alt=""></i>
            <small>Error messager</small>
        </div>
        <div class="form-control">
            <label for="senha">
                <input type="password" placeholder="Senha" name="senha" id="senha">
            </label>
            <i><img src="" alt=""></i>
            <small>Error messager</small>
        </div>
        <div class="form-control">
            <label for="senha_repetida">
                <input type="password" placeholder="Repetir Senha" name="senha_repetida" id="senha_repetida">
            </label>
            <i><img src="" alt=""></i>
            <small>Error messager</small>
        </div>
        <div class="data_nascimento">
            <label>
                <small>Data de nascimento</small>
                <div class="seletores">
                    <select id="dia" name="dia">
                        <option value="" disabled selected>Dia</option>
                        <!-- Adicionando as opções de 1 a 31 -->
                        <% int dia = 0, anoMaximo = anoAtual - 100;
                            for (dia = 1; dia <= 31; dia++) { %>
                        <option value="<%=dia%>"><%=dia%>
                        </option>
                        <% }%>
                    </select>
                    <select id="mes" name="mes">
                        <option value="" disabled selected>Mês</option>
                        <!-- Adicionando as opções de mês-->
                        <% String mes = null;
                            for (int i = 1; i <= 12; i++) {
                                if (i == 1) {
                                    mes = "Janeiro";
                                } else if (i == 2) {
                                    mes = "Fevereiro";
                                } else if (i == 3) {
                                    mes = "Março";
                                } else if (i == 4) {
                                    mes = "Abril";
                                } else if (i == 5) {
                                    mes = "Maio";
                                } else if (i == 6) {
                                    mes = "Junho";
                                } else if (i == 7) {
                                    mes = "Julho";
                                } else if (i == 8) {
                                    mes = "Agosto";
                                } else if (i == 9) {
                                    mes = "Setembro";
                                } else if (i == 10) {
                                    mes = "Outubro";
                                } else if (i == 11) {
                                    mes = "Novembro";
                                } else {
                                    mes = "Dezembro";
                                }%>
                        <option value="<%=mes%>"><%=mes%>
                        </option>
                        <%}%>
                    </select>

                    <select id="ano" name="ano">
                        <option value="" disabled selected>Ano</option>
                        <!-- Adicionando as opções de ano-->
                        <%
                            for (int ano = anoAtual; ano >= anoMaximo; ano--) {%>
                        <option value="<%=ano%>"><%=ano%>
                        </option>
                        <%}%>
                    </select>
=======
    <%@ page language="java" contentType="text/html; charset=UTF-8"
             pageEncoding="UTF-8" %>
    <%@ page import="java.util.Calendar" %>
    <html lang="pt-BR">
    <head>
        <title>Inclusive Link</title>
        <link rel="stylesheet" href="styles/RealizarCadastro.css">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <script src="scripts/realizarCadastro.js"></script>
        <meta charset="UTF-8">
    </head>
    <body>
    <div class="container">
        <form id="formCadastro" action="Cadastrar" method="post" name="frmCadastro">
            <div class="lado-a-lado">
                <div class="form-control">
                    <label for="nome">
                        <input type="text" placeholder="Nome" name="nome" id="nome">
                    </label>
                    <i><img src="" alt=""></i>
                    <small>Error messager</small>
                </div>
                <div class="form-control">
                    <label for="sobrenome">
                        <input type="text" placeholder="Sobrenome" name="sobrenome" id="sobrenome">
                    </label>
                    <i><img src="" alt=""></i>
                    <small>Error messager</small>
>>>>>>> 592929f69bc590a8b2286f9ff14b97a22a8579ec
                </div>
            </div>
            <div class="form-control">
                <label for="nome_usuario">
                    <input type="text" placeholder="Nome de usuário" name="nome_usuario" id="nome_usuario">
                </label>
                <i><img src="" alt=""></i>
                <small>Error messager</small>
            </div>
            <div class="form-control">
                <label for="email">
                    <input type="email" placeholder="Email" name="email" id="email">
                </label>
                <i><img src="" alt=""></i>
                <small>Error messager</small>
            </div>
            <div class="form-control">
                <label for="senha">
                    <input type="password" placeholder="Senha" name="senha" id="senha">
                </label>
                <i><img src="" alt=""></i>
                <small>Error messager</small>
            </div>
            <div class="form-control">
                <label for="senha_repetida">
                    <input type="password" placeholder="Repetir Senha" name="senha_repetida" id="senha_repetida">
                </label>
                <i><img src="" alt=""></i>
                <small>Error messager</small>
            </div>
            <div class="data_nascimento">
                <label>
                    <small>Data de nascimento</small>
                    <div class="seletores">
                        <div class="form-control">
                            <select id="dia" name="dia" required>
                                <option value="" disabled selected>Dia</option>
                            </select>
                        </div>
                        <div class="form-control">
                            <select id="mes" name="mes" required>
                                <option value="" disabled selected>Mês</option>
                            </select>
                        </div>
                        <div class="form-control">
                            <select id="ano" name="ano" required>
                                <option value="" disabled selected>Ano</option>
                            </select>
                        </div>
                    </div>
                </label>
            </div>
            <div class="btnCadastrar">
                <label>
                    <button type="submit">Cadastrar</button>
                </label>
            </div>
        </form>
    </div>
    <footer>
        <div class="LOGO-E-NOMES">
            <div class="Logo-Nome-IL">
                <div class="bola-azul">
                    <img src="images/logo_InclusiveLink-ruim.png" alt="Logo InclusiveLink">
                </div>
                <h1>Inclusive Link</h1>
            </div>
            <div class="Nomes">
                <h3>Abimael Lima</h3>
                <h3>Anthonius Figueiredo</h3>
                <h3>Igor Souza</h3>
            </div>
        </div>
    </footer>
    </body>
    </html>
