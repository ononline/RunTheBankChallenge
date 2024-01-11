package com.ononline.RunTheBankChallenge.Data.Repositories;

import com.ononline.RunTheBankChallenge.Data.Entities.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
