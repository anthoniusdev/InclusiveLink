function obterUsuarioAutenticado() {
    return new Promise(function (resolve, reject) {
        $.ajax({
            type: 'GET',
            url: 'obterUsuarioAutenticado',
            dataType: 'json',
            success: function (usuario) {
                resolve(usuario);
            },
            error: function (status, error) {
                console.error('Erro na requisição: ', status, error);
                reject('Erro na requisição');
            }
        })
    })
}