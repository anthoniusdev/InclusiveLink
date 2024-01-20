<%--
  Created by IntelliJ IDEA.
  User: antho
  Date: 10/01/2024
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@page import="model.Membro" %>
<%
    HttpSession httpSession = request.getSession(false);
    if (httpSession == null || httpSession.getAttribute("authenticated") == null) {
        response.sendRedirect("index.html");
        return;
    } else {
        if (httpSession.getAttribute("usuario") != null) {
            Membro membro = (Membro) httpSession.getAttribute("usuario");
            String fotoPerfil = membro.getFotoPerfil();
            if (fotoPerfil == null){
                fotoPerfil = "images/person_foto.svg";
            }
%>
<html lang="pt-BR">
<head>
    <title>Inclusive link</title>
    <link rel="stylesheet" href="styles/PaginaInicial.css">
</head>
<body>
<main>
    <div class="container">
        <div class="principal">
            <div class="cabecalho">
                <div class="selecionado">
                    PÁGINA INICIAL
                    <span id="linhaSelecionado"></span></div>
                <a href="">PERFIL</a>
            </div>
            <div class="criarPublicacao">
                <form action="novaPublicacao" method="post" id="formNovaPublicacao">
                    <div class="foto-perfil">
                        <img src="<%=fotoPerfil%>" alt="Foto de perfil">
                    </div>
                    <div class="areaInput">
                        <label for="textoNovaPublicacao">
                            <textarea name="inputTexto" id="textoNovaPublicacao" placeholder="O que está acontecendo?" rows="1" maxlength="200"></textarea>
                        </label>
                        <div id="contagemCaracteres">200</div>
                        <span id="linhaAreaInput"></span>
                        <input name="inputMidia" id="imagemPublicacao" type="image" alt="" src="images/gallery_img.svg">
                        <%=membro.getNome()%>
                        <%=membro.getNomeUsuario()%>
                        <%=membro.getEmail()%>
                    </div>
                    <div class="btnPostar">
                        <button type="submit" id="btnPostar">POSTAR</button>
                    </div>
                </form>
            </div>
            <div class="postagens">

            </div>
        </div>
    </div>
</main>
<h1>Página Inicial</h1>
<script src="scripts/paginaInicial.js"></script>
</body>
</html>
<%
        }
    }
%>