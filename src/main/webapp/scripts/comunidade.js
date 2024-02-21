let carregando = false, usuarioAutenticado, alteracaoSalva = false, ftFunURL, ftPerURL, ftPerCom, ftFunCom;
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
        // if (alteracaoSalva === true) {
        //     window.location.reload();
        // } else if (alteracaoSalva === false) {
        //     if (ftpChange) {
        //         let fp = 'images/person_foto.svg';
        //         if (usuarioAutenticado.fotoPerfil !== undefined && usuarioAutenticado.fotoPerfil !== '') {
        //             fp = usuarioAutenticado.fotoPerfil;
        //         }
        //         $('#foto-perfil-usuario').attr('src', fp);
        //     }
        //     if (ftfChange) {
        //         let ff = 'images/DefaultFundoPerfil.png';
        //         if (usuarioAutenticado.fotoFundo !== undefined && usuarioAutenticado.fotoFundo !== '') {
        //             ff = usuarioAutenticado.fotoFundo;
        //         }
        //         $('#foto-fundo-usuario').attr('src', ff);
        //     }
        //     if (usuarioAutenticado.descricao !== null) {
        //         inputDescricao.text(usuarioAutenticado.descricao);
        //     } else {
        //         inputDescricao.text('');
        //     }
        //     inputNomeUsuario.val(usuarioAutenticado.nome);
        // }
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
        console.log('Arquivo selecionado:', this.files[0]);
    })
    editarFtPer.on('change', function () {
        const file = this.files[0];
        if (file) {
            ftPerURL = URL.createObjectURL(file);
            $('#foto-perfil-comunidade').attr('src', ftPerURL);
            ftPerCom = file;
            ftpChange = true;
        }
        console.log('Arquivo selecionado:', this.files[0]);
    });
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
