package br.com.medmentor.mobile.dto;

import java.util.List;

import br.com.medmentor.dto.GenericDTO;

public class GestoraSaudeDTO extends GenericDTO {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private List<UnidadeSaudeDTO> listaUnidadeSaude;

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

	public List<UnidadeSaudeDTO> getListaUnidadeSaude() {
		return listaUnidadeSaude;
	}

	public void setListaUnidadeSaude(List<UnidadeSaudeDTO> listaUnidadeSaude) {
		this.listaUnidadeSaude = listaUnidadeSaude;
	}

	@Override
	public String toString() {
		return "GestoraSaudeDTO [id=" + id + ", nome=" + nome + ", listaUnidadeSaude=" + listaUnidadeSaude + "]";
	}
	
}
