package com.ononline.RunTheBankChallenge.Data.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * Representa a entidade Cliente, que é armazenada no banco de dados.
 * Cada instância desta classe corresponde a um cliente com informações associadas.
 */
@Entity
public class Cliente {
    
    /**
     * Identificador único do cliente no banco de dados.
     */
    @Id
    @GeneratedValue
    private Long id;
    
    /**
     * Nome do cliente.
     */
    private String nome;
    
    /**
     * CPF ou CNPJ do cliente.
     */
    @Column(unique = true)
    private String cpfCnpj;
    
    /**
     * Endereço do cliente.
     */
    private String endereco;
    
    /**
     * Senha associada ao cliente.
     */
    private String senha;
    
    public Cliente(Long id, String nome, String cpfCnpj, String endereco, String senha) {
        this.id = id;
        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
        this.endereco = endereco;
        this.senha = senha;
    }
    
    public Cliente() {
    }
    
    public Long getId() {
        return this.id;
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public String getCpfCnpj() {
        return this.cpfCnpj;
    }
    
    public String getEndereco() {
        return this.endereco;
    }
    
    public String getSenha() {
        return this.senha;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }
}
