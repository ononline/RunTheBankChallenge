package com.ononline.RunTheBankChallenge.Data.Ids;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class ContaId implements Serializable {

    private long id;
    private int agencia;
}
