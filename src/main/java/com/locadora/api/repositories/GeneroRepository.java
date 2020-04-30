package com.locadora.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.locadora.api.entities.Genero;

public interface GeneroRepository extends JpaRepository<Genero, Integer> {

	Genero findByNome(String nomeGenero);

}
