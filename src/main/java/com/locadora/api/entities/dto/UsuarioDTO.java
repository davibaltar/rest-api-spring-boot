package com.locadora.api.entities.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.locadora.api.entities.Role;
import com.locadora.api.entities.Sexo;

public class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotNull(message = "O campo CPF nao pode ser nulo.")
	@NotEmpty(message = "O campo CPF nao pode estar vazio.")
	@NotBlank(message = "O campo CPF nao pode estar em branco.")
	@Pattern(regexp = "^[0-9]{11}", message = "O campo CPF deve conter 11 numeros.")
	private String cpf;

	@NotNull(message = "O campo NOME nao pode ser nulo.")
	@NotEmpty(message = "O campo NOME nao pode estar vazio.")
	@NotBlank(message = "O campo NOME nao pode estar em branco.")
	@Size(min = 1, message = "O campo NOME conter pelo menos 1 caracteres.")
	private String nome;

	// @Pattern(regexp = "(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/(19|20)\\d{4}",
	// message = "O campo DATA NASCIMENTO deve estar no formato dd/mm/aaaa.")
	@NotNull(message = "O campo DATA NASCIMENTO nao pode ser nulo.")
	private LocalDate dataNascimento;

	@NotNull(message = "O campo SENHA nao pode ser nulo.")
	@NotEmpty(message = "O campo SENHA nao pode estar vazio.")
	@NotBlank(message = "O campo SENHA nao pode estar em branco.")
	@Size(min = 4, message = "O campo SENHA conter pelo menos 4 caracteres.")
	private String senha;

	private Sexo sexo;

	List<Role> roles;

	public UsuarioDTO() {
		// TODO Auto-generated constructor stub
	}

	public UsuarioDTO(Integer id, String cpf, String nome, LocalDate dataNascimento, String senha, Sexo sexo,
			List<Role> roles) {
		super();
		this.id = id;
		this.cpf = cpf;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.senha = senha;
		this.sexo = sexo;
		this.roles = roles;
	}

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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
