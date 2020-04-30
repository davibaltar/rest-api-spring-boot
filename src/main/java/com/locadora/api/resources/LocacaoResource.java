package com.locadora.api.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.locadora.api.entities.Locacao;
import com.locadora.api.services.LocacaoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/locacoes")
@Api(tags = "Locações")
public class LocacaoResource {

	@Autowired
	private LocacaoService locacaoService;

	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
	@ApiOperation(value = "Retorna locacoes. Utilize os filtros usuarioCPF e filmeNome para busca personalizada", response = Locacao.class)
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Solicitacao Invalida."), //
			@ApiResponse(code = 403, message = "Acesso negado."), //
			@ApiResponse(code = 404, message = "Locacao nao exitente."), //
			@ApiResponse(code = 500, message = "Token (JWT) invalido ou expirado.") })
	public ResponseEntity<List<Locacao>> getLocacoes(
			@ApiParam("usuarioCPF") @RequestParam(required = false, defaultValue = "") String usuarioCPF,
			@ApiParam("filmeNome") @RequestParam(required = false, defaultValue = "") String filmeNome,
			@ApiParam("alugados") @RequestParam(required = false, defaultValue = "false") Boolean alugados) {
		List<Locacao> locacoes = locacaoService.getLocacoes(usuarioCPF, filmeNome, alugados);
		return ResponseEntity.ok().body(locacoes);
	}

	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
	@ApiOperation(value = "Adiciona uma locação", response = Locacao.class)
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Solicitacao Invalida."), //
			@ApiResponse(code = 403, message = "Acesso negado."), //
			@ApiResponse(code = 404, message = "Locacao nao exitente."), //
			@ApiResponse(code = 500, message = "Token (JWT) invalido ou expirado.") })
	public ResponseEntity<Locacao> save(@RequestBody Locacao locacao) throws URISyntaxException {
		Locacao novaLocacao = locacaoService.save(locacao);
		return ResponseEntity.created(new URI("/locacoes/" + novaLocacao.getId())).body(novaLocacao);
	}

	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Retorna uma determinada locacao", response = Locacao.class)
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Solicitacao Invalida."), //
			@ApiResponse(code = 403, message = "Acesso negado."), //
			@ApiResponse(code = 404, message = "A locacao nao existe."), //
			@ApiResponse(code = 500, message = "Token (JWT) invalido ou expirado.") })
	public Locacao search(@ApiParam("id") @PathVariable Integer id) {
		return locacaoService.search(id);
	}

	@PutMapping(value = "/renovar/{id}")
	//@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
	@ApiOperation(value = "Renovacao de uma locação com base no ID da locacao por mais 3 dias.", response = Locacao.class)
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Solicitacao Invalida."), //
			@ApiResponse(code = 403, message = "Acesso negado."), //
			@ApiResponse(code = 404, message = "Locacao nao exitente."), //
			@ApiResponse(code = 500, message = "Token (JWT) invalido ou expirado.") })
	public void renovacao(@ApiParam("id") @PathVariable Integer id) throws URISyntaxException {
		locacaoService.renovacao(id);
	}
}
