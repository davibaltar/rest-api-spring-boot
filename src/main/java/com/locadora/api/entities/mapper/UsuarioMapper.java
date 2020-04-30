package com.locadora.api.entities.mapper;

import org.springframework.stereotype.Service;

import com.locadora.api.entities.Usuario;
import com.locadora.api.entities.dto.UsuarioDTO;

@Service
public class UsuarioMapper {

	public Usuario mapUsuarioDTOToUsuario(UsuarioDTO dto) {
		Usuario usuario = new Usuario(dto.getCpf(), dto.getNome(), dto.getDataNascimento(), dto.getSenha(), dto.getSexo(), dto.getRoles());
		return usuario;
	}

}
