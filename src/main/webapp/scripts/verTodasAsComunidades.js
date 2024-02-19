function participarComunidade(idComunidade, idClasse){
    let classe = '#btnParticipar' + idClasse;
    classe = $(classe);
    classe.css({
        border: "1px solid #00ff79",
        width: '180px',
        backgroundColor: 'var(--COR-03)',
        marginLeft: '35px',
        color: '#00ff79'
    });
    classe.text('PARTICIPANDO');
}