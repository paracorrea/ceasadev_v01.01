package com.ceasa.dev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ceasa.dev.dominio.Propriedade;

import com.ceasa.dev.repository.PropriedadeRepository;
import com.ceasa.dev.service.exceptions.DataIntegrityException;
import com.ceasa.dev.service.exceptions.ObjectNotFoundException;

@Service
public class PropriedadeService {

	
		@Autowired
		private PropriedadeRepository propRepo;
		
		public Propriedade insert(Propriedade obj) {
			return propRepo.save(obj);
		}
		
		
		public Propriedade findById(Integer id) {
			
			Optional<Propriedade> obj = propRepo.findById(id);
			return obj.orElseThrow(() -> new ObjectNotFoundException("Objecto não encontrado id: "+id+", tipo: "+Propriedade.class.getName()));
			
		}
		
		public List<Propriedade> findAll() {
			List<Propriedade> lista = propRepo.findAll();
			return lista;
		}
		
		
		public Propriedade update(Propriedade obj) {
			
			findById(obj.getId());
			return propRepo.save(obj);
		}
		
		public void delete(Integer id) {
			
			findById(id);
			
			try {
				
				propRepo.deleteById(id);
			} catch (DataIntegrityViolationException e) {
				
				throw new DataIntegrityException
				("Não é possível excluir um Subgrupo "
						+ "já associação ou com grupo ou com produto");
			}
		}
}
