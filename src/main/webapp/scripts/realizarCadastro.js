document.addEventListener("DOMContentLoaded", function () {
        console.log("Script carregado com sucesso.");
        preencherSelectData();
        const form = document.getElementById("formCadastro");
        const nome = document.getElementById("nome");
        const sobrenome = document.getElementById("sobrenome");
        const nomeUsuario = document.getElementById("nome_usuario");
        const email = document.getElementById("email");
        const senha = document.getElementById("senha");
        const senhaRepetida = document.getElementById("senha_repetida");

        form.addEventListener("submit", async (event) => {
            event.preventDefault();
            if (await checkInput()) {
                form.submit();
            }
        });

        async function checkInput() {
            return !!(await validarNome() && await validarSobrenome() && await validarNomeUsuario() && await validarEmail() && await validarSenha() && await validarSenhaRepetida());

        }

        async function validarNome() {
            if (!isEmpty(nome)) {
                const nomeCorrigido = corrigirNomeEValidar(nome);
                if (nomeCorrigido !== false) {
                    nome.value = nomeCorrigido;
                    succesValidation(nome);
                    return true;
                }
            }
            return false;
        }

        async function validarSobrenome() {
            if (!isEmpty(sobrenome)) {

                const sobrenomeCorrigido = corrigirNomeEValidar(sobrenome);

                if (sobrenomeCorrigido !== false) {

                    sobrenome.value = sobrenomeCorrigido;
                    succesValidation(sobrenome);
                    return true;
                }
            }
            return false;
        }

        async function validarNomeUsuario() {
            if (!isEmpty(nomeUsuario)) {
                const nomeUsuarioCorrigido = corrigirNomeUsuarioEValidar(nomeUsuario);

                if (nomeUsuarioCorrigido !== false) {
                    if (await verificarUnicidade(nomeUsuario, nomeUsuarioCorrigido, "nomeUsuario")) {
                        nomeUsuario.value = nomeUsuarioCorrigido;
                        succesValidation(nomeUsuario);
                        return true;
                    }
                }
            }
            return false;
        }

        async function validarEmail() {
            if (!isEmpty(email)) {
                const emailCorrigido = corrigirEmailEValidar(email);
                if (emailCorrigido !== false) {
                    if (await verificarUnicidade(email, emailCorrigido, "email")) {
                        email.value = emailCorrigido;
                        succesValidation(email);
                        return true;
                    }
                }
            }
            return false;
        }

        async function validarSenha() {
            if (!isEmpty(senha)) {
                const senhaCorrigida = corrigirSenhaEValidar(senha);
                if (senhaCorrigida !== false) {
                    senha.value = senhaCorrigida;
                    succesValidation(senha);
                    return true;
                }
            }
            return false;
        }

        async function validarSenhaRepetida() {
            if (!isEmpty(senhaRepetida)) {
                const senhaRepetidaCorrigida = verificarSenhas(senha.value, senhaRepetida);
                if (senhaRepetidaCorrigida !== false) {
                    senhaRepetida.value = senhaRepetidaCorrigida;
                    succesValidation(senhaRepetida);
                    return true;
                }
            }
            return false;
        }

        async function verificarUnicidade(input, valor, nomeInput) {
            try {
                let complementoMensagem;
                const response = await fetch("verificarUnicidade", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",
                    },
                    body: `tipo=${nomeInput}&valor=${encodeURIComponent(valor)}`,
                });
                if (response.ok) {
                    const data = await response.json();
                    if (!data.unico) {
                        complementoMensagem = nomeInput === "nomeUsuario" ? "Nome de usuário" : "Email";
                        errorValidation(input, `${complementoMensagem} já existe. Escolha outro!`);
                    } else {
                        succesValidation(input);
                        return true;
                    }
                }
            } catch (error) {
                console.error(error.message);
                alert("Erro na verificação de unicidade. Tente novamente mais tarde.");
            }
            return false;
        }

        function errorValidation(input, message) {
            const formControl = input.parentElement;
            const senhaFormControl = document.querySelector('.form-control label[for="senha"]');
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
                            if (senhaFormControl === formControl) {
                                img.style.paddingBottom = "75px";
                            } else {
                                img.style.paddingBottom = "0px";
                            }
                            small.style.justifyContent = "center";
                            small.innerText = message;
                            formControl.className = 'form-control error';
                            small.style.visibility = "visible";
                            small.style.color = "#782121";
                            input.focus();
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
                            img.style.paddingBottom = "0";
                            img.style.bottom = "20px";
                            small.style.display = "flex";
                            small.style.height = "20px";
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
                } else {
                    errorValidation(input, "Insira um email válido!");
                }
            } else {
                let mensagem;
                if (comprimento < 3) {
                    mensagem = "Nome de usúario muito pequeno!";
                } else {
                    mensagem = "Nome de usuário muito grande!";
                }
                errorValidation(input, mensagem + " Insira de 3 a 8 caracteres!");
            }
            return false;
        }

        function corrigirSenhaEValidar(input) {
            const senhaCorrigida = input.value.trim();
            const comprimentoSenha = senhaCorrigida.length;
            const comprimentoValido = comprimentoSenha >= 8 && comprimentoSenha <= 32;
            const regexSenha = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$#!%*?&]).+$/;
            if (comprimentoValido) {
                if (regexSenha.test(senhaCorrigida)) {
                    return senhaCorrigida;
                } else {
                    errorValidation(input, "A sua senha deve conter pelo menos:\n1 letra minúscula\n1 letra maiúscula\n1 número\n1 caractere especial. Ex.: @#$%");
                }
            } else {
                errorValidation(input, "A senha deve ter entre 8 a 32 caracteres!");
            }
            return false;
        }

        function verificarSenhas(senhaCorrigida, input) {
            const senhaRepetidaCorrigida = input.value.trim();
            if (senhaRepetidaCorrigida === senhaCorrigida) {
                return senhaRepetidaCorrigida;
            } else {
                errorValidation(input, "As senhas diferem!");
                return false;
            }
        }

        function anoBissexto(ano) {
            ano = parseInt(ano);
            return (ano % 4 === 0 && ano % 100 !== 0) || (ano % 400 === 0);
        }

        function preencherSelectData() {
            let selectDia = document.getElementById('dia');
            let selectMes = document.getElementById('mes');
            let selectAno = document.getElementById('ano');

            // Adiciona eventListeners para os eventos change nos selects de mês e ano
            selectMes.addEventListener('change', atualizarDias);
            selectAno.addEventListener('change', atualizarDias);
            let anoAtual = new Date().getFullYear();
            let anoMinimo = anoAtual - 120;

            // Preenche as opções do ano
            for (let ano = anoAtual - 10; ano >= anoMinimo; ano--) {
                let optionAno = document.createElement('option');
                optionAno.value = ano.toString();
                optionAno.text = ano.toString();
                selectAno.appendChild(optionAno);
            }

            // Preenche as opções do mês
            var meses = ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'];
            for (let i = 0; i < meses.length; i++) {
                let optionMes = document.createElement('option');
                optionMes.value = meses[i]; // Mês é base 1 no JavaScript
                optionMes.text = meses[i];
                selectMes.appendChild(optionMes);
            }

            // Preenche as opções do dia (considerando meses com 31 dias)
            for (let dia = 1; dia <= 31; dia++) {
                let optionDia = document.createElement('option');
                optionDia.value = dia.toString();
                optionDia.text = dia.toString();
                selectDia.appendChild(optionDia);
            }

        }

        function atualizarDias() {
            let selectDia = document.getElementById('dia');
            let selectMes = document.getElementById('mes');
            let selectAno = document.getElementById('ano');
            let mesSelecionado = selectMes.value;
            let anoSelecionado = selectAno.value;
            let meses = ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'];
            mesSelecionado = meses.indexOf(mesSelecionado);

            // Obtém o valor atualmente selecionado para o dia
            let diaSelecionado = parseInt(selectDia.value);

            // Adiciona a opção padrão para dia
            const optionPadrao = document.createElement('option');
            optionPadrao.value = '';
            optionPadrao.text = 'Dia';
            selectDia.innerHTML = ''; // Limpa as opções atuais

            selectDia.add(optionPadrao);

            // Obtém o número de dias no mês selecionado
            let diasNoMes;
            if (mesSelecionado === 1) {
                // Fevereiro
                diasNoMes = anoBissexto(anoSelecionado) ? 29 : 28;
            } else if ([3, 5, 8, 10].includes(mesSelecionado)) {
                // Meses com 30 dias
                diasNoMes = 30;
            } else {
                // Meses com 31 dias
                diasNoMes = 31;
            }

            // Preenche as opções do select de dia com base no número de dias no mês
            for (let dia = 1; dia <= diasNoMes; dia++) {
                let optionDia = document.createElement('option');
                optionDia.value = dia.toString();
                optionDia.text = dia.toString();
                selectDia.add(optionDia);

                // Mantém o dia selecionado se for válido, caso contrário, seleciona a opção padrão
                if (dia === diaSelecionado) {
                    optionDia.selected = true;
                }
            }

            // Se o dia selecionado não for válido para o mês atual, redefina para a opção padrão
            if (diaSelecionado > diasNoMes) {
                optionPadrao.selected = true;
            }
        }
    }
<<<<<<< HEAD
)
=======
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
>>>>>>> 8356736e332ecca35c56d2a8b36037b997168230
