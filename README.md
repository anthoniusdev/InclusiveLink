<img src="https://i.ibb.co/7V0btfH/IL-LOGO-login.png" alt="IL-LOGO-login" border="0" />

# InclusiveLink: Plataforma de comunidade para responsáveis de crianças com necessidades específicas

Este projeto é uma aplicação Java EE que segue a metodologia de Programação Orientada a Objetos (POO) e utiliza tecnologias como JSP (Java Server Pages), MVC (Model View Controller), e DAO (Data Access Object) para implementar operações CRUD (Create, Read, Update, Delete) em um banco de dados MySQL.

## Dependências do Maven

O projeto utiliza o Maven para gerenciar suas dependências. Certifique-se de ter uma conexão com a internet durante a primeira compilação para que o Maven baixe automaticamente as dependências necessárias. Abaixo estão as principais dependências especificadas no arquivo `pom.xml`:

- Servlet API 3.1.0
- JBCrypt 0.4
- Jakarta EE API 8.0.0
- Gson 2.10.1
- Jackson Databind 2.14.0-rc1
- Commons IO 2.15.0
- Commons FileUpload 1.5
- JSON 20231013
- MySQL Connector/J 8.0.33

## Informações referente ao `Structure Project`
### SDK:
- Oracle OpenJDK version 21
### Language Level:
- SDK default  

# PASSO A PASSO
## 1. Configuração do Ambiente

### 1.1 Pré-requisitos
Certifique-se de ter o seguinte instalado em seu ambiente de desenvolvimento:

- **Java JDK**: Baixe e instale o [Java SE Development Kit](https://www.oracle.com/br/java/technologies/downloads/).
- **Apache Tomcat**: Faça o download e instalação do [Apache Tomcat](https://tomcat.apache.org/).
- **MySQL Server**: Instale o [MySQL](https://www.mysql.com/downloads/).
- **Intellij Idea**: Faça o download e instale o [Intellij Idea](https://www.jetbrains.com/idea/download/) (Pode ser a Community ou a Professional).

### 1.2 Configuração do Banco de Dados
- Execute o script SQL fornecido em `/scripts` para criar o banco de dados e tabelas necessários.

### 1.3 Abrir projeto no Intellij
1. Clique em `File` > `Open` e selecione o diretorio do projeto
2. Selecione a opção de `Maven Project`

### 1.4 Configurar Servidor
- Configure o servidor editando o Run/Debug configurations, provavelmente, na primeira vez que estiver usando o projeto, vai aparecer um `Current File` no lugar de executar, lá você pode configurar o servidor.
- Caso esteja com dúvidas [assista este vídeo](https://www.youtube.com/watch?v=LKK4OKSfmDU).
### 1.5 Alteração de classes
- Mude as credenciais do banco de dados na classe **Conexao** `/src/main/java/dao/Conexao.java`. 
- Mude a URL do caminho que leva até o `/webapp` no seu computador, faça isso na classe **ObterURL** `/src/main/java/util/ObterURL.java`.
## 2.0 Executar projeto
1. Após configurar o ambiente, execute o projeto.
2. Copie o link que o TomCat gerou e cole no navegador, caso você não tenha mudado nenhuma configuração, por padrão, este é o link: ´http://localhost:8080/InclusiveLink´. Porém, pegue o link gerado pelo TomCat. 

Caso tenha dado algum erro, o problema pode ser por que você não alterou as classes ditas anteriormente ou, tem algum problema referente as importações das classes, ou até mesmo seja algum problema referente a versão do JDK que você está usando, o Intellij apontará o erro, caso o problema seja mesmo uma dessas opções.

Caso tenha dado algum erro desconhecido, nos informe via email: 
- `anthoniusmiguel@gmail.com`
- `igorsouza1771marketing@gmail.com`
- `abimael034@gmail.com`

# Observações
- Embora seja fortemente recomendado o uso do Intellij Idea, nós também testamos e comprovamos que outras IDEs do mercado, como o `Eclipse IDE`, também conseguem executar o projeto sem problemas.
## Importante

