package com.locadora.api.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.locadora.api.entities.Filme;
import com.locadora.api.entities.dto.FilmeDTO;
import com.locadora.api.entities.dto.FilmeResponseDTO;
import com.locadora.api.entities.mapper.FilmeMapper;
import com.locadora.api.services.FilmeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/filmes")
@Api(tags = "Filmes")
public class FilmeResource {

	@Autowired
	private FilmeService filmeService;

	@Autowired
	private FilmeMapper filmeMapper;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	@ApiOperation(value = "Retorna todos os filmes", response = FilmeDTO.class)
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Solicitacao Invalida."), //
			@ApiResponse(code = 403, message = "Acesso negado."), //
			@ApiResponse(code = 404, message = "O filme nao existe."), //
			@ApiResponse(code = 500, message = "Token (JWT) invalido ou expirado.") })
	public ResponseEntity<List<Filme>> getFilmes(
			@ApiParam("filtroNome") @RequestParam(required = false, defaultValue = "") String filtroNome) {
		List<Filme> filmes = filmeService.getFilmes(filtroNome);
		return ResponseEntity.ok().body(filmes);
	}

	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Retorna um determinado filme", response = FilmeResponseDTO.class)
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Solicitacao Invalida."), //
			@ApiResponse(code = 403, message = "Acesso negado."), //
			@ApiResponse(code = 404, message = "O filme nao existe."), //
			@ApiResponse(code = 500, message = "Token (JWT) invalido ou expirado.") })
	public FilmeResponseDTO search(@ApiParam("id") @PathVariable Integer id) {
		return modelMapper.map(filmeService.search(id), FilmeResponseDTO.class);
	}

	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
	@ApiOperation(value = "Adiciona um filme", response = FilmeDTO.class)
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Solicitacao Invalida."), //
			@ApiResponse(code = 403, message = "Acesso negado."), //
			@ApiResponse(code = 404, message = "O filme nao existe."), //
			@ApiResponse(code = 500, message = "Token (JWT) invalido ou expirado.") })
	public ResponseEntity<Filme> save(@Valid @RequestBody FilmeDTO dto) throws URISyntaxException {
		Filme novoFilme = filmeService.save(filmeMapper.mapFilmeDTOToFilme(dto));
		return ResponseEntity.created(new URI("/usuarios/" + novoFilme.getId())).body(novoFilme);
	}

	@PutMapping(value = "/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
	@ApiOperation(value = "Atualiza um filme", response = FilmeResponseDTO.class)
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Solicitacao Invalida."), //
			@ApiResponse(code = 403, message = "Acesso negado."), //
			@ApiResponse(code = 404, message = "O filme nao existe."), //
			@ApiResponse(code = 500, message = "Token (JWT) invalido ou expirado.") })
	public FilmeResponseDTO update(@PathVariable Integer id, @RequestBody Filme filme) {
		return modelMapper.map(filmeService.update(id, filme), FilmeResponseDTO.class);
	}

	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
	@ApiOperation(value = "Remove um filme", response = Integer.class)
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Solicitacao Invalida."), //
			@ApiResponse(code = 403, message = "Acesso negado."), //
			@ApiResponse(code = 404, message = "O filme nao existe."), //
			@ApiResponse(code = 500, message = "Token (JWT) invalido ou expirado.") })
	public Integer delete(@PathVariable Integer id) {
		filmeService.delete(id);
		return id;
	}

}
