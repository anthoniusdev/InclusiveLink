<%--
  Created by IntelliJ IDEA.
  User: antho
  Date: 20/02/2024
  Time: 05:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="model.Membro" %>
<%@ page import="model.Comunidade" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Publicacao" %>
<%
    HttpSession httpSession = request.getSession(false);
    if (httpSession == null || httpSession.getAttribute("authenticated") == null || httpSession.getAttribute("usuario") == null) {
        response.sendRedirect("index.html");
        return;
    } else {
        @SuppressWarnings("unchecked")
        ArrayList<Membro> membrosRede = (ArrayList<Membro>) httpSession.getAttribute("perfis");
        Membro usuario = (Membro) httpSession.getAttribute("usuario");
        Comunidade comunidade = (Comunidade) request.getAttribute("comunidade");
        String fotoPerfil = comunidade.getFotoPerfil();
        String fotoFundo = comunidade.getFotoFundo();
        String nome = comunidade.getNome();
        String descricao = comunidade.getDescricao();
        if (fotoPerfil == null) {
            fotoPerfil = "images/person_foto.svg";
        }
        if (fotoFundo == null) {
            fotoFundo = "images/DefaultFundoPerfil.png";
        }
%>
<!doctype html>
<html lang="pt-br">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<head>
    <title><%=comunidade.getNome()%> - InclusiveLink</title>
</head>
<body>
<div class="principal">
    <div class="cabecalho-comunidade">
        <div class="icone-voltar" onclick="window.location.reload();">
            <img src="images/ri_arrow-up-line.svg" alt="imagem de setinha para voltar a página anterior">
        </div>
        <span class="txtPost">COMUNIDADE</span>
    </div>
    <%--    Foto de fundo do usuário --%>
    <img src="<%=fotoFundo%>" class="fundoPerfil" alt="Foto de fundo do usuário">
    <div class="botao-acao-perfil">
        <button class="btn-editar-perfil" id="editar-perfil">EDITAR PERFIL</button>
    </div>
</div>
</body>
</html>
<%}%>