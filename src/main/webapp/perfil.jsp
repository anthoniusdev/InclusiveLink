<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="model.Membro" %>
<%@ page import="java.util.ArrayList" %>
<%
    HttpSession httpSession = request.getSession(false);
    if (httpSession == null || httpSession.getAttribute("authenticated") == null || httpSession.getAttribute("usuario") == null) {
        response.sendRedirect("index.html");
        return;
    } else {
        Membro usuario = (Membro) httpSession.getAttribute("usuario");
        boolean paginaUsuario = false;
        if (request.getAttribute("perfilVisitado") != null) {
            Membro perfilVisitado = (Membro) request.getAttribute("perfilVisitado");

            if (perfilVisitado.getIdPessoa() == usuario.getIdPessoa()) {
                paginaUsuario = true;
            }
            String fotoPerfil = perfilVisitado.getFotoPerfil();
            String fotoFundo = perfilVisitado.getFotoFundo();
            if (fotoPerfil == null) {
                fotoPerfil = "images/person_foto.svg";
            }
            if (fotoFundo == null) {
                fotoFundo = "images/DefaultFundoPerfil.png";
            }

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="images/LOGO.ico">
    <link rel="stylesheet" href="styles/PaginaPerfil.css">
    <script src="scripts/js/jquery-3.7.1.js"></script>
    <script src="scripts/barra-lateral-amigo/pesquisarPerfil.js"></script>
    <script src="scripts/carregarPublicacoes.js"></script>
    <script src="scripts/barra-lateral-comunidade/criarComunidade.js"></script>
    <script src="scripts/barra-lateral-comunidade/pesquisarComunidade.js"></script>
    <script src="scripts/curtirPublicacao.js"></script>
    <script src="scripts/excluirPublicacao.js"></script>
    <script src="scripts/carregarPublicacoes.js"></script>
    <script src="scripts/obterUsuarioAutenticado.js"></script>
    <script src="scripts/obterPerfilVisitado.js"></script>
    <script src="scripts/perfil.js"></script>
    <title>Title</title>
</head>
<body>

<div class="retanguloPerfil">

    <div class="header">
        <a href="home"><p class="homePage">Home</p></a>
        <p class="perfil">Perfil</p>
    </div>
    <%--    Foto de fundo do usuário --%>
    <img src="<%=fotoFundo%>" class="fundoPerfil" alt="Foto de fundo do usuário">
    <%--    Foto de perfil do usuário --%>
    <img src="<%=fotoPerfil%>" class="fotoPerfil" alt="Foto de perfil do usuário">
    <p class="nomeUsuario"><%=perfilVisitado.getNome()%>
    </p>

    <div class="profile-description">
        <p class="descricao"><%=perfilVisitado.getDescricao()%>
        </p>
    </div>

    <div class="seguindoSeguidores">
        <p class="numSeguindo"><%=perfilVisitado.getMembrosSeguindo().size() + perfilVisitado.getComunidadesSeguindo().size()%>
        </p>
        <p class="seguindo">Seguindo</p>

        <p class="numSeguidores"><%=perfilVisitado.getMembrosSeguidores()%>
        </p>
        <p class="seguidores">Seguidores</p>
    </div>

    <div class="linha"></div>
    <div class="postagens" id="postagens"></div>
</div>

<div class="pesquisaPerfis">
    <div class="conteudo">

    </div>
</div>
<div class="Perfis">
    <div class="conteudo">

    </div>
</div>

<div class="pesquisaComunidades">
    <div class="conteudo">

    </div>
</div>
<div class="Comunidades">
    <div class="conteudo">

    </div>
</div>

</body>
</html>
<%
        }

    }%>