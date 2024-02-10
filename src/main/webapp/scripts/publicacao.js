var idPublicacao;
obterIdPublicacao().then(function (id) {
    idPublicacao = id;
    carregarComentarios(idPublicacao);
}).catch(function (error) {
    console.log(error);
});
let usuarioAutenticado;
obterUsuarioAutenticado().then(function (usuario) {
    usuarioAutenticado = usuario;
}).catch(function (error) {
    console.log(error);
});
let carregando = false, comentariosCarregados = [];
document.addEventListener('DOMContentLoaded', function () {
    let form_novoComentario = $('#formNovoComentario');
    let icone_curtida = $('.icone-curtida');
    let icone_voltar = $('.icone-voltar');
    let texto_textArea = $('#textoNovaPublicacao');
    let elementoContagem = $('#contagemCaracteresPublicacao');
    let botaoPostar = $('#btnPostar');
    carregarInformacoesInshights();
    form_novoComentario.on('submit', function (event) {
        event.preventDefault();
        if (texto_textArea.val().length > 0) {
            submeterForm();
        } else {
            texto_textArea.focus();
        }
    })

    function submeterForm() {
        elementoContagem.text('200');
        elementoContagem.css({
            display: 'none'
        });
        botaoPostar.css({
            backgroundColor: '#0c202a1f',
            cursor: 'default'
        });
        $.ajax({
            url: 'novoComentario',
            type: 'POST',
            data: {
                inputTexto: texto_textArea.val(),
                idPublicacao: idPublicacao
            },
            success: function () {
                window.location.reload();
            },
            error: function () {
                console.log('deu erro')
            }
        })
        texto_textArea.val('');
    }

    texto_textArea.on('input', function () {
        elementoContagem.text((200 - this.value.length).toString());
        if (this.value.length > 200) {
            this.value = this.value.slice(0, 200);
        }
        if (this.value.length > 0) {
            botaoPostar.css({
                backgroundColor: '#164863',
                cursor: 'pointer'
            });
            elementoContagem.css({
                display: 'flex',
                paddingRight: '20px'
            })
        } else {
            botaoPostar.css({
                backgroundColor: '#0c202a1f',
                cursor: 'default'
            })
            elementoContagem.css({
                display: 'none'
            })
        }
        this.style.height = 'auto';
        this.style.height = Math.min(this.scrollHeight, 300) + 'px';
    })
    icone_curtida.on('mouseenter', function () {
        if (this.src.includes('images/iconamoon_heart-bold.svg')) {
            this.src = 'images/iconamoon_heart-bold-js.svg';
        }
        icone_curtida.css({
            cursor: 'pointer',
            boxShadow: '0 0 20px 5px #FC6E6E'
        });
    });
    icone_curtida.on('mouseleave', function () {
        if (this.src.includes('images/iconamoon_heart-bold-js.svg')) {
            this.src = 'images/iconamoon_heart-bold.svg';
        }
        icone_curtida.css({
            cursor: 'default',
            boxShadow: ''
        });
    });
    icone_curtida.on('click', function () {
        let contagem = $('.contagem-curtidas');
        if (this.src.includes('images/iconamoon_heart-bold-js.svg')) {
            this.src = 'images/iconamoon_heart-fill.svg';
            contagem.text(1 + parseInt(contagem.text())).toString()
        } else {
            this.src = 'images/iconamoon_heart-bold-js.svg';
            contagem.text(parseInt(contagem.text()) - 1).toString()
        }
    });
    icone_voltar.on('click', function () {
        window.location.href = 'PaginaInicial.jsp';
    })
    $(window).scroll(function () {
        if (!carregando && $(window).scrollTop() + $(window).height() >= $(document).height() - 100) {
            carregando = true;
            carregarComentarios(idPublicacao);
        }
    })
})

function carregarInformacoesInshights() {
    carregarInformacoesCurtidas();
    carregarInformacoesComentarios();
}

function carregarComentarios(idPublicacao) {
    let div_postagens = document.getElementById('comentarios');
    let proximo_intervalo;
    if (div_postagens) {
        proximo_intervalo = div_postagens.childElementCount;
    } else {
        proximo_intervalo = 0;
    }
    console.log('pro' + proximo_intervalo);
    console.log('id' + idPublicacao)
    proximo_intervalo = parseInt(proximo_intervalo);
    $.ajax({
        type: 'GET',
        url: 'obterComentarios',
        data: {
            idPublicacao: idPublicacao,
            intervalo: proximo_intervalo
        },
        success: function (results) {
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
            results.forEach(function (comentario) {
                let classe_comentarios = $('#comentarios');
                let classe_caixa_comentario = $('<div>', {
                    class: 'caixa-comentario'
                });
                let classe_foto_perfil_autor = $("<div>", {
                    class: 'foto-perfil-autor'
                });
                if (comentario.autor.fotoPerfil == null) {
                    comentario.autor.fotoPerfil = "images/person_foto.svg";
                }
                let imagem_foto_perfil_autor = $("<img>", {
                    src: comentario.autor.fotoPerfil,
                    alt: 'Foto de perfil de ' + comentario.autor.nome
                });
                classe_foto_perfil_autor.append(imagem_foto_perfil_autor);
                classe_caixa_comentario.append(classe_foto_perfil_autor);
                let classe_informacoes_comentario = $('<div>', {
                    class: 'informacoes-comentario'
                })
                let classe_nome_autor = $("<div>", {
                    class: 'nome-autor'
                })
                let valor_nome_autor = $("<h3>", {
                    text: comentario.autor.nome
                })
                classe_nome_autor.append(valor_nome_autor);
                classe_informacoes_comentario.append(classe_nome_autor);
                let classe_texto_comentario = $("<div>", {
                    class: 'texto-comentario'
                })
                let valor_texto_comentario = $("<p>", {
                    text: comentario.texto
                })
                valor_texto_comentario.css({
                    height: Math.min(this.scrollHeight, 300) + 'px'
                })
                classe_texto_comentario.append(valor_texto_comentario);
                classe_informacoes_comentario.append(classe_texto_comentario);
                let classe_inshights_comentario = $("<div>", {
                    class: 'inshights-comentario'
                });
                let classe_curtida_comentario = $("<div>", {
                    class: 'curtida-comentario'
                })
                let ftCurt = 'images/iconamoon_heart-bold.svg';
                if (comentario.curtidas.length > 0) {
                    comentario.curtidas.forEach(function (curtida) {
                        if (curtida === usuarioAutenticado) {
                            ftCurt = 'images/iconamoon_heart-fill.svg';
                        }
                    })
                }
                let icone_curtida_comentario = $("<img>", {
                    class: 'icone-curtida',
                    src: ftCurt,
                    alt: 'ícone de curtida',
                })
                icone_curtida_comentario.on('click', function () {
                    curtirComentario(comentario.idComentario);
                });
                icone_curtida_comentario.on('mouseenter', function () {
                    this.style.cursor = 'pointer';
                    if (this.src.includes('images/iconamoon_heart-bold.svg')) {
                        this.src = 'images/iconamoon_heart-bold-js.svg';
                    }
                    this.style.boxShadow = "0 0 20px 5px #FC6E6E";
                });
                icone_curtida_comentario.on('mouseleave', function () {
                    this.style.cursor = 'default';
                    if (this.src.includes('images/iconamoon_heart-bold-js.svg')) {
                        this.src = 'images/iconamoon_heart-bold.svg';
                    }
                    this.style.boxShadow = '';
                })
                let contador_curtidas = $("<small>", {
                    text: comentario.curtidas.length.toString()
                });
                icone_curtida_comentario.on('click', function () {
                    if (this.src.includes('images/iconamoon_heart-bold-js.svg')) {
                        this.src = 'images/iconamoon_heart-fill.svg';
                        contador_curtidas.text(1 + parseInt(contador_curtidas.text())).toString()
                    } else {
                        this.src = 'images/iconamoon_heart-bold-js.svg';
                        contador_curtidas.text(parseInt(contador_curtidas.text()) - 1).toString()
                    }
                });
                classe_curtida_comentario.append(icone_curtida_comentario);
                classe_curtida_comentario.append(contador_curtidas);
                classe_inshights_comentario.append(classe_curtida_comentario);
                classe_informacoes_comentario.append(classe_inshights_comentario);
                classe_caixa_comentario.append(classe_informacoes_comentario);
                if (comentario.autor.idPessoa === usuarioAutenticado) {
                    let botaoApagar = $('<button>', {
                        class: 'botao-apagar',
                        text: 'EXCLUIR',
                    })
                    botaoApagar.on('click', function () {
                        excluirComentario(comentario.idComentario, classe_caixa_comentario, confirmacao);
                    })
                    classe_caixa_comentario.append(botaoApagar);
                }
                classe_comentarios.append(classe_caixa_comentario);
                comentariosCarregados.push(comentario.idComentario);
                carregando = false;
            })
        }
    })
}

function carregarInformacoesCurtidas() {
    $.ajax({
        url: 'curtidasPublicacao',
        data: {idPublicacao: idPublicacao},
        type: 'GET',
        dataType: 'json',
        success: function (results) {
            let contagem_curtidas = $('<div>', {
                class: 'contagem-curtidas',
                text: results.length
            });
            $('#curtida-publicacao').append(contagem_curtidas);
        },
        error: function (error) {
            console.log(error);
        }
    })
}

function carregarInformacoesComentarios() {
    $.ajax({
        url: 'comentariosPublicacao',
        data: {idPublicacao: idPublicacao},
        type: 'GET',
        dataType: 'json',
        success: function (results) {
            let contagem_comentarios = $('<div>', {
                class: 'contagem-comentarios',
                text: results.length
            })
            $('#comentarios-publicacao').append(contagem_comentarios);
        },
        error: function (error) {
            console.log(error);
        }
    })
}

function obterIdPublicacao() {
    return new Promise(function (resolve, reject) {
        $.ajax({
            type: 'GET',
            url: 'obterIdPublicacao',
            dataType: 'json',
            success: function (id) {
                resolve(id)
            },
            error: function (status, error) {
                console.error('Erro na requisição: ', status, error);
                reject('Erro na requisição');
            }
        })
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

function excluirComentario(idComentario, post, confirmacao) {
    $.ajax({
        url: 'excluirComentario',
        data: {idComentario: idComentario},
        type: 'POST',
        dataType: 'json',
        success: function () {
            post.css({
                display: 'none'
            });
            confirmacao.css({
                display: 'block'
            })
            setTimeout(function () {
                confirmacao.css({
                    display: 'none'
                });
            }, 3000)
            console.log('publicacao excluida');
        }, error: function (erro) {
            console.log(erro);
        }
    })
}

function curtirComentario(idComentario) {
    $.ajax({
        url: "curtirComentario",
        type: "POST",
        data: {idComentario: idComentario}
    })
}

function apagarPost(){

}