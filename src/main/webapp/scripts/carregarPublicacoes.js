publicacoesCarregadas = [];

function carregarPublicacoes(tipo) {
    let div_postagens = document.getElementById('postagens');
    let proximo_intervalo;
    if (div_postagens) {
        proximo_intervalo = div_postagens.childElementCount;
    } else {
        proximo_intervalo = 0;
    }
    let dados, perfil, comunidade;
    try {
        let parametrosURL = new URL(window.location.href);
        if (tipo === "perfil") {
            perfil = parametrosURL.searchParams.get("nome_usuario");
            console.log("perfil: " + perfil);
        } else if (tipo === "comunidade") {
            comunidade = parametrosURL.searchParams.get("idComunidade");
            console.log("comunidade: " + comunidade);
        }
    } catch (error) {
        console.error("Erro ao analisar a URL:", error);
    }
    let url = 'verPublicacoes';
    if (tipo === "perfil" && perfil !== null) {
        dados = {intervalo: parseInt(proximo_intervalo), nomeUsuario: perfil};
    } else if (tipo === "comunidade" && comunidade !== null) {
        dados = {intervalo: parseInt(proximo_intervalo), idComunidade: comunidade};
        url = 'verPublicacoesComunidade';
    } else {
        dados = {intervalo: parseInt(proximo_intervalo)};
    }

    $.ajax({
        type: 'GET',
        url: url,
        data: dados,
        dataType: "json",
        cache: false,
        success: function (publicacoesEncontradas) {
            proximo_intervalo += 4;
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
                    if (!$(event.target).hasClass('icone-curtida') && !$(event.target).hasClass('botao-apagar')) {
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
                        if (curtida === usuarioAutenticado.idPessoa) {
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
                if (publicacao.autor.idPessoa === usuarioAutenticado.idPessoa) {
                    let botaoApagar = $('<button>', {
                        class: 'botao-apagar',
                        text: 'EXCLUIR',
                    })
                    botaoApagar.on('click', function () {
                        excluirPublicacao(publicacao.idPublicacao, confirmacao, false);
                        classe_caixa_publicacao.css({
                            display: 'none'
                        })
                        confirmacao.css({
                            display: 'block'
                        })
                        setTimeout(function () {
                            confirmacao.css({
                                display: 'none'
                            });
                        }, 3000);
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
