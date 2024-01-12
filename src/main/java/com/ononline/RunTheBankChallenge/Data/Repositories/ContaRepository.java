package com.ononline.RunTheBankChallenge.Data.Repositories;

import com.ononline.RunTheBankChallenge.Data.Entities.Conta;
import com.ononline.RunTheBankChallenge.Data.Ids.ContaId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, ContaId> {
}
