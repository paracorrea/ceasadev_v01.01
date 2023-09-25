package com.ceasa.dev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ceasa.dev.dominio.Subgrupo;
import com.ceasa.dev.repository.SubgrupoRepository;
import com.ceasa.dev.service.exceptions.DataIntegrityException;
import com.ceasa.dev.service.exceptions.ObjectNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class SubgrupoService {
	
	@Autowired
	private SubgrupoRepository subRepo;
	
	
	
	

	
	public Subgrupo findById(Integer id) {
		
		
		Optional<Subgrupo> obj = subRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objecto não encontrado id: "+id+", tipo: "+Subgrupo.class.getName()));
	}
	
	public List<Subgrupo> findAll() {
		
		List<Subgrupo> lista = subRepo.findAll();
		
		return lista;
	}
	
	
	@Transactional
	public Subgrupo insert(Subgrupo obj) {
		
		
		
		
		obj.setId(null);
		
		
			
		// fazer verificação se objeto existe antes de salvar
		return subRepo.save(obj);
	}
		

	
	@Transactional
	public Subgrupo update(Subgrupo obj) {
		
		findById(obj.getId());
		
		// fazer verificação se objeto existe antes de salvar
		return subRepo.save(obj);
	}
	
	
	
	
	@Transactional
	public void delete(Integer id) {
		
		findById(id);
		
		try {
			
			subRepo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			
			throw new DataIntegrityException
			("Não é possível excluir um Subgrupo "
					+ "já associação ou com grupo ou com produto");
		}
	}


	public Subgrupo findByNome(String nome) {
		
		Subgrupo theQuery = subRepo.findSubgrupoByNome(nome);
		return theQuery;
	}
	
	@Transactional
	public List<Subgrupo> findProdutoPorSubgrupo(Integer id) {
		List<Subgrupo> listGroups = subRepo.findProdutoEmSubgrupo(id);
		return listGroups;
	}
	@Transactional
	public List<Subgrupo> findGrupoSubgrupo(Integer id) {
		List<Subgrupo> listGrupos = subRepo.findGrupoPorSubgrupo(id);
		return listGrupos;
		
	}
	
}

