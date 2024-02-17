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
    <title>Inclusive Link</title>
    <link rel="stylesheet" href="styles/PaginaInicial.css">
    <link rel="stylesheet" href="styles/barra-lateral.css">
    <link rel="icon" href="images/LOGO.ico">
    <script src="scripts/js/jquery-3.7.1.js"></script>
    <script src="scripts/obterUsuarioAutenticado.js"></script>
    <script src="scripts/paginaInicial.js"></script>
    <script src="scripts/barra-lateral-amigo/pesquisarPerfil.js"></script>
    <script src="scripts/barra-lateral-comunidade/criarComunidade.js"></script>
    <script src="scripts/barra-lateral-comunidade/pesquisarComunidade.js"></script>
    <script src="scripts/curtirPublicacao.js"></script>
    <script src="scripts/excluirPublicacao.js"></script>
    <script src="scripts/carregarPublicacoes.js"></script>
    <script src="scripts/seguirUsuario.js"></script>
</head>
<body id="body">
<main>
    <div class="container">
        <div class="principal">
            <div class="cabecalho">
                <div class="selecionado">
                    HOME
                    <span id="linhaSelecionado"></span></div>
                <a href="perfil?nome_usuario=<%=membro.getNomeUsuario()%>">PERFIL</a>
            </div>
            <div class="criarPublicacao">
                <form action="novaPublicacao" method="post" id="formNovaPublicacao" enctype="multipart/form-data">
                    <div class="foto-perfil">
                        <img src="<%=fotoPerfil%>" alt="Foto de perfil">
                    </div>
                    <div class="areaInput">
                        <label for="textoNovaPublicacao">
                            <textarea name="inputTexto" id="textoNovaPublicacao" placeholder="O que está acontecendo?"
                                      rows="1" maxlength="200"></textarea>
                        </label>
                        <div id="contagemCaracteresPublicacao">200</div>
                        <span id="linhaAreaInput"></span>
                        <label id="icone-escolher-imagem">
                            <img src="images/gallery_img.svg" alt="">
                        </label>
                        <input type="file" id="input-imagem" name="imagem" accept="image/*">
                    </div>
                    <div class="im"></div>
                    <div class="btnPostar">
                        <button type="submit" id="btnPostar">POSTAR</button>
                    </div>
                </form>
                <div class="imgPreview">
                    <img id="imagem-preview" alt="" src="">
                    <div class="remover-foto" id="remover-foto">
                        <img src="images/octicon_x-12.svg" alt="ícone de remover a foto da publicação">
                    </div>
                </div>
            </div>
            <div class="postagens" id="postagens"></div>
        </div>
        <div class="pesquisar-amigo">
            <label>
                <img src="images/search.svg" alt="search">
                <input type="search" placeholder="PESQUISAR PERFIL" id="pesquisarPerfil">
            </label>
            <div class="lista-pesquisa-perfil" id="listaPesquisaPerfil"></div>
        </div>
        <div class="perfis-sugeridos" id="perfisSugeridos">
            <%
                for (Membro membroSugerido : membrosRede) {
                    if (!membroSugerido.getMembrosSeguidores().contains(membro.getIdPessoa())) {
            %>
            <div class="caixa-usuario" id="caixa-usuario">
                <a href="perfil?nome_usuario=<%=membroSugerido.getNomeUsuario()%>">
                <%
                    if (membroSugerido.getFotoPerfil() == null) {
                        membroSugerido.setFotoPerfil("images/person_foto.svg");
                    }
                %>
                <img src="<%=membroSugerido.getFotoPerfil()%>" alt="Foto de perfil de <%=membroSugerido.getNome()%>">
                <p class="nomeUsuario"><%=membroSugerido.getNome()%>
                </p>
                </a>
                <button onclick="seguirUsuario(<%=membro.getIdPessoa()%>, <%=membroSugerido.getIdPessoa()%>, <%=membrosRede.indexOf(membroSugerido) + 1%>)"
                        class="botaoSeguir" id="botaoSeguir<%=membrosRede.indexOf(membroSugerido) + 1%>">
                    SEGUIR
                </button>
            </div>
            <%
                    }
                }
            %>
        </div>
        <div class="pesquisar-comunidade">
            <label>
                <img src="images/search.svg" alt="search">
                <input type="search" placeholder="PESQUISAR COMUNIDADE" id="pesquisarComunidade">
            </label>
            <div class="lista-pesquisa-comunidade" id="lista-pesquisa-comunidade"></div>
        </div>
        <div class="comunidades-sugeridas" id="comunidades-sugeridas">
            <div class="criar-comunidade" id="criar-comunidade">
                <button id="botaoCriarNovaComunidade">
                    <div class="imagem"><img src="images/gravity-ui_circle-plus-fill.svg" alt=""></div>
                    CRIAR NOVA COMUNIDADE
                </button>
            </div>
            <span id="linhaCriarComunidade"></span>
            <div class="ver-comunidades" id="ver-comunidades">
                <a href="verComunidades">VER COMUNIDADES PARTICIPANTES</a>
                <div class="icone-caret-right"></div>
            </div>
        </div>
    </div>
    <div class="fundo-escuro" id="fundo-escuro-comunidade">
        <div id="popup-nova-comunidade" class="popup-nova-comunidade">
            <div class="popup-content">
                <div class="cabecalho">
                    <span class="close" id="close"><img src="images/octicon_x-12.svg" alt=""></span>
                    <p>NOVA COMUNIDADE</p>
                    <button onclick="criarComunidade(<%=membro.getIdPessoa()%>)" id="botaoCriarComunidade">CRIAR
                    </button>
                </div>
                <div class="foto-fundo" id="foto-fundo">
                    <img id="img-foto-fundo" src="images/img-foto-fundo.png" alt="foto fundo da comunidade">
                    <div class="icone-editar-foto" id="icone-editar-foto-fundo">
                        <img src="images/ri_edit-fill.svg" id="img-icone-editar-foto-fundo"
                             alt="Ícone de alterar foto-fundo">
                    </div>
                    <input type="file" id="editarFotoFundo" name="fotoFundo" accept="image/*">
                </div>
                <div class="foto-perfil-comunidade">
                    <img src="" id="img-foto-perfil-comunidade" alt="foto de perfil da comunidade">
                    <div class="icone-editar-foto" id="icone-editar-foto-perfil">
                        <img src="images/ri_edit-fill.svg" id="img-icone-editar-foto-perfil"
                             alt="Ícone de alterar foto-perfil">
                    </div>
                    <input type="file" id="editarFotoPerfil" name="fotoPerfil" accept="image/*">
                </div>
                <div class="inputs">
                    <label for="nomeComunidade" id="labelNomeComunidade">
                        <small>Nome da comunidade</small>
                        <input type="text" id="nomeComunidade" name="nomeComunidade">
                    </label>
                    <label for="descricaoComunidade" id="labelDescricaoComunidade">
                        <small>Descrição da comunidade</small>
                        <textarea id="descricaoComunidade" name="descricaoComunidade" rows="1"
                                  maxlength="200"></textarea>
                        <div id="contagem-caracteres-comunidade">200</div>
                    </label>
                </div>
            </div>
        </div>
    </div>
</main>
</body>
</html>
<%
        }
    }
%>