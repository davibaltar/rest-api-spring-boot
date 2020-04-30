package com.locadora.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.locadora.api.entities.Locacao;

public interface LocacaoRepository extends JpaRepository<Locacao, Integer> {

}
