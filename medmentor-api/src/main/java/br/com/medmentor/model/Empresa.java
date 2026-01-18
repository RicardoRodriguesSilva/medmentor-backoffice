package br.com.medmentor.model;

import java.io.Serializable;

public class Empresa implements Serializable {
    
	private static final long serialVersionUID = 1L;	
	
    private Integer id; 
    private PessoaJuridica pessoaJuridica;
    private String nomeFantasia;
    private String nomeResponsavel;

    public Empresa() {}

    public Empresa(PessoaJuridica pessoaJuridica, String nomeFantasia, String nomeResponsavel) {
        this.pessoaJuridica = pessoaJuridica;
        this.nomeFantasia = nomeFantasia;
        this.nomeResponsavel = nomeResponsavel;
    }

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PessoaJuridica getPessoaJuridica() {
		return pessoaJuridica;
	}

	public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

	@Override
	public String toString() {
		return "Empresa [id=" + id + ", pessoaJuridica=" + pessoaJuridica + ", nomeFantasia=" + nomeFantasia
				+ ", nomeResponsavel=" + nomeResponsavel + "]";
	}
}