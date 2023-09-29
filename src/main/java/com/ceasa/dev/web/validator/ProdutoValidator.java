package com.ceasa.dev.web.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ceasa.dev.dominio.Produto;

import com.ceasa.dev.service.ProdutoService;


public class ProdutoValidator implements Validator {

	
	ProdutoService produtoService;
	
	
	
	public ProdutoValidator(ProdutoService produtoService) {
		this.produtoService=produtoService;
	}
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Produto.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		
		Produto d = (Produto) object;
		
		String nome = d.getNome();
		List<Produto> produtos = produtoService.findByNome(nome);
		
		if (nome !=null) {
			if (produtos.size() > 0) {
				errors.rejectValue("nome", "GrupoJaExiste.grupo.nome");
			}
		}
		
	}

}
