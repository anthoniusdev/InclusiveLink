<<<<<<< HEAD

<%--
  Created by IntelliJ IDEA.
  User: antho
  Date: 08/01/2024
  Time: 12:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
=======
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.Calendar" %>
>>>>>>> dabbabf02ee1aaf3322ac598887546a51cf45be7
<html>
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
    <div class="frmCadastro">
        <form action="Cadastrar" method="post">
            <label>
                <input type="text" placeholder="Nome" name="nome">
            </label>
            <label>
                <input type="text" placeholder="Sobrenome" name="sobrenome">
            </label>
            <label>
                <input type="text" placeholder="Nome de usuário" name="nome_usuario">
            </label>
            <label>
                <input type="text" placeholder="Email" name="email">
            </label>
            <label>
                <input type="text" placeholder="Senha" name="senha">
            </label>
            <label>
                Data de nascimento
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
                    <% for (int ano = anoAtual; ano >= anoMaximo; ano--) {%>
                    <option value="<%=ano%>"><%=ano%>
                    </option>
                    <%}%>
                </select>
            </label>
            <label>

            </label>
            <label>
                <input type="submit" placeholder="Cadastrar">
            </label>
        </form>
    </div>
</div>

</body>
</html>
