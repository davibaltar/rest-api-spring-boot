package com.locadora.api.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.locadora.api.entities.Diretor;
import com.locadora.api.entities.Filme;
import com.locadora.api.entities.Genero;
import com.locadora.api.exception.CustomException;
import com.locadora.api.repositories.DiretorRepository;
import com.locadora.api.repositories.FilmeRepository;
import com.locadora.api.repositories.GeneroRepository;
import com.locadora.api.services.FilmeService;

@Service
public class FilmeServiceImpl implements FilmeService {

	@Autowired
	private FilmeRepository filmeRepository;

	@Autowired
	private GeneroRepository generoRepository;

	@Autowired
	private DiretorRepository diretorRepository;

	@Override
	public List<Filme> getFilmes(String filtroNome) {
		if (filtroNome.isEmpty()) {
			return filmeRepository.findAll();
		}
		// Aplicando filtro NOME
		return filmeRepository.findAll().stream().filter(filme -> filme.getNome().compareTo(filtroNome) == 0)
				.collect(Collectors.toList());
	}

	@Override
	public Filme save(Filme filme) {
		// Caso o diretor ou genero nao seja encontrado, o mesmo será inserido no banco.
		Diretor diretor = diretorRepository.findByNome(filme.getDiretor().getNome());
		if (diretor == null)
			diretorRepository.save(filme.getDiretor());
		filme.setDiretor(diretorRepository.findByNome(filme.getDiretor().getNome()));

		Genero genero = generoRepository.findByNome(filme.getGenero().getNome());
		if (genero == null)
			generoRepository.save(filme.getGenero());
		filme.setGenero(generoRepository.findByNome(filme.getGenero().getNome()));

		return filmeRepository.save(filme);
	}

	@Override
	public Filme search(Integer id) {
		try {
			Optional<Filme> filme = filmeRepository.findById(id);
			return filme.get();
		} catch (Exception e) {
			throw new CustomException("Filme nao encontrado", HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Filme update(Integer id, Filme filme) {
		try {
			Optional<Filme> filmeFound = filmeRepository.findById(id);

			Filme filmeToUpdate = filmeFound.get();
			if (filme.getNome() != null)
				filmeToUpdate.setNome(filme.getNome());
			if (filme.getQuantidade() != null)
				filmeToUpdate.setQuantidade(filme.getQuantidade());

			// Verificando se Diretor e Genero sao novos
			if (filme.getDiretor() != null) {
				Diretor diretor = diretorRepository.findByNome(filme.getDiretor().getNome());
				if (diretor == null)
					diretorRepository.save(filme.getDiretor());
				filmeToUpdate.setDiretor(diretorRepository.findByNome(filme.getDiretor().getNome()));
			}
			if (filme.getGenero() != null) {
				Genero genero = generoRepository.findByNome(filme.getGenero().getNome());
				if (genero == null)
					generoRepository.save(filme.getGenero());
				filmeToUpdate.setGenero(generoRepository.findByNome(filme.getGenero().getNome()));
			}

			return filmeRepository.save(filmeToUpdate);
		} catch (Exception e) {
			throw new CustomException("Filme nao encontrado.", HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void delete(Integer id) {
		filmeRepository.deleteById(id);
	}

}
