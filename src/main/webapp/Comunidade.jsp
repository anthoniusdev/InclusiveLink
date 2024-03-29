<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="model.Membro" %>
<%@ page import="model.Comunidade" %>
<%@ page import="java.util.ArrayList" %>
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
        if (comunidade != null) {
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
    <script src="scripts/comunidade.js"></script>
    <script src="scripts/seguirUsuario.js"></script>
    <script src="scripts/verificaSeguindo.js"></script>
    <script src="scripts/participarComunidade.js"></script>
    <script src="scripts/sairComunidade.js"></script>
    <title><%=comunidade.getNome()%> - Inclusive Link</title>
</head>
<body id="body">
<main>
    <div class="container">
        <div class="retanguloPerfil">
            <div class="header">
                <div class="icone-voltar" onclick="window.history.back();">
                    <img src="images/ri_arrow-up-line.svg" alt="Ícone de voltar">
                </div>
                <p class="voltar">VOLTAR</p>
            </div>
            <% if (comunidade.getFotoFundo() == null) {
                comunidade.setFotoFundo("images/DefaultFundoPerfil.png");
            }%>
            <img src="<%=comunidade.getFotoFundo()%>" class="fundoPerfil"
                 alt="Foto de fundo da comunidade <%=comunidade.getNome()%>">
            <div class="botao-acao-perfil">
                <%if (comunidade.getIdModeradores().contains(usuario.getIdPessoa())) {%>
                <button class="adicionar-moderador" id="adicionar-moderador">ADICIONAR MODERADOR</button>
                <button class="excluir-comunidade" id="excluir-comunidade">EXCLUIR COMUNIDADE</button>
                <button class="editar-perfil" id="editar-perfil">EDITAR PERFIL</button>
                <%
                    }
                    if (comunidade.getIdParticipantes().contains(usuario.getIdPessoa())) {
                %>
                <button class="sair-comunidade"
                        onclick="sairComunidade(<%=comunidade.getIdComunidade()%>); window.location.reload(true)">SAIR
                </button>
                <%if(!comunidade.getIdModeradores().contains(usuario.getIdPessoa())){%>
                <style>
                    .fotoPerfil{
                        margin-top: -9rem;
                    }
                </style>
                <%}%>

                <%} else {%>
                <button class="participar-comunidade"
                        onclick="participarComunidade(<%=comunidade.getIdComunidade()%>); window.location.reload(true)">
                    PARTICIPAR
                </button>

                <%if(!comunidade.getIdModeradores().contains(usuario.getIdPessoa())){%>
                <style>
                    .fotoPerfil{
                        margin-top: -9rem;
                    }
                </style>
                <%}%>

                <%}%>
            </div>
            <% if (comunidade.getFotoPerfil() == null) {
                comunidade.setFotoPerfil("images/DefaultFundoPerfil.png");
            }%>
            <img src="<%=comunidade.getFotoPerfil()%>" class="fotoPerfil"
                 alt="Foto de perfil da comunidade <%=comunidade.getNome()%>">
            <p class="nome-comunidade"><%=comunidade.getNome()%>
            </p>
            <div class="profile-description">
                <%if (comunidade.getDescricao() != null) {%>
                <p class="descricao"><%=comunidade.getDescricao()%>
                </p>
                <%}%>
            </div>
            <div class="divParticipantes">
                <p class="numParticipantes" id="numParticipantes"><%=comunidade.getIdParticipantes().size()%>
                </p>
                <p class="participantes">Participantes</p>
            </div>
            <%if (comunidade.getIdParticipantes().contains(usuario.getIdPessoa())) {%>
            <div class="criarPublicacao">
                <form action="novaPublicacao" method="post" id="formNovaPublicacao" enctype="multipart/form-data">
                    <div class="foto-perfil">
                        <%
                            if (usuario.getFotoPerfil() == null) {
                                usuario.setFotoPerfil("images/person_foto.svg");
                            }
                        %>
                        <img src="<%=usuario.getFotoPerfil()%>" alt="Foto de perfil">
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
            <%}%>
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
        <%-- Fundo escuro para mostrar participantes--%>
        <div class="fundo-escuro" id="fundo-escuro-participantes">
            <div class="pop-up-ver-participantes">
                <div class="cabecalho">
                    <span class="close" id="close-participantes"><img src="images/octicon_x-12.svg" alt=""></span>
                    <p>PARTICIPANTES</p>
                </div>
                <div class="lista-participantes">
                    <%
                        for (int idParticipante : comunidade.getIdParticipantes()) {
                            Membro participante = new Membro(idParticipante);
                            String ftPer = participante.getFotoPerfil();
                            if (ftPer == null) {
                                ftPer = "images/person_foto.svg";
                            }
                    %>
                    <div class="caixa-perfil" id="<%=participante.getNomeUsuario()%>">
                        <div class="foto-perfil">
                            <img src="<%=ftPer%>" alt="foto de perfil de <%=participante.getNome()%>">
                        </div>
                        <p class="nome"><%=participante.getNome()%>
                        </p>
                        <%
                            if (comunidade.getIdModeradores().contains(usuario.getIdPessoa()) && participante.getIdPessoa() != usuario.getIdPessoa()) {
                        %>
                        <button class="remover"
                                onclick="removerParticipante(<%=participante.getIdPessoa()%>, '<%=participante.getNomeUsuario()%>'); this.parentNode.style='none'">
                            REMOVER
                        </button>
                        <%}%>
                    </div>
                    <%}%>
                </div>
            </div>
        </div>
        <%if (comunidade.getIdModeradores().contains(usuario.getIdPessoa())) {%>
        <%-- Fundo escuro para editar a comunidade--%>
        <div class="fundo-escuro" id="fundo-escuro-editar-comunidade">
            <div class="pop-up-editar-perfil">
                <div class="cabecalho">
                    <span class="close" id="close-editar-comunidade"><img src="images/octicon_x-12.svg" alt=""></span>
                    <p>EDITANDO COMUNIDADE</p>
                    <button onclick="editarComunidade()" id="btnSave">SALVAR ALTERAÇÕES</button>
                </div>
                <div class="foto-fundo-comunidade">
                    <img id="foto-fundo-comunidade" src="<%=comunidade.getFotoFundo()%>"
                         alt="Foto de fundo de <%=comunidade.getNome()%>">
                    <div class="icone-editar-foto" id="icone-editar-foto-fundo-comunidade">
                        <img src="images/ri_edit-fill.svg" id="img-icone-editar-foto-fundo-usuario"
                             alt="Ícone de alterar foto de fundo">
                    </div>
                    <input type="file" id="editarFotoFundoComunidade" name="fotoFundoComunidade" accept="image/*">
                </div>
                <div class="foto-perfil-comunidade">
                    <img id="foto-perfil-comunidade" src="<%=comunidade.getFotoPerfil()%>"
                         alt="Foto do perfil de <%=comunidade.getNome()%>">
                    <div class="icone-editar-foto" id="icone-editar-foto-perfil-comunidade">
                        <img src="images/ri_edit-fill.svg" id="img-icone-editar-foto-perfil-comunidade"
                             alt="Ícone de alterar foto perfil">
                    </div>
                    <input type="file" id="editarFotoPerfilComunidade" name="fotoPerfilComunidade" accept="image/*">
                </div>
                <div class="inputs">
                    <label for="nome-comunidade" id="label-nome-comunidade">
                        <small>Nome</small>
                        <input type="text" id="nome-comunidade" name="nome-comunidade" value="<%=comunidade.getNome()%>"
                               required>
                    </label>
                    <label for="descricao-comunidade" id="label-descricao-comunidade">
                        <small>Descrição</small>
                        <textarea id="descricao-comunidade" name="descricao-comunidade" rows="1"
                                  maxlength="200"><%if (comunidade.getDescricao() != null) {%><%=comunidade.getDescricao()%><%}%></textarea>
                        <div id="contagem-caracteres-descricao-comunidade">200</div>
                    </label>
                </div>
            </div>
        </div>
        <%-- Fundo escuro para adicionar moderador--%>
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
        <%}%>
    </div>
</main>
</body>
</html>
<%
        }else{
            response.sendRedirect("verComunidades");
        }
    }
%>