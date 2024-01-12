package com.ononline.RunTheBankChallenge.Data.Entities;

import com.ononline.RunTheBankChallenge.Data.Ids.ContaId;
import lombok.*;

/**
 * Representa uma versão simplificada da entidade Transacao, para permitir chamadas simples da API de transações.
 * Cada instância desta classe corresponde a uma transação com informações associadas.
 */

@Data
@NoArgsConstructor
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
}
