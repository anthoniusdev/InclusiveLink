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
        <form action="Cadastrar">
            <label>
                <input type="text" placeholder="Nome" id="nome">
            </label>
            <label>
                <input type="text" placeholder="Sobrenome" id="sobrenome">
            </label>
            <label>
                <input type="text" placeholder="Nome de usuário" id="nome_usuario">
            </label>
            <label>
                <input type="text" placeholder="Email" id="email">
            </label>
            <label>
                <input type="text" placeholder="Senha" id="senha">
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
                            switch (i) {
                                case 1:
                                    mes = "Janeiro";
                                    break;
                                case 2:
                                    mes = "Fevereiro";
                                    break;
                                case 3:
                                    mes = "Março";
                                    break;
                                case 4:
                                    mes = "Abril";
                                    break;
                                case 5:
                                    mes = "Maio";
                                    break;
                                case 6:
                                    mes = "Junho";
                                    break;
                                case 7:
                                    mes = "Julho";
                                    break;
                                case 8:
                                    mes = "Agosto";
                                    break;
                                case 9:
                                    mes = "Setembro";
                                    break;
                                case 10:
                                    mes = "Outubro";
                                    break;
                                case 11:
                                    mes = "Novembro";
                                    break;
                                case 12:
                                    mes = "Dezembro";
                                    break;

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
