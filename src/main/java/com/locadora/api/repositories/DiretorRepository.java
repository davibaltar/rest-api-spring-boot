package com.locadora.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.locadora.api.entities.Diretor;

public interface DiretorRepository extends JpaRepository<Diretor, Integer> {

	Diretor findByNome(String nomeDiretor);

}
