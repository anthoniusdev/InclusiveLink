    <%@ page language="java" contentType="text/html; charset=UTF-8"
             pageEncoding="UTF-8" %>
    <%@ page import="java.util.Calendar" %>
    <html lang="pt-BR">
    <head>
        <title>Inclusive Link</title>
        <link rel="stylesheet" href="styles/RealizarCadastro.css">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <script src="scripts/realizarCadastro.js"></script>
        <meta charset="UTF-8">
    </head>
    <body>
    <div class="container">
        <form id="formCadastro" action="Cadastrar" method="post" name="frmCadastro">
            <div class="lado-a-lado">
                <div class="form-control">
                    <label for="nome">
                        <input type="text" placeholder="Nome" name="nome" id="nome">
                    </label>
                    <i><img src="" alt=""></i>
                    <small>Error messager</small>
                </div>
                <div class="form-control">
                    <label for="sobrenome">
                        <input type="text" placeholder="Sobrenome" name="sobrenome" id="sobrenome">
                    </label>
                    <i><img src="" alt=""></i>
                    <small>Error messager</small>
                </div>
            </div>
            <div class="form-control">
                <label for="nome_usuario">
                    <input type="text" placeholder="Nome de usuário" name="nome_usuario" id="nome_usuario">
                </label>
                <i><img src="" alt=""></i>
                <small>Error messager</small>
            </div>
            <div class="form-control">
                <label for="email">
                    <input type="email" placeholder="Email" name="email" id="email">
                </label>
                <i><img src="" alt=""></i>
                <small>Error messager</small>
            </div>
            <div class="form-control">
                <label for="senha">
                    <input type="password" placeholder="Senha" name="senha" id="senha">
                </label>
                <i><img src="" alt=""></i>
                <small>Error messager</small>
            </div>
            <div class="form-control">
                <label for="senha_repetida">
                    <input type="password" placeholder="Repetir Senha" name="senha_repetida" id="senha_repetida">
                </label>
                <i><img src="" alt=""></i>
                <small>Error messager</small>
            </div>
            <div class="data_nascimento">
                <label>
                    <small>Data de nascimento</small>
                    <div class="seletores">
                        <div class="form-control">
                            <select id="dia" name="dia" required>
                                <option value="" disabled selected>Dia</option>
                            </select>
                        </div>
                        <div class="form-control">
                            <select id="mes" name="mes" required>
                                <option value="" disabled selected>Mês</option>
                            </select>
                        </div>
                        <div class="form-control">
                            <select id="ano" name="ano" required>
                                <option value="" disabled selected>Ano</option>
                            </select>
                        </div>
                    </div>
                </label>
            </div>
            <div class="btnCadastrar">
                <label>
                    <button type="submit">Cadastrar</button>
                </label>
            </div>
        </form>
    </div>
    <footer>
        <div class="LOGO-E-NOMES">
            <div class="Logo-Nome-IL">
                <div class="bola-azul">
                    <img src="images/logo_InclusiveLink-ruim.png" alt="Logo InclusiveLink">
                </div>
                <h1>Inclusive Link</h1>
            </div>
            <div class="Nomes">
                <h3>Abimael Lima</h3>
                <h3>Anthonius Figueiredo</h3>
                <h3>Igor Souza</h3>
            </div>
        </div>
    </footer>
    </body>
    </html>
