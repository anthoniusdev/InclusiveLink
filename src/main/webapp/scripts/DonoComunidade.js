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

let botaoEditarPerfil = $('#editar-perfil');
let botaoFecharEditarPerfil = $('#close-editar-perfil');
botaoEditarPerfil.on('click', function () {
    $('#fundo-escuro-editar-perfil').css({
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
            $('#foto-perfil-usuario').attr('src', fp);
        }
        if (ftfChange) {
            let ff = 'images/DefaultFundoPerfil.png';
            if (usuarioAutenticado.fotoFundo !== undefined && usuarioAutenticado.fotoFundo !== '') {
                ff = usuarioAutenticado.fotoFundo;
            }
            $('#foto-fundo-usuario').attr('src', ff);
        }
        if (usuarioAutenticado.descricao !== null) {
            inputDescricao.text(usuarioAutenticado.descricao);
        } else {
            inputDescricao.text('');
        }
        inputNomeUsuario.val(usuarioAutenticado.nome);
    }
    $('#fundo-escuro-editar-perfil').css({
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
    formData.append('idUsuario', usuarioAutenticado.idComunidade);
    formData.append('nome', $('#nome-usuario').val());
    formData.append('descricao', $('#descricao-usuario').val());
    formData.append('fotoPerfil', ftPerUsuario);
    formData.append('fotoFundo', ftFunUsuario);
    $.ajax({
        url: 'editarPerfil',
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false,
        dataType: "json",
        success: function () {
            $('#btnSave').text('SALVO').css({
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

