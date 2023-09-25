package com.ceasa.dev.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ceasa.dev.dominio.Grupo;

public interface GrupoRepository extends JpaRepository<Grupo, Integer> {
	
	
	@Query("SELECT t FROM Grupo t WHERE t.nome = ?1")
	List<Grupo> findByNome(String nome);



}
