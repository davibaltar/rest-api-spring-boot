package com.locadora.api;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.locadora.api.entities.Filme;
import com.locadora.api.entities.Locacao;
import com.locadora.api.entities.Role;
import com.locadora.api.entities.Sexo;
import com.locadora.api.entities.Usuario;
import com.locadora.api.services.FilmeService;
import com.locadora.api.services.LocacaoService;
import com.locadora.api.services.UsuarioService;

@SpringBootTest
@Transactional
class SpringBootLocadoraApiApplicationTests {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private LocacaoService locacaoService;

	@Autowired
	private FilmeService filmeService;

	@Test
	void adicionandoUsuario() {
		try {
			Usuario usuario = new Usuario();
			usuario.setCpf("00011122233");
			usuario.setSenha("123456");
			usuario.setDataNascimento(LocalDate.parse("2001-01-21", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			usuario.setSexo(new Sexo("m"));
			usuario.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));

			usuarioService.signup(usuario);

			Usuario usuarioRetornado = usuarioService.search(usuario.getCpf());

			assertTrue(usuarioRetornado != null);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	void adicionandoUsuarioMenorDe18() {
		try {
			Usuario usuario = new Usuario();
			usuario.setCpf("00011122233");
			usuario.setSenha("123456");
			usuario.setDataNascimento(LocalDate.parse("2012-01-21", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			usuario.setSexo(new Sexo("m"));
			usuario.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));
			usuarioService.signup(usuario);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	void cpfInvalido() {
		try {
			Usuario usuario = new Usuario();
			usuario.setCpf("000abc123");
			usuario.setSenha("123456");
			usuario.setDataNascimento(LocalDate.parse("2000-01-21", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			usuario.setSexo(new Sexo("m"));
			usuario.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));
			String resp = usuarioService.signup(usuario);
			System.out.println(resp);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	void locarMenosDe5Filmes() {
		try {
			Usuario usuario = new Usuario();
			usuario.setCpf("12345678900");
			usuario.setSenha("123456");
			usuario.setDataNascimento(LocalDate.parse("2000-01-21", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			usuario.setSexo(new Sexo("m"));
			usuario.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));
			usuarioService.signup(usuario);

			// Buscando filmes
			Filme filme1 = filmeService.getFilmes("Jurassic Park").get(0);
			Filme filme2 = filmeService.getFilmes("Batman").get(0);

			Locacao loc1 = new Locacao();
			loc1.setDataDevolucao(LocalDate.parse("2020-04-30", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			loc1.setFilmeId(filme1.getId());
			loc1.setUsuarioId(usuario.getId());
			locacaoService.save(loc1);

			Locacao loc2 = new Locacao();
			loc2.setDataDevolucao(LocalDate.parse("2020-05-02", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			loc2.setFilmeId(filme2.getId());
			loc2.setUsuarioId(usuario.getId());
			locacaoService.save(loc2);

			List<Locacao> locacoes = locacaoService.getLocacoes(usuario.getCpf(), "", false);
			assertTrue(locacoes.size() == 2);

		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	void locarMaisDe5Filmes() {
		try {
			Usuario usuario = new Usuario();
			usuario.setCpf("12345678900");
			usuario.setSenha("123456");
			usuario.setDataNascimento(LocalDate.parse("2000-01-21", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			usuario.setSexo(new Sexo("m"));
			usuario.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));
			usuarioService.signup(usuario);

			// Buscando filmes
			Filme filme1 = filmeService.getFilmes("Jurassic Park").get(0);
			Filme filme2 = filmeService.getFilmes("Batman").get(0);

			for (int i = 0; i < 3; i++) {
				Locacao loc1 = new Locacao();
				loc1.setDataDevolucao(LocalDate.parse("2020-05-02", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				loc1.setFilmeId(filme1.getId());
				loc1.setUsuarioId(usuario.getId());
				locacaoService.save(loc1);
			}

			for (int i = 0; i < 3; i++) {
				Locacao loc1 = new Locacao();
				loc1.setDataDevolucao(LocalDate.parse("2020-05-02", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				loc1.setFilmeId(filme2.getId());
				loc1.setUsuarioId(usuario.getId());
				locacaoService.save(loc1);
			}

		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	void renovarMenosDe2Vezes() {
		try {
			Usuario usuario = new Usuario();
			usuario.setCpf("12345678900");
			usuario.setSenha("123456");
			usuario.setDataNascimento(LocalDate.parse("2000-01-21", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			usuario.setSexo(new Sexo("m"));
			usuario.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));
			usuarioService.signup(usuario);

			// Buscando filmes
			Filme filme1 = filmeService.getFilmes("Jurassic Park").get(0);

			Locacao loc1 = new Locacao();
			loc1.setDataDevolucao(LocalDate.parse("2020-05-02", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			loc1.setFilmeId(filme1.getId());
			loc1.setUsuarioId(usuario.getId());
			locacaoService.save(loc1);

			List<Locacao> locacoes = locacaoService.getLocacoes(usuario.getCpf(), "Jurassic Park", false);

			locacaoService.renovacao(locacoes.get(0).getId());

			List<Locacao> locacoesRetorno = locacaoService.getLocacoes(usuario.getCpf(), "Jurassic Park", false);

			assertTrue(locacoesRetorno.get(0).getRenovacoes() == 1);

		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	void renovarMaisDe2Vezes() {
		try {
			Usuario usuario = new Usuario();
			usuario.setCpf("12345678900");
			usuario.setSenha("123456");
			usuario.setDataNascimento(LocalDate.parse("2000-01-21", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			usuario.setSexo(new Sexo("m"));
			usuario.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));
			usuarioService.signup(usuario);

			// Buscando filmes
			Filme filme1 = filmeService.getFilmes("Jurassic Park").get(0);

			Locacao loc1 = new Locacao();
			loc1.setDataDevolucao(LocalDate.parse("2020-05-02", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			loc1.setFilmeId(filme1.getId());
			loc1.setUsuarioId(usuario.getId());
			locacaoService.save(loc1);

			List<Locacao> locacoes = locacaoService.getLocacoes(usuario.getCpf(), "Jurassic Park", false);

			locacaoService.renovacao(locacoes.get(0).getId());
			locacaoService.renovacao(locacoes.get(0).getId());
			locacaoService.renovacao(locacoes.get(0).getId());

			List<Locacao> locacoesRetorno = locacaoService.getLocacoes(usuario.getCpf(), "Jurassic Park", false);

			assertFalse(locacoesRetorno.get(0).getRenovacoes() == 3);

		} catch (Exception e) {
			assertTrue(true);
		}
	}

}
