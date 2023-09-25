package com.ceasa.dev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ceasa.dev.dominio.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	// RETURN A LIST OF THE PRODUCTS IN A SUBGROUP
	@Query("SELECT a FROM Produto a INNER JOIN a.subgrupo s WHERE s.id = ?1")
	List<Produto> findSubgrupoEmProduto(Integer id);

	@Query("SELECT p FROM Produto p INNER JOIN p.subgrupo s INNER JOIN s.grupo g WHERE g.id = ?1")
	List<Produto> findProdutoPertencenteAoGrupo(Integer id);

}
