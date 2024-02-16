let usuarioAutenticado, alteracaoSalva = false, ftFunURL, ftPerURL, carregando = false;
let ftPerUsuario, ftFunUsuario;
obterUsuarioAutenticado().then(function (usuario) {
    usuarioAutenticado = usuario;
}).catch(function (error) {
    console.log(error);
});
document.addEventListener("DOMContentLoaded", function () {
    let ftpChange = false, ftfChange = false;
    let botaoEditarPerfil = $('#editar-perfil');
    let botaoFecharEditarPerfil = $('#close-editar-perfil');
    let labelNomeUsuario = $('#label-nome-usuario');
    let inputNomeUsuario = $('#nome-usuario');
    let labelDescricao = $('#label-descricao-usuario');
    let elementoContagemDescricao = $('#contagem-caracteres-descricao-usuario');
    let inputDescricao = $('#descricao-usuario');
    let iconeEditarFtFundo = $('#icone-editar-foto-fundo-usuario');
    let iconeEditarFtPerfil = $('#icone-editar-foto-perfil-usuario');
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

    })
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
    labelDescricao.on('click', function () {
        inputDescricao.focus();
    });
    let editarFtFun = $('#editarFotoFundoUsuario');
    let editarFtPer = $('#editarFotoPerfilUsuario');
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
            console.log(ftFunURL)
            $('#foto-fundo-usuario').attr('src', ftFunURL);
            ftFunUsuario = file;
            ftfChange = true;
        }
        console.log('Arquivo selecionado:', this.files[0]);
    })
    editarFtPer.on('change', function () {
        const file = this.files[0];
        if (file) {
            ftPerURL = URL.createObjectURL(file);
            console.log(ftPerURL);
            $('#foto-perfil-usuario').attr('src', ftPerURL);
            ftPerUsuario = file;
            ftpChange = true;
        }
        console.log('Arquivo selecionado:', this.files[0]);
    })
})

function editarPerfil() {
    let formData = new FormData();
    formData.append('idUsuario', usuarioAutenticado);
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