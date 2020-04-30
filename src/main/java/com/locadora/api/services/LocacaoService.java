package com.locadora.api.services;

import java.util.List;

import com.locadora.api.entities.Locacao;

public interface LocacaoService {

	public List<Locacao> getLocacoes(String usuarioCPF, String filmeNome, Boolean alugados);
	
	public Locacao save(Locacao locacao);

	public void renovacao(Integer id);

	public Locacao search(Integer id);

}
