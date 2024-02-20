let novoModerador = false;
let p = new URLSearchParams(new URL(window.location).search);
let idComunidade = p.get("idComunidade");

let labelNomeComunidade = $('#label-nome-comunidade');
let inputNomeComunidade = $('#nome-comunidade');
function ativarOpcoes(event) {
    console.log("Função ativarOpcoes() chamada.");
    var minhaDiv = document.getElementById("opcoes");
    if (minhaDiv.classList.contains("d-none")) {

        minhaDiv.classList.remove("d-none");
        minhaDiv.classList.add("d-block");
    }
    event.stopPropagation();
}

function ocultarDiv(event) {
    var minhaDiv = document.getElementById("opcoes");
    var botao = document.getElementById("botaoAtivar");

    // Verifique se o clique não foi no botão ou dentro da div
    if (event.target !== botao && !minhaDiv.contains(event.target)) {
        // Oculte a div
        minhaDiv.classList.remove("d-block");
        minhaDiv.classList.add("d-none");
    }
}
$('#excluir-comunidade').on('click', function (){
    abrirPopUpA();
});
$('#answer-no').on('click', function (){
    fecharPopUpA();
});
$('#answer-yes').on('click', function (){
    $.ajax({
        url: 'excluirComunidade',
        type: 'POST',
        data: {idComunidade: idComunidade}
    });
    window.location.href = 'home';
})
function abrirPopUpA(){
    $('#body').css({
        overflow: 'hidden'
    });
    $('#fundo-escuro-answer').css({
        display: 'block',
        overflow: 'auto'
    });
}
function fecharPopUpA(){
    $('#fundo-escuro-answer').css({
        display: 'none',
        overflow: 'hidden'
    });
    $('#body').css({
        overflow: 'auto'
    });
}
$('#adicionar-moderador').on('click', function (){
    abrirPopUpAM();
})
$('#close-adicionar-moderador').on('click', function (){
    fecharPopUpAM();
})
function abrirPopUpAM(){
    $('#body').css({
        overflow: 'hidden'
    });
    $('#fundo-escuro-adicionar-moderador').css({
        display: 'block',
        overflow: 'auto'
    });
}
function fecharPopUpAM(){
    if (novoModerador === true){
        window.location.reload();
    }else {
        $('#fundo-escuro-adicionar-moderador').css({
            display: 'none',
            overflow: 'hidden'
        });
        $('#body').css({
            overflow: 'auto'
        });
    }
}
function adicionarModerador(i){
    let btn = $('#btn-am' + i);
    btn.text('MODERADOR');
    btn.css({
        border: '1px solid #fff'
    })
    $.ajax({
        url: 'adicionarModerador',
        type: 'POST',
        data: {idModerador: i, idComunidade:idComunidade}
    })
    novoModerador = true;
}
// Adicionar ouvinte de eventos ao botão para ativar as opções
document.getElementById("botaoAtivar").addEventListener("click", ativarOpcoes);

// Adicionar ouvinte de eventos ao documento para ocultar a div quando clicar fora dela
document.addEventListener("click", ocultarDiv);

function ativarEditarPerfil(event) {
    console.log("Função ativarEditarPerfil() chamada.");
    var divEdit = document.querySelector(".fundo-escuro.aparecerDivEditar");
    if (divEdit.classList.contains("d-none")) {

        divEdit.classList.remove("d-none");
        divEdit.classList.add("d-block");
    }

    event.stopPropagation();
}

let botaoEditarPerfil = $('#editar-comunidade');
let botaoFecharEditarPerfil = $('#close-comunidade');
botaoEditarPerfil.on('click', function () {
    $('#fundo-escuro-editar-comunidade').css({
        display: 'block',
        overflow: 'auto'
    });
    botaoEditarPerfil.css({
        display: 'none'
    });
    $('#body').css({
        overflow: 'hidden'
    });
});

botaoFecharEditarPerfil.on('click', function () {
    if (alteracaoSalva === true) {
        window.location.reload();
    } else if (alteracaoSalva === false) {
        if (ftpChange) {
            let fp = 'images/person_foto.svg';
            if (usuarioAutenticado.fotoPerfil !== undefined && usuarioAutenticado.fotoPerfil !== '') {
                fp = usuarioAutenticado.fotoPerfil;
            }
            $('#foto-perfil-comunidade').attr('src', fp);
        }
        if (ftfChange) {
            let ff = 'images/DefaultFundoPerfil.png';
            if (usuarioAutenticado.fotoFundo !== undefined && usuarioAutenticado.fotoFundo !== '') {
                ff = usuarioAutenticado.fotoFundo;
            }
            $('#foto-fundo-comunidade').attr('src', ff);
        }
        if (usuarioAutenticado.descricao !== null) {
            inputDescricao.text(usuarioAutenticado.descricao);
        } else {
            inputDescricao.text('');
        }
        inputNomeUsuario.val(usuarioAutenticado.nome);
    }
    $('#fundo-escuro-editar-comunidade').css({
        display: 'none',
        overflow: 'hidden'
    });
    $('#body').css({
        overflow: 'auto'
    });
    botaoEditarPerfil.css({
        display: 'block'
    });
});

function editarPerfil() {
    let formData = new FormData();
    formData.append('idComunidade', usuarioAutenticado.idComunidade);
    formData.append('nome', $('#nome-comunidade').val());
    formData.append('descricao', $('#descricao-comunidade').val());
    formData.append('fotoPerfil', ftPerComunidade);
    formData.append('fotoFundo', ftFunComunidade);
    $.ajax({
        url: 'editarPerfil',
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false,
        dataType: "json",
        success: function () {
            $('#btnSave-2').text('SALVO').css({
                border: "4px solid #427D9D",
                backgroundColor: "#164863",
                transition: '0.5s'
            });
            alteracaoSalva = true;
        },
        error: function (error) {
            console.log('ERRO: ' + error);
        }
    })
}
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
    console.log('Arquivo selecionado:', this.files[0]);
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
})
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

<<<<<<< HEAD
function abrirPopUpE(){
    $('#body').css({
        overflow: 'hidden'
    });
    $('#fundo-escuro-editar-comunidade').css({
        display: 'block',
        overflow: 'auto'
    });
}
function fecharPopUpE(){
    $('#fundo-escuro-editar-comunidade').css({
        display: 'none',
        overflow: 'hidden'
    });
    $('#body').css({
        overflow: 'auto'
    });
}

$('#editar-comunidade').on('click', function (){
    abrirPopUpE();
})
$('#close-editar-comunidade').on('click', function (){
    fecharPopUpE();
})

=======
function abrirSeletorArquivo() {
    const inputImagem = document.getElementById('input-imagem');
    inputImagem.click();
}
>>>>>>> 1193fc5dbb7ab1a7dfc7c7f5965f311eaa0147f7
