package com.ceasa.dev.web;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ceasa.dev.dominio.Grupo;
import com.ceasa.dev.dominio.Subgrupo;
import com.ceasa.dev.service.GrupoService;
import com.ceasa.dev.service.SubgrupoService;
import com.ceasa.dev.web.validator.SubgrupoValidator;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "/ceasadev")
public class SubgrupoController {
	
		@Autowired
		private SubgrupoService subgrupoService;
		
		
		@Autowired
		private GrupoService grupoService;
		
		@InitBinder
		public void initBinder(WebDataBinder binder) {
				
			binder.addValidators(new SubgrupoValidator(subgrupoService));
		}
		
		public SubgrupoController(SubgrupoService subgrupoService) {
			
			this.subgrupoService = subgrupoService;
		}

		
		@GetMapping("/subgrupos/cadastrar")
		public String cadastrar(Subgrupo subgrupos, ModelMap model) {
			model.addAttribute("subgrupos", subgrupoService.findAll());						
			return "/subgrupo/cadastro";
		}
		
		@GetMapping("/subgrupos/listar")
		public String findAll(ModelMap model) {
			
			
			
			List<Subgrupo> listSubgrupos = subgrupoService.findAll();
			model.addAttribute("subgrupos",listSubgrupos);
			
			return "/subgrupo/listar";
		}
		
		
		@GetMapping("/subgrupos/editar/{id}")
		public String preEditar(@PathVariable("id") Integer id, ModelMap model) {
			model.addAttribute("subgrupos", subgrupoService.findById(id));
			return "/subgrupo/cadastro";
		}
		
		@PostMapping("/subgrupos/editar")
		public String editar(@Valid Subgrupo subgrupo, BindingResult result, RedirectAttributes attr) {
			
			if (result.hasErrors()) {
				return "/subgrupo/cadastro";
			}
			
			subgrupoService.update(subgrupo);
			attr.addFlashAttribute("message", "Subgrupo editado com sucesso");
			return "redirect:/ceasadev/subgrupos/cadastrar";
		}
		
		
		@ModelAttribute("departamentos")
		public List<Grupo> listaDepartamentos() {
			return grupoService.findAll();
		}
		
		
		@PostMapping("/subgrupos/salvar")
		public String salvar(@Valid Subgrupo subgrupo, BindingResult result, RedirectAttributes attr) {

			if (result.hasErrors()) {
				return "/subgrupo/cadastro";
			}
			
			// antes de inserir o obj no service, fazer o find para pa
			subgrupoService.insert(subgrupo);
			
			attr.addAttribute("message", "Subgrupo cadastrado com sucesso");
			
			return "/grupo/mensagem";
		}
		
		@DeleteMapping("/subgrupos/excluir/{id}")
		public String excluir(@PathVariable("id") Integer id, RedirectAttributes attr)  {

			
			if (subgrupoService.subgrupoTemProdutos(id)) {
				attr.addFlashAttribute("message", "subgrupo não removido. Possui Produtos");
				return "redirect:/cargos/listar";
				
			} else {
			subgrupoService.delete(id);
			
			attr.addFlashAttribute("message", "Subgrupo removido com sucesso");		
			return "redirect:/ceasdev/subgrupos/listar";

		}
		}
		
		//@GetMapping("/subgrupos/view/{id}")
		//public ResponseEntity<?> findById(@PathVariable Integer id) {
			
		//	Subgrupo obj = subgrupoService.findById(id);
		//	return ResponseEntity.ok().body(obj);	}
		
	//	@GetMapping("/subgrupos/all")
		//public ResponseEntity<List<Subgrupo>> findAll1() {
			
		//	List<Subgrupo> listSubgrupos = subgrupoService.findAll();
		//	return ResponseEntity.ok().body(listSubgrupos)	;
		//}
		
	
		
	
		
		
		
		// Lista todos os grupos de um determinado subgrupo
		@GetMapping("/subgrupos/grupos/{id}")
		public String findGrupoPorSubgrupo(@PathVariable Integer id, ModelMap model) {
			
			List<Subgrupo> obj = subgrupoService.findGrupoSubgrupo(id);
			model.addAttribute("subgrupos", obj);
			return "/subgrupo/listar";	}
		
	
		
		//Lista todos os subgrupos pelo nome passado
		//@GetMapping("/subgrupos/nome/{nome}")
		//public ResponseEntity<?> findSubgrupoByName(@PathVariable String nome){
			
		//	List<Subgrupo> obj =subgrupoService.findByNome(nome);
		//	return ResponseEntity.ok().body(obj);		}
		
		
	
		
		//@PostMapping("/subgrupos")
		//public ResponseEntity<Void> insert(@RequestBody Subgrupo obj) {

			// antes de inserir o obj no service, fazer o find para pa
		//	obj = subgrupoService.insert(obj);
		//	URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		//	return ResponseEntity.created(uri).build();
		//}
		
		
	
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
		
	
		
		//@GetMapping(value = "/subgrupos/produtos/{id}")
		//public ResponseEntity<?> findProdutoEmSubgrupo(@PathVariable Integer id) {

		//	List<Subgrupo> obj = (List<Subgrupo>) subgrupoService.findProdutoPorSubgrupo(id);
		//	return ResponseEntity.ok().body(obj);

		//}

	}


