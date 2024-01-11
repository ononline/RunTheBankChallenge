package com.ononline.RunTheBankChallenge.Data.Ids;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ContaId implements Serializable {

    private long id;
    private int agencia;
}
