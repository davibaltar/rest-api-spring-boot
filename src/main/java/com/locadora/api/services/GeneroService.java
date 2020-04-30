package com.locadora.api.services;

import java.util.List;

import com.locadora.api.entities.Genero;

public interface GeneroService {

	List<Genero> getGeneros();

	Genero save(Genero genero);

	Genero search(String nomeGenero);

}
