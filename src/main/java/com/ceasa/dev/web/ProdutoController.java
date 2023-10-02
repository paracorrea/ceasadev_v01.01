package com.ceasa.dev.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ceasa.dev.dominio.Produto;
import com.ceasa.dev.dominio.Subgrupo;
import com.ceasa.dev.service.ProdutoService;
import com.ceasa.dev.service.SubgrupoService;
import com.ceasa.dev.web.validator.ProdutoValidator;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "/ceasadev")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private SubgrupoService subService;
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
			
		binder.addValidators(new ProdutoValidator(produtoService));
	}
	

	public ProdutoController(ProdutoService produtoService) {
		super();
		this.produtoService = produtoService;
	}
	
	@ModelAttribute("subgrupos")
	public List<Subgrupo> listaGrupos() {
		return subService.findAll();
	}
	
	
	
	@GetMapping("/produtos/cadastrar")
	public String listarProdutos(Produto produto , ModelMap model) {
		List<Produto> lista = produtoService.findAll();
		model.addAttribute("produtos", lista);
		return "/produto/cadastro";
	
}
	
	
	@GetMapping("/produtos/editar/{id}")
	public String preEditar(@PathVariable("id") Integer id, ModelMap model) {

		model.addAttribute("produto", produtoService.findById(id));
		return "/produto/cadastro";

	}
	
		
	@PostMapping("/produtos/editar")
	public String editar (@Valid Produto produto,BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "/produto/cadastro";
		}
		
		
		produtoService.update(produto);
		attr.addFlashAttribute("mensagem", "Produto editado com sucesso");
		return "redirect:/ceasadev/produtos/cadastrar";

	}
	
	@PostMapping("/produtos/salvar")
	public String salvar(@Valid Produto produto,BindingResult result, Model model) {

		

		if (result.hasErrors()) {
			return "/produto/cadastro";
		}
		
		
		produtoService.insert(produto);
		model.addAttribute("mensagem", "Subgrupo cadastrado com sucesso");
		return "/produto/mensagem";
		
	
	}
	
	@GetMapping("/produtos/excluir/{id}")
	public String excluir(@PathVariable("id") Integer id, Model model) {

		if (produtoService.produtoTemPropriedades(id)) {
			model.addAttribute("mensagem", "Produto não pode ser removido. Possui propriedades");
			
			
		} else {
		
		produtoService.delete(id);
		model.addAttribute("mensagem","Produto excluído com sucesso");		
		
		}		
		return "/produto/mensagem";

	
	}
	
	
	@GetMapping(value = "/produtos/subgrupos/{id}")
	public ResponseEntity<?> findProdutoEmSubgrupo(@PathVariable Integer id) {

		List<Produto> obj = produtoService.findProdutoPorSubgrupo(id);
		return ResponseEntity.ok().body(obj);

	}

	// Query testada
	@GetMapping(value = "/produtos/grupos/{id}")
	public ResponseEntity<?> findProdutoPertencenteAoGrupo(@PathVariable Integer id) {

		List<Produto> obj = produtoService.findProdutoPertencenteAoGrupo(id);
		return ResponseEntity.ok().body(obj);

	}
	
	
	@GetMapping("/produtos/listar")
	public String findAll(ModelMap model) {
		
		
		
		List<Produto> listSubgrupos = produtoService.findAll();
		model.addAttribute("produtos",listSubgrupos);
		
		return "/produto/listar";
	

	}

	

	



}
