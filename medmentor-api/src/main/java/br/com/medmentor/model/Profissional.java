package br.com.medmentor.model;

import java.io.Serializable;

public class Profissional implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	private PessoaFisica pessoaFisica;

	public Profissional() {
		
	}

	public Profissional(PessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PessoaFisica getPessoaFisica() {
		return pessoaFisica;
	}

	public void setPessoaFisica(PessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

	@Override
	public String toString() {
		return "Profissional [id=" + id + ", pessoaFisica=" + pessoaFisica + "]";
	}
}