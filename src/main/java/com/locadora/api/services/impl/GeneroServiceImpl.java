package com.locadora.api.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.locadora.api.entities.Genero;
import com.locadora.api.exception.CustomException;
import com.locadora.api.repositories.GeneroRepository;
import com.locadora.api.services.GeneroService;

@Service
public class GeneroServiceImpl implements GeneroService {

	@Autowired
	private GeneroRepository generoRepository;

	@Override
	public List<Genero> getGeneros() {
		return generoRepository.findAll();
	}

	@Override
	public Genero save(Genero genero) {
		generoRepository.save(genero);
		return genero;
	}

	@Override
	public Genero search(String nomeGenero) {
		Genero genero = generoRepository.findByNome(nomeGenero);
		if (genero == null) {
			throw new CustomException("Genero nao existe.", HttpStatus.NOT_FOUND);
		}
		return genero;
	}

}
