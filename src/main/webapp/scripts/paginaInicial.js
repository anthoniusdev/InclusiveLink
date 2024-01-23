document.addEventListener("DOMContentLoaded", function () {
    let formNovaPublicacao = document.getElementById("formNovaPublicacao");
    let textoPublicacao = document.getElementById("textoNovaPublicacao");
    let elementoContagem = document.getElementById("contagemCaracteres");
    let botaoPostar = document.getElementById("btnPostar");
    const iconeSVGinputIMG = document.getElementById("icone-escolher-imagem");
    const inputImagem = document.getElementById('input-imagem');
    const imagemPreview = document.getElementById('imagem-preview');
    const botaoSeguir1 = document.getElementById('botaoSeguir1');
    const botaoSeguir2 = document.getElementById('botaoSeguir2');
    const botaoSeguir3 = document.getElementById('botaoSeguir3');

    let imageURL;
    textoPublicacao.addEventListener('input', function () {
        if (this.value.length > 200) {
            this.value = this.value.slice(0, 200);
        }
        elementoContagem.textContent = (200 - this.value.length).toString();
        if (this.value.length > 0) {
            elementoContagem.style.display = "flex";
            botaoPostar.style.backgroundColor = "#164863";
            botaoPostar.style.cursor = "pointer";
        } else {
            elementoContagem.style.display = "none";
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
    })
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
    botaoSeguir1.addEventListener('click', function () {
        let id = this.id.charAt(this.id.length - 1);
        alert(id);
    });
    botaoSeguir2.addEventListener('click', function () {
        let id = this.id.charAt(this.id.length - 1);
        alert(id);
    });
    botaoSeguir3.addEventListener('click', function () {
        let id = this.id.charAt(this.id.length - 1);
        alert(id);
    });
    function seguirUsuario(idMembro, idSeguindo){
        $.ajax({
            type: "POST",
            url: "seguirMembro",
            data:{
                idMembro: idMembro,
                idSeguindo: idSeguindo
            },
            success: function (response){
                console.log("Usuário seguido com sucesso");
            },
            error: function (error){
                console.log("Erro ao seguir usuário: " + error.responseText);
            }
        })
    };
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