package com.ceasa.dev.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "grupos")
public class Grupo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@Column(name = "nome", length = 20)
	@NotNull(message="Campo não pode ser nullo")
	//@Size(min=5, message="Deve ter no mínimo 5 caracteres")
	String nome;

	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
	@OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL )

	private List<Subgrupo> subgrupos = new ArrayList<>();

	public Grupo() {

	}

	public Grupo(String nome) {
		super();
		this.nome = nome;
	}

	// 
	public void addSubgrupo(Subgrupo subgrupo) {

		this.subgrupos.add(subgrupo);
		subgrupo.setGrupo(this); // mantém a consistência

	}

	// Method getSubgroup return a list of the subgroups
	public List<Subgrupo> getSubgrupos() {
		List<Subgrupo> listaSegura = Collections.unmodifiableList(this.subgrupos);
		return listaSegura;
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

	public void setSubgrupos(List<Subgrupo> subgrupos) {
		this.subgrupos = subgrupos;
	}

	@Override
	public String toString() {
		return "Grupo [id=" + id + ", nome=" + nome + ", subgrupos=" + subgrupos + "]";
	}

}
