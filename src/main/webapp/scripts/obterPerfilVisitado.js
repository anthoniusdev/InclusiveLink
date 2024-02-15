function obterPerfilVisitado(){
    return new Promise(function (resolve, reject){
        $.ajax({
            type: 'GET',
            url: 'obterPerfilVisitado',
            dataType: 'json',
            success: function (perfilVisitado){
                resolve(perfilVisitado)
            },
            error: function (error){
                console.log("ERRO EM OBTER O PERFIL VISITADO" + error)
                reject(error);
            }
        })
    })
}