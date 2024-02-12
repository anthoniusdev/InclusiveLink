let usuarioAutenticado;
obterUsuarioAutenticado().then(function (usuario) {
    usuarioAutenticado = usuario;
}).catch(function (error) {
    console.log(error);
})
let carregando = false;
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
    carregarPublicacoes();
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
    });
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
