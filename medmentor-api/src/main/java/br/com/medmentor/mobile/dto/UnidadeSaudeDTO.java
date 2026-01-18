package br.com.medmentor.mobile.dto;

import br.com.medmentor.dto.GenericDTO;

public class UnidadeSaudeDTO  extends GenericDTO {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String nome;

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
