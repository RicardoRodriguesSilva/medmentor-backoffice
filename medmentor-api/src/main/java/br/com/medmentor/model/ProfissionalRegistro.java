package br.com.medmentor.model;

import java.io.Serializable;

public class ProfissionalRegistro implements Serializable {
	
    private static final long serialVersionUID = 1L;
	
	private Profissional profissional;
	private String nomeInstituicao;
	private Integer numeroAnoFormacao;
	private String numeroRegistro;

	public ProfissionalRegistro() {
	}

	public Profissional getProfissional() {
		return profissional;
	}

	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}

	public String getNomeInstituicao() {
		return nomeInstituicao;
	}

	public void setNomeInstituicao(String nomeInstituicao) {
		this.nomeInstituicao = nomeInstituicao;
	}

	public Integer getNumeroAnoFormacao() {
		return numeroAnoFormacao;
	}

	public void setNumeroAnoFormacao(Integer numeroAnoFormacao) {
		this.numeroAnoFormacao = numeroAnoFormacao;
	}

	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	@Override
	public String toString() {
		return "ProfissionalRegistro [profissional=" + profissional + ", nomeInstituicao="
				+ nomeInstituicao + ", numeroAnoFormacao=" + numeroAnoFormacao + ", numeroRegistro=" + numeroRegistro
				+ "]";
	}
}