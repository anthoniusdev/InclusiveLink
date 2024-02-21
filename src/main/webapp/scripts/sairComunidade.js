function sairComunidade(i, ii, tipo, iii) {
    if (tipo === 'botao'){
        let btn = '#btnSair' + iii;
        btn = $(btn);
        btn.css({
            backgroundColor: 'ffa15e',
            cursor: 'default',
            color: '#fff'
        });
        btn.text('SAIU');
    }else {
        if (ii) {
            let caixa_comunidade = document.getElementById('caixa-comunidade' + ii);
            caixa_comunidade.style.display = 'none';
        }
    }
    $.ajax({
        url: 'sairComunidade',
        type: 'POST',
        data: {idComunidade: i}
    });
}