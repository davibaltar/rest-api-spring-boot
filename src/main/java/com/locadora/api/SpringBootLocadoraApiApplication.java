package com.locadora.api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.locadora.api.entities.Diretor;
import com.locadora.api.entities.Filme;
import com.locadora.api.entities.Genero;
import com.locadora.api.entities.Locacao;
import com.locadora.api.entities.Role;
import com.locadora.api.entities.Sexo;
import com.locadora.api.entities.Usuario;
import com.locadora.api.repositories.SexoRepository;
import com.locadora.api.services.DiretorService;
import com.locadora.api.services.FilmeService;
import com.locadora.api.services.GeneroService;
import com.locadora.api.services.LocacaoService;
import com.locadora.api.services.UsuarioService;

@SpringBootApplication
public class SpringBootLocadoraApiApplication implements CommandLineRunner {

	@Autowired
	private SexoRepository sexoRepository;

	@Autowired
	private GeneroService generoService;

	@Autowired
	private DiretorService diretorService;

	@Autowired
	private FilmeService filmeService;
	
	@Autowired
	private LocacaoService locacaoService;

	@Autowired
	private UsuarioService usuarioService;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootLocadoraApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Sexo m = new Sexo("m");
		Sexo f = new Sexo("f");
		sexoRepository.save(m);
		sexoRepository.save(f);

		Usuario admin = new Usuario();
		admin.setCpf("00000000000");
		admin.setNome("admin");
		admin.setSenha("123456");
		admin.setDataNascimento(LocalDate.parse("2000-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		admin.setSexo(new Sexo("m"));
		admin.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));
		usuarioService.signup(admin);

		// Adicionando Generos
		generoService.save(new Genero("Ação"));
		generoService.save(new Genero("Aventura"));
		generoService.save(new Genero("Comédia"));

		// Adicionando Diretores
		diretorService.save(new Diretor("Steven Spielberg"));
		diretorService.save(new Diretor("Quentin Tarantino"));
		diretorService.save(new Diretor("Tim Burton"));

		// Adicionando filmes
		Filme filme1 = new Filme();
		filme1.setNome("Jurassic Park");
		filme1.setDiretor(new Diretor("Steven Spielberg"));
		filme1.setGenero(new Genero("Aventura"));
		filme1.setQuantidade(5);
		filmeService.save(filme1);

		Filme filme2 = new Filme();
		filme2.setNome("Django Livre");
		filme2.setDiretor(new Diretor("Quentin Tarantino"));
		filme2.setGenero(new Genero("Aventura"));
		filme2.setQuantidade(3);
		filmeService.save(filme2);

		Filme filme3 = new Filme();
		filme3.setNome("Batman");
		filme3.setDiretor(new Diretor("Tim Burton"));
		filme3.setGenero(new Genero("Ação"));
		filme3.setQuantidade(7);
		filmeService.save(filme3);
		
		Locacao loc1 = new Locacao();
		loc1.setDataDevolucao(LocalDate.parse("2020-04-30", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		loc1.setFilmeId(filme1.getId());
		loc1.setUsuarioId(admin.getId());
		locacaoService.save(loc1);
		
		Locacao loc2 = new Locacao();  // Filme ja devolvido
		loc2.setDataDevolucao(LocalDate.parse("2020-01-30", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		loc2.setFilmeId(filme2.getId());
		loc2.setUsuarioId(admin.getId());
		locacaoService.save(loc2);

	}

}
