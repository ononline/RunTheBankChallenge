package com.ononline.RunTheBankChallenge.Data.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

/**
 * Representa a entidade Transacao, que registra transações financeiras entre contas.
 * Cada instância desta classe corresponde a uma transação com informações associadas.
 */
@Entity
public class Transacao {
    
    /**
     * Identificador único da transação no banco de dados.
     */
    @Id
    @GeneratedValue
    @JsonProperty
    private long id;
    
    /**
     * Conta de origem da transação.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "contaOrigemId", referencedColumnName = "id"),
            @JoinColumn(name = "contaOrigemAgencia", referencedColumnName = "agencia")
    })
    @JsonProperty
    private Conta contaOrigem;
    
    /**
     * Conta de destino da transação.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "contaDestinoId", referencedColumnName = "id"),
            @JoinColumn(name = "contaDestinoAgencia", referencedColumnName = "agencia")
    })
    @JsonProperty
    private Conta contaDestino;
    
    /**
     * Valor da transação.
     */
    @JsonProperty
    private float valor;
    
    /**
     * Indica se a transação foi revertida.
     */
    @JsonProperty
    private boolean revertido;
    
    /**
     * Observações adicionais sobre a transação.
     */
    @JsonProperty
    private String observacoes;
    
    public Transacao(long id, Conta contaOrigem, Conta contaDestino, float valor, boolean revertido, String observacoes) {
        this.id = id;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.valor = valor;
        this.revertido = revertido;
        this.observacoes = observacoes;
    }
    
    public Transacao() {
    }
    
    public long getId() {
        return this.id;
    }
    
    public Conta getContaOrigem() {
        return this.contaOrigem;
    }
    
    public Conta getContaDestino() {
        return this.contaDestino;
    }
    
    public float getValor() {
        return this.valor;
    }
    
    public boolean isRevertido() {
        return this.revertido;
    }
    
    public String getObservacoes() {
        return this.observacoes;
    }
    
    @JsonProperty
    public void setId(long id) {
        this.id = id;
    }
    
    @JsonProperty
    public void setContaOrigem(Conta contaOrigem) {
        this.contaOrigem = contaOrigem;
    }
    
    @JsonProperty
    public void setContaDestino(Conta contaDestino) {
        this.contaDestino = contaDestino;
    }
    
    @JsonProperty
    public void setValor(float valor) {
        this.valor = valor;
    }
    
    @JsonProperty
    public void setRevertido(boolean revertido) {
        this.revertido = revertido;
    }
    
    @JsonProperty
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
