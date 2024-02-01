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
<%@ page import="model.Comunidade" %>
<%@ page import="model.Publicacao" %>
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
            @SuppressWarnings("unchecked")
            ArrayList<Publicacao> feedUsuario = (ArrayList<Publicacao>) httpSession.getAttribute("feed");

%>
<html lang="pt-BR">
<head>
    <title>Inclusive Link</title>
    <link rel="stylesheet" href="styles/PaginaInicial.css">
    <script src="scripts/js/jquery-3.7.1.js"></script>
    <script src="scripts/paginaInicial.js"></script>
    <script src="scripts/barra-lateral-pesquisar-amigo/pesquisarPerfil.js"></script>
    <script src="scripts/barra-lateral-criar-comunidade/criarComunidade.js"></script>
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
            <div class="postagens">
                <%
                    if (!feedUsuario.isEmpty()) {
                        for (Publicacao publicacao : feedUsuario) {
                            if (publicacao.getAutor().getFotoPerfil() == null) {
                                publicacao.getAutor().setFotoPerfil("images/person_foto.svg");
                            }
                %>
                <div class="caixa-publicacao">
                    <div class="foto-perfil-autor">
                        <img src="<%=publicacao.getAutor().getFotoPerfil()%>"
                             alt="Foto do perfil de <%=publicacao.getAutor().getNome()%>">
                    </div>
                    <div class="informacoes-publicacao">
                        <div class="nome-autor">
                            <h3><%=publicacao.getAutor().getNome()%>
                            </h3>
                        </div>
                        <div class="texto-publicacao">
                            <p><%=publicacao.getTexto()%>
                            </p>
                        </div>
                        <%
                            if (publicacao.getMidia() != null) {
                        %>
                        <div class="midia-publicacao">
                            <img src="<%=publicacao.getMidia()%>" alt="">
                        </div>
                        <%
                            }
                        %>
                        <div class="inshights-publicacao">
                            <div class="curtida-publicacao">
                                <%
                                    String ftCurt = null;
                                    if (!publicacao.getCurtidas().isEmpty()) {
                                        System.out.println(publicacao.getCurtidas().getFirst().getIdPessoa());
                                        System.out.println("Membro " + publicacao.getCurtidas().getFirst().getNome() + " curtiu");
                                        for (Membro membro1: publicacao.getCurtidas()){
                                            if (membro1.getIdPessoa() == membro.getIdPessoa()) {
                                                ftCurt = "images/iconamoon_heart-fill.svg";
                                                break;
                                            }
                                        }
                                    } else {
                                        System.out.println("ta vazio essa porrrra");
                                        ftCurt = "images/iconamoon_heart-bold.svg";
                                    }
                                %>
                                <img class="icone-curtida" src="<%=ftCurt%>" alt="Ícone de curtida"
                                     onclick="curtirPublicacao(<%=publicacao.getIdPublicacao()%>)">
                                <%--                        Adicionar a lógica para adicionar as imagens certas aqui--%>
                                <small><%=publicacao.getCurtidas().size()%>
                                </small>
                            </div>
                            <div class="comentarios-publicacao">
                                <img src="images/majesticons_comment-line.svg" alt="Ícone de comentário">
                                <%--                        Adicionar a lógica para adicionar as imagens certas aqui--%>
                                <small><%=publicacao.getComentarios().size()%>
                                </small>
                            </div>
                        </div>
                    </div>
                </div>
                <%
                    }
                } else {
                %>
                <h3>ArrayVazia</h3>
                <%}%>
            </div>
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
    <div id="fundo-escuro">
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