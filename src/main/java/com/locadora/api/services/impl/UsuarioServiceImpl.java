package com.locadora.api.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.locadora.api.entities.Role;
import com.locadora.api.entities.Sexo;
import com.locadora.api.entities.Usuario;
import com.locadora.api.exception.CustomException;
import com.locadora.api.repositories.SexoRepository;
import com.locadora.api.repositories.UsuarioRepository;
import com.locadora.api.security.JwtTokenProvider;
import com.locadora.api.services.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private SexoRepository sexoRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public List<Usuario> getUsuarios() {
		return usuarioRepository.findAll();
	}

	@Override
	public Usuario saveUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	@Override
	public String signin(String cpf, String senha) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(cpf, senha));
			return jwtTokenProvider.createToken(cpf, usuarioRepository.findByCpf(cpf).getRoles());
		} catch (AuthenticationException e) {
			throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@Override
	public String signup(Usuario usuario) {

		// Validando se o usuario tem mais de 18 anos
		Boolean validDateYear = LocalDate.now().getYear() - usuario.getDataNascimento().getYear() > 18;
		Boolean validDateYearMonth = LocalDate.now().getYear() - usuario.getDataNascimento().getYear() == 18
				&& LocalDate.now().getMonthValue() > usuario.getDataNascimento().getMonthValue();
		Boolean validDateYearMonthDay = LocalDate.now().getYear() - usuario.getDataNascimento().getYear() == 18
				&& LocalDate.now().getMonthValue() == usuario.getDataNascimento().getMonthValue()
				&& LocalDate.now().getDayOfMonth() >= usuario.getDataNascimento().getDayOfMonth();

		if (!validDateYear && !validDateYearMonth && !validDateYearMonthDay) {
			throw new CustomException("O usuario deve ter mais de 18 anos", HttpStatus.UNPROCESSABLE_ENTITY);
		} else if (usuarioRepository.existsByCpf(usuario.getCpf())) {
			throw new CustomException("CPF ja cadastrado", HttpStatus.UNPROCESSABLE_ENTITY);
		} else {
			usuario.setSenha(passwordEncoder.encode(usuario._getSenha()));
			if (usuario.getRoles() == null)
				usuario.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_EMPLOYEE)));

			Sexo sexo = sexoRepository.findByTipo(usuario.getSexo().getTipo());
			usuario.setSexo(sexo);

			usuarioRepository.save(usuario);
			return jwtTokenProvider.createToken(usuario.getCpf(), usuario.getRoles());
		}
	}

	@Override
	public void delete(String cpf) {
		usuarioRepository.deleteByCpf(cpf);
	}

	@Override
	public Usuario search(String username) {
		Usuario user = usuarioRepository.findByCpf(username);
		if (user == null) {
			throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
		}
		return user;
	}

	@Override
	public Usuario perfil(HttpServletRequest req) {
		return usuarioRepository.findByCpf(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
	}

	@Override
	public String refresh(String username) {
		return jwtTokenProvider.createToken(username, usuarioRepository.findByCpf(username).getRoles());
	}

	@Override
	public Usuario update(String cpf, Usuario usuario) {
		Usuario usuarioToUpdate = usuarioRepository.findByCpf(cpf);
		if (usuarioToUpdate == null) {
			throw new CustomException("Usuario nao encontrado.", HttpStatus.NOT_FOUND);
		} else {

			if (usuario.getCpf() != null)
				usuarioToUpdate.setCpf(usuario.getCpf());
			if (usuario.getDataNascimento() != null)
				usuarioToUpdate.setDataNascimento(usuario.getDataNascimento());
			if (usuario.getNome() != null)
				usuarioToUpdate.setNome(usuario.getNome());
			if (usuario._getSenha() != null)
				usuarioToUpdate.setSenha(passwordEncoder.encode(usuario._getSenha()));
			if (usuario.getSexo() != null) {
				Sexo sexo = sexoRepository.findByTipo(usuario.getSexo().getTipo());
				if (sexo == null) {
					throw new CustomException("Campo SEXO nao foi identificado.", HttpStatus.NOT_FOUND);
				}
				usuarioToUpdate.setSexo(sexo);
			}

			return usuarioRepository.save(usuarioToUpdate);
		}
	}

}
