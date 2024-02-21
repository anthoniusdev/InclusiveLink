# InclusiveLink: Plataforma de comunidade para responsáveis de crianças com necessidades específicas

Este projeto é uma aplicação Java EE que segue a metodologia de Programação Orientada a Objetos (POO) e utiliza tecnologias como JSP (Java Server Pages), MVC (Model View Controller), e DAO (Data Access Object) para implementar operações CRUD (Create, Read, Update, Delete) em um banco de dados MySQL.

## Configuração do Ambiente

### Pré-requisitos
Certifique-se de ter o seguinte instalado em seu ambiente de desenvolvimento:

- **Java JDK**: Baixe e instale o [Java SE Development Kit](https://www.oracle.com/br/java/technologies/downloads/).
- **Apache Tomcat**: Faça o download e instalação do [Apache Tomcat](https://tomcat.apache.org/).
- **MySQL Server**: Instale o MySQL Server e execute o script SQL fornecido em `/scripts` para criar o banco de dados e tabelas necessários.

### Configuração do Banco de Dados
Execute o script SQL fornecido em `/scripts` para criar o banco de dados e tabelas necessários.

## Compilação e Implantação

1. Baixe o arquivo .zip
2. Faça a extração do arquivo .zip

## Inicialização

1. Configure sua IDE de preferência de maneira convencional para executar o projeto.
2. Instale e configure o Apache TomCat.
3. Execute o projeto na IDE.
4. Acesse a aplicação no navegador utilizando o endereço fornecido pelo servidor Tomcat.

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

## Importante
Certifique-se de ter o Java JDK, Apache Tomcat e MySQL Server configurados corretamente antes de iniciar o projeto. Consulte a documentação específica dessas ferramentas para obter ajuda na configuração.
