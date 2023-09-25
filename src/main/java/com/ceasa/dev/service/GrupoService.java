package com.ceasa.dev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceasa.dev.dominio.Grupo;
import com.ceasa.dev.dominio.Subgrupo;
import com.ceasa.dev.repository.GrupoRepository;
import com.ceasa.dev.service.exceptions.ObjectNotFoundException;

import jakarta.transaction.Transactional;



@Service
public class GrupoService {

	@Autowired
	private GrupoRepository grupoRepository;
	
	
	
	public Grupo findById(Integer id) {
		
		Optional<Grupo> obj = grupoRepository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado id: " + id + ", tipo: " + Grupo.class.getName()));
	}
	
	public List<Grupo> findAll() {
		List<Grupo> listGroups = grupoRepository.findAll();
		return listGroups;
	}
	@Transactional
	public Grupo insert(Grupo obj) {
		obj.setId(null);
		return grupoRepository.save(obj);
	}
	
	@Transactional
	public Grupo update(Grupo obj) {
		findById(obj.getId());
		return grupoRepository.save(obj);
		
	}
	
	public List<Grupo> findByNome(String nome) {
		
		List<Grupo> theQuery = grupoRepository.findByNome(nome);
		return theQuery;
	}
	
	@Transactional
	public void delete(Integer id)  {
		
		Grupo tempGrupo = findById(id);
		
		// When a "Groups" were deleted, then is necessary set null for "Subgroups" objects with each order
		List<Subgrupo> subgrupos = tempGrupo.getSubgrupos();
		
		for (Subgrupo tempSubgrupos: subgrupos  ) {
			tempSubgrupos.setGrupo(null);
		}
		
			
			grupoRepository.deleteById(id);
			
			}

	public boolean grupoTemSubgrupo(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

	}
	

