let usuarioAutenticado;
obterUsuarioAutenticado().then(function (usuario) {
    usuarioAutenticado = usuario;
}).catch(function (error) {
    console.log(error);
});
let carregando = false;
document.addEventListener("DOMContentLoaded", function (){
    carregarPublicacoes("perfil");
    $(window).scroll(function () {
        if (!carregando && $(window).scrollTop() + $(window).height() >= $(document).height() - 100) {
            carregando = true;
            carregarPublicacoes("perfil");
        }
    });
})