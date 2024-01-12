package com.ononline.RunTheBankChallenge.Data.Entities;

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
    private long id;
    
    /**
     * Conta de origem da transação.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name="contaOrigemId", referencedColumnName = "id"),
            @JoinColumn(name="contaOrigemAgencia", referencedColumnName = "agencia")
    })
    private Conta contaOrigem;
    
    /**
     * Conta de destino da transação.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name="contaDestinoId", referencedColumnName = "id"),
            @JoinColumn(name="contaDestinoAgencia", referencedColumnName = "agencia")
    })
    private Conta contaDestino;
    
    /**
     * Valor da transação.
     */
    private float valor;
    
    /**
     * Indica se a transação foi revertida.
     */
    private boolean revertido;
    
    /**
     * Observações adicionais sobre a transação.
     */
    private String observacoes;
}
