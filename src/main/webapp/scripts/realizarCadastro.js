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
        if (!isEmpty(nome)) {

            var nomeCorrigido = corrigirNomeEValidar(nome);

            if (nomeCorrigido !== false) {

                nome.value = nomeCorrigido;
                succesValidation(nome);

                if (!isEmpty(sobrenome)) {

                    var sobrenomeCorrigido = corrigirNomeEValidar(sobrenome);

                    if (sobrenomeCorrigido !== false) {

                        sobrenome.value = sobrenomeCorrigido;
                        succesValidation(sobrenome);
                        if (!isEmpty(nomeUsuario)) {

                            var nomeUsuarioCorrigido = corrigirNomeUsuarioEValidar(nomeUsuario);

                            if (nomeUsuarioCorrigido !== false) {

                                nomeUsuario.value = nomeUsuarioCorrigido;
                                succesValidation(nomeUsuario);

                                if (!isEmpty(email)) {

                                    var emailCorrigido = corrigirEmailEValidar(email);

                                    if (emailCorrigido !== false) {

                                        email.value = emailCorrigido;
                                        succesValidation(email);

                                        if (!isEmpty(senha)){
                                            var senhaCorrigida = corrigirSenhaEValidar(senha);

                                            if (senhaCorrigida !== false){

                                                senha.value = senhaCorrigida;
                                                succesValidation(senha);

                                            }
                                        }

                                    }
                                }
                            }
                        }

                    }
                }
            }

        }
    }

    function errorValidation(input, message) {
        var formControl = input.parentElement;
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
            } else {
                console.log("ELEMENTO SMALL NÃO ENCONTRADO");
            }
        } else {
            console.log("FORM CONTROL NÃO ENCONTRADO");
        }
    }

    function succesValidation(input) {
        const formControl = input.parentElement;
        if (formControl) {
            const i = formControl.parentElement.querySelector('i');
            if (i) {
                const img = i.querySelector('img');
                if (img) {
                    const small = formControl.parentElement.querySelector('small');
                    if (small) {
                        small.style.visibility = "hidden";
                        i.style.visibility = "visible";
                        img.src = "images/success-icon.svg";
                        formControl.className = "form-control success";
                    }
                } else {
                    console.log("ELEMENTO IMG NAO ENCONTRADO")
                }
            } else {
                console.log("ELEMENTO I NAO ENCONTRADO LINHA 72");
            }
        } else {
            console.log("FORM CONTROL NAO ENCONTRADO LINHA 70");
        }
    }

    function isEmpty(input) {
        if (input.value.trim() === "") {
            errorValidation(input, "Preencha esse campo!");
            return true;
        }
        return false;
    }

    function corrigirNomeEValidar(input) {
        // Verifica se a string possui apenas letras (maiúsculas e minúsculas) com acentos
        let regex = /^[a-zA-ZÀ-ÖØ-öø-ÿ]+(?:\s[a-zA-ZÀ-ÖØ-öø-ÿ]+)*$/;

        // Remove espaços em branco extras
        let stringCorrigida = input.value.trim();
        if (!regex.test(stringCorrigida)) {
            errorValidation(input, "Insira apenas letras!");
            return false;
        }
        return stringCorrigida;
    }

    function corrigirEmailEValidar(input) {
        const regexEmail = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

        let emailCorrigido = input.value.trim();
        if (!regexEmail.test(emailCorrigido)) {
            errorValidation(input, "Insira um email válido!");
            return false;
        }
        return emailCorrigido;
    }

    function corrigirNomeUsuarioEValidar(input) {
        const nomeUsuarioCorrigido = input.value.trim();
        const comprimento = nomeUsuarioCorrigido.length;
        const comprimentoValido = comprimento >= 3 && comprimento <= 20;
        const regexNomeUsuario = /^[a-zA-Z0-9_]+$/;
        if (comprimentoValido) {
            if (regexNomeUsuario.test(nomeUsuarioCorrigido)) {
                return nomeUsuarioCorrigido;
            }else{
                errorValidation(input, "Insira um email válido!");
            }
        } else {
            let mensagem = "";
            if (comprimento < 3) {
                mensagem = "Nome de usúario muito pequeno!";
            } else {
                mensagem = "Nome de usuário muito grande!";
            }
            errorValidation(input, mensagem + " Insira de 3 a 8 caracteres!");
        }
        return false;
    }
    function corrigirSenhaEValidar(input){
        const senhaCorrigida = input.value.trim();
        const comprimentoSenha = senhaCorrigida.length;
        const comprimentoValido = comprimentoSenha >= 8 && comprimentoSenha <=32;
        const regexSenha = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&]).+$/;
        if (comprimentoValido){
            if (regexSenha.test(senhaCorrigida)){
                return senhaCorrigida;
            }
            else {
                errorValidation(input, "A sua senha deve conter pelo menos:\n1 letra minúscula\n1 letra maiúscula\n1 número\n1 caractere especial. Ex.: @#$%");
            }
        }else {
            errorValidation(input, "A senha deve ter entre 8 a 32 caracteres!");
        }
        return false;
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
*/