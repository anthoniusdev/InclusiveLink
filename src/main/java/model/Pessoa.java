package model;

import dao.PessoaDAO;

public class Pessoa {
    private int idPessoa;
    private String nome;
    private String dataNascimento;
    private String email;
    private String senha;

    public Pessoa(int idPessoa, String nome, String dataNascimento, String email, String senha) {
        this.idPessoa = idPessoa;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.senha = senha;
    }

    public Pessoa(String nome, String dataNascimento, String email, String senha) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.senha = senha;
    }

    public Pessoa(){}

    public Pessoa(int idPessoa) {
        Pessoa pessoaEncontrada = new PessoaDAO().retornaPessoa(idPessoa);
        this.idPessoa = pessoaEncontrada.getIdPessoa();
        this.nome = pessoaEncontrada.getNome();
        this.dataNascimento = pessoaEncontrada.getDataNascimento();
        this.email = pessoaEncontrada.getEmail();
        this.senha = pessoaEncontrada.getSenha();
    }

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    public boolean isEmailUnique(String email){
        return new PessoaDAO().isEmailUnique(email);
    }

}
