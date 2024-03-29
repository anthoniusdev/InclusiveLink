document.addEventListener('DOMContentLoaded', function () {
    const inputPesquisarPerfil = $('#pesquisarPerfil');
    const inputPesquisarComunidade = $('#pesqComunidade');
    let listaPerfisPesquisa = $('#listaPesquisaPerfil');
    inputPesquisarPerfil.on('input change', function () {
        let valor = inputPesquisarPerfil.val();
        if (valor.length > 0) {
            mostrarCaixaResultados(true);
            if (valor.length > 1) {
                pesquisarPerfil(valor);
            }
        } else {
            mostrarCaixaResultados(false);
        }
    });
    function mostrarCaixaResultados(bool) {
        let perfisSugeridos = $('#perfisSugeridos');

        if (bool) {
            inputPesquisarComunidade.css({
                marginTop: "290px"
            });
            inputPesquisarPerfil.css({
                borderRadius: "0px",
                borderTopLeftRadius: "20px",
                borderTopRightRadius: "20px"
            });
            listaPerfisPesquisa.css({
                display: "flex",
                flexDirection: "column"
            });
            perfisSugeridos.css({
                display: 'none'
            });
        } else {
            inputPesquisarComunidade.css({
                marginTop: "20px"
            });
            inputPesquisarPerfil.css({
                borderRadius: '20px'
            });
            listaPerfisPesquisa.css({
                display: 'none'
            });
            perfisSugeridos.css({
                display: 'block'
            });
        }
    }
    function pesquisarPerfil(query) {
        $.ajax({
            type: "GET",
            url: "pesquisarPerfil",
            data: {query: query},
            dataType: "json",
            success: function (results) {
                console.log(results);
                mostrarResultadoPesquisa(results);
            },
            error: function (status, error) {
                console.error('ERRO NA REQUISIÇÃO:', status, error)
            }
        })
    }
    function mostrarResultadoPesquisa(results) {
        listaPerfisPesquisa.empty();
        results.forEach(function (membroEncontrado) {
            const caixaUsuario = $("<div>", {
                class: "resultado-pesquisa-perfil"
            });
            caixaUsuario.on('click', function () {
                $(this).css({
                    backgroundColor: "rgb(7,25,37)"
                });
                window.location.href = "perfil?nome_usuario=" + membroEncontrado.nomeUsuario;
            })
            if (membroEncontrado.fotoPerfil == null) {
                membroEncontrado.fotoPerfil = "images/person_foto.svg";
            }
            const imgPerfil = $("<img>", {
                src: membroEncontrado.fotoPerfil,
                alt: "Foto de perfil de " + membroEncontrado.nome
            });
            const nome = $("<p>", {
                class: "nomeUsuario",
                text: membroEncontrado.nome
            });
            caixaUsuario.append(imgPerfil, nome);
            listaPerfisPesquisa.append(caixaUsuario);
            console.log(membroEncontrado.nome)
        })
    }
    listaPerfisPesquisa.on('mouseenter', '.resultado-pesquisa-perfil', function () {
        $(this).css({
            backgroundColor: "rgba(51,71,87,0.42)",
            cursor: 'pointer',
            transition: '0.5s'
        })
    });
    listaPerfisPesquisa.on('mouseleave', '.resultado-pesquisa-perfil', function () {
        $(this).css({
            backgroundColor: "var(--COR-03)",
            cursor: 'default',
        })
    });

})