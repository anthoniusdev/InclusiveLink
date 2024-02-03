let ftPer, ftFun;
document.addEventListener("DOMContentLoaded", function () {
    let botaoCriarNovaComunidade = $('#botaoCriarNovaComunidade');
    let botaoCriarComunidade = $('#botaoCriarComunidade');
    let ftPerURL, ftFunURL;
    let labelNomeComunidade = $('#labelNomeComunidade');
    let inputDescricaoComunidade = $('#descricaoComunidade');
    let elementoContagemDescricao = $('#contagem-caracteres-comunidade');
    let iconeEditarFotoPerfil = $('#icone-editar-foto-perfil');
    let iconeEditarFotoFundo = $('#icone-editar-foto-fundo')
    let fotoPerfilComunidade = $('#img-foto-perfil-comunidade');
    let fotoFundoComunidade = $('#img-foto-fundo');
    let criarComunidade = $('#criar-comunidade');
    botaoCriarNovaComunidade.on('click', function () {
        abrirPopUpCriarComunidade();
    })
    botaoCriarComunidade.on('mouseenter', function () {
        this.style.cursor = 'pointer';
    })
    botaoCriarComunidade.on('mouseleave', function () {
        this.style.cursor = 'default';
    })
    criarComunidade.on('mouseenter', function () {
        $(this).css({
            cursor: 'pointer',
            backgroundColor: '#1F465EFF',
            transition: '0.5s'
        })
        botaoCriarNovaComunidade.css({
            backgroundColor: '#1F465EFF',
            transition: '0.5s'
        })
    })
    criarComunidade.on('mouseleave', function () {
        botaoCriarNovaComunidade.css({
            backgroundColor: 'var(--COR-03)',
            transition: '0.5s'
        })
        $(this).css({
            cursor: 'default',
            backgroundColor: 'var(--COR-03)',
            transition: '0.5s'
        })
    })
    labelNomeComunidade.on('mouseenter', function () {
        this.style.cursor = 'text';
    })
    labelNomeComunidade.on('click', function () {
        this.style.cursor = 'default';
    })
    inputDescricaoComunidade.on('input', function () {
        elementoContagemDescricao.text((200 - this.value.length).toString());
        if (this.value.length > 200) {
            this.value = this.value.slice(0, 200);
        }
        if (this.value.length > 0) {
            elementoContagemDescricao.css({
                display: 'flex',
                paddingRight: '20px'
            })
        } else {
            elementoContagemDescricao.css({
                display: 'none'
            })
        }
        this.style.height = 'auto';
        this.style.height = Math.min(this.scrollHeight, 300) + 'px';
    })
    $('#close').on('click', function () {
        fecharPopUpCriarComunidade();
    })
    iconeEditarFotoFundo.on('click', function () {
        console.log('Clicou em iconeEditarFotoFundo');
        $('#editarFotoFundo').click();
    })
    iconeEditarFotoPerfil.on('click', function () {
        console.log('Clicou em iconeEditarFotoPerfil');
        $('#editarFotoPerfil').click();
    })
    $('#editarFotoPerfil').on('change', function () {
        const file = this.files[0];
        if (file) {
            ftPerURL = URL.createObjectURL(file);
            console.log(ftPerURL)
            fotoPerfilComunidade.attr('src', ftPerURL);
            ftPer = file;
        }
        console.log('Arquivo selecionado:', this.files[0]);
    });
    $('#editarFotoFundo').on('change', function () {
        const file = this.files[0];
        if (file) {
            ftFunURL = URL.createObjectURL(file);
            console.log(ftFunURL)
            fotoFundoComunidade.attr('src', ftFunURL);
            ftFun = file;
        }
        console.log('Arquivo selecionado:', this.files[0]);
    })

    function abrirPopUpCriarComunidade() {
        $('#popup-nova-comunidade').css({
            display: 'block'
        })
        $('#fundo-escuro').css({
            display: 'block'
        })
        if (fotoPerfilComunidade.src == null) {
            fotoPerfilComunidade.attr('src', "images/img-foto-perfil.png");
        }
        if (fotoFundoComunidade.src == null) {
            fotoFundoComunidade.attr('src', "images/img-foto-fundo.png");
        }
    }

    function fecharPopUpCriarComunidade() {
        $('#nomeComunidade').val('');
        $('#descricaoComunidade').val('');
        ftPer = null;
        ftFun = null;
        ftPerURL = null;
        ftFunURL = null;
        let botao = $('#botaoCriarComunidade');
        botao.css({
            border: "none",
            backgroundColor: "var(--COR-03)",
            transition: '0.5s'
        });
        botao.text('CRIAR');

        $('#popup-nova-comunidade').css({
            display: 'none',
            transition: '0.2s'
        })
        $('#fundo-escuro').css({
            display: 'none'
        })

    }
})

function criarComunidade(idAutor) {
    let formData = new FormData();
    formData.append('nomeComunidade', $('#nomeComunidade').val());
    formData.append('descricaoComunidade', $('#descricaoComunidade').val());
    formData.append('idAutor', idAutor);
    formData.append('fotoPerfil', ftPer);
    formData.append('fotoFundo', ftFun);
    $.ajax({
        url: "criarComunidade",
        type: "POST",
        data: formData,
        processData: false,
        contentType: false,
        dataType: "json",
        success: function (response) {
            let botao = $('#botaoCriarComunidade');
            botao.css({
                border: "4px solid #427D9D",
                backgroundColor: "#164863",
                transition: '0.5s'
            })
            botao.text("CRIADO");
        },
        error: function (error) {
            console.log("ERRO NA CRIAÇÃO DA COMUNIDADE: " + (error.responseText || "Erro desconhecido."));
        }
    });
}