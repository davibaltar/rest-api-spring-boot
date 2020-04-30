package com.locadora.api.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.locadora.api.entities.Usuario;

public interface UsuarioService {

	public List<Usuario> getUsuarios();

	public Usuario saveUsuario(Usuario usuario);

	public String signin(String cpf, String senha);

	public String signup(Usuario usuario);

	public void delete(String cpf);

	public Usuario search(String cpf);

	public Usuario perfil(HttpServletRequest req);

	public String refresh(String cpf);

	public Usuario update(String cpf, Usuario usuario);

}
