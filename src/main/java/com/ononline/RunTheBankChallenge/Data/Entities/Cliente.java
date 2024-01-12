package com.ononline.RunTheBankChallenge.Data.Entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Representa a entidade Cliente, que é armazenada no banco de dados.
 * Cada instância desta classe corresponde a um cliente com informações associadas.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    
    /**
     * Identificador único do cliente no banco de dados.
     */
    @Id @GeneratedValue
    private Long id;
    
    /**
     * Nome do cliente.
     */
    private String nome;
    
    /**
     * CPF ou CNPJ do cliente.
     */
    @Column(unique = true)
    private String cpfCnpj;
    
    /**
     * Endereço do cliente.
     */
    private String endereco;
    
    /**
     * Senha associada ao cliente.
     */
    private String senha;
}
