let ftPer, ftFun;
document.addEventListener("DOMContentLoaded", function () {
    let formNovaPublicacao = document.getElementById("formNovaPublicacao");
    let textoPublicacao = document.getElementById("textoNovaPublicacao");
    let elementoContagemPublicacao = document.getElementById("contagemCaracteresPublicacao");
    let botaoPostar = document.getElementById("btnPostar");
    const iconeSVGinputIMG = document.getElementById("icone-escolher-imagem");
    const inputImagem = document.getElementById('input-imagem');
    const imagemPreview = document.getElementById('imagem-preview');
    const inputPesquisarPerfil = document.getElementById('pesquisarPerfil');
    const perfisSugeridos = document.getElementById('perfisSugeridos');
    let listaPerfisPesquisa = $('#listaPesquisaPerfil');
    let listaComunidadesSugeridas = $('#lista-comunidade-sugerida');
    let imageURL, ftPerURL, ftFunURL;
    let botaoCriarNovaComunidade = $('#botaoCriarNovaComunidade');
    let botaoCriarComunidade = $('#botaoCriarComunidade');
    let labelNomeComunidade = $('#labelNomeComunidade');
    let inputDescricaoComunidade = $('#descricaoComunidade');
    let elementoContagemDescricao = $('#contagem-caracteres-comunidade');
    let iconeEditarFotoPerfil = $('#icone-editar-foto-perfil');
    let iconeEditarFotoFundo = $('#icone-editar-foto-fundo')
    let fotoPerfilComunidade = $('#img-foto-perfil-comunidade');
    let fotoFundoComunidade = $('#img-foto-fundo');
    obterComunidades();
    botaoCriarNovaComunidade.on('click', function (){
        abrirPopUpCriarComunidade();
    })
    textoPublicacao.addEventListener('input', function () {
        if (this.value.length > 200) {
            this.value = this.value.slice(0, 200);
        }
        elementoContagemPublicacao.textContent = (200 - this.value.length).toString();
        if (this.value.length > 0) {
            elementoContagemPublicacao.style.display = "flex";
            botaoPostar.style.backgroundColor = "#164863";
            botaoPostar.style.cursor = "pointer";
        } else {
            elementoContagemPublicacao.style.display = "none";
            botaoPostar.style.backgroundColor = "#0c202a1f"
            botaoPostar.style.cursor = "no-drop";
        }
    })
    textoPublicacao.addEventListener('input', function () {
        this.style.height = 'auto';
        this.style.height = Math.min(this.scrollHeight, 300) + 'px';
    });
    formNovaPublicacao.addEventListener("submit", function (event) {
        event.preventDefault();
        submeterFormulario();
    });
    iconeSVGinputIMG.addEventListener('click', function () {
        abrirSeletorArquivo();
    });
    inputImagem.addEventListener('change', function () {
        const file = this.files[0];
        if (file) {
            imageURL = URL.createObjectURL(file);
            imagemPreview.src = imageURL;
            botaoPostar.style.backgroundColor = "#164863";
            botaoPostar.style.cursor = "pointer";
        }
        console.log('Arquivo selecionado:', this.files[0]);
    });
    inputPesquisarPerfil.addEventListener('input', function () {
        let valor = this.value;
        if (valor.length > 0) {
            mostrarCaixaResultados(true, this);
        } else {
            mostrarCaixaResultados(false, this);
        }
        if (valor.length > 1) {
            pesquisarPerfil(valor);
        }
    });
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
    listaPerfisPesquisa.on('click', '.resultado-pesquisa-perfil', function () {
        $(this).css({
            backgroundColor: "rgb(7,25,37)"
        });
        window.location.href = "perfil";
    })
    botaoCriarComunidade.on('mouseenter', function () {
        this.style.cursor = 'pointer';
    })
    botaoCriarComunidade.on('mouseleave', function () {
        this.style.cursor = 'default';
    })
    botaoCriarComunidade.on('click', function () {
        $(this).css({
            border: "4px solid #427D9D",
            backgroundColor: "#164863",
            transition: '0.5s'
        })
    })
    labelNomeComunidade.on('mouseenter', function () {
        this.style.cursor = 'text';
    })
    labelNomeComunidade.on('click', function () {
        this.style.cursor = 'default';
    })
    inputDescricaoComunidade.on('input', function () {
        elementoContagemDescricao.text((200 - this.value.length).toString());
        if (this.value.length > 200) {
            this.value = this.value.slice(0, 200);
        }
        if (this.value.length > 0) {
            elementoContagemDescricao.css({
                display: 'flex',
                paddingRight: '20px'
            })
        } else {
            elementoContagemDescricao.css({
                display: 'none'
            })
        }
        this.style.height = 'auto';
        this.style.height = Math.min(this.scrollHeight, 300) + 'px';
    })
    $('#close').on('click', function (){
        fecharPopUpCriarComunidade();
    })
    iconeEditarFotoFundo.on('click', function (){
        console.log('Clicou em iconeEditarFotoFundo');
        $('#editarFotoFundo').click();
    })
    iconeEditarFotoPerfil.on('click', function (){
        console.log('Clicou em iconeEditarFotoPerfil');
        $('#editarFotoPerfil').click();
    })
    $('#editarFotoPerfil').on('change', function (){
        const file = this.files[0];
        if (file) {
            ftPerURL = URL.createObjectURL(file);
            console.log(ftPerURL)
            fotoPerfilComunidade.attr('src', ftPerURL);
            ftPer = file;
        }
        console.log('Arquivo selecionado:', this.files[0]);
    });
    $('#editarFotoFundo').on('change', function (){
        const file = this.files[0];
        if (file) {
            ftFunURL = URL.createObjectURL(file);
            console.log(ftFunURL)
            fotoFundoComunidade.attr('src', ftFunURL);
            ftFun = file;
        }
        console.log('Arquivo selecionado:', this.files[0]);
    })
    function abrirPopUpCriarComunidade(){
        $('#popup-nova-comunidade').css({
            display: 'block'
        })
        $('#fundo-escuro').css({
            display: 'block'
        })
        if (fotoPerfilComunidade.src == null){
            fotoPerfilComunidade.attr('src', "images/img-foto-perfil.png");
        }
        if (fotoFundoComunidade.src == null){
            fotoFundoComunidade.attr('src', "images/img-foto-fundo.png");
        }
    }
    function fecharPopUpCriarComunidade(){
        $('#popup-nova-comunidade').css({
            display: 'none',
            transition: '0.2s'
        })
        $('#fundo-escuro').css({
            display: 'none'
        })

    }

    function mostrarCaixaResultados(bool, input) {
        if (bool) {
            input.style.borderRadius = "0px";
            input.style.borderTopLeftRadius = "20px";
            input.style.borderTopRightRadius = "20px";
            listaPerfisPesquisa.css("display", "flex");
            listaPerfisPesquisa.css("flex-direction", "column");
            perfisSugeridos.style.display = "none";
        } else {
            input.style.borderRadius = "20px";
            listaPerfisPesquisa.css("display", "none")
            perfisSugeridos.style.display = "fixed";
        }
    }

    function obterComunidades() {
        $.ajax({
            url: "obterComunidades",
            type: "GET",
            dataType: "json",
            success: function (results) {
                // mostrarResultadoPesquisa(results)
                // Manipular os dados do resultado
                console.log("Sucesso: " + results);
            },
            error: function (error) {
                console.log("Erro: " + error)
            }
        })

    }

    function submeterFormulario() {
        let dados = {
            inputTexto: textoPublicacao.value,
            inputMidia: imageURL
        };
        new FormData(formNovaPublicacao);
        textoPublicacao.value = "";
        imagemPreview.src = "";
        fetch("novaPublicacao", {
            method: "POST",
            body: JSON.stringify(dados)
        }).then(response => {
            if (!response.ok) {
                throw new Error("Erro na requisicição: " + response.status);
            }
            return response.text();
        }).then(data => {
            console.log(data);
        }).catch(error => {
            console.error("Erro na requisição:", error)
        }).finally(() => {
            textoPublicacao.value = "";
            imageURL = null;
            imagemPreview.src = '';
        })
    }

    function abrirSeletorArquivo() {
        const inputImagem = document.getElementById('input-imagem');
        inputImagem.click();
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

    function mostrarComunidadesSugeridas(results) {
        listaComunidadesSugeridas.empty();
        results.forEach(function (comunidadeSugerida) {
            const caixaComunidade = $("<div>", {
                class: "comunidade-sugerida"
            })
            if (comunidadeSugerida.fotoPerfil == null) {
                comunidadeSugerida.fotoPerfil = "images/people-fill.svg";
            }
            const imgPerfil = $('<img>', {
                src: comunidadeSugerida.fotoPerfil,
                alt: "Foto do perfil da comunidade " + comunidadeSugerida.nome
            });
            const nome = $('<p>', {
                class: "nome-comunidade",
                text: comunidadeSugerida.nome
            });
            caixaComunidade.append(imgPerfil, nome)
            listaPerfisPesquisa.append(caixaComunidade)
            console.log("DEU BOM AQUI NA LINHA 198")
        })
    }
});

function seguirUsuario(idMembro, idSeguindo, i) {
    let botaoSeguir = document.getElementById('botaoSeguir' + i);
    botaoSeguir.style.outline = "4px solid #FFF";
    botaoSeguir.style.transition = "0.25s";
    $.ajax({
        type: "POST",
        url: "seguirMembro",
        data: {
            idMembro: idMembro,
            idSeguindo: idSeguindo
        },
        success: function () {
            console.log("Usuário seguido com sucesso");
            botaoSeguir.innerText = 'SEGUINDO';
            botaoSeguir.style.fontSize = '20px';
            botaoSeguir.style.backgroundColor = 'var(--COR-03)';
            botaoSeguir.style.outline = '3px solid var(--COR-04)';
        },
        error: function (error) {
            console.log("Erro ao seguir usuário: " + error.responseText);
        }
    })
}
function criarComunidade(idAutor) {
    let formData = new FormData();
    formData.append('nomeComunidade', $('#nomeComunidade').val());
    formData.append('descricaoComunidade', $('#descricaoComunidade').val());
    formData.append('idAutor', idAutor);
    formData.append('fotoPerfil', ftPer);
    formData.append('fotoFundo', ftFun);
    $.ajax({
        url: "criarComunidade",
        type: "POST",
        data: formData,
        processData: false,
        contentType: false,
        dataType: "json",
        success: function (response) {
            if (response && response.success) {
                console.log("SUCESSO NA CRIAÇÃO DA COMUNIDADE: " + (response.message || "Sem mensagem de sucesso."));
            } else {
                console.log("ERRO NA CRIAÇÃO DA COMUNIDADE: " + (response.message || "Sem mensagem de erro específica."));
            }
            console.log(response)
        },
        error: function (error) {
            console.log("ERRO NA CRIAÇÃO DA COMUNIDADE: " + (error.responseText || "Erro desconhecido."));
        }

    });
}