package com.ononline.RunTheBankChallenge.Data.Repositories;

import com.ononline.RunTheBankChallenge.Data.Entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
