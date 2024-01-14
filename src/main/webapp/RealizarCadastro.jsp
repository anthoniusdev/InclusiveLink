<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.Calendar" %>
<html lang="pt-BR">
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
    <form class="frmCadastro" action="Cadastrar" method="post" name="frmCadastro">
        <div class="lado-a-lado">
            <div class="nome">
                <label for="nome">
                    <input type="text" placeholder="Nome" name="nome" id="nome" required>
                    <!--<img src="images/success-icon.svg" alt="Imagem de sucesso!">
                    <img src="images/error-icon.svg" alt="Imagem de erro!">
                    <small>Insira um nome válido!</small>-->
                </label>
            </div>
            <div class="sobrenome">
                <label for="sobrenome">
                    <input type="text" placeholder="Sobrenome" name="sobrenome" id="sobrenome" required>
                    <!--<img src="images/success-icon.svg" alt="Imagem de erro!">
                     <img src="images/error-icon.svg" alt="Imagem de erro!">
                     <small>Insira um sobrenome válido!</small>-->
                </label>
            </div>
        </div>
        <div class="nome_usuario">
            <label for="nome_usuario">
                <input type="text" placeholder="Nome de usuário" name="nome_usuario" id="nome_usuario" required>
                <!--<img src="images/success-icon.svg" alt="Imagem de erro!">
                <img src="images/error-icon.svg" alt="Imagem de erro!">
                <small>Insira um nome de usuário válido!</small>-->
            </label>
        </div>
        <div class="email">
            <label for="email">
                <input type="text" placeholder="Email" name="email" id="email" required>
                <!--<img src="images/success-icon.svg" alt="Imagem de erro!">
                <img src="images/error-icon.svg" alt="Imagem de erro!">
                <small>Insira um email válido!</small>-->
            </label>
        </div>
        <div class="senha">
            <label for="senha">
                <input type="password" placeholder="Senha" name="senha" id="senha" required>
                <!--<img src="images/success-icon.svg" alt="Imagem de erro!">
                <img src="images/error-icon.svg" alt="Imagem de erro!">
                <small>Insira uma senha válido!</small>-->
            </label>
        </div>
        <div class="senha_repetida">
            <label for="senha_repetida">
                <input type="password" placeholder="Repetir Senha" name="senha_repetida" id="senha_repetida" required>
                <!--<img src="images/success-icon.svg" alt="Imagem de erro!">
                <img src="images/error-icon.svg" alt="Imagem de erro!">
                <small>As senhas diferem, insira senhas que sejam iguais!</small>-->
            </label>
        </div>
        <div class="data_nascimento">
            <label>
                <small>Data de nascimento</small>
                <div class="seletores">
                    <select id="dia" name="dia" required>
                        <option value="" disabled selected>Dia</option>
                        <!-- Adicionando as opções de 1 a 31 -->
                        <% int dia = 0, anoMaximo = anoAtual - 100;
                            for (dia = 1; dia <= 31; dia++) { %>
                        <option value="<%=dia%>"><%=dia%>
                        </option>
                        <% }%>
                    </select>
                    <select id="mes" name="mes" required>
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

                    <select id="ano" name="ano" required>
                        <option value="" disabled selected>Ano</option>
                        <!-- Adicionando as opções de ano-->
                        <%
                            for (int ano = anoAtual; ano >= anoMaximo; ano--) {%>
                        <option value="<%=ano%>"><%=ano%>
                        </option>
                        <%}%>
                    </select>
                </div>
            </label>
        </div>
        <div class="btnCadastrar">
            <label>
                <input type="button" value="Cadastrar" onclick="realizarCadastro()">
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
<script src="scripts/realizarCadastro.js"></script>
</body>
</html>
