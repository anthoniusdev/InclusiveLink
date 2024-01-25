document.addEventListener("DOMContentLoaded", async function () {

    const form = document.getElementById("formLogin");
    const nomeUsuario = document.getElementById("username");
    const senha = document.getElementById("password");
    const buttonLogin = document.querySelector("button[id='entrar']");
    const buttonCadastrar = document.querySelector("button[id='cadastrar']");
    const spanLinha = document.getElementById("linha");

    await verificarLogin();

    buttonLogin.addEventListener("click", async (event) => {
        event.preventDefault();
        if (await checkInput()) {
            await realizarLogin();
        }
    })
    buttonCadastrar.addEventListener("click", async (event) => {
        event.preventDefault();
        window.location.href = "RealizarCadastro.jsp";
    })

    async function realizarLogin() {
        const loginValido = await verificarLogin();

        if (loginValido) {
            clearErrors();
            form.submit();
        }
    }

    async function verificarLogin() {
        let urlParametros = new URLSearchParams(window.location.search);
        let erroParam = urlParametros.get('erro');

        if (erroParam === '1') {
            errorValidation(nomeUsuario, "Nome de usuário ou senha incorretos!");
            errorValidation(senha, "Nome de usuário ou senha incorretos!");
            urlParametros.delete('erro');
            window.history.replaceState({}, document.title, "?" + urlParametros.toString());

            return false;
        }

        return true;
    }


    async function checkInput() {
        return !isEmpty(nomeUsuario) && !isEmpty(senha);

    }

    function isEmpty(input) {
        if (input.value.trim() === "") {
            errorValidation(input, "Preencha todos os campos!");
            return true;
        }
        return false;
    }

    function errorValidation(input, mensagem) {
        const formControl = input.parentElement.parentElement;
        if (formControl) {
            const small = formControl.querySelector("small");
            if (small) {
                small.style.display = "flex";
                small.style.justifyContent = "center";
                small.innerText = mensagem;
                small.style.visibility = "visible";
                small.style.color = "#782121";
                formControl.className = "form-control error";
                formControl.style.marginTop = "25px";
                form.style.paddingBottom = "20px";
                spanLinha.style.marginTop = "11px";
                spanLinha.style.position = "relative";
            } else {
                console.log("ERRO NO SMALL")
            }
        } else {
            console.log("ERRO NO FORMCONTROL")
        }
    }
    function clearErrors(){
        const formControls = document.querySelectorAll(".form-control");
        formControls.forEach((formControl) =>{
            formControl.classList.remove("error");
            const small = formControl.querySelector("small");
            if (small){
                small.innerText = "";
                small.display = "none";
            }
        })
    }
})