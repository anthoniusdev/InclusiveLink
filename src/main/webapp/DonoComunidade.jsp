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
</head>

<body>
<div class="container">
    <div class="row">
        <div class="col-8 mt-5">
            <div
                    style="background-color: #427D9D; border-radius: 20px; margin-bottom: 50px; width: 90%;
                          box-shadow: 5px 5px 7px rgba(0, 0, 0, 0.5); border-bottom: 4px solid #164863;">
                <div>
                    <div style="padding-top: 15px;">
                        <i class="bi bi-arrow-left fs-4 ms-3 icone-voltar" onclick="window.history.back()"> </i>
                        <span class="h4">COMUNIDADE</span>
                    </div>
                    <div style="margin-top: 10px;">
                        <img src="<%=fotoFundo%>"
                             class="img-fluid fundoComunidade"
                             style="width: 100%;  height: 280px; border-top: 4px solid #164863;">
                        <%
                            boolean temModeradores = comunidade.getIdModeradores() != null;

                            if(comunidade.getIdCriador() == usuario.getIdPessoa() || (temModeradores && comunidade.getIdModeradores().contains(usuario.getIdPessoa()))){


                        %>

                        <div class="d-flex justify-content-end pt-3">
                            <button type="button" id="botaoAtivar" class="btn icone"
                                    style="margin-right: 10px;"><i
                                    class="bi bi-list" onclick="ativarOpcoes()"></i></button>
                        </div>
                        <div
                                id="opcoes" class="container-xm d-flex justify-content-end align-items-center d-none"
                                style="height: 15px; margin-top: -15px">
                            <div class="menu">
                                <div class="row"
                                     style="border-radius: 20px">
                                    <div class="col-lg-12 col-sm-6 col-md-4">
                                        <button
                                                type="button" class="btn btn-extra-small">ADICIONAR
                                            MODERADOR
                                        </button>
                                    </div>
                                    <div class="col-lg-12 col-sm-6 ">
                                        <button
                                                type="button" class="btn btn-extra-small">ACEITAR
                                            PARTICIPANTE
                                        </button>
                                    </div>
                                    <div class="col-lg-12 col-sm-6">
                                        <button
                                                type="button" class="btn btn-extra-small">EXCLUIR
                                            COMUNIDADE
                                        </button>
                                    </div>
                                    <div class="col-lg-12 col-sm-6">
                                        <button
                                                type="button" class="btn btn-extra-small" id="editarPerfil" onclick="ativarEditarPerfil()"()>EDITAR
                                            PERFIL
                                        </button>
                                    </div>
                                    <div class="fundo-escuro" name="EditandoPerfil" id="fundo-escuro-editar-perfil" style="display: none">
                                        <div class="pop-up-editar-perfil">
                                            <div class="cabecalho">
                                                <span class="close" id="close-editar-perfil"><img src="images/octicon_x-12.svg" alt=""></span>
                                                <p>EDITANDO PERFIL</p>
                                                <button onclick="editarPerfil()" id="btnSave">SALVAR ALTERAÇÕES</button>
                                            </div>
                                            <div class="foto-fundo-usuario">
                                                <img id="foto-fundo-usuario" src="<%=fotoFundo%>" alt="Foto de fundo de <%=comunidade.getNome()%>">
                                                <div class="icone-editar-foto" id="icone-editar-foto-fundo-usuario">
                                                    <img src="images/ri_edit-fill.svg" id="img-icone-editar-foto-fundo-usuario"
                                                         alt="Ícone de alterar foto perfil">
                                                </div>
                                                <input type="file" id="editarFotoFundoUsuario" name="fotoFundoUsuario" accept="image/*">
                                            </div>
                                            <div class="foto-perfil-usuario">
                                                <img id="foto-perfil-usuario" src="<%=fotoPerfil%>" alt="Foto do perfil de <%=comunidade.getNome()%>">
                                                <div class="icone-editar-foto" id="icone-editar-foto-perfil-usuario">
                                                    <img src="images/ri_edit-fill.svg" id="img-icone-editar-foto-perfil-usuario"
                                                         alt="Ícone de alterar foto perfil">
                                                </div>
                                                <input type="file" id="editarFotoPerfilUsuario" name="fotoPerfilUsuario" accept="image/*">
                                            </div>
                                            <div class="inputs">
                                                <label for="nome-usuario" id="label-nome-usuario">
                                                    <small>Nome</small>
                                                    <input type="text" id="nome-usuario" name="nome-usuario" value="<%=comunidade.getNome()%>"
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
                        <%}else {
                        %>
                        <div class="d-flex justify-content-end pt-3">
                            <button type="button" class="btn icone"
                                    style="margin-right: 10px;">PARTICIPAR</button>
                        </div>
                        <%}
                        %>

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
                        <span style="padding-left: 50px;">0</span>
                        <span style="opacity: 0.5;">Seguindo</span>
                        <span>0</span>
                        <span style="opacity: 0.5;">Seguidores</span>
                    </div>
                </div>
                <div class="postar"
                     style="border-top: 4px solid #164863; padding-bottom: 10px;">
                    <div
                            style="padding-left: 40px; padding-top: 20px; margin-bottom: 15px;">
                        <%String ftPer;
                            ftPer = usuario.getFotoPerfil();
                            if (ftPer == null){
                                ftPer = "images/person_foto.svg";
                            }
                        %>
                        <img src="<%=ftPer%>"
                             class="img-fluid"
                             style="border-radius: 100%; width: 70px; height: 70px;">
                        <i class="bi bi-image"
                           style="padding-left: 10px; cursor: pointer;"></i>
                        <input type="text"
                               placeholder="Oque está acontecendo?">
                        <button type="button" class="btn post"
                                style="font-size: 1.2rem;">Postar
                        </button>
                    </div>
                </div>
                <div class="pots" style="padding-bottom: 10px;">
                    <%for(int idPub: comunidade.getIdPublicacoes()){
                        Publicacao publicacao = new Publicacao(idPub);
                        ftPer = publicacao.getAutor().getFotoPerfil();
                        if (ftPer == null){
                            ftPer = "images/person_foto.svg";
                        }
                    %>
                    <div
                            style="border-top: 4px solid #164863;  padding-left: 40px; padding-top: 20px; margin-bottom: 15px;">
                        <div class="row">
                            <div class="col-md-10">
                                <img src="<%=ftPer%>"
                                     class="img-fluid"
                                     style="border-radius: 100%; width: 70px; height: 70px;">
                                <span class="h5"
                                      style="margin-left: 10px;"><%=publicacao.getAutor().getNome()%></span>
                            </div>
                            <div class="col-md-2">
                                <button type="button" class="btn post"
                                        style="width: 50px; padding-top: 10px;"><i
                                        class="bi bi-trash-fill"></i></button>
                            </div>
                        </div>
                        <div style="padding-left: 80px;">
                            <div
                                    style="width: 90%; height: 300px; border-radius: 20px;">
                                <img src="<%=publicacao.getMidia()%>"
                                     class="img-fluid"
                                     style="border-radius: 20px;">
                            </div>
                        </div>
                        <p style="padding-left: 85px; width: 99%;"><%=publicacao.getTexto()%></p>
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
<%--                    <div--%>
<%--                            style="border-top: 4px solid #164863;  padding-left: 40px; padding-top: 20px; margin-bottom: 15px;">--%>
<%--                        <div class="row">--%>
<%--                            <div class="col-md-10">--%>
<%--                                <img src="DefaultFotoPerfil.webp"--%>
<%--                                     class="img-fluid"--%>
<%--                                     style="border-radius: 100%; width: 70px; height: 70px;">--%>
<%--                                <span class="h5"--%>
<%--                                      style="margin-left: 10px;">Nome de--%>
<%--                                            Usuário</span>--%>
<%--                            </div>--%>
<%--                            <div class="col-md-2">--%>
<%--                                <button type="button" class="btn post"--%>
<%--                                        style="width: 50px; padding-top: 10px;"><i--%>
<%--                                        class="bi bi-trash-fill"></i></button>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                        <p style="padding-left: 85px; width: 99%;">Lorem--%>
<%--                            ipsum dolor sit amet, consectetur adipiscing--%>
<%--                            elit, sed do eiusmod tempor incididunt ut--%>
<%--                            labore et dolore magna aliqua.--%>
<%--                        </p>--%>
<%--                        <div style="padding-left: 80px;">--%>
<%--                            <div--%>
<%--                                    style="width: 90%; height: 300px; border-radius: 20px;">--%>
<%--                                <img src="DefaultFundoPerfil.webp"--%>
<%--                                     class="img-fluid"--%>
<%--                                     style="border-radius: 20px;">--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                        <div--%>
<%--                                style="padding-left: 80px; padding-top:30px">--%>
<%--                            <i class="bi bi-heart icon-custom-size"--%>
<%--                               style=" size: 50px; cursor: pointer;"></i>--%>
<%--                            <span>0</span>--%>
<%--                            <i class="bi bi-chat-left icon-custom-size"--%>
<%--                               style=" size: 50px; cursor: pointer; padding-left: 10px;"></i>--%>
<%--                            <span>0</span>--%>
<%--                        </div>--%>
<%--                    </div>--%>
                </div>
            </div>
        </div>

        <div class="col-4 mt-5">
            <div class="row mb-3"
                 style="background-color: #427D9D; border-radius: 20px; width: 100%; height: 50px;">
                <div class="col-1 fs-5 mt-2 ms-2">
                    <i class="bi bi-search"></i>
                </div>
                <div class="col-7 d-flex justify-content-start">
                    <input type="text" class="form-control"
                           style="color: #DDF2FD; padding-left: 0;"
                           placeholder="PESQUISAR AMIGO">
                </div>

            </div>
            <div class="row mb-3"
                 style="background-color: #427D9D; border-radius: 20px; width: 100%; height: 50px;">

            </div>
            <div class="row mb-3"
                 style="background-color: #427D9D; border-radius: 20px; width: 100%; height: 50px;">
                <div class="col-1 fs-5 mt-2 ms-2">
                    <i class="bi bi-search"></i>
                </div>
                <div class="col-7 d-flex justify-content-start">
                    <input type="text" class="form-control"
                           style="color: #DDF2FD; padding-left: 0;"
                           placeholder="PESQUISAR COMUNIDADE">
                </div>
            </div>
            <div class="row mb-3"
                 style="background-color: #427D9D; border-radius: 20px; width: 100%; height: 50px;">

            </div>
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