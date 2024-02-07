function curtirPublicacao(idPublicacao) {
    $.ajax({
        type: 'POST',
        url: 'curtirPublicacao',
        data: {idPublicacao: idPublicacao},
        dataType: "json",
        success: function () {
            console.log('publicacao curtida')
        },
        error: function (error) {
            console.log(error)
        }
    })
}