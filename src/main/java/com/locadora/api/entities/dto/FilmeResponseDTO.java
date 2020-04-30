package com.locadora.api.entities.dto;

import io.swagger.annotations.ApiModelProperty;

public class FilmeResponseDTO {

	@ApiModelProperty(position = 0)
	private Integer id;

	@ApiModelProperty(position = 1)
	private String nome;

	@ApiModelProperty(position = 2)
	private Integer quantidade;

	@ApiModelProperty(position = 3)
	private GeneroResponseDTO genero;

	@ApiModelProperty(position = 4)
	private DiretorResponseDTO diretor;

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

	public GeneroResponseDTO getGenero() {
		return genero;
	}

	public void setGenero(GeneroResponseDTO genero) {
		this.genero = genero;
	}

	public DiretorResponseDTO getDiretor() {
		return diretor;
	}

	public void setDiretor(DiretorResponseDTO diretor) {
		this.diretor = diretor;
	}

}
