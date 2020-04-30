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

import com.locadora.api.entities.Genero;
import com.locadora.api.entities.dto.GeneroDTO;
import com.locadora.api.entities.dto.GeneroResponseDTO;
import com.locadora.api.services.GeneroService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/generos")
@Api(tags = "Generos")
public class GeneroResource {

	@Autowired
	private GeneroService generoService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	@ApiOperation(value = "Retorna um determinado genero", response = GeneroResponseDTO.class)
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Solicitacao Invalida."), //
			@ApiResponse(code = 403, message = "Acesso negado."), //
			@ApiResponse(code = 404, message = "O genero nao existe."), //
			@ApiResponse(code = 500, message = "Token (JWT) invalido ou expirado.") })
	public ResponseEntity<List<Genero>> getGeneros() {
		List<Genero> generos = generoService.getGeneros();
		return ResponseEntity.ok().body(generos);
	}

	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
	@ApiOperation(value = "Adiciona um genero")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Solicitacao Invalida."), //
			@ApiResponse(code = 403, message = "Acesso negado."), //
			@ApiResponse(code = 404, message = "O genero nao existe."), //
			@ApiResponse(code = 500, message = "Token (JWT) invalido ou expirado.") })
	public void save(@RequestBody GeneroDTO genero) {
		generoService.save(modelMapper.map(genero, Genero.class));
	}
}
