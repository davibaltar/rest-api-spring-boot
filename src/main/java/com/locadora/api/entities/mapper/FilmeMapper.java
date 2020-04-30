package com.locadora.api.entities.mapper;

import org.springframework.stereotype.Service;

import com.locadora.api.entities.Filme;
import com.locadora.api.entities.dto.FilmeDTO;

@Service
public class FilmeMapper {

	public Filme mapFilmeDTOToFilme(FilmeDTO dto) {
		Filme filme = new Filme(dto.getNome(), dto.getQuantidade(), dto.getGenero(), dto.getDiretor());
		return filme;
	}

}
