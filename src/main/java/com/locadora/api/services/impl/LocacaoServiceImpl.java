package com.locadora.api.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.locadora.api.entities.Filme;
import com.locadora.api.entities.Locacao;
import com.locadora.api.entities.Usuario;
import com.locadora.api.exception.CustomException;
import com.locadora.api.repositories.FilmeRepository;
import com.locadora.api.repositories.LocacaoRepository;
import com.locadora.api.repositories.UsuarioRepository;
import com.locadora.api.services.LocacaoService;

@Service
public class LocacaoServiceImpl implements LocacaoService {

	@Autowired
	private LocacaoRepository locacaoRepository;

	@Autowired
	private FilmeRepository filmeRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public List<Locacao> getLocacoes(String usuarioCPF, String filmeNome, Boolean alugados) {
		if (alugados == true) { // Retorna todos os que estao alugados no momento
			return locacaoRepository.findAll().stream()
					.filter(locacao -> locacao.getDataDevolucao().isAfter(LocalDate.now()))
					.collect(Collectors.toList());
		}
		if (!usuarioCPF.isEmpty() && filmeNome.isEmpty()) { // somente 'usuarioCPF'
			return locacaoRepository.findAll().stream()
					.filter(locacao -> locacao.getUsuario().getCpf().compareTo(usuarioCPF) == 0)
					.collect(Collectors.toList());
		} else if (usuarioCPF.isEmpty() && !filmeNome.isEmpty()) { // somente 'filmeNome'
			return locacaoRepository.findAll().stream()
					.filter(locacao -> locacao.getFilme().getNome().compareTo(filmeNome) == 0)
					.collect(Collectors.toList());
		} else if (!usuarioCPF.isEmpty() && !filmeNome.isEmpty()) { // 'usuarioCPF' e 'filmeNome'
			return locacaoRepository.findAll().stream()
					.filter(locacao -> locacao.getUsuario().getCpf().compareTo(usuarioCPF) == 0
							&& locacao.getFilme().getNome().compareTo(filmeNome) == 0)
					.collect(Collectors.toList());
		} else {
			return locacaoRepository.findAll();
		}
	}

	@Override
	public Locacao save(Locacao locacao) {

		Optional<Filme> filme = filmeRepository.findById(locacao.getFilmeId());
		if (filme == null)
			throw new CustomException("Filme invalido.", HttpStatus.UNPROCESSABLE_ENTITY);
		locacao.setFilme(filme.get());

		Optional<Usuario> usuario = usuarioRepository.findById(locacao.getUsuarioId());
		if (usuario == null)
			throw new CustomException("Usuario invalido.", HttpStatus.UNPROCESSABLE_ENTITY);
		locacao.setUsuario(usuario.get());

		// Verificando se usuario possui mais de 5 locações
		List<Locacao> locacoes = getLocacoes(locacao.getUsuario().getCpf(), "", false).stream()
				.filter(loc -> loc.getDataDevolucao().isAfter(LocalDate.now())).collect(Collectors.toList());
		if (locacoes.size() > 5)
			throw new CustomException("Cliente ja possui 5 locacoes.", HttpStatus.UNPROCESSABLE_ENTITY);

		// Verificando estoque do filme
		List<Locacao> estoque = locacaoRepository.findAll().stream()
				.filter(loc -> loc.getFilme().getId() == locacao.getFilme().getId()
						&& loc.getDataDevolucao().isAfter(LocalDate.now()))
				.collect(Collectors.toList());
		if (estoque.size() >= filme.get().getQuantidade())
			throw new CustomException("Estoque vazio.", HttpStatus.UNPROCESSABLE_ENTITY);

		return locacaoRepository.save(locacao);
	}

	@Override
	public Locacao search(Integer id) {
		try {
			Optional<Locacao> locacao = locacaoRepository.findById(id);
			return locacao.get();
		} catch (Exception e) {
			throw new CustomException("Locacao nao existe", HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void renovacao(Integer id) {
		Locacao locacao = search(id);

		// Permitido no maximo 2 renovacoes
		if (locacao.getRenovacoes() >= 2)
			throw new CustomException("O filme atingiu o limite de renovacoes.", HttpStatus.UNPROCESSABLE_ENTITY);
		locacao.setRenovacoes(locacao.getRenovacoes() + 1);

		// Renova uma locação por mais 3 dias. TODO: Parametrizar este valor.
		locacao.setDataDevolucao(locacao.getDataDevolucao().plusDays(3));
		locacaoRepository.save(locacao);
	}

}
