function sairComunidade(i, ii) {
    $.ajax({
        url: 'sairComunidade',
        type: 'POST',
        data: { id: i },
        success: function (data) {
            if (data.status === 'success') {
                let caixa_usuario = document.getElementById('caixa-usuario' + ii);
                console.log(caixa_usuario);
                caixa_usuario.querySelector('div.imagem-foto-perfil-comunidade').style.display = 'none';
                caixa_usuario.querySelector('h3').innerText = 'Você saiu desta comunidade.';
                caixa_usuario.querySelector('button').style.display = 'none';
            } else {
                console.log('Erro:' + data.message);
            }
        }
    });
}