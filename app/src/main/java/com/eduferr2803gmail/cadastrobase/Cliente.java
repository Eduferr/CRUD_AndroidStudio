package com.eduferr2803gmail.cadastrobase;

/**
 * Created by Eduferr on 08/06/2017.
 */

public class Cliente {

    int codigo;
    String nome;
    String telefone;
    String celular;
    String email;

    //Método Construtor vazio para ser usado com instância
    public Cliente() {

    }

    //Método Construtor para update
    public Cliente(int _codigo, String _nome, String _telefone, String _celular, String _email) {
        this.codigo = _codigo;
        this.nome = _nome;
        this.telefone = _telefone;
        this.celular = _celular;
        this.email = _email;
    }

    //Método Construtor para fazer o Insert (não á necessidade do parametro do Código)
    public Cliente(String _nome, String _telefone, String _celular, String _email) {
        this.nome = _nome;
        this.telefone = _telefone;
        this.celular = _celular;
        this.email = _email;
    }

    // ======================Get & Set======================

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
