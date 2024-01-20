document.addEventListener("DOMContentLoaded", function (){
    let formNovaPublicacao = document.getElementById("formNovaPublicacao");
    let textoPublicacao = document.getElementById("textoNovaPublicacao");
    let elementoContagem = document.getElementById("contagemCaracteres");
    let botaoPostar = document.getElementById("btnPostar");
    textoPublicacao.addEventListener('input', function (){
        if (this.value.length > 200){
            this.value = this.value.slice(0, 200);
        }
        elementoContagem.textContent = (200 - this.value.length).toString();
        if (this.value.length > 0) {
            elementoContagem.style.display = "flex";
            botaoPostar.style.backgroundColor = "#164863";
            botaoPostar.style.cursor = "pointer";
        }else{
            elementoContagem.style.display = "none";
            botaoPostar.style.backgroundColor = "#0c202a1f"
            botaoPostar.style.cursor = "no-drop";
        }
    })
    textoPublicacao.addEventListener('input', function (){
        this.style.height = 'auto';
        this.style.height = Math.min(this.scrollHeight, 300) +  'px';
    });
    formNovaPublicacao.addEventListener("submit", function(event) {
        event.preventDefault();
        submeterFormulario();
    })
    function submeterFormulario(){
        let inputMidia = document.getElementById("imagemPublicacao").value;
        let dados = {
            inputTexto: textoPublicacao.value,
            inputMidia: inputMidia
        };
        new FormData(formNovaPublicacao);
        textoPublicacao.value = "";

        fetch("novaPublicacao", {
            method: "POST",
            headers:{
                "Content-Type": "application/json"
            },
            body: JSON.stringify(dados)
        }).then(response =>{
            if (!response.ok){
                throw new Error("Erro na requisicição: " + response.status);
            }
            return response.text();
        }).then(data =>{
            console.log(data);
        }).catch(error =>{
            console.error("Erro na requisição:", error)
        })

    }
});