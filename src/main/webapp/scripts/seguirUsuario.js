function seguirUsuario(idMembro, idSeguindo, i) {
    let reload = false;
    let botaoSeguir;
    if (i) {
        let s = '#botaoSeguir' + i;
        botaoSeguir = $(s);
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
        success: function () {
            console.log("Usuário seguido com sucesso");
        },
        error: function (error) {
            console.log("Erro ao seguir usuário: " + error.responseText);
        }
    })
    if (reload) {
        window.location.reload();
    }
}
