package com.ceasa.dev.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ceasa.dev.dominio.Grupo;
import com.ceasa.dev.service.GrupoService;
import com.ceasa.dev.service.SubgrupoService;
import com.ceasa.dev.web.validator.GrupoValidator;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "/ceasadev")
public class GrupoController {

	
	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private SubgrupoService subService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
			
		binder.addValidators(new GrupoValidator(grupoService));
	}

	@GetMapping(value = "/grupos/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {

		Grupo obj = grupoService.findById(id);
		return ResponseEntity.ok().body(obj);

	}

	@GetMapping("/grupos/cadastrar")
	public String cadastrar(Grupo grupo,ModelMap model) {

		model.addAttribute("grupos", grupoService.findAll());
		return "/grupo/cadastro";

	}
	
	@PostMapping("/grupos/salvar")
	public String salvar(@Valid Grupo grupo, BindingResult result, RedirectAttributes attr, Model model) {
		
		if (result.hasErrors()) {
			return "/grupo/cadastro";
		}
		
		grupoService.insert(grupo);
		//attr.addFlashAttribute("success", "Grupo cadastrado com sucesso");
		model.addAttribute("message", "Grupo cadastrado com sucesso");
		return "/grupo/mensagem";
		
		//return "redirect:/ceasadev/grupos/cadastrar"; // return from method "/cadastrar" linha 20
	}
	
	
	@GetMapping("/grupos/listar")
	public String listar(ModelMap model) {

		model.addAttribute("grupos", grupoService.findAll());
		return "/grupo/lista";

	}
	

	
	
	@GetMapping("/grupos/editar/{id}")
	public String preEditar(@PathVariable("id") Integer id, ModelMap model) {
		model.addAttribute("grupo", grupoService.findById(id));
		return "/grupo/cadastro";
	}
	
	@PostMapping("/grupos/editar")
	public String editar(@Valid Grupo grupo, BindingResult result, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return "/grupo/cadastro";
		}
		
		grupoService.update(grupo);
		attr.addFlashAttribute("success", "Grupo editado com sucesso");
		return "redirect:/ceasadev/grupos/cadastrar";
	}
	
	@GetMapping("/grupos/excluir/{id}")
	public String excluir(@PathVariable("id") Integer id, ModelMap model) {
		
		if (grupoService.grupoTemSubgrupo(id)) {
			model.addAttribute("message", "Grupo não removido. Possui Subgrupo(s) vinculado(s)");
		} else {
			grupoService.delete(id);
			model.addAttribute("message", "Grupo excluido com sucesso");
		}
			
		return "/grupo/mensagem";
		//return listar(model);
	}
	
	
	
//	@GetMapping("/grupos")
//	public ResponseEntity<List<Grupo>> findAll() {
//
//		List<Grupo> listGrupos = grupoService.findAll();
//		// List<GrupoDAO> listDTO = listGrupos.stream().map(obj -> new
//		// GrupoDAO(obj)).collect(Collectors.toList());
//		return ResponseEntity.ok().body(listGrupos);
//
//	}
//	@PostMapping("/grupos/cadastrar")
//	public ResponseEntity<Void> cadastrar(@RequestBody Grupo obj) {
//
//		obj = grupoService.insert(obj);
//		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
//		return ResponseEntity.created(uri).build();
//
//	}	
//	@PutMapping("/grupos/{id}")
//	public ResponseEntity<Void> update(@RequestBody Grupo obj, @PathVariable Integer id) {
//
//		obj.setId(id);
//		obj = grupoService.update(obj);
//
//		return ResponseEntity.noContent().build();
//
//	}

	@DeleteMapping("/grupos/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {

		grupoService.delete(id);
		return ResponseEntity.noContent().build();

	}

	@GetMapping("/grupos/nome/{nome}")
	ResponseEntity<?> findByName(@PathVariable String nome) {

		List<Grupo> obj = grupoService.findByNome(nome);
		return ResponseEntity.ok().body(obj);

	}

}
