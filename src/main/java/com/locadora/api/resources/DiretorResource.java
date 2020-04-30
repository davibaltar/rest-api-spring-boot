package com.locadora.api.resources;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.locadora.api.entities.Diretor;
import com.locadora.api.entities.dto.DiretorDTO;
import com.locadora.api.services.DiretorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/diretores")
@Api(tags = "Diretores")
public class DiretorResource {

	@Autowired
	private DiretorService diretorService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	@ApiOperation(value = "Retorna todos os diretores", response = DiretorDTO.class)
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Solicitacao Invalida."), //
			@ApiResponse(code = 403, message = "Acesso negado."), //
			@ApiResponse(code = 404, message = "O diretor nao existe."), //
			@ApiResponse(code = 500, message = "Token (JWT) invalido ou expirado.") })
	public ResponseEntity<List<Diretor>> getDiretores() {
		List<Diretor> diretores = diretorService.getDiretores();
		return ResponseEntity.ok().body(diretores);
	}

	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
	@ApiOperation(value = "Adiciona um diretor")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Solicitacao Invalida."), //
			@ApiResponse(code = 403, message = "Acesso negado."), //
			@ApiResponse(code = 404, message = "O diretor nao existe."), //
			@ApiResponse(code = 500, message = "Token (JWT) invalido ou expirado.") })
	public void save(@RequestBody DiretorDTO diretor) {
		diretorService.save(modelMapper.map(diretor, Diretor.class));
	}
}
