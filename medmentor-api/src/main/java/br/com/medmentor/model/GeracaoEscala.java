package br.com.medmentor.model;

import java.io.Serializable;
import java.time.LocalDate;

public class GeracaoEscala implements Serializable {
    
	private static final long serialVersionUID = 1L;

    private Integer id;
    private EmpresaUnidadeGestao empresaUnidadeGestao;
    private LocalDate dataGeracao;

    public GeracaoEscala() {
    	
    }

	public GeracaoEscala(Integer id, EmpresaUnidadeGestao empresaUnidadeGestao, LocalDate dataGeracao) {
		super();
		this.id = id;
		this.empresaUnidadeGestao = empresaUnidadeGestao;
		this.dataGeracao = dataGeracao;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EmpresaUnidadeGestao getEmpresaUnidadeGestao() {
		return empresaUnidadeGestao;
	}

	public void setEmpresaUnidadeGestao(EmpresaUnidadeGestao empresaUnidadeGestao) {
		this.empresaUnidadeGestao = empresaUnidadeGestao;
	}

	public LocalDate getDataGeracao() {
		return dataGeracao;
	}

	public void setDataGeracao(LocalDate dataGeracao) {
		this.dataGeracao = dataGeracao;
	}

	@Override
	public String toString() {
		return "GeracaoEscala [id=" + id + ", empresaUnidadeGestao=" + empresaUnidadeGestao + ", dataGeracao="
				+ dataGeracao + "]";
	}
}