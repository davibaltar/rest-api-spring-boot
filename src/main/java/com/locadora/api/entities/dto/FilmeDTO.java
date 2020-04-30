package com.locadora.api.entities.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.locadora.api.entities.Diretor;
import com.locadora.api.entities.Genero;

public class FilmeDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotNull(message = "O campo NOME nao pode ser nulo.")
	@NotEmpty(message = "O campo NOME nao pode estar vazio.")
	@NotBlank(message = "O campo NOME nao pode estar em branco.")
	@Size(min = 1, message = "O campo NOME conter pelo menos 1 caracteres.")
	private String nome;

	private Integer quantidade;

	private Genero genero;

	private Diretor diretor;

	public FilmeDTO() {
		// TODO Auto-generated constructor stub
	}

	public FilmeDTO(Integer id, String nome, Integer quantidade, Genero genero, Diretor diretor) {
		super();
		this.id = id;
		this.nome = nome;
		this.quantidade = quantidade;
		this.genero = genero;
		this.diretor = diretor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public Diretor getDiretor() {
		return diretor;
	}

	public void setDiretor(Diretor diretor) {
		this.diretor = diretor;
	}

}
