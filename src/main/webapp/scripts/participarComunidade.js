function participarComunidade(i, ii){
    if (ii) {
        let btn = '#btnParticipar' + ii;
        btn = $(btn);
        btn.css({
            width: '180px',
            backgroundColor: '#00ff79',
            marginLeft: '35px',
            color: '#fff',
            cursor: 'default'
        });
        btn.text('PARTICIPANDO');
    }
    $.ajax({
        type: 'POST',
        url: 'participarComunidade',
        data: {idComunidade: i}
    })
}