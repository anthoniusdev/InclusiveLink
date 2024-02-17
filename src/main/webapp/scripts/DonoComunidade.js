function ativarOpcoes(event) {
    console.log("Função ativarOpcoes() chamada.");
    var minhaDiv = document.getElementById("opcoes");
    if (minhaDiv.classList.contains("d-none")) {

        minhaDiv.classList.remove("d-none");
        minhaDiv.classList.add("d-block");
    }
    event.stopPropagation();
}

function ocultarDiv(event) {
    var minhaDiv = document.getElementById("opcoes");
    var botao = document.getElementById("botaoAtivar");

    // Verifique se o clique não foi no botão ou dentro da div
    if (event.target !== botao && !minhaDiv.contains(event.target)) {
        // Oculte a div
        minhaDiv.classList.remove("d-block");
        minhaDiv.classList.add("d-none");
    }
}

// Adicionar ouvinte de eventos ao botão para ativar as opções
document.getElementById("botaoAtivar").addEventListener("click", ativarOpcoes);

// Adicionar ouvinte de eventos ao documento para ocultar a div quando clicar fora dela
document.addEventListener("click", ocultarDiv);