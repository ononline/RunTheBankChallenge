package com.ononline.RunTheBankChallenge.Data.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
public class Transacao {
    @Id private long id;
    private long contaOrigemId;
    private long contaDestinoId;
    private float valor;
    private boolean revertido;
    private String observacoes;
}
