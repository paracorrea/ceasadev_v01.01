package com.ceasa.dev.dto;

import java.io.Serializable;

public class SubgrupoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	Integer id;
	String nome;

	Integer grupo_id;
	
	
	private SubgrupoDTO() {
		
	}

	private SubgrupoDTO(Integer id, String nome, Integer grupo_id) {
		
		this.id = id;
		this.nome = nome;
		this.grupo_id = grupo_id;
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

	public Integer getGrupo_id() {
		return grupo_id;
	}

	public void setGrupo_id(Integer grupo_id) {
		this.grupo_id = grupo_id;
	}
	
	
	
	
	
}
