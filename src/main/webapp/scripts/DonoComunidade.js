let novoModerador = false;
let p = new URLSearchParams(new URL(window.location).search);
let idComunidade = p.get("idComunidade");
function ativarOpcoes(event) {
    console.log("Função ativarOpcoes() chamada.");
    var minhaDiv = document.getElementById("opcoes");
    if (minhaDiv.classList.contains("d-none")) {

        minhaDiv.classList.remove("d-none");
        minhaDiv.classList.add("d-block");
    }
    event.stopPropagation();
}
function ativarEditarPerfil() {
    console.log("Função ativarEditarPerfil() chamada.");
    var minhaDiv = document.getElementsByName("EditandoPerfil");
    if (minhaDiv.classList.contains("d-none")) {

        minhaDiv.classList.remove("d-none");
        minhaDiv.classList.add("d-block");
    }
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

document.getElementById("editarPerfil").addEventListener("click", ativarEditarPerfil);
