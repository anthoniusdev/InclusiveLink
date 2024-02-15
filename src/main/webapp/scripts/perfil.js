let usuarioAutenticado;
obterUsuarioAutenticado().then(function (usuario) {
    usuarioAutenticado = usuario;
}).catch(function (error) {
    console.log(error);
});
let carregando = false;
document.addEventListener("DOMContentLoaded", function () {
    let botaoEditarPerfil = $('#editar-perfil');
    let botaoFecharEditarPerfil = $('#close-editar-perfil');
    let labelNomeUsuario = $('#label-nome-usuario');
    let inputNomeUsuario = $('#nome-usuario');
    let labelDescricao = $('#label-descricao-usuario');
    let elementoContagemDescricao = $('#contagem-caracteres-descricao-usuario');
    let inputDescricao = $('#descricao-usuario');
    let fundoPreto = $('#fundo-escuro-editar-perfil');
    botaoEditarPerfil.on('click', function () {
        $('#fundo-escuro-editar-perfil').css({
            display: 'block'
        })
        botaoEditarPerfil.css({
            display: 'none'
        })
    })
    botaoFecharEditarPerfil.on('click', function () {
        $('#fundo-escuro-editar-perfil').css({
            display: 'none'
        });
        botaoEditarPerfil.css({
            display: 'block'
        });
    });
    carregarPublicacoes("perfil");
    $(window).scroll(function () {
        if (!carregando && $(window).scrollTop() + $(window).height() >= $(document).height() - 100) {
            carregando = true;
            carregarPublicacoes("perfil");
        }
    });
    labelNomeUsuario.on('click', function () {
        inputNomeUsuario.focus();
    });
    inputDescricao.on('input', function () {
        elementoContagemDescricao.text((200 - inputDescricao.val().length).toString());
        if (inputDescricao.val().length > 200) {
            inputDescricao.val(inputDescricao.val().slice(0, 200));
        }
        if (inputDescricao.val().length > 0) {
            elementoContagemDescricao.css({
                display: 'flex',
                paddingRight: '20px'
            })
        } else {
            elementoContagemDescricao.css({
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
    labelDescricao.on('click', function (){
        inputDescricao.focus();
    })
})
function editarPerfil(){
    $.ajax({
        url: 'editarPerfil',
        type: 'POST',
        data: {
            idUsuario: usuarioAutenticado,
            nome: $('#nome-usuario').val(),
            descricao: $('#descricao-usuario').val()
        },
        success: function (){
            $('#btnSave').text('SALVO');
        }
    })
}