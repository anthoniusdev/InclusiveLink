document.addEventListener("DOMContentLoaded", function () {
    let formNovaPublicacao = document.getElementById("formNovaPublicacao");
    let textoPublicacao = document.getElementById("textoNovaPublicacao");
    let elementoContagemPublicacao = document.getElementById("contagemCaracteresPublicacao");
    let botaoPostar = document.getElementById("btnPostar");
    const iconeSVGinputIMG = document.getElementById("icone-escolher-imagem");
    const inputImagem = document.getElementById('input-imagem');
    const imagemPreview = document.getElementById('imagem-preview');
    // let icone_curtir_publicacao = document.getElementById('icone-curtir-publicacao');
    let icone_curtir_publicacao = document.querySelectorAll('.icone-curtida');
    let file;
    let xFt = document.getElementById('remover-foto');
    let imageURL;
    obterComunidades();
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
    xFt.addEventListener('click', function (){
        file = null;
        imageURL = null;
        imagemPreview.src = null;
        xFt.style.display = 'none';
        botaoPostar.style.backgroundColor = "#0c202a1f";
        botaoPostar.style.cursor = "default";
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
    icone_curtir_publicacao.forEach(function (icones){
        icones.addEventListener('mouseenter', function (){
            this.style.cursor = 'pointer';
            if (this.src.includes('images/iconamoon_heart-bold.svg')){
                this.src = 'images/iconamoon_heart-bold-js.svg';
            }
            this.style.boxShadow = "0px 0 20px 5px #FC6E6E";

        })
        icones.addEventListener('mouseleave', function (){
            this.style.cursor = 'default';
            if (this.src.includes('images/iconamoon_heart-bold-js.svg')){
                this.src = 'images/iconamoon_heart-bold.svg';
            }
            this.style.boxShadow = '';
        })
        icones.addEventListener('click', function (){
            let smallElement = icones.parentElement.querySelector('small');
            if (!smallElement) {
                console.error("Elemento <small> não encontrado.");
                console.log("Conteúdo de icones:", icones);
                return;
            }
            if (this.src.includes('images/iconamoon_heart-bold-js.svg')){
                this.src = 'images/iconamoon_heart-fill.svg';
                smallElement.innerHTML = (1 + parseInt(smallElement.innerHTML)).toString()
            }else{
                this.src = 'images/iconamoon_heart-bold-js.svg';
                smallElement.innerHTML = (parseInt(smallElement.innerHTML) - 1).toString()
            }
        })
    })
    function obterComunidades() {
        $.ajax({
            url: "obterComunidades",
            type: "GET",
            dataType: "json",
            success: function (results) {
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
function curtirPublicacao(idPublicacao){
    $.ajax({
        type: 'POST',
        url: 'curtirPublicacao',
        data: {idPublicacao: idPublicacao},
        dataType: "json",
        success: function (){
            console.log('publicacao curtida')
        },
        error: function (error){
            console.log(error)
        }
    })
}