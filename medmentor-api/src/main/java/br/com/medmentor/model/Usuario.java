package br.com.medmentor.model;

import java.io.Serializable;

public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
    private PessoaFisica pessoaFisica; 
    private String nomeUsuario;
    private String senhaUsuario;
    private Boolean bolAtivo;

    public Usuario() {
    }

    public PessoaFisica getPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenhaUsuario() {
        return senhaUsuario;
    }

    public void setSenhaUsuario(String senhaUsuario) {
        this.senhaUsuario = senhaUsuario;
    }

    public Boolean getBolAtivo() {
        return bolAtivo;
    }

    public void setBolAtivo(Boolean bolAtivo) {
        this.bolAtivo = bolAtivo;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", pessoaFisica=" + pessoaFisica + ", nomeUsuario=" + nomeUsuario
				+ ", senhaUsuario=" + senhaUsuario + ", bolAtivo=" + bolAtivo + "]";
	}


}