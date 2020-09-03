package com.br.msscssm.repository;

import com.br.msscssm.domain.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PropostaRepository extends JpaRepository<Proposta, UUID> {
}
