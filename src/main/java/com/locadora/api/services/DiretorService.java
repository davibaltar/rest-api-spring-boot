package com.locadora.api.services;

import java.util.List;

import com.locadora.api.entities.Diretor;

public interface DiretorService {

	List<Diretor> getDiretores();

	Diretor save(Diretor diretor);

	Diretor search(String nomeDiretor);

}
