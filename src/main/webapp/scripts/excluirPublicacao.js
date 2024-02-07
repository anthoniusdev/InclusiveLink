function excluirPublicacao(idPublicacao, post, confirmacao) {
    $.ajax({
        type: 'POST',
        url: 'excluirPublicacao',
        data: {idPublicacao: idPublicacao},
        dataType: "json",
        success: function () {
            post.css({
                display: 'none'
            });
            confirmacao.css({
                display: 'block'
            })
            setTimeout(function () {
                confirmacao.css({
                    display: 'none'
                });
            }, 3000)
            console.log('publicacao excluida');
        }, error: function (erro) {
            console.log(erro);
        }
    })
    if (!post){
        window.location.href='PaginaInicial.jsp';
    }
}