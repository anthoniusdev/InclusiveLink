document.addEventListener('DOMContentLoaded', function () {
    const inputPesquisarComunidade = $('#pesquisarComunidade');
    const listaPesquisaComunidade = $('#lista-pesquisa-comunidade');
    inputPesquisarComunidade.on('input change', function () {
        let valor = inputPesquisarComunidade.val();
        if (valor.length > 0) {
            mostrarCaixaResultados(true)
            if (valor.length > 1) {
                pesquisarComunidade(valor);
            }
        } else {
            mostrarCaixaResultados(false)
        }
    });

    function mostrarCaixaResultados(bool) {
        let acoesComunidade = $('#comunidades-sugeridas');
        if (bool) {
            inputPesquisarComunidade.css({
                borderRadius: "0px",
                borderTopLeftRadius: "20px",
                borderTopRightRadius: "20px"
            });
            listaPesquisaComunidade.css({
                display: "flex",
                flexDirection: "column"
            });
            acoesComunidade.css({
                display: 'none'
            })
        } else {
            inputPesquisarComunidade.css({
                borderRadius: '20px'
            });
            listaPesquisaComunidade.css({
                display: 'none'
            });
            acoesComunidade.css({
                display: 'flex'
            });

        }
    }
    function pesquisarComunidade(query){
        $.ajax({
            type: 'GET',
            url: 'pesquisarComunidade',
            data: {query: query},
            dataType: 'json',
            success: function (results){
                mostrarResultadosPesquisa(results);
            }, error: function (erro){
                console.log(erro);
            }
        })
    }
    function mostrarResultadosPesquisa(results){
        listaPesquisaComunidade.empty();
        results.forEach(function (comunidade){
            let caixa_comunidade = $('<div>',{
                class: 'resultados-pesquisa-comunidade'
            });
            if (comunidade.fotoPerfil === null){
                comunidade.fotoPerfil = 'images/img-foto-perfil.png';
            }
            let imgPerfil = $('<img>', {
                src: comunidade.fotoPerfil,
                alt: 'Foto de perfil de ' + comunidade.nome
            })
            let nome = $('<div>', {
                class: 'nome-comunidade',
                text: comunidade.nome
            })
            caixa_comunidade.append(imgPerfil, nome);
            listaPesquisaComunidade.append(caixa_comunidade);
        })
    }

})