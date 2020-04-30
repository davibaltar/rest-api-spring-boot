package com.locadora.api.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.locadora.api.entities.Diretor;
import com.locadora.api.exception.CustomException;
import com.locadora.api.repositories.DiretorRepository;
import com.locadora.api.services.DiretorService;

@Service
public class DiretorServiceImpl implements DiretorService {

	@Autowired
	private DiretorRepository diretorRepository;

	@Override
	public List<Diretor> getDiretores() {
		return diretorRepository.findAll();
	}

	@Override
	public Diretor save(Diretor diretor) {
		diretorRepository.save(diretor);
		return diretor;
	}

	@Override
	public Diretor search(String nomeDiretor) {
		Diretor diretor = diretorRepository.findByNome(nomeDiretor);
		if (diretor == null) {
			throw new CustomException("Diretor nao existe.", HttpStatus.NOT_FOUND);
		}
		return diretor;
	}

}
