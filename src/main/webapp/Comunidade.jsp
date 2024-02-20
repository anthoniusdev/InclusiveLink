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
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="images/LOGO.ico">
    <link rel="stylesheet" href="styles/barra-lateral.css">
    <link rel="stylesheet" href="styles/Comunidade.css">
    <script src="scripts/js/jquery-3.7.1.js"></script>
    <script src="scripts/barra-lateral-amigo/pesquisarPerfil.js"></script>
    <script src="scripts/carregarPublicacoes.js"></script>
    <script src="scripts/barra-lateral-comunidade/criarComunidade.js"></script>
    <script src="scripts/barra-lateral-comunidade/pesquisarComunidade.js"></script>
    <script src="scripts/curtirPublicacao.js"></script>
    <script src="scripts/excluirPublicacao.js"></script>
    <script src="scripts/obterUsuarioAutenticado.js"></script>
    <script src="scripts/perfil.js"></script>
    <script src="scripts/seguirUsuario.js"></script>
    <script src="scripts/verificaSeguindo.js"></script>
    <title></title>
</head>
<body id="body">
<main>
    <div class="container">
        <div class="retanguloPerfil">
            <div class="header">
                <div class="icone-voltar" onclick="window.location.reload();">
                    <img src="images/ri_arrow-up-line.svg">
                </div>
                <p class="voltar">VOLTAR</p>
            </div>
            <img src="images/DefaultFundoPerfil.png" class="fundoPerfil" alt="Foto de fundo do usuário">
            <div class="botao-acao-perfil">
                <button id="editar-perfil">AÇÕES</button>
            </div>
            <img src="images/FOTO-defaultComunidade.png" class="fotoPerfil" alt="Foto de perfil do usuário">
            <p class="nome-usuario">Nome Usuário</p>
            <div class="profile-description">
                <p class="descricao">Lorem ipsum dolor sit amet, consectetur adipiscing elit,
                    sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                    Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut
                    aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in
                    voluptate velit esse cillum dolore eu fugiat nulla pariatur.
                    Excepteur sint occaecat cupidatat non proident,
                    sunt in culpa qui officia deserunt mollit anim id est laborum.
                </p>
            </div>
            <div class="divParticipantes">
                <p class="numParticipantes" id="numParticipantes">0</p>
                <p class="participantes">Participantes</p>
            </div>
            <div class="postagens" id="postagens"></div>
        </div>
        <div class="containerSide">
            <div class="containerSide">
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
                            if (!membroSugerido.getMembrosSeguidores().contains(usuario.getIdPessoa())) {
                    %>
                    <div class="caixa-usuario" id="caixa-usuario">
                        <a href="perfil?nome_usuario=<%=membroSugerido.getNomeUsuario()%>">
                            <%
                                if (membroSugerido.getFotoPerfil() == null) {
                                    membroSugerido.setFotoPerfil("images/person_foto.svg");
                                }
                            %>
                            <img src="<%=membroSugerido.getFotoPerfil()%>"
                                 alt="Foto de perfil de <%=membroSugerido.getNome()%>">
                            <p class="nomeUsuario"><%=membroSugerido.getNome()%>
                            </p>
                        </a>
                        <button onclick="seguirUsuario(<%=usuario.getIdPessoa()%>, <%=membroSugerido.getIdPessoa()%>, <%=membrosRede.indexOf(membroSugerido) + 1%>)"
                                class="botaoSeguir" id="botaoSeguir<%=membrosRede.indexOf(membroSugerido) + 1%>">
                            SEGUIR
                        </button>
                    </div>
                    <%
                            }
                        }
                    %>
                </div>
                <div class="pesquisar-comunidade" id="pesqComunidade">
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
        </div>
        <div class="fundo-escuro" id="fundo-escuro-comunidade">
            <div id="popup-nova-comunidade" class="popup-nova-comunidade">
                <div class="popup-content">
                    <div class="cabecalho">
                        <span class="close" id="close"><img src="images/octicon_x-12.svg" alt=""></span>
                        <p>NOVA COMUNIDADE</p>
                        <button onclick="criarComunidade(<%=usuario.getIdPessoa()%>)" id="botaoCriarComunidade">CRIAR
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
<%--        <%--%>
<%--            if (paginaUsuario) {--%>
<%--        %>--%>
<%--        &lt;%&ndash; Fundo escuro para editar o perfil&ndash;%&gt;--%>
<%--        <div class="fundo-escuro" id="fundo-escuro-editar-perfil">--%>
<%--            <div class="pop-up-editar-perfil">--%>
<%--                <div class="cabecalho">--%>
<%--                    <span class="close" id="close-editar-perfil"><img src="images/octicon_x-12.svg" alt=""></span>--%>
<%--                    <p>EDITANDO PERFIL</p>--%>
<%--                    <button onclick="editarPerfil()" id="btnSave">SALVAR ALTERAÇÕES</button>--%>
<%--                </div>--%>
<%--                <div class="foto-fundo-usuario">--%>
<%--                    <img id="foto-fundo-usuario" src="<%=fotoFundo%>" alt="Foto de fundo de <%=usuario.getNome()%>">--%>
<%--                    <div class="icone-editar-foto" id="icone-editar-foto-fundo-usuario">--%>
<%--                        <img src="images/ri_edit-fill.svg" id="img-icone-editar-foto-fundo-usuario"--%>
<%--                             alt="Ícone de alterar foto perfil">--%>
<%--                    </div>--%>
<%--                    <input type="file" id="editarFotoFundoUsuario" name="fotoFundoUsuario" accept="image/*">--%>
<%--                </div>--%>
<%--                <div class="foto-perfil-usuario">--%>
<%--                    <img id="foto-perfil-usuario" src="<%=fotoPerfil%>" alt="Foto do perfil de <%=usuario.getNome()%>">--%>
<%--                    <div class="icone-editar-foto" id="icone-editar-foto-perfil-usuario">--%>
<%--                        <img src="images/ri_edit-fill.svg" id="img-icone-editar-foto-perfil-usuario"--%>
<%--                             alt="Ícone de alterar foto perfil">--%>
<%--                    </div>--%>
<%--                    <input type="file" id="editarFotoPerfilUsuario" name="fotoPerfilUsuario" accept="image/*">--%>
<%--                </div>--%>
<%--                <div class="inputs">--%>
<%--                    <label for="nome-usuario" id="label-nome-usuario">--%>
<%--                        <small>Nome</small>--%>
<%--                        <input type="text" id="nome-usuario" name="nome-usuario" value="<%=perfilVisitado.getNome()%>"--%>
<%--                               required>--%>
<%--                    </label>--%>
<%--                    <label for="descricao-usuario" id="label-descricao-usuario">--%>
<%--                        <small>Descrição</small>--%>
<%--                        <textarea id="descricao-usuario" name="descricao-usuario" rows="1"--%>
<%--                                  maxlength="200"><%if (perfilVisitado.getDescricao() != null) {%><%=perfilVisitado.getDescricao()%><%}%></textarea>--%>
<%--                        <div id="contagem-caracteres-descricao-usuario">200</div>--%>
<%--                    </label>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--        <%}%>--%>
<%--        &lt;%&ndash; Fundo escuro para visualizar os seguindos&ndash;%&gt;--%>
<%--        <div class="fundo-escuro" id="fundo-escuro-seguindos">--%>
<%--            <div class="pop-up-ver-seguindos">--%>
<%--                <div class="cabecalho">--%>
<%--                    <span class="close" id="close-seguindos"><img src="images/octicon_x-12.svg" alt=""></span>--%>
<%--                    <p>SEGUINDOS</p>--%>
<%--                </div>--%>
<%--                <div class="lista-seguindos">--%>
<%--                    <%--%>
<%--                        for (int idSeguindo : perfilVisitado.getMembrosSeguindo()) {--%>
<%--                            Membro seguindo = new Membro(idSeguindo);--%>
<%--                            String ftPer = seguindo.getFotoPerfil();--%>
<%--                            if (ftPer == null) {--%>
<%--                                ftPer = "images/person_foto.svg";--%>
<%--                            }--%>
<%--                    %>--%>
<%--                    <div class="caixa-perfil" id="<%=seguindo.getNomeUsuario()%>">--%>
<%--                        <div class="foto-perfil">--%>
<%--                            <img src="<%=ftPer%>" alt="foto de perfil de <%=seguindo.getNome()%>">--%>
<%--                        </div>--%>
<%--                        <p class="nome"><%=seguindo.getNome()%>--%>
<%--                        </p>--%>
<%--                        <%--%>
<%--                            if (paginaUsuario) {--%>
<%--                        %>--%>
<%--                        <button class="remover"--%>
<%--                                onclick="pararSeguir(<%=usuario.getIdPessoa()%>, <%=seguindo.getIdPessoa()%>, '<%=seguindo.getNomeUsuario()%>')">--%>
<%--                            REMOVER--%>
<%--                        </button>--%>
<%--                        <%}%>--%>
<%--                    </div>--%>
<%--                    <%--%>
<%--                        }--%>
<%--                    %>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>&lt;%&ndash;--%>
<%--        &lt;%&ndash; Fundo escuro para visulizar os seguidores&ndash;%&gt;--%>
<%--        <div class="fundo-escuro" id="fundo-escuro-seguidores">--%>
<%--            <div class="pop-up-seguidores">--%>
<%--                <div class="cabecalho">--%>
<%--                    <span class="close" id="close-seguidores"><img src="images/octicon_x-12.svg" alt=""></span>--%>
<%--                    <p>SEGUIDORES</p>--%>
<%--                </div>--%>
<%--                <div class="lista-seguidores">--%>
<%--                    <%--%>
<%--                        for (int idSeguidor : perfilVisitado.getMembrosSeguidores()) {--%>
<%--                            Membro seguidor = new Membro(idSeguidor);--%>
<%--                            String ftPer = seguidor.getFotoPerfil();--%>
<%--                            if (ftPer == null) {--%>
<%--                                ftPer = "images/person_foto.svg";--%>
<%--                            }--%>
<%--                    %>--%>
<%--                    <div class="caixa-perfil" id="<%=seguidor.getNomeUsuario()%>">--%>
<%--                        <div class="foto-perfil">--%>
<%--                            <img src="<%=ftPer%>" alt="foto de perfil de <%=seguidor.getNome()%>">--%>
<%--                        </div>--%>
<%--                        <p class="nome"><%=seguidor.getNome()%>--%>
<%--                        </p>--%>
<%--                        <%--%>
<%--                            if (paginaUsuario) {--%>
<%--                        %>--%>
<%--                        <button class="remover"--%>
<%--                                onclick="removerSeguidor(<%=seguidor.getIdPessoa()%>); this.parentNode.style.display='none'">--%>
<%--                            REMOVER--%>
<%--                        </button>--%>
<%--                        <%}%>--%>
<%--                    </div>--%>
<%--                    <%--%>
<%--                        }--%>
<%--                    %>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>&ndash;%&gt;--%>
    </div>
</main>
</body>
</html>
<%}%>