package com.locadora.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.locadora.api.entities.Filme;

public interface FilmeRepository extends JpaRepository<Filme, Integer> {

}
