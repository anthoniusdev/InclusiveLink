let carregando = false, usuarioAutenticado, alteracaoSalva = false, ftFunURL, ftPerURL, ftPerCom, ftFunCom;
let p = new URLSearchParams(new URL(window.location).search), alteracaoParticipante = false;
let idComunidade = p.get("idComunidade");

obterUsuarioAutenticado().then(function (usuario) {
    usuarioAutenticado = usuario;
}).catch(function (error) {
    console.log(error);
});
document.addEventListener('DOMContentLoaded', function () {
    let ftpChange = false, ftfChange = false;
    let botaoEditarComunidade = $('#editar-perfil');
    let botaoFecharEditarComunidade = $('#close-editar-comunidade');
    let labelNomeComunidade = $('#label-nome-comunidade');
    let inputNomeComunidade = $('#nome-comunidade');
    let inputDescricao = $('#descricao-comunidade');
    let elementoContagemDescricaoComunidade = $('#contagem-caracteres-descricao-comunidade');
    let labelDescricao = $('#label-descricao-comunidade');
    let iconeEditarFtFundo = $('#icone-editar-foto-fundo-comunidade');
    let iconeEditarFtPerfil = $('#icone-editar-foto-perfil-comunidade');
    let formNovaPublicacao = document.getElementById("formNovaPublicacao");
    let textoPublicacao = document.getElementById("textoNovaPublicacao");
    let elementoContagemPublicacao = document.getElementById("contagemCaracteresPublicacao");
    let botaoPostar = document.getElementById("btnPostar");
    const iconeSVGinputIMG = document.getElementById("icone-escolher-imagem");
    const inputImagem = document.getElementById('input-imagem');
    const imagemPreview = document.getElementById('imagem-preview');
    let file;
    let xFt = document.getElementById('remover-foto');
    let imageURL;
    textoPublicacao.addEventListener('input', function () {
        if (this.value.length > 200) {
            this.value = this.value.slice(0, 200);
        }
        elementoContagemPublicacao.textContent = (200 - this.value.length).toString();
        if (this.value.length > 0) {
            elementoContagemPublicacao.style.display = "flex";
            botaoPostar.style.backgroundColor = "#164863";
            botaoPostar.style.cursor = "pointer";
        } else {
            elementoContagemPublicacao.style.display = "none";
            botaoPostar.style.backgroundColor = "#0c202a1f";
            botaoPostar.style.cursor = "default";
        }
    })
    textoPublicacao.addEventListener('input', function () {
        this.style.height = 'auto';
        this.style.height = Math.min(this.scrollHeight, 300) + 'px';
    });
    textoPublicacao.addEventListener('focus', function () {
        let linha = document.getElementById('linhaAreaInput');
        linha.style.height = '4px';
    });
    textoPublicacao.addEventListener('focusout', function () {
        let linha = document.getElementById('linhaAreaInput');
        linha.style.height = '2px';
    })
    formNovaPublicacao.addEventListener("submit", function (event) {
        event.preventDefault();
        if (textoPublicacao.value.length > 0 || imageURL != null) {
            submeterFormulario();
        } else {
            textoPublicacao.focus();
        }
    });
    iconeSVGinputIMG.addEventListener('click', function () {
        abrirSeletorArquivo();
    });
    inputImagem.addEventListener('input', function () {
        file = this.files[0];
        if (file) {
            imageURL = URL.createObjectURL(file);
            imagemPreview.src = imageURL;
            xFt.style.display = 'block';
            botaoPostar.style.backgroundColor = "#164863";
            botaoPostar.style.cursor = "pointer";
        }
    });
    xFt.addEventListener('click', function () {
        file = null;
        imageURL = null;
        imagemPreview.src = null;
        xFt.style.display = 'none';
        if (textoPublicacao.value.length === 0) {
            botaoPostar.style.backgroundColor = "#0c202a1f";
            botaoPostar.style.cursor = "default";
        }
    });
    function submeterFormulario() {
        let formData = new FormData(formNovaPublicacao);
        formData.append("idComunidade", idComunidade);
        textoPublicacao.value = "";
        imagemPreview.src = "";
        elementoContagemPublicacao.textContent = (200).toString();
        elementoContagemPublicacao.style.display = "none";
        botaoPostar.style.backgroundColor = "#0c202a1f"
        botaoPostar.style.cursor = "default";
        xFt.style.display = 'none';
        fetch("novaPublicacaoComunidade", {
            method: "POST",
            body: formData
        }).then(response => {
            if (!response.ok) {
                throw new Error("Erro na requisicição: " + response.status);
            }
            return response.text();
        }).then(data => {
            console.log(data);
            location.reload();
        }).catch(error => {
            console.error("Erro na requisição:", error)
        }).finally(() => {
            textoPublicacao.value = "";
            imageURL = null;
            imagemPreview.src = '';
        })
    }
    function abrirSeletorArquivo() {
        const inputImagem = document.getElementById('input-imagem');
        inputImagem.click();
    }
    carregarPublicacoes("comunidade");
    $(window).scroll(function () {
        if (!carregando && $(window).scrollTop() + $(window).height() >= $(document).height() - 100) {
            carregando = true;
            carregarPublicacoes("comunidade");
        }
    });
    botaoEditarComunidade.on('click', function () {
        $('#fundo-escuro-editar-comunidade').css({
            display: 'block',
            overflow: 'auto'
        });
        botaoEditarComunidade.css({
            display: 'none'
        });
        $('#body').css({
            overflow: 'hidden'
        });
    });
    botaoFecharEditarComunidade.on('click', function () {
        window.location.reload();
        $('#fundo-escuro-editar-perfil').css({
            display: 'none',
            overflow: 'hidden'
        });
        $('#body').css({
            overflow: 'auto'
        });
        botaoEditarComunidade.css({
            display: 'block'
        });
    });
    labelNomeComunidade.on('click', function () {
        inputNomeComunidade.focus();
    });
    inputDescricao.on('input', function () {
        elementoContagemDescricaoComunidade.text((200 - inputDescricao.val().length).toString());
        if (inputDescricao.val().length > 200) {
            inputDescricao.val(inputDescricao.val().slice(0, 200));
        }
        if (inputDescricao.val().length > 0) {
            elementoContagemDescricaoComunidade.css({
                display: 'flex',
                paddingRight: '20px'
            })
        } else {
            elementoContagemDescricaoComunidade.css({
                display: 'none'
            })
        }
        inputDescricao.css({
            height: 'auto'
        });
        inputDescricao.css({
            height: Math.min(this.scrollHeight, 300) + 'px'
        });
    });
    labelDescricao.on('click', function () {
        inputDescricao.focus();
    });
    let editarFtFun = $('#editarFotoFundoComunidade');
    let editarFtPer = $('#editarFotoPerfilComunidade');
    iconeEditarFtFundo.on('click', function () {
        editarFtFun.click();
    });
    iconeEditarFtPerfil.on('click', function () {
        editarFtPer.click();
    });
    editarFtFun.on('change', function () {
        const file = this.files[0];
        if (file) {
            ftFunURL = URL.createObjectURL(file);
            $('#foto-fundo-comunidade').attr('src', ftFunURL);
            ftFunCom = file;
            ftfChange = true;
        }
    })
    editarFtPer.on('change', function () {
        const file = this.files[0];
        if (file) {
            ftPerURL = URL.createObjectURL(file);
            $('#foto-perfil-comunidade').attr('src', ftPerURL);
            ftPerCom = file;
            ftpChange = true;
        }
    });
    // --------------------- POP-UP PARTICIPANTES ----------------------------
    let fundoEscuroParticipantes = $('#fundo-escuro-participantes');
    // abrir pop-up
    $('#numParticipantes').on('click', function (){
        fundoEscuroParticipantes.css({
            display: 'block'
        });
        $('#body').css({
            overflow: 'hidden'
        })
    })
    // fechar pop-up
    $('#close-participantes').on('click', function (){
        if (alteracaoParticipante === true){
            window.location.reload();
        }else{
            fundoEscuroParticipantes.css({
                display: 'none'
            });
            $('#body').css({
                overflow: 'auto'
            })
        }
    })
});

function editarComunidade() {
    let formData = new FormData();
    let parametrosURL = new URL(window.location.href);
    let idComunidade = parametrosURL.searchParams.get("idComunidade");
    formData.append('idComunidade', idComunidade);
    formData.append('nome', $('#nome-comunidade').val());
    formData.append('descricao', $('#descricao-comunidade').val());
    formData.append('fotoPerfil', ftPerCom);
    formData.append('fotoFundo', ftFunCom);
    $.ajax({
        url: 'editarComunidade?idComunidade=' + idComunidade,
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false,
        dataType: "json",
    });
    $('#btnSave').text('SALVO').css({
        border: "4px solid #427D9D",
        backgroundColor: "#164863",
        transition: '0.5s'
    });
    alteracaoSalva = true;
}
function removerParticipante(i, ii){
    let classeParticipante = '#' + ii;
    classeParticipante = $(classeParticipante);
    classeParticipante.css({
        display: 'none'
    })
    $.ajax({
        type: 'POST',
        url: 'excluirParticipante',
        data: {idParticipante: i, idComunidade: idComunidade}
    });
    alteracaoParticipante = true;
}