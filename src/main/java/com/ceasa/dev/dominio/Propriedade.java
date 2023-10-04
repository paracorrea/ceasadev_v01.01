package com.ceasa.dev.dominio;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Propriedade  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Column(name="variedade", length = 25)
	String variedade;
	
	@Column(name="subvariedade", length = 25)
	String subvariedade;
	@Column(name="classificacao", length = 25)
	String classificacao;
	
	@Column(name="unidade", length = 2)
	String unidadePadrao;
	
	@Column(name="peso")
	Integer pesoPadrao;
	
	@Column(name="embalagemPadrao", length = 25)
	String EmbalagemPadrao;
	
	
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
	
	@ManyToOne(cascade= {CascadeType.PERSIST,CascadeType.MERGE,	CascadeType.DETACH,
							CascadeType.REFRESH})
	@JoinColumn(name = "produto_id")
	@JsonIgnore
	private Produto produto;
	
	public Propriedade() {
		
	}




	public Propriedade(String variedade, String subvariedade, String classificacao, String unidadePadrao,
			Integer pesoPadrao, String embalagemPadrao) {
		super();
		this.variedade = variedade;
		this.subvariedade = subvariedade;
		this.classificacao = classificacao;
		this.unidadePadrao = unidadePadrao;
		this.pesoPadrao = pesoPadrao;
		this.EmbalagemPadrao = embalagemPadrao;
	}




	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getVariedade() {
		return variedade;
	}


	public void setVariedade(String variedade) {
		if (variedade != null) {
		this.variedade = variedade.toUpperCase();}
		this.variedade = variedade;
	}


	public String getSubvariedade() {
		return subvariedade;
	}


	public void setSubvariedade(String subvariedade) {
		if (subvariedade !=null) {
		this.subvariedade = subvariedade.toUpperCase();}
		this.subvariedade = subvariedade;
	}


	public String getUnidadePadrao() {
		return unidadePadrao;
	}


	public void setUnidadePadrao(String unidadePadrao) {
		this.unidadePadrao = unidadePadrao;
	}


	public Integer getPesoPadrao() {
		return pesoPadrao;
	}


	public void setPesoPadrao(Integer pesoPadrao) {
		this.pesoPadrao = pesoPadrao;
	}


	public String getEmbalagemPadrao() {
		return EmbalagemPadrao;
	}


	public void setEmbalagemPadrao(String embalagemPadrao) {
		EmbalagemPadrao = embalagemPadrao;
	}

	

	public String getClassificacao() {
		return classificacao;
	}




	public void setClassificacao(String classificacao) {
		if (classificacao !=null) {
		this.classificacao = classificacao.toUpperCase();}
		
		this.classificacao = classificacao;
	}




	public Produto getProduto() {
		return produto;
	}


	public void setProduto(Produto produto) {
		this.produto = produto;
	}


	@Override
	public String toString() {
		return "Propriedade [id=" + id + ", variedade=" + variedade + ", subvariedade=" + subvariedade
				+ ", unidadePadrao=" + unidadePadrao + ", pesoPadrao=" + pesoPadrao + ", EmbalagemPadrao="
				+ EmbalagemPadrao + ", produto=" + produto + "]";
	}


	


	
	
	
	
	
	
	
	
}
