function abrirOpcoes() {
    var menu = document.getElementById("contextMenu");
    if (menu.style.display === "none" || menu.style.display === "") {
        menu.style.display = "block";
    } else {
        menu.style.display = "none";
    }
}

document.addEventListener('click', function(event) {
    var contextMenu = document.getElementById("contextMenu");
    if (event.target !== contextMenu && event.target.parentNode !== contextMenu) {
        contextMenu.style.display = "none";
    }
});