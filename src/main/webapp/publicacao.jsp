<%@ page import="model.Membro" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Publicacao" %><%--
  Created by IntelliJ IDEA.
  User: antho
  Date: 05/02/2024
  Time: 00:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    HttpSession httpSession = request.getSession(false);
    if (httpSession == null || httpSession.getAttribute("authenticated") == null) {
        response.sendRedirect("index.html");
        return;
    } else {
        if (httpSession.getAttribute("usuario") != null) {
            Membro membro = (Membro) httpSession.getAttribute("usuario");
            @SuppressWarnings("unchecked")
            ArrayList<Membro> membrosRede = (ArrayList<Membro>) httpSession.getAttribute("perfis");
            Publicacao publicacao = (Publicacao) httpSession.getAttribute("publicacao");
%>
<html lang="pt-BR">
<head>
    <title>Inclusive Link</title>
    <link rel="icon" href="images/LOGO.ico">
    <link rel="stylesheet" href="styles/barra-lateral.css">
    <link rel="stylesheet" href="styles/publicacao.css">
    <script src="scripts/js/jquery-3.7.1.js"></script>
    <script src="scripts/barra-lateral-amigo/pesquisarPerfil.js"></script>
    <script src="scripts/barra-lateral-comunidade/criarComunidade.js"></script>
    <script src="scripts/barra-lateral-comunidade/pesquisarComunidade.js"></script>
    <script src="scripts/curtirPublicacao.js"></script>
    <script src="scripts/excluirPublicacao.js"></script>
    <script src="scripts/publicacao.js"></script>
    <script src="scripts/seguirUsuario.js"></script>
</head>
<body>
<main>
    <div class="principal">
        <div class="cabecalho-post">
            <div class="icone-voltar">
                <img src="images/ri_arrow-up-line.svg" alt="imagem de setinha para voltar a página anterior">
            </div>
            <h3>POST</h3>
        </div>
        <div class="publicacao" id="publicacao">
            <div class="img-foto-perfil">
                <%
                    String urlFtPer = publicacao.getAutor().getFotoPerfil();
                    if (urlFtPer == null) {
                        urlFtPer = "images/person_foto.svg";
                    }
                %>
                <img src="<%=urlFtPer%>" alt="Foto de perfil de <%=publicacao.getAutor().getNome()%>">
            </div>
            <div class="informacoes-publicacao">
                <div class="nome-autor">
                    <h3><%=publicacao.getAutor().getNome()%>
                    </h3>
                </div>
                <div class="texto-publicacao">
                    <%=publicacao.getTexto()%>
                </div>
                <%
                    if (publicacao.getMidia() != null) {
                %>
                <div class="midia-publicacao">
                    <img src="<%=publicacao.getMidia()%>" alt="Foto da publicação">
                </div>
                <%}%>
                <div class="data-hora">
                    <div class="data"><%=publicacao.getData()%>
                    </div>
                    <div class="hora"><%=publicacao.getHora()%>
                    </div>
                </div>
                <div class="inshights-publicacao">
                    <div class="curtida-publicacao" id="curtida-publicacao">
                        <%
                            String urlCurt = "images/iconamoon_heart-bold.svg";
                            for (int membroCurtiu : publicacao.getCurtidas()) {
                                if (membroCurtiu == membro.getIdPessoa()) {
                                    urlCurt = "images/iconamoon_heart-fill.svg";
                                    break;
                                }
                            }
                        %>
                        <img src="<%=urlCurt%>" alt="Ícone de curtida" class="icone-curtida"
                             onclick="curtirPublicacao(<%=publicacao.getIdPublicacao()%>)">
                    </div>
                    <div class="comentarios-publicacao" id="comentarios-publicacao">
                        <img src="images/majesticons_comment-line.svg" alt="Ícone de comentário"
                             class="icone-comentario">
                    </div>
                </div>
            </div>
        </div>
        <div class="adicionar-comentario">
            <form action="novoComentario" method="post" id="formNovoComentario">
                <div class="foto-perfil">
                    <img src="<%=urlFtPer%>" alt="Foto do perfil de <%=membro.getNome()%>">
                </div>
                <div class="area-input">
                    <label for="textoNovaPublicacao">
                        <textarea name="inputTexto" id="textoNovaPublicacao" placeholder="Digite sua resposta"
                                  rows="1" maxlength="200"></textarea>
                    </label>
                    <div id="contagemCaracteresPublicacao">200</div>
                    <span id="linhaAreaInput"></span>
                </div>
                <div class="btnPostar">
                    <button type="submit" id="btnPostar">POSTAR</button>
                </div>
            </form>
        </div>
        <div class="comentarios-publicacao" id="comentarios"></div>
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
            <%
                if (membroSugerido.getFotoPerfil() == null) {
                    membroSugerido.setFotoPerfil("images/person_foto.svg");
                }
            %>
            <img src="<%=membroSugerido.getFotoPerfil()%>" alt="Foto de perfil de <%=membroSugerido.getNome()%>">
            <p class="nomeUsuario"><%=membroSugerido.getNome()%>
            </p>
            <button onclick="seguirUsuario(<%=membro.getIdPessoa()%>, <%=membroSugerido.getIdPessoa()%>, <%=membrosRede.indexOf(membroSugerido)%>)"
                    class="botaoSeguir" id="botaoSeguir<%=membrosRede.indexOf(membroSugerido)%>">
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