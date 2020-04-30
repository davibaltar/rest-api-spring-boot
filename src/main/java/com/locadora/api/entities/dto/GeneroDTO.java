package com.locadora.api.entities.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class GeneroDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotNull(message = "O campo NOME nao pode ser nulo.")
	@NotEmpty(message = "O campo NOME nao pode estar vazio.")
	@NotBlank(message = "O campo NOME nao pode estar em branco.")
	@Size(min = 1, message = "O campo NOME conter pelo menos 1 caracteres.")
	private String nome;

	public GeneroDTO() {
		// TODO Auto-generated constructor stub
	}

	public GeneroDTO(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
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

}
