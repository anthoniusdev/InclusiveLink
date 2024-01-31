function sairComunidade(i, ii) {
    let caixa_comunidade = document.getElementById('caixa-comunidade' + ii);
    $.ajax({
        url: 'sairComunidade',
        type: 'POST',
        data: {id: i},
        success: function (data) {
            if (data.status === 'success') {
                console.log(caixa_comunidade);
                caixa_comunidade.style.display = 'none';
            } else {
                console.log('Erro:' + data.message);
            }
        }
    });
}