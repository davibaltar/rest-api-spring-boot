package com.locadora.api.resources;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.locadora.api.entities.Usuario;
import com.locadora.api.entities.dto.UsuarioDTO;
import com.locadora.api.entities.dto.UsuarioResponseDTO;
import com.locadora.api.services.UsuarioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/usuarios")
@Api(tags = "Usuários")
public class UsuarioResource {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ApiOperation(value = "Retorna todos os usuarios")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Solicitacao Invalida."), //
			@ApiResponse(code = 403, message = "Acesso negado."), //
			@ApiResponse(code = 404, message = "Usuario nao existe."), //
			@ApiResponse(code = 500, message = "Token (JWT) invalido ou expirado.") })
	public ResponseEntity<List<Usuario>> getUsuarios() {
		List<Usuario> usuarios = usuarioService.getUsuarios();
		return ResponseEntity.ok().body(usuarios);
	}

	@PostMapping("/signin")
	@ApiOperation(value = "Login do usuario}")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Solicitacao Invalida."), //
			@ApiResponse(code = 422, message = "Usuario já existe") })
	public String login(@ApiParam("usuario") @RequestBody Usuario usuario) {
		return usuarioService.signin(usuario.getCpf(), usuario._getSenha());
	}

	@PostMapping("/signup")
	@ApiOperation(value = "Adiciona um usuario")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Solicitacao Invalida."), //
			@ApiResponse(code = 403, message = "Acesso negado."), //
			@ApiResponse(code = 422, message = "Usuario já existe") })
	public String signup(@ApiParam("usuario") @RequestBody UsuarioDTO usuario) {
		return usuarioService.signup(modelMapper.map(usuario, Usuario.class));
	}

	@DeleteMapping(value = "/{cpf}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ApiOperation(value = "Remove um determinado usuario")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Solicitacao Invalida."), //
			@ApiResponse(code = 403, message = "Acesso negado."), //
			@ApiResponse(code = 404, message = "Usuario nao existe."), //
			@ApiResponse(code = 500, message = "Token (JWT) invalido ou expirado.") })
	public String delete(@ApiParam("cpf") @PathVariable String cpf) {
		usuarioService.delete(cpf);
		return cpf;
	}

	@GetMapping(value = "/{cpf}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ApiOperation(value = "Retorna um determinado usuario", response = UsuarioResponseDTO.class)
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Solicitacao Invalida."), //
			@ApiResponse(code = 403, message = "Acesso negado."), //
			@ApiResponse(code = 404, message = "Usuario nao existe."), //
			@ApiResponse(code = 500, message = "Token (JWT) invalido ou expirado.") })
	public UsuarioResponseDTO search(@ApiParam("cpf") @PathVariable String cpf) {
		return modelMapper.map(usuarioService.search(cpf), UsuarioResponseDTO.class);
	}

	@GetMapping(value = "/perfil")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "Retorna o perfil que está logado", response = UsuarioResponseDTO.class)
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Solicitacao Invalida."), //
			@ApiResponse(code = 403, message = "Acesso negado."), //
			@ApiResponse(code = 500, message = "Token (JWT) invalido ou expirado.") })
	public UsuarioResponseDTO perfil(HttpServletRequest req) {
		return modelMapper.map(usuarioService.perfil(req), UsuarioResponseDTO.class);
	}

	@GetMapping("/refresh")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "Atualiza o token (JWT)")
	public String refresh(HttpServletRequest req) {
		return usuarioService.refresh(req.getRemoteUser());
	}

	@PutMapping(value = "/{cpf}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ApiOperation(value = "Atualiza um determinado usuario", response = UsuarioResponseDTO.class)
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Solicitacao Invalida."), //
			@ApiResponse(code = 403, message = "Acesso negado."), //
			@ApiResponse(code = 404, message = "Usuario nao existe."), //
			@ApiResponse(code = 500, message = "Token (JWT) invalido ou expirado.") })
	public UsuarioResponseDTO update(@ApiParam("cpf") @PathVariable String cpf,
			@ApiParam("Update User") @RequestBody Usuario usuario) {
		return modelMapper.map(usuarioService.update(cpf, usuario), UsuarioResponseDTO.class);
	}

}
