package br.com.medmentor.dto;

import java.io.Serializable;

public class ParametroDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
    private String nome;
    private String valor;

    public ParametroDTO() {
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

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "ParametroDTO [id=" + id + ", descricao=" + nome + ", valor=" + valor + "]";
	}
}