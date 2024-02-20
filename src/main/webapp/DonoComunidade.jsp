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
    <link rel="icon"
          href="images/LOGO-InclusiveLink.png">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">

    <link rel="stylesheet" href="styles/DonoComunidade.css">
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
                                                <div class="icone-editar-foto" id="icone-editar-foto-fundo-usuario">
                                                    <img src="images/ri_edit-fill.svg"
                                                         id="img-icone-editar-foto-fundo-usuario"
                                                         alt="Ícone de alterar foto perfil">
                                                </div>
                                                <input type="file" id="editarFotoFundoUsuario" name="fotoFundoUsuario"
                                                       accept="image/*">
                                            </div>
                                            <div class="foto-perfil-usuario">
                                                <img id="foto-perfil-usuario" src="<%=fotoPerfil%>"
                                                     alt="Foto do perfil de <%=comunidade.getNome()%>">
                                                <div class="icone-editar-foto" id="icone-editar-foto-perfil-usuario">
                                                    <img src="images/ri_edit-fill.svg"
                                                         id="img-icone-editar-foto-perfil-usuario"
                                                         alt="Ícone de alterar foto perfil">
                                                </div>
                                                <input type="file" id="editarFotoPerfilUsuario" name="fotoPerfilUsuario"
                                                       accept="image/*">
                                            </div>
                                            <div class="inputs">
                                                <label for="nome-usuario" id="label-nome-usuario">
                                                    <small>Nome</small>
                                                    <input type="text" id="nome-usuario" name="nome-usuario"
                                                           value="<%=comunidade.getNome()%>"
                                                           required>
                                                </label>
                                                <label for="descricao-usuario" id="label-descricao-usuario">
                                                    <small>Descrição</small>
                                                    <textarea id="descricao-usuario" name="descricao-usuario" rows="1"
                                                              maxlength="200"><%if (comunidade.getDescricao() != null) {%><%=comunidade.getDescricao()%><%}%></textarea>
                                                    <div id="contagem-caracteres-descricao-usuario">200</div>
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
<div class="fundo-escuro" id="fundo-escuro-adicionar-moderador">
    <div class="pop-up-adicionar-moderador">
        <div class="cabecalho">
                        <span class="close" id="close-adicionar-moderador"><img src="images/octicon_x-12.svg"
                                                                                alt=""></span>
            <p>ADICIONAR MODERADOR</p>
        </div>
        <div class="lista-participantes">
            <%
                for (int id : comunidade.getIdParticipantes()) {
                    if (id != usuario.getIdPessoa() && !comunidade.getIdModeradores().contains(id)) {
                        Membro membro = new Membro(id);
                        if (membro.getFotoPerfil() == null) {
                            membro.setFotoPerfil("images/person_foto.svg");
                        }
            %>
            <div class="caixa-perfil" id="<%=membro.getNomeUsuario()%>">
                <div class="foto-perfil">
                    <img src="<%=membro.getFotoPerfil()%>" alt="foto de perfil de <%=membro.getNome()%>">
                </div>
                <p class="nome"><%=membro.getNome()%>
                </p>
                <button id="btn-am<%=membro.getIdPessoa()%>" class="btn-adicionar-moderador"
                        onclick="adicionarModerador(<%=membro.getIdPessoa()%>);">PROMOVER
                </button>
            </div>
            <%
                    }
                }
            %>
        </div>
    </div>
</div>
<div class="fundo-escuro" id="fundo-escuro-answer">
    <div class="pop-up-answer">
        <div class="pergunta">
            <small>DESEJA REALMENTE EXCLUIR A COMUNIDADE <%=comunidade.getNome()%>?</small>
        </div>
        <div class="botoes">
            <button id="answer-yes">SIM</button>
            <button id="answer-no">NÃO</button>
        </div>
    </div>
</div>
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
<script src="scripts/DonoComunidade.js"></script>
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
</body>
</html>
<%}%>