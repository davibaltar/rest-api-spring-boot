package com.locadora.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.locadora.api.entities.Sexo;

public interface SexoRepository extends JpaRepository<Sexo, Integer> {

	Sexo findByTipo(String tipo);

}
