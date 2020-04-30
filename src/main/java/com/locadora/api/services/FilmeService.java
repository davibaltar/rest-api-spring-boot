package com.locadora.api.services;

import java.util.List;

import com.locadora.api.entities.Filme;

public interface FilmeService {

	public List<Filme> getFilmes(String filtroNome);

	public Filme search(Integer id);

	public Filme save(Filme filme);

	public void delete(Integer id);

	public Filme update(Integer id, Filme filme);

}
