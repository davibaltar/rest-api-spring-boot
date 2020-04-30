package com.locadora.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.locadora.api.entities.Usuario;
import com.locadora.api.repositories.UsuarioRepository;

@Service
public class UsuarioDetalhes implements UserDetailsService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Override
  public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
    final Usuario usuario = usuarioRepository.findByCpf(cpf);

    if (usuario == null) {
      throw new UsernameNotFoundException("User '" + cpf + "' not found");
    }

    return org.springframework.security.core.userdetails.User//
        .withUsername(cpf)//
        .password(usuario._getSenha())//
        .authorities(usuario.getRoles())//
        .accountExpired(false)//
        .accountLocked(false)//
        .credentialsExpired(false)//
        .disabled(false)//
        .build();
  }

}
