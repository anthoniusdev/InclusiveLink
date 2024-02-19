function excluirPublicacao(idPublicacao, confirmacao) {
    $.ajax({
        type: 'POST',
        url: 'excluirPublicacao',
        data: {idPublicacao: idPublicacao},
        dataType: "json",
        success: function () {
            console.log('publicacao excluida');
        },
        error: function (erro) {
            console.log(erro);
        }
    })
}