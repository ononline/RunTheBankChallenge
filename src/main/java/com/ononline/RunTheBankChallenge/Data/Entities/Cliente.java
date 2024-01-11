package com.ononline.RunTheBankChallenge.Data.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
public class Cliente {
    @Id private Long id;
    private String nome;
    private String cpfCnpj;
    private String endereco;
    private String senha; //TODO: Incluir segurança
}
