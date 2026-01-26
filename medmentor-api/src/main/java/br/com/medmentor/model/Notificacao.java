package br.com.medmentor.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Notificacao implements Serializable {
	   
	private static final long serialVersionUID = 1L;

	private Integer id;
	private LocalDateTime dataHora;
	private String descricao;
    private EmpresaProfissional empresaProfissional;
    private EmpresaUnidadeGestao empresaUnidadeGestao;
    private Boolean bolLida;
	
    public Notificacao() {
    	
    }
    
    public Notificacao(Integer id, LocalDateTime dataHora, String descricao,
			EmpresaProfissional empresaProfissional, EmpresaUnidadeGestao empresaUnidadeGestao,
			Boolean bolLida) {
		super();
		this.id = id;
		this.dataHora = dataHora;
		this.descricao = descricao;
		this.empresaProfissional = empresaProfissional;
		this.empresaUnidadeGestao = empresaUnidadeGestao;
		this.bolLida = bolLida;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public EmpresaProfissional getEmpresaProfissional() {
		return empresaProfissional;
	}

	public void setEmpresaProfissional(EmpresaProfissional empresaProfissional) {
		this.empresaProfissional = empresaProfissional;
	}

	public EmpresaUnidadeGestao getEmpresaUnidadeGestao() {
		return empresaUnidadeGestao;
	}

	public void setEmpresaUnidadeGestao(EmpresaUnidadeGestao empresaUnidadeGestao) {
		this.empresaUnidadeGestao = empresaUnidadeGestao;
	}

	public Boolean getBolLida() {
		return bolLida;
	}

	public void setBolLida(Boolean bolLida) {
		this.bolLida = bolLida;
	}

	@Override
	public String toString() {
		return "Notificacao [id=" + id + ", dataHora=" + dataHora + ", descricao=" + descricao
				+ ", empresaProfissional=" + empresaProfissional + ", empresaUnidadeGestao=" + empresaUnidadeGestao
				+ ", bolLida=" + bolLida + "]";
	}
}