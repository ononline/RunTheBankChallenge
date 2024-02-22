package com.ononline.RunTheBankChallenge.Data.Entities;

import com.ononline.RunTheBankChallenge.Data.Ids.ContaId;
import jakarta.persistence.*;

/**
 * Representa a entidade Conta, que é armazenada no banco de dados.
 * Cada instância desta classe corresponde a uma conta bancária com informações associadas.
 */
@Entity
@IdClass(ContaId.class)
public class Conta {
    /**
     * Identificador único da conta no banco de dados.
     */
    @Id
    @GeneratedValue
    private long id;
    
    /**
     * Cliente associado a esta conta.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clienteId")
    private Cliente cliente;
    
    /**
     * Número da agência da conta.
     */
    @Id
    private int agencia;
    
    /**
     * Saldo atual da conta.
     */
    private float saldo;
    
    /**
     * Status da conta, indicando se está ativa ou não.
     */
    private boolean status;
    
    public Conta(long id, Cliente cliente, int agencia, float saldo, boolean status) {
        this.id = id;
        this.cliente = cliente;
        this.agencia = agencia;
        this.saldo = saldo;
        this.status = status;
    }
    
    public Conta() {
    }
    
    /**
     * Realiza um débito na conta, subtraindo o valor especificado do saldo.
     *
     * @param value O valor a ser debitado da conta.
     */
    public void debit(float value) {
        this.saldo -= value;
    }
    
    /**
     * Realiza um crédito na conta, adicionando o valor especificado ao saldo.
     *
     * @param value O valor a ser creditado na conta.
     */
    public void credit(float value) {
        this.saldo += value;
    }
    
    public long getId() {
        return this.id;
    }
    
    public Cliente getCliente() {
        return this.cliente;
    }
    
    public int getAgencia() {
        return this.agencia;
    }
    
    public float getSaldo() {
        return this.saldo;
    }
    
    public boolean isStatus() {
        return this.status;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }
    
    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }
    
    public void setStatus(boolean status) {
        this.status = status;
    }
}
