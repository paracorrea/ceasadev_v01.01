package com.ceasa.dev;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.ceasa.dev.dominio.Grupo;
import com.ceasa.dev.dominio.Produto;
import com.ceasa.dev.dominio.Propriedade;

import com.ceasa.dev.dominio.Subgrupo;


import com.ceasa.dev.repository.GrupoRepository;
import com.ceasa.dev.repository.ProdutoRepository;
import com.ceasa.dev.repository.PropriedadeRepository;
import com.ceasa.dev.repository.SubgrupoRepository;


@SpringBootApplication
public class CeasadevApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(CeasadevApplication.class, args);
	}

	@Autowired
	private GrupoRepository grupoRepository;
	@Autowired
	private SubgrupoRepository subgrupoRepo;
	@Autowired
	private ProdutoRepository produtoRepo;
	@Autowired
	private PropriedadeRepository propriedadeRepo;
	
	
	
	@Override
	public void run(String... args) throws Exception {
		
		// Adiciona os Grupos
		Grupo gr1 = new Grupo("FRUTAS");
		Grupo gr2 = new Grupo("CEREAIS");
		Grupo gr3 = new Grupo("HORTALICAS");
		Grupo gr4 = new Grupo("FLORES");
		Grupo gr5 = new Grupo("AVES");
		Grupo gr6 = new Grupo("OVOS");
		Grupo gr7 = new Grupo("HIDROPONICOS");
		Grupo gr8 = new Grupo("PROCESSADOS");
		
		
		// Adiciona os Subgrupos
		Subgrupo sub1 = new Subgrupo("Frutas Importadas");
		Subgrupo sub2 = new Subgrupo("Frutas Nacionais");
		Subgrupo sub3 = new Subgrupo("Frutas Organicas");
		Subgrupo sub4 = new Subgrupo("Folhas, Hastes");
		Subgrupo sub5 = new Subgrupo("Legumes");
		Subgrupo sub6 = new Subgrupo("Ovo de Codorna");
		Subgrupo sub7 = new Subgrupo("Ovo Galinha Organico");
		Subgrupo sub8 = new Subgrupo("Folhas Orgânicas");
		Subgrupo sub9 = new Subgrupo("FAVOS");
		
		
		// Grupo FRUTAS recebe Frutas Importadas, Frutas Nacionais, Frutas Organicas
		gr1.addSubgrupo(sub1);
		gr1.addSubgrupo(sub2);
		gr1.addSubgrupo(sub3);
		
		// Grupo Cereais recebe Folhas, favos, hortaliças e legumes
		gr2.addSubgrupo(sub9);
		gr3.addSubgrupo(sub4);
		gr3.addSubgrupo(sub5);
		gr3.addSubgrupo(sub8);
		
		// Grupo Ovos recebe Ovos de codorna e ovos de galinha
		gr6.addSubgrupo(sub6);
		gr6.addSubgrupo(sub7);
		
		// Por ter Produto uma relação N - N com subgrupos, relaciona cada produto com os subgrupos
		Produto p1 = new Produto("MACA");
		Produto p2 = new Produto("BANANA");
		Produto p3 = new Produto("MAMAO");
		Produto p4 = new Produto("MELAO");
		Produto p5 = new Produto("MACA");
		
		
		
		
		// Subgrupo frutas nacionais recebe produto maca, banana, mamao, melao
		sub2.addProduto(p1);
		sub2.addProduto(p2);
		sub2.addProduto(p3);
		sub2.addProduto(p4);
		// subgrupo frutas importadas recebe maca
		sub1.addProduto(p5);
		
		
		Propriedade prop1 = new Propriedade();
		prop1.setVariedade("Galla");
		prop1.setSubvariedade(null);
		prop1.setUnidadePadrao("Kg");
		prop1.setPesoPadrao(20);
		prop1.setEmbalagemPadrao("Caixa");
		prop1.setEmbalagemPadrao("CAT I 100 - 120");
		
		p1.addPropriedade(prop1);
		
		
		grupoRepository.saveAll(Arrays.asList(gr1,gr2,gr3,gr4,gr5,gr6,gr7,gr8));
		subgrupoRepo.saveAll(Arrays.asList(sub1,sub2,sub3,sub4,sub5,sub6,sub7,sub8));
		produtoRepo.saveAll(Arrays.asList(p1,p2,p3,p4));
		propriedadeRepo.save(prop1);
		
	}



}
