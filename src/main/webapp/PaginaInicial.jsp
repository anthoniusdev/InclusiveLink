<%--
  Created by IntelliJ IDEA.
  User: antho
  Date: 10/01/2024
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@page import="model.Membro" %>
<%@ page import="java.util.ArrayList" %>
<%
    HttpSession httpSession = request.getSession(false);
    if (httpSession == null || httpSession.getAttribute("authenticated") == null) {
        response.sendRedirect("index.html");
        return;
    } else {
        if (httpSession.getAttribute("usuario") != null) {
            Membro membro = (Membro) httpSession.getAttribute("usuario");
            String fotoPerfil = membro.getFotoPerfil();
            if (fotoPerfil == null) {
                fotoPerfil = "images/person_foto.svg";
            }
            @SuppressWarnings("unchecked")
            ArrayList<Membro> membrosRede = (ArrayList<Membro>) httpSession.getAttribute("perfis");
%>
<html lang="pt-BR">
<head>
    <title>Inclusive link</title>
    <link rel="stylesheet" href="styles/PaginaInicial.css">
    <script src="scripts/js/jquery-3.7.1.js"></script>
    <script src="scripts/paginaInicial.js"></script>
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
                            <textarea name="inputTexto" id="textoNovaPublicacao" placeholder="O que está acontecendo?"
                                      rows="1" maxlength="200"></textarea>
                        </label>
                        <div id="contagemCaracteres">200</div>
                        <span id="linhaAreaInput"></span>
                        <label id="icone-escolher-imagem">
                            <img src="images/gallery_img.svg" alt="">
                        </label>
                        <input type="file" id="input-imagem" name="imagem" accept="image/*">
                        <%=membro.getNome()%>
                        <%=membro.getNomeUsuario()%>
                        <%=membro.getEmail()%>
                        <%=membro.getMembrosSeguindo().size()%>
                        <%=membro.getMembrosSeguidores().size()%>
                    </div>
                    <div class="im"></div>
                    <div class="btnPostar">
                        <button type="submit" id="btnPostar">POSTAR</button>
                    </div>
                </form>
                <div class="imgPreview">
                    <img id="imagem-preview" alt="" src="">
                </div>
            </div>
            <div class="postagens">

            </div>
        </div>
        <div class="pesquisar-amigo">
            <label>
                <img src="images/search.svg" alt="search">
                <input type="search" placeholder="PESQUISAR PERFIL">
            </label>
        </div>
        <div class="lista-pesquisa-amigo">
            <%
                for (Membro membroSeguir : membrosRede) {
                    if (!membroSeguir.getMembrosSeguidores().contains(membro.getIdPessoa())) {
            %>
            <div class="caixa-usuario" id="caixa-usuario">
                <%
                    if (membroSeguir.getFotoPerfil() == null) {
                        membroSeguir.setFotoPerfil("images/person_foto.svg");
                    }
                %>
                <img src="<%=membroSeguir.getFotoPerfil()%>" alt="Foto de perfil de <%=membroSeguir.getNome()%>">
                <p class="nomeUsuario"><%=membroSeguir.getNome()%>
                </p>
                <button onclick="seguirUsuario(<%=membro.getIdPessoa()%>, <%=membroSeguir.getIdPessoa()%>, <%=membrosRede.indexOf(membroSeguir)%>)"
                        class="botaoSeguir" id="botaoSeguir<%=membrosRede.indexOf(membroSeguir)%>">
                    SEGUIR
                </button>
            </div>
            <%
                    }
                }
            %>
        </div>
    </div>
</main>
<h1>Página Inicial</h1>
</body>
</html>
<%
        }
    }
%>