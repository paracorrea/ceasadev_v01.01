package com.ceasa.dev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ceasa.dev.dominio.Grupo;
import com.ceasa.dev.dominio.Produto;
import com.ceasa.dev.dominio.Subgrupo;
import com.ceasa.dev.repository.ProdutoRepository;
import com.ceasa.dev.service.exceptions.DataIntegrityException;
import com.ceasa.dev.service.exceptions.ObjectNotFoundException;

import jakarta.transaction.Transactional;



@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepo;
	@Transactional
	public Produto findById(Integer id) {
		
		Optional<Produto> obj = produtoRepo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado id: " + id + ", tipo: " + Grupo.class.getName()));
	}
	@Transactional
	public List<Produto> findAll() {
		List<Produto> listGroups = produtoRepo.findAll();
		return listGroups;
	}
	@Transactional
	public Produto insert(Produto obj) {
		obj.setId(null);
		return produtoRepo.save(obj);
	}
	@Transactional
	public Produto update(Produto obj) {
		findById(obj.getId());
		return produtoRepo.save(obj);
		
	}
	@Transactional
	public void delete(Integer id)  {
		
		findById(id);
		
		try {
			
			produtoRepo.deleteById(id);
			
		} catch (DataIntegrityViolationException e ) {
			
			throw new DataIntegrityException
			("Não é possível excluir um Grupo "
					+ "já associado a um subgrupo");
		}
	}
	
	@Transactional
	public List<Produto> findProdutoPorSubgrupo(Integer id) {
		List<Produto> listGroups = produtoRepo.findSubgrupoEmProduto(id);
		return listGroups;
	}
	
	
	@Transactional
	public List<Produto> findProdutoPertencenteAoGrupo(Integer id) {
		List<Produto> listGroups = produtoRepo.findProdutoPertencenteAoGrupo(id);
		return listGroups;
	}
	

	/*
	 * public void encontrar(String nome) {
	 * 
	 * grupoRepository.existeGrupoComNome(nome);
	 * 
	 * 
	 * }
	 */
	
	
	/*
	 * @Override
	 * 
	 * @Transactional(readOnly = true) public List<Grupo> buscarPorNome(String nome)
	 * { return dao.findByName(nome); }
	 */
	}
	

