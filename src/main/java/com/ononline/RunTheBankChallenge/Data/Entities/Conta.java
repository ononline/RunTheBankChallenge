package com.ononline.RunTheBankChallenge.Data.Entities;

import com.ononline.RunTheBankChallenge.Data.Ids.ContaId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@IdClass(ContaId.class)
public class Conta {
    @Id private long id;
    private long clienteId;
    @Id private int agencia;
    private float saldo;
    private boolean status;

}
