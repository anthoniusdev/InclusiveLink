function realizarCadastro() {
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
}