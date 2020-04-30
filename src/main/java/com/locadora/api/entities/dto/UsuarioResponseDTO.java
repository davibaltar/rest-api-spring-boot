package com.locadora.api.entities.dto;

import java.time.LocalDate;
import java.util.List;

import com.locadora.api.entities.Role;

import io.swagger.annotations.ApiModelProperty;

public class UsuarioResponseDTO {

	@ApiModelProperty(position = 0)
	private Integer id;

	@ApiModelProperty(position = 1)
	private String cpf;

	@ApiModelProperty(position = 2)
	private LocalDate dataNascimento;

	@ApiModelProperty(position = 3)
	private String nome;

	@ApiModelProperty(position = 4)
	private SexoResponseDTO sexo;

	@ApiModelProperty(position = 5)
	List<Role> roles;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public SexoResponseDTO getSexo() {
		return sexo;
	}

	public void setSexo(SexoResponseDTO sexo) {
		this.sexo = sexo;
	}

}
