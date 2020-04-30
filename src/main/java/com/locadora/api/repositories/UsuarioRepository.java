package com.locadora.api.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.locadora.api.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	boolean existsByCpf(String cpf);

	Usuario findByCpf(String cpf);
	
	Usuario findById(long id);

	@Transactional
	void deleteByCpf(String cpf);

}
