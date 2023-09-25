package com.ceasa.dev.dto;

import java.io.Serializable;

public class GrupoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	Integer id;
	String nome;
	
	private GrupoDTO() {
		super();
	}

	private GrupoDTO(Integer id, String nome) {
		
		this.id = id;
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
	

}
