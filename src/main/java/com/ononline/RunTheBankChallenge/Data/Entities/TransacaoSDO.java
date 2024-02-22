package com.ononline.RunTheBankChallenge.Data.Entities;

import com.ononline.RunTheBankChallenge.Data.Ids.ContaId;

/**
 * Representa uma versão simplificada da entidade Transacao, para permitir chamadas simples da API de transações.
 * Cada instância desta classe corresponde a uma transação com informações associadas.
 */

public class TransacaoSDO {
    
    /**
     * Conta de origem da transação.
     */
    private ContaId contaOrigem;
    
    /**
     * Conta de destino da transação.
     */
    private ContaId contaDestino;
    
    /**
     * Valor da transação.
     */
    private float valor;
    
    public TransacaoSDO() {
    }
    
    public ContaId getContaOrigem() {
        return this.contaOrigem;
    }
    
    public ContaId getContaDestino() {
        return this.contaDestino;
    }
    
    public float getValor() {
        return this.valor;
    }
    
    public void setContaOrigem(ContaId contaOrigem) {
        this.contaOrigem = contaOrigem;
    }
    
    public void setContaDestino(ContaId contaDestino) {
        this.contaDestino = contaDestino;
    }
    
    public void setValor(float valor) {
        this.valor = valor;
    }
}
