package com.ononline.RunTheBankChallenge.Data.Entities;

import com.ononline.RunTheBankChallenge.Data.Ids.ContaId;
import jakarta.persistence.*;
import lombok.*;

/**
 * Representa a entidade Conta, que é armazenada no banco de dados.
 * Cada instância desta classe corresponde a uma conta bancária com informações associadas.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    
    /**
     * Realiza um débito na conta, subtraindo o valor especificado do saldo.
     * @param value O valor a ser debitado da conta.
     */
    public void debit(float value){
        this.saldo -= value;
    }
    
    /**
     * Realiza um crédito na conta, adicionando o valor especificado ao saldo.
     * @param value O valor a ser creditado na conta.
     */
    public void credit(float value){
        this.saldo += value;
    }
    
}
