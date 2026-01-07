package br.com.medmentor.model;

import java.io.Serializable;

public class PessoaJuridica implements Serializable {
    
	private static final long serialVersionUID = 1L;

    private Integer id;
    private Pessoa pessoa; 
    private String cnpj;
    private String razaoSocial; 

    public PessoaJuridica() {}

    public PessoaJuridica(Pessoa pessoa, String cnpj, String razaoSocial) {
        this.pessoa = pessoa;
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
    }

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	@Override
    public String toString() {
       return "PessoaJuridica{" +
              "id=" + id +
              ", idPessoa=" + (pessoa != null ? pessoa.getId() : "null") +
              ", cnpj='" + cnpj + '\'' +
              '}';
    }
}