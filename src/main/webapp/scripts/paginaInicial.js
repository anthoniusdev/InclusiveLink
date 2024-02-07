let usuarioAutenticado;
obterUsuarioAutenticado().then(function (usuario) {
    usuarioAutenticado = usuario;
}).catch(function (error) {
    console.log(error);
})
carregarPublicacoes();
let carregando = false, publicacoesCarregadas = [];
document.addEventListener("DOMContentLoaded", function () {
    let formNovaPublicacao = document.getElementById("formNovaPublicacao");
    let textoPublicacao = document.getElementById("textoNovaPublicacao");
    let elementoContagemPublicacao = document.getElementById("contagemCaracteresPublicacao");
    let botaoPostar = document.getElementById("btnPostar");
    const iconeSVGinputIMG = document.getElementById("icone-escolher-imagem");
    const inputImagem = document.getElementById('input-imagem');
    const imagemPreview = document.getElementById('imagem-preview');
    let file;
    let xFt = document.getElementById('remover-foto');
    let imageURL;
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
            botaoPostar.style.backgroundColor = "#0c202a1f";
            botaoPostar.style.cursor = "default";
        }
    })
    textoPublicacao.addEventListener('input', function () {
        this.style.height = 'auto';
        this.style.height = Math.min(this.scrollHeight, 300) + 'px';
    });
    textoPublicacao.addEventListener('focus', function () {
        let linha = document.getElementById('linhaAreaInput');
        linha.style.height = '4px';
    });
    textoPublicacao.addEventListener('focusout', function () {
        let linha = document.getElementById('linhaAreaInput');
        linha.style.height = '2px';
    })
    formNovaPublicacao.addEventListener("submit", function (event) {
        event.preventDefault();
        if (textoPublicacao.value.length > 0 || imageURL != null) {
            submeterFormulario();
        } else {
            textoPublicacao.focus();
        }
    });
    iconeSVGinputIMG.addEventListener('click', function () {
        abrirSeletorArquivo();
    });
    inputImagem.addEventListener('input', function () {
        file = this.files[0];
        if (file) {
            imageURL = URL.createObjectURL(file);
            imagemPreview.src = imageURL;
            xFt.style.display = 'block';
            botaoPostar.style.backgroundColor = "#164863";
            botaoPostar.style.cursor = "pointer";
        }
        console.log('Arquivo selecionado:', this.files[0]);
    });
    xFt.addEventListener('click', function () {
        file = null;
        imageURL = null;
        imagemPreview.src = null;
        xFt.style.display = 'none';
        if (textoPublicacao.value.length === 0) {
            botaoPostar.style.backgroundColor = "#0c202a1f";
            botaoPostar.style.cursor = "default";
        }
    })
    let verComunidades = $('#ver-comunidades');
    verComunidades.on('mouseenter', function () {
        $(this).css({
            cursor: 'pointer',
            backgroundColor: '#1F465EFF',
            transition: '0.5s'
        })
    })
    verComunidades.on('mouseleave', function () {
        $(this).css({
            cursor: 'default',
            backgroundColor: 'var(--COR-03)',
            transition: '0.5s'
        })
    })

    function submeterFormulario() {
        let formData = new FormData(formNovaPublicacao);
        textoPublicacao.value = "";
        imagemPreview.src = "";
        elementoContagemPublicacao.textContent = (200).toString();
        elementoContagemPublicacao.style.display = "none";
        botaoPostar.style.backgroundColor = "#0c202a1f"
        botaoPostar.style.cursor = "default";
        xFt.style.display = 'none';
        fetch("novaPublicacao", {
            method: "POST",
            body: formData
        }).then(response => {
            if (!response.ok) {
                throw new Error("Erro na requisicição: " + response.status);
            }
            return response.text();
        }).then(data => {
            console.log(data);
            location.reload();
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

    $(window).scroll(function () {
        if (!carregando && $(window).scrollTop() + $(window).height() >= $(document).height() - 100) {
            carregando = true;
            carregarPublicacoes();
        }
    })
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

function carregarPublicacoes() {
    let div_postagens = document.getElementById('postagens');
    let proximo_intervalo;
    if (div_postagens) {
        proximo_intervalo = div_postagens.childElementCount;
    } else {
        proximo_intervalo = 0;
    }
    $.ajax({
        type: 'GET',
        url: 'verPublicacoes',
        data: {intervalo: parseInt(proximo_intervalo)},
        dataType: "json",
        cache: false,
        success: function (publicacoesEncontradas) {
            proximo_intervalo += 5;
            let confirmacao = $('<div>', {
                class: 'confirmacao-exclusao',
            });
            let mensagem = $('<div>', {
                class: 'mensagem',
                text: 'PUBLICAÇÃO EXCLUÍDA'
            });
            confirmacao.css({
                display: 'none'
            });
            confirmacao.append(mensagem);
            $('.postagens').append(confirmacao);
            publicacoesEncontradas.forEach(function (publicacao) {
                let classe_caixa_publicacao = $("<div>", {
                    class: 'caixa-publicacao'
                });
                classe_caixa_publicacao.on('click', function (event) {
                    if (!$(event.target).hasClass('icone-curtida')) {
                        window.location.href = 'verPublicacao?username=' + publicacao.autor.nomeUsuario + '&idPublicacao=' + publicacao.idPublicacao;
                    }
                })
                let classe_foto_perfil_autor = $("<div>", {
                    class: 'foto-perfil-autor'
                })
                if (publicacao.autor.fotoPerfil == null) {
                    publicacao.autor.fotoPerfil = "images/person_foto.svg";
                }
                let imagem_foto_perfil_autor = $("<img>", {
                    src: publicacao.autor.fotoPerfil,
                    alt: 'Foto de perfil de ' + publicacao.autor.nome
                })
                classe_foto_perfil_autor.append(imagem_foto_perfil_autor);
                classe_caixa_publicacao.append(classe_foto_perfil_autor);
                let classe_informacoes_publicacao = $("<div>", {
                    class: 'informacoes-publicacao'
                });
                let classe_nome_autor = $("<div>", {
                    class: 'nome-autor'
                })
                let valor_nome_autor = $("<h3>", {
                    text: publicacao.autor.nome
                })
                classe_nome_autor.append(valor_nome_autor);
                classe_informacoes_publicacao.append(classe_nome_autor);
                let classe_texto_publicacao = $("<div>", {
                    class: 'texto-publicacao'
                })
                let valor_texto_publicacao = $("<p>", {
                    text: publicacao.texto
                })
                classe_texto_publicacao.append(valor_texto_publicacao);
                classe_informacoes_publicacao.append(classe_texto_publicacao)
                if (publicacao.midia !== null) {
                    let classe_midia_publicacao = $("<div>", {
                        class: 'midia-publicacao'
                    })
                    let imagem_midia_publicacao = $("<img>", {
                        src: publicacao.midia
                    })
                    classe_midia_publicacao.append(imagem_midia_publicacao);
                    classe_informacoes_publicacao.append(classe_midia_publicacao);
                }
                let classe_inshights_publicacao = $("<div>", {
                    class: 'inshights-publicacao'
                });
                let classe_curtida_publicacao = $("<div>", {
                    class: 'curtida-publicacao'
                })
                let ftCurt = 'images/iconamoon_heart-bold.svg';
                if (publicacao.curtidas.length > 0) {
                    publicacao.curtidas.forEach(function (curtida) {
                        if (curtida === usuarioAutenticado) {
                            ftCurt = 'images/iconamoon_heart-fill.svg';
                        }
                    })
                }
                let icone_curtida_publicacao = $("<img>", {
                    class: 'icone-curtida',
                    src: ftCurt,
                    alt: 'ícone de curtida',
                })
                icone_curtida_publicacao.on('click', function () {
                    curtirPublicacao(publicacao.idPublicacao);
                });
                icone_curtida_publicacao.on('mouseenter', function () {
                    this.style.cursor = 'pointer';
                    if (this.src.includes('images/iconamoon_heart-bold.svg')) {
                        this.src = 'images/iconamoon_heart-bold-js.svg';
                    }
                    this.style.boxShadow = "0 0 20px 5px #FC6E6E";
                });
                icone_curtida_publicacao.on('mouseleave', function () {
                    this.style.cursor = 'default';
                    if (this.src.includes('images/iconamoon_heart-bold-js.svg')) {
                        this.src = 'images/iconamoon_heart-bold.svg';
                    }
                    this.style.boxShadow = '';
                })
                let contador_curtidas = $("<small>", {
                    text: publicacao.curtidas.length.toString()
                });
                icone_curtida_publicacao.on('click', function () {
                    if (this.src.includes('images/iconamoon_heart-bold-js.svg')) {
                        this.src = 'images/iconamoon_heart-fill.svg';
                        contador_curtidas.text(1 + parseInt(contador_curtidas.text())).toString()
                    } else {
                        this.src = 'images/iconamoon_heart-bold-js.svg';
                        contador_curtidas.text(parseInt(contador_curtidas.text()) - 1).toString()
                    }
                });
                classe_curtida_publicacao.append(icone_curtida_publicacao);
                classe_curtida_publicacao.append(contador_curtidas);
                classe_inshights_publicacao.append(classe_curtida_publicacao);
                let classe_comentario_publicacao = $("<div>", {
                    class: 'comentarios-publicacao'
                })
                let icone_comentario_publicacao = $("<img src='images/majesticons_comment-line.svg' alt='ícone de comentário'>")
                let contador_comentarios = $("<small>", {
                    text: publicacao.numeroComentarios
                })
                classe_comentario_publicacao.append(icone_comentario_publicacao);
                classe_comentario_publicacao.append(contador_comentarios);
                classe_inshights_publicacao.append(classe_comentario_publicacao);
                classe_informacoes_publicacao.append(classe_inshights_publicacao);
                classe_caixa_publicacao.append(classe_informacoes_publicacao);
                if (publicacao.autor.idPessoa === usuarioAutenticado) {
                    let botaoApagar = $('<button>', {
                        class: 'botao-apagar',
                        text: 'EXCLUIR',
                    })
                    botaoApagar.on('click', function () {
                        excluirPublicacao(publicacao.idPublicacao, classe_caixa_publicacao, confirmacao);
                    })
                    classe_caixa_publicacao.append(botaoApagar);
                }
                $('#postagens').append(classe_caixa_publicacao);
                console.log('publicacao encontrada:' + publicacao);
                publicacoesCarregadas.push(publicacao.idPublicacao)
                carregando = false;
            })
        }, error: function (error) {
            console.log(error)
        }
    })
}

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