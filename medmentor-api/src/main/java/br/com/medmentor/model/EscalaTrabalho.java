package br.com.medmentor.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class EscalaTrabalho implements Serializable {
    
	private static final long serialVersionUID = 1L;

    private Integer id;
    private EmpresaProfissional empresaProfissional;
    private EmpresaUnidadeGestao empresaUnidadeGestao;
    private LocalDateTime dataHoraEntrada;
    private LocalDateTime dataHoraSaida;
    private Boolean bolAtivo;
    private Boolean bolRealizado;

    public EscalaTrabalho() {
    	
    }

	public EscalaTrabalho(Integer id, EmpresaProfissional empresaProfissional,
			EmpresaUnidadeGestao empresaUnidadeGestao, LocalDateTime dataHoraEntrada, LocalDateTime dataHoraSaida,
			Boolean bolAtivo, Boolean bolRealizado) {
		super();
		this.id = id;
		this.empresaProfissional = empresaProfissional;
		this.empresaUnidadeGestao = empresaUnidadeGestao;
		this.dataHoraEntrada = dataHoraEntrada;
		this.dataHoraSaida = dataHoraSaida;
		this.bolAtivo = bolAtivo;
		this.bolRealizado = bolRealizado;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public LocalDateTime getDataHoraEntrada() {
		return dataHoraEntrada;
	}

	public void setDataHoraEntrada(LocalDateTime dataHoraEntrada) {
		this.dataHoraEntrada = dataHoraEntrada;
	}

	public LocalDateTime getDataHoraSaida() {
		return dataHoraSaida;
	}

	public void setDataHoraSaida(LocalDateTime dataHoraSaida) {
		this.dataHoraSaida = dataHoraSaida;
	}

	public Boolean getBolAtivo() {
		return bolAtivo;
	}

	public void setBolAtivo(Boolean bolAtivo) {
		this.bolAtivo = bolAtivo;
	}

	public Boolean getBolRealizado() {
		return bolRealizado;
	}

	public void setBolRealizado(Boolean bolRealizado) {
		this.bolRealizado = bolRealizado;
	}

	@Override
	public String toString() {
		return "EscalaTrabalho [id=" + id + ", empresaProfissional=" + empresaProfissional + ", empresaUnidadeGestao="
				+ empresaUnidadeGestao + ", dataHoraEntrada=" + dataHoraEntrada + ", dataHoraSaida=" + dataHoraSaida
				+ ", bolAtivo=" + bolAtivo + ", bolRealizado=" + bolRealizado + "]";
	}
  
}