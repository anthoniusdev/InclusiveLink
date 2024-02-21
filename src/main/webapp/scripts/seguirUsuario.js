function seguirUsuario(idMembro, idSeguindo, i) {
    let reload = false;
    let botaoSeguir;
    if (i) {
        let s = '#botaoSeguir' + i;
        botaoSeguir = $(s);
        botaoSeguir.text('SEGUINDO');
        botaoSeguir.css({
            marginLeft: '53rem'
        })
    } else {
        botaoSeguir = $('#botao-seguir-perfil');
        reload = true;
    }
    botaoSeguir.css({
        outline: '4px solid #FFF',
        transition: '0.25s'
    });
    $.ajax({
        type: "POST",
        url: "seguirMembro",
        data: {
            idMembro: idMembro,
            idSeguindo: idSeguindo
        },
        error: function (error) {
            console.log("Erro ao seguir usuário: " + error.responseText);
        }
    })
    if (reload) {
        window.location.reload();
    }
}
