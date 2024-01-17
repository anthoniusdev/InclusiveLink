document.addEventListener("DOMContentLoaded", function () {
    console.log("Script carregado com sucesso.");
    const form = document.getElementById("formCadastro");
    const nome = document.getElementById("nome");
    const sobrenome = document.getElementById("sobrenome");
    const nomeUsuario = document.getElementById("nome_usuario");
    const email = document.getElementById("email");
    const senha = document.getElementById("senha");
    const senhaRepetida = document.getElementById("senha_repetida");
    const dia = document.getElementById("dia");
    const mes = document.getElementById("mes");
    const ano = document.getElementById("ano");

    form.addEventListener("submit", (e) => {
        e.preventDefault();
        checkInput();
    })

    function checkInput() {
        const nomeValue = nome.value;
        const sobrenomeValue = sobrenome.value;
        const nomeUsuarioValue = nomeUsuario.value;
        const emailValue = email.value;
        const senhaValue = senha.value;
        const senhaRepetidaValue = senhaRepetida.value;
        // Checando se o nome está vazio
        if (nomeValue.trim() === "") {
            // Mostrar o erro
            // adicionar a classe error
            errorValidation(nome, "Preencha esse campo");
        } else {
            // adicionar a classe de sucesso
            succesValidation(nome);
        }
    }

    function errorValidation(input, message) {
        const formControl = input.parentElement;
        if (formControl) {
            const small = formControl.parentElement.querySelector('small');
            if (small) {
                const i = formControl.parentElement.querySelector('i');
                if (i) {
                    const img = i.querySelector('img');
                    if (img) {
                        i.style.visibility = "visible";
                        img.src = "images/error-icon.svg";
                        img.alt = "Imagem de erro!";
                        img.style.visibility = "visible";
                        small.innerText = message;
                        formControl.className = 'form-control error';
                        small.style.visibility = "visible";
                        small.style.color = "#782121";
                    } else {
                        console.error("Elemento img não encontrado");
                    }
                } else {
                    console.error("Elemento i não encontrado");
                }
            }else {
                console.log("ELEMENTO SMALL NÃO ENCONTRADO");
            }
        }else{
            console.log("FORM CONTROL NÃO ENCONTRADO");
        }
    }
    function succesValidation(input){
        const formControl = input.parentElement;
        if (formControl){
            const i = formControl.parentElement.querySelector('i');
            if (i){
                const img = i.querySelector('img');
                if (img){
                    i.style.visibility = "visible";
                    img.src = "images/success-icon.png";
                    formControl.className = "form-control success";
                }else {
                    console.log("ELEMENTO IMG NAO ENCONTRADO")
                }
            }else {
                console.log("ELEMENTO I NAO ENCONTRADO LINHA 72");
            }
        }else {
            console.log("FORM CONTROL NAO ENCONTRADO LINHA 70");
        }
    }
});
/*function realizarCadastro() {
    let formulario = frmCadastro;
    let nome = formulario["nome"];
    let sobrenome = formulario["sobrenome"];
    let nome_usuario = formulario["nome_usuario"];
    let email = formulario["email"];
    let senha = formulario["senha"];
    let senhaRepetida = formulario["senha_repetida"];
    let dia = formulario["dia"];
    let mes = formulario["mes"];
    let ano = formulario["ano"];
    let nomeFormatado = corrigirStringEValidar(nome.value);
    if (nomeFormatado === false) {
        alert("O CAMPO NOME CONTÉM CARACTERE(S) ESPECIAL(IS)")
        nome.focus();
        return false;
    } else {
        let sobrenomeFormatado = corrigirStringEValidar(sobrenome.value);
        if (sobrenomeFormatado === false) {
            alert("O CAMPO SOBRENOME CONTÉM CARACTERE(S) ESPECIAL(IS)");
            sobrenome.focus();
            return false;
        } else {
            if (senhaRepetida.value !== senha.value) {
                alert("SENHAS SE DIFEREM");
                senhaRepetida.focus();
                return false;
            } else {
                formulario.submit();
                return true;
            }
        }
    }
}

function corrigirStringEValidar(str) {
    if (typeof str !== 'string') {
        console.error('O argumento não é uma string:', str);
        return false;
    }
    // Verifica se a string possui apenas letras (maiúsculas e minúsculas) com acentos
    let regex = /^[a-zA-ZÀ-ÖØ-öø-ÿ]+(?:\s[a-zA-ZÀ-ÖØ-öø-ÿ]+)*$/;

    // Remove espaços em branco extras e ajusta espaços no meio da string
    let stringCorrigida = str.trim().replace(/\s+/g, ' ');
    
    // Testa se a string corrigida atende ao padrão da expressão regular
    return regex.test(stringCorrigida) ? stringCorrigida : false;
}*/
