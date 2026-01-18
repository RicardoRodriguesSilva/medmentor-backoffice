package br.com.medmentor.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class GeracaoEscalaDTO implements Serializable {
    
	private static final long serialVersionUID = 1L;

    private Integer id;
    private EmpresaUnidadeGestaoDTO empresaUnidadeGestaoDTO;
    private LocalDate dataGeracao;

    public GeracaoEscalaDTO() {
    	
    }

	public GeracaoEscalaDTO(Integer id, EmpresaUnidadeGestaoDTO empresaUnidadeGestao, LocalDate dataGeracao) {
		super();
		this.id = id;
		this.empresaUnidadeGestaoDTO = empresaUnidadeGestao;
		this.dataGeracao = dataGeracao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EmpresaUnidadeGestaoDTO getEmpresaUnidadeGestaoDTO() {
		return empresaUnidadeGestaoDTO;
	}

	public void setEmpresaUnidadeGestaoDTO(EmpresaUnidadeGestaoDTO empresaUnidadeGestaoDTO) {
		this.empresaUnidadeGestaoDTO = empresaUnidadeGestaoDTO;
	}

	public LocalDate getDataGeracao() {
		return dataGeracao;
	}

	public void setDataGeracao(LocalDate dataGeracao) {
		this.dataGeracao = dataGeracao;
	}

	@Override
	public String toString() {
		return "GeracaoEscala [id=" + id + ", empresaUnidadeGestao=" + empresaUnidadeGestaoDTO + ", dataGeracao="
				+ dataGeracao + "]";
	}
}