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
<!doctype html>
<html lang="pt-br">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Inclusive Link</title>
    <link rel="icon" href="images/LOGO-InclusiveLink.png">

    <link rel="stylesheet" href="styles/DonoComunidade.css">
<<<<<<< HEAD

    <link rel="icon" href="images/LOGO.ico">
    <link rel="stylesheet" href="styles/barra-lateral.css">
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
    <script src="scripts/perfil.js"></script>
    <script src="scripts/seguirUsuario.js"></script>
    <script src="scripts/verificaSeguindo.js"></script>

</head>


<div class="container">
    <div class="retanguloPerfil">
        <div class="header">
            <a href="home"><p class="homePage">VOLTAR</p></a>
        </div>
        <%--    Foto de fundo do usuário --%>
        <img src="<%=fotoFundo%>" class="fundoPerfil" alt="Foto de fundo do usuário">
        <%
            boolean temModeradores = comunidade.getIdModeradores() != null;
            if (comunidade.getIdCriador() == usuario.getIdPessoa() || (temModeradores && comunidade.getIdModeradores().contains(usuario.getIdPessoa()))) {
        %>
        <div class="botao-acao-perfil">
            <button class="btn-editar-perfil" id="editar-perfil">EDITAR PERFIL</button>
        </div>
        <%
        } else {
            String txt = "SEGUIR";
            if (usuario.segue(perfilVisitado.getIdPessoa())) {
                txt = "SEGUINDO";
            }
        %>
        <div class="botao-acao-perfil">
            <button id="botao-seguir-perfil"
                    onclick="seguirUsuario(<%=usuario.getIdPessoa()%>, <%=perfilVisitado.getIdPessoa()%>, false)"><%=txt%>
            </button>
        </div>
        <%}%>
        <%-- Foto de perfil do usuário --%>
        <img src="<%=fotoPerfil%>" class="fotoPerfil" alt="Foto de perfil do usuário">
        <p class="nome-usuario"><%=perfilVisitado.getNome()%></p>

        <%if (perfilVisitado.getDescricao() != null) {%>
        <div class="profile-description">
            <p class="descricao"><%=perfilVisitado.getDescricao()%>
            </p>
        </div>
        <%}%>

        <div class="seguindoSeguidores">
            <p class="numSeguindo"
               id="numSeguindos"><%=perfilVisitado.getMembrosSeguindo().size() + perfilVisitado.getComunidadesSeguindo().size()%>
            </p>
            <p class="seguindo">Seguindo</p>

            <p class="numSeguidores" id="numSeguidores"><%=perfilVisitado.getMembrosSeguidores().size()%>
            </p>
            <p class="seguidores">Seguidores</p>
        </div>
        <div class="postagens" id="postagens"></div>
    </div>
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
                <button onclick="seguirUsuario(<%=usuario.getIdPessoa()%>, <%=membroSugerido.getIdPessoa()%>, <%=membrosRede.indexOf(membroSugerido)%>)"
                        class="botaoSeguir" id="botaoSeguir<%=membrosRede.indexOf(membroSugerido)%>">
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
    <%-- Fundo escuro para criar comunidade --%>
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
    <%
        if (paginaUsuario) {
    %>
    <%-- Fundo escuro para editar o perfil--%>
    <div class="fundo-escuro" id="fundo-escuro-editar-perfil">
        <div class="pop-up-editar-perfil">
            <div class="cabecalho">
                <span class="close" id="close-editar-perfil"><img src="images/octicon_x-12.svg" alt=""></span>
                <p>EDITANDO PERFIL</p>
                <button onclick="editarPerfil()" id="btnSave">SALVAR ALTERAÇÕES</button>
            </div>
            <div class="foto-fundo-usuario">
                <img id="foto-fundo-usuario" src="<%=fotoFundo%>" alt="Foto de fundo de <%=usuario.getNome()%>">
                <div class="icone-editar-foto" id="icone-editar-foto-fundo-usuario">
                    <img src="images/ri_edit-fill.svg" id="img-icone-editar-foto-fundo-usuario"
                         alt="Ícone de alterar foto perfil">
                </div>
                <input type="file" id="editarFotoFundoUsuario" name="fotoFundoUsuario" accept="image/*">
            </div>
            <div class="foto-perfil-usuario">
                <img id="foto-perfil-usuario" src="<%=fotoPerfil%>" alt="Foto do perfil de <%=usuario.getNome()%>">
                <div class="icone-editar-foto" id="icone-editar-foto-perfil-usuario">
                    <img src="images/ri_edit-fill.svg" id="img-icone-editar-foto-perfil-usuario"
                         alt="Ícone de alterar foto perfil">
                </div>
                <input type="file" id="editarFotoPerfilUsuario" name="fotoPerfilUsuario" accept="image/*">
            </div>
            <div class="inputs">
                <label for="nome-usuario" id="label-nome-usuario">
                    <small>Nome</small>
                    <input type="text" id="nome-usuario" name="nome-usuario" value="<%=perfilVisitado.getNome()%>"
                           required>
                </label>
                <label for="descricao-usuario" id="label-descricao-usuario">
                    <small>Descrição</small>
                    <textarea id="descricao-usuario" name="descricao-usuario" rows="1"
                              maxlength="200"><%if (perfilVisitado.getDescricao() != null) {%><%=perfilVisitado.getDescricao()%><%}%></textarea>
                    <div id="contagem-caracteres-descricao-usuario">200</div>
                </label>
            </div>
        </div>
    </div>
    <%}%>
    <%-- Fundo escuro para visualizar os seguindos--%>
    <div class="fundo-escuro" id="fundo-escuro-seguindos">
        <div class="pop-up-ver-seguindos">
            <div class="cabecalho">
                <span class="close" id="close-seguindos"><img src="images/octicon_x-12.svg" alt=""></span>
                <p>SEGUINDOS</p>
            </div>
            <div class="lista-seguindos">
                <%
                    for (int idSeguindo : perfilVisitado.getMembrosSeguindo()) {
                        Membro seguindo = new Membro(idSeguindo);
                        String ftPer = seguindo.getFotoPerfil();
                        if (ftPer == null) {
                            ftPer = "images/person_foto.svg";
                        }
                %>
                <div class="caixa-perfil" id="<%=seguindo.getNomeUsuario()%>">
                    <div class="foto-perfil">
                        <img src="<%=ftPer%>" alt="foto de perfil de <%=seguindo.getNome()%>">
                    </div>
                    <p class="nome"><%=seguindo.getNome()%>
                    </p>
                    <%
                        if (paginaUsuario) {
                    %>
                    <button class="remover"
                            onclick="pararSeguir(<%=usuario.getIdPessoa()%>, <%=seguindo.getIdPessoa()%>, '<%=seguindo.getNomeUsuario()%>')">
                        REMOVER
                    </button>
                    <%}%>
                </div>
                <%
                    }
                %>
            </div>
        </div>
    </div>
    <%-- Fundo escuro para visulizar os seguidores--%>
    <div class="fundo-escuro" id="fundo-escuro-seguidores">
        <div class="pop-up-seguidores">
            <div class="cabecalho">
                <span class="close" id="close-seguidores"><img src="images/octicon_x-12.svg" alt=""></span>
                <p>SEGUIDORES</p>
            </div>
            <div class="lista-seguidores">
                <%
                    for (int idSeguidor : perfilVisitado.getMembrosSeguidores()) {
                        Membro seguidor = new Membro(idSeguidor);
                        String ftPer = seguidor.getFotoPerfil();
                        if (ftPer == null) {
                            ftPer = "images/person_foto.svg";
                        }
                %>
                <div class="caixa-perfil" id="<%=seguidor.getNomeUsuario()%>">
                    <div class="foto-perfil">
                        <img src="<%=ftPer%>" alt="foto de perfil de <%=seguidor.getNome()%>">
                    </div>
                    <p class="nome"><%=seguidor.getNome()%>
                    </p>
                    <%
                        if (paginaUsuario) {
                    %>
                    <button class="remover"
                            onclick="removerSeguidor(<%=seguidor.getIdPessoa()%>); this.parentNode.style.display='none'">
                        REMOVER
                    </button>
                    <%}%>
                </div>
                <%
                    }
                %>
            </div>
        </div>
    </div>
</div>
</main>
</body>


<!--
<body>
=======
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css"
            rel="stylesheet">
    <script src="scripts/js/jquery-3.7.1.js"></script>
    <script src="scripts/participarComunidade.js"></script>
    <script src="scripts/sairComunidade.js"></script>
    <script src="scripts/comunidade.js"></script>
    <script src="scripts/excluirPublicacao.js"></script>
    <link rel="stylesheet" href="styles/barra-lateral.css">
</head>

<body id="body">
>>>>>>> a44f48951deef5c895dfcb321d0dea23a44cd895
<div class="container">
    <div class="row">
        <div class="col-8 mt-5">
            <div
                    style="background-color: #427D9D; border-radius: 20px; margin-bottom: 50px; width: 90%;
                          box-shadow: 5px 5px 7px rgba(0, 0, 0, 0.5); border-bottom: 4px solid #164863;">
                <div>
                    <div style="padding-top: 15px;">
                        <i class="bi bi-arrow-left fs-4 ms-3 icone-voltar" onclick="window.history.back();"> </i>
                        <span class="h4">COMUNIDADE</span>
                    </div>
                    <div style="margin-top: 10px;">
                        <img src="<%=fotoFundo%>"
                             class="img-fluid fundoComunidade"
                             style="width: 100%;  height: 280px; border-top: 4px solid #164863;">
                        <%
                            boolean temModeradores = comunidade.getIdModeradores() != null;

                            if (temModeradores && comunidade.getIdModeradores().contains(usuario.getIdPessoa())) {


                        %>

                        <div class="d-flex justify-content-end pt-3">
                            <button type="button" id="botaoAtivar" class="btn icone"
                                    style="margin-right: 10px;"><i
                                    class="bi bi-list" onclick="ativarOpcoes()"></i></button>
                        </div>
                        <div id="opcoes" class="container-xm d-flex justify-content-end align-items-center d-none"
                             style="height: 15px; margin-top: -15px">
                            <div class="menu">
                                <div class="row"
                                     style="border-radius: 20px">
                                    <div class="col-lg-12 col-sm-6 col-md-4">
                                        <button id="adicionar-moderador" type="button" class="btn btn-extra-small">
                                            ADICIONAR
                                            MODERADOR
                                        </button>
                                    </div>
                                    <div class="col-lg-12 col-sm-6 ">
                                        <button type="button" class="btn btn-extra-small">ACEITAR
                                            PARTICIPANTE
                                        </button>
                                    </div>
                                    <div class="col-lg-12 col-sm-6">
                                        <button id="excluir-comunidade" type="button" class="btn btn-extra-small"
                                                onclick="">EXCLUIR
                                            COMUNIDADE
                                        </button>
                                    </div>
                                    <div class="col-lg-12 col-sm-6">
                                        <button
                                                type="button" class="btn btn-extra-small" id="editarPerfil"
                                                onclick="ativarEditarPerfil()" ()>EDITAR
                                            PERFIL
                                        </button>
                                    </div>
                                    <div class="fundo-escuro" name="EditandoPerfil" id="fundo-escuro-editar-perfil"
                                         style="display: none">
                                        <div class="pop-up-editar-perfil">
                                            <div class="cabecalho">
                                                <span class="close" id="close-editar-perfil"><img
                                                        src="images/octicon_x-12.svg" alt=""></span>
                                                <p>EDITANDO PERFIL</p>
                                                <button onclick="editarPerfil()" id="btnSave">SALVAR ALTERAÇÕES</button>
                                            </div>
                                            <div class="foto-fundo-usuario">
                                                <img id="foto-fundo-usuario" src="<%=fotoFundo%>"
                                                     alt="Foto de fundo de <%=comunidade.getNome()%>">
                                                <div class="icone-editar-foto" id="icone-editar-foto-fundo-usuario-2">
                                                    <img src="images/ri_edit-fill.svg"
                                                         id="img-icone-editar-foto-fundo-usuario-2"
                                                         alt="Ícone de alterar foto perfil">
                                                </div>
                                                <input type="file" id="editarFotoFundoUsuario-2" name="fotoFundoUsuario"
                                                       accept="image/*">
                                            </div>
                                            <div class="foto-perfil-usuario">
                                                <img id="foto-perfil-usuario-2" src="<%=fotoPerfil%>"
                                                     alt="Foto do perfil de <%=comunidade.getNome()%>">
                                                <div class="icone-editar-foto" id="icone-editar-foto-perfil-usuario-2">
                                                    <img src="images/ri_edit-fill.svg"
                                                         id="img-icone-editar-foto-perfil-usuario-2"
                                                         alt="Ícone de alterar foto perfil">
                                                </div>
                                                <input type="file" id="editarFotoPerfilUsuario-2" name="fotoPerfilUsuario"
                                                       accept="image/*">
                                            </div>
                                            <div class="inputs">
                                                <label for="nome-comunidade" id="label-nome-usuario-2">
                                                    <small>Nome</small>
                                                    <input type="text" id="nome-usuario-2" name="nome-usuario"
                                                           value="<%=comunidade.getNome()%>"
                                                           required>
                                                </label>
                                                <label for="descricao-comunidade" id="label-descricao-usuario-2">
                                                    <small>Descrição</small>
                                                    <textarea id="descricao-usuario-2" name="descricao-usuario" rows="1"
                                                              maxlength="200"><%if (comunidade.getDescricao() != null) {%><%=comunidade.getDescricao()%><%}%></textarea>
                                                    <div id="contagem-caracteres-descricao-usuario-2">200</div>
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%
                        } else if (!comunidade.getIdParticipantes().contains(usuario.getIdPessoa())) {
                        %>
                        <div class="d-flex justify-content-end pt-3">
                            <button type="button" class="btn icone"
                                    style="margin-right: 10px;"
                                    onclick="participarComunidade(<%=comunidade.getIdComunidade()%>)">PARTICIPAR
                            </button>
                        </div>
                        <%
                        } else {
                        %>
                        <div class="d-flex justify-content-end pt-3">
                            <button type="button" onclick="sairComunidade(<%=comunidade.getIdComunidade()%>)"
                                    class="btn icone sair-comunidade">SAIR
                            </button>
                        </div>
                        <%}%>
                        <div
                                style="margin-left: 30px; margin-top: -140px;">
                            <img src="<%=fotoPerfil%>"
                                 class="img-fluid fotoComunidade"
                                 style="border-radius: 100%; width: 150px; height: 150px; border: 5px solid #427D9D">
                        </div>
                        <p class="h5 nomeComunidade" style="margin-left: 50px;"><%=nome%>
                        </p>
                    </div>
                    <p class="descricaoComunidade" style="margin-left: 50px; width: 80%;"><%=descricao%>
                    </p>

                    <div style="padding-bottom: 20px;">
                        <span style="padding-left: 50px;"><%=comunidade.getIdParticipantes().size()%></span>
                        <span style="opacity: 0.5;">Participantes</span>
                    </div>
                </div>
                <%
                    String ftPer = usuario.getFotoPerfil();
                    if (ftPer == null) {
                        ftPer = "images/person_foto.svg";
                    }
                %>
                <div class="criarPublicacao">
                    <form action="novaPublicacao" method="post" id="formNovaPublicacao" enctype="multipart/form-data">
                        <div class="foto-perfil">
                            <img src="<%=ftPer%>" alt="Foto de perfil">
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
                <div class="pots" style="padding-bottom: 10px;">
                    <%
                        for (int idPub : comunidade.getIdPublicacoes()) {
                            Publicacao publicacao = new Publicacao(idPub);
                            ftPer = publicacao.getAutor().getFotoPerfil();
                            if (ftPer == null) {
                                ftPer = "images/person_foto.svg";
                            }
                    %>
                    <div style="border-top: 4px solid #164863;  padding-left: 40px; padding-top: 20px; margin-bottom: 15px;">
                        <div class="row">
                            <div class="col-md-10">
                                <img src="<%=ftPer%>"
                                     class="img-fluid"
                                     style="border-radius: 100%; width: 70px; height: 70px;">
                                <span class="h5"
                                      style="margin-left: 10px;"><%=publicacao.getAutor().getNome()%></span>
                            </div>
                            <div class="col-md-2">
                                <%if (publicacao.getAutor().getIdPessoa() == usuario.getIdPessoa()) {%>
                                <button type="button" class="btn post"
                                        style="width: 50px; padding-top: 10px;"
                                        onclick="excluirPublicacao(<%=publicacao.getIdPublicacao()%>); this.parentNode.parentNode.parentNode.parentNode.style.display = 'none';">
                                    <i
                                            class="bi bi-trash-fill"></i></button>
                                <%}%>
                            </div>
                        </div>
                        <p style="padding-left: 85px; width: 99%;"><%=publicacao.getTexto()%>
                        </p>
                        <%if (publicacao.getMidia() != null) {%>
                        <div style="padding-left: 80px;">
                            <div
                                    style="width: 90%; height: 300px; border-radius: 20px;">
                                <img id="imgP" src="<%=publicacao.getMidia()%>"
                                     class="img-fluid"
                                     style="border-radius: 20px;">
                            </div>
                        </div>
                        <%}%>
                        <div style="padding-left: 80px;">
                            <i class="bi bi-heart icon-custom-size"
                               style=" size: 50px; cursor: pointer;"></i>
                            <span><%=publicacao.getNumeroCurtidas()%></span>
                            <i class="bi bi-chat-left icon-custom-size"
                               style=" size: 50px; cursor: pointer; padding-left: 10px;"></i>
                            <span><%=publicacao.getNumeroComentarios()%></span>
                        </div>
                    </div>
                    <%}%>
                </div>
            </div>
        </div>
    </div>
</div>
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


<div class="fundo-escuro" id="fundo-escuro-editar-comunidade">
    <div class="pop-up-editar-comunidade">
        <div class="cabecalho">
            <span class="close" id="close-editar-comunida"><img src="images/octicon_x-12.svg" alt=""></span>
            <p>EDITANDO COMUNIDADE</p>
            <button onclick="editarPerfil()" id="btnSave-2">SALVAR ALTERAÇÕES</button>
        </div>
        <div class="foto-fundo-comunidade">
            <img id="foto-fundo-comunidade" src="<%=fotoFundo%>" alt="Foto de fundo de <%=usuario.getNome()%>">
            <div class="icone-editar-foto" id="icone-editar-foto-fundo-comunidade">
                <img src="images/ri_edit-fill.svg" id="img-icone-editar-foto-fundo-comunidade"
                     alt="Ícone de alterar foto comunidade">
            </div>
            <input type="file" id="editarFotoFundoComunidade" name="fotoFundoComunidade" accept="image/*">
        </div>
        <div class="foto-perfil-comunidade">
            <img id="foto-perfil-comunidade" src="<%=fotoPerfil%>" alt="Foto do perfil de <%=usuario.getNome()%>">
            <div class="icone-editar-foto" id="icone-editar-foto-perfil-comunidade">
                <img src="images/ri_edit-fill.svg" id="img-icone-editar-foto-perfil-comunidade"
                     alt="Ícone de alterar foto comunidade">
            </div>
            <input type="file" id="editarFotoPerfilComunidade" name="fotoPerfilComunidade" accept="image/*">
        </div>
        <div class="inputs">
            <label for="nome-usuario-2" id="label-nome-comunidade">
                <small>Nome</small>
                <input type="text" id="nome-comunidade" name="nome-comunidade value="<%=comunidade.getNome()%>"
                       required>
            </label>
            <label for="descricao-usuario-2" id="label-descricao-comunidade">
                <small>Descrição</small>
                <textarea id="descricao-comunidade" name="descricao-comunidade" rows="1"
                          maxlength="200"><%if (comunidade.getDescricao() != null) {%><%=comunidade.getDescricao()%><%}%></textarea>
                <div id="contagem-caracteres-descricao-usuario">200</div>
            </label>
        </div>
    </div>
</div>
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
<script src="scripts/DonoComunidade.js"></script>
<<<<<<< HEAD
<script>
    function ativarEditarPerfil() {
        console.log("Função ativarEditarPerfil() chamada.");
        var minhaDiv = document.getElementsByName("EditandoPerfil");
        if (minhaDiv.classList.contains("d-none")) {

            minhaDiv.classList.remove("d-none");
            minhaDiv.classList.add("d-block");
        }
    }
</script>
</body>-->
=======
</body>
>>>>>>> da4e84a21652f8a2fb6e9c3be1185ba385e4c3ae
</html>
<%}%>