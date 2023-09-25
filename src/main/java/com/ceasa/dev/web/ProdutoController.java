package com.ceasa.dev.web;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ceasa.dev.dominio.Produto;
import com.ceasa.dev.service.ProdutoService;

@Controller
@RequestMapping(value = "/ceasadev")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;
	
	

	public ProdutoController(ProdutoService produtoService) {
		super();
		this.produtoService = produtoService;
	}
	
	@GetMapping(value = "/produtos/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {

		Produto obj = produtoService.findById(id);
		return ResponseEntity.ok().body(obj);

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
	
	@GetMapping("/produtos")
	public ResponseEntity<List<Produto>> findAll() {

		List<Produto> listProdutos = produtoService.findAll();
		// List<GrupoDAO> listDTO = listGrupos.stream().map(obj -> new
		// GrupoDAO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listProdutos);

	}

	@PostMapping("/produtos")
	public ResponseEntity<Void> insert(@RequestBody Produto obj) {

		obj = produtoService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();

	}

	@PutMapping("/produtos/{id}")
	public ResponseEntity<Void> update(@RequestBody Produto obj, @PathVariable Integer id) {

		obj.setId(id);
		obj = produtoService.update(obj);

		return ResponseEntity.noContent().build();

	}

	@DeleteMapping("/produtos/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {

		produtoService.delete(id);
		return ResponseEntity.noContent().build();

	}
	
}
