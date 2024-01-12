package com.ononline.RunTheBankChallenge.Data.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

/**
 * Representa a entidade Transacao, que registra transações financeiras entre contas.
 * Cada instância desta classe corresponde a uma transação com informações associadas.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
            @JoinColumn(name="contaOrigemId", referencedColumnName = "id"),
            @JoinColumn(name="contaOrigemAgencia", referencedColumnName = "agencia")
    })
    @JsonProperty
    private Conta contaOrigem;
    
    /**
     * Conta de destino da transação.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name="contaDestinoId", referencedColumnName = "id"),
            @JoinColumn(name="contaDestinoAgencia", referencedColumnName = "agencia")
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
}
