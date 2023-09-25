package com.ceasa.dev.web;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.ceasa.dev.dominio.Grupo;
import com.ceasa.dev.dominio.Subgrupo;
import com.ceasa.dev.service.GrupoService;
import com.ceasa.dev.service.SubgrupoService;

@Controller
@RequestMapping(value = "/ceasadev")
public class SubgrupoController {
	
		@Autowired
		private SubgrupoService subgrupoService;
		
		
		@Autowired
		private GrupoService grupoService;
		
		
		public SubgrupoController(SubgrupoService subgrupoService) {
			super();
			this.subgrupoService = subgrupoService;
		}

		@GetMapping("/subgrupos/view/{id}")
		public ResponseEntity<?> findById(@PathVariable Integer id) {
			
			Subgrupo obj = subgrupoService.findById(id);
			return ResponseEntity.ok().body(obj);	}
		
		@GetMapping("/subgrupos/all")
		public ResponseEntity<List<Subgrupo>> findAll1() {
			
			List<Subgrupo> listSubgrupos = subgrupoService.findAll();
			return ResponseEntity.ok().body(listSubgrupos)	;
		}
		
		@GetMapping("/subgrupos")
		public String findAll(Model model) {
			
			List<Subgrupo> listSubgrupos = subgrupoService.findAll();
		
			model.addAttribute("subgrupos",listSubgrupos);
			return "/subgrupo/listar";
		}
		
		// Lista todos os grupos de um determinado subgrupo
		@GetMapping("/subgrupos/grupos/{id}")
		public ResponseEntity<?> findGrupoPorSubgrupo(@PathVariable Integer id) {
			
			List<Subgrupo> obj = subgrupoService.findGrupoSubgrupo(id);
			return ResponseEntity.ok().body(obj);	}
		
	
		
		//Lista todos os subgrupos pelo nome passado
		@GetMapping("/subgrupos/nome/{nome}")
		public ResponseEntity<?> findSubgrupoByName(@PathVariable String nome){
			
			Subgrupo obj =subgrupoService.findByNome(nome);
			return ResponseEntity.ok().body(obj);		}
		
		
	
		
		@PostMapping("/subgrupos")
		public ResponseEntity<Void> insert(@RequestBody Subgrupo obj) {

			// antes de inserir o obj no service, fazer o find para pa
			obj = subgrupoService.insert(obj);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
			return ResponseEntity.created(uri).build();
		}
		
		// MÉTODO DE TESTE PARA AO INSERIR UM SUBGRUPO O MESMO POSSA ESTAR RELACIONADO COM UM GRUPO - ENTÃO DEVE-SE PASSAR 
		// O ID DO GRUPO PARA O RESP
		@PostMapping("/subgrupos/ingrupo/{id}")
		public ResponseEntity<Void> insertbyGrupo(@RequestBody Subgrupo obj,  @PathVariable Integer id) {

			
			// BUSCA O GRUPO QUAL O ID FOI PASSADO AO REST E FAZ O RELACIONAMENTO COM O MÉTIDO ADICIONA
			Grupo objSub = grupoService.findById(id);
			objSub.addSubgrupo(obj);
										
			obj = subgrupoService.insert(obj);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
			return ResponseEntity.created(uri).build();
		}
		
		
		
		
		@PutMapping("/subgrupos/{id}")
		public ResponseEntity<Void> update(@RequestBody Subgrupo obj, @PathVariable Integer id) {

			obj.setId(id);
			obj = subgrupoService.update(obj);

			return ResponseEntity.noContent().build();

		}
		
		@DeleteMapping("/subgrupos/{id}")
		public ResponseEntity<?> delete(@PathVariable Integer id) {

			subgrupoService.delete(id);
			return ResponseEntity.noContent().build();

		}
		
		@GetMapping(value = "/subgrupos/produtos/{id}")
		public ResponseEntity<?> findProdutoEmSubgrupo(@PathVariable Integer id) {

			List<Subgrupo> obj = (List<Subgrupo>) subgrupoService.findProdutoPorSubgrupo(id);
			return ResponseEntity.ok().body(obj);

		}

	}


