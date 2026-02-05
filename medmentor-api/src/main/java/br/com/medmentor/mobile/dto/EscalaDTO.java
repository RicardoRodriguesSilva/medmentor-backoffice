package br.com.medmentor.mobile.dto;

import java.time.LocalDateTime;

import br.com.medmentor.dto.GenericDTO;

public class EscalaDTO extends GenericDTO {
	
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private Integer idGestoraSaude;
    private String nomeGestoraSaude;
    private Integer idUnidadeSaude;
    private String nomeUnidadeSaude;
    private Integer idProfissional;
    private LocalDateTime dataHoraEntrada;
    private LocalDateTime dataHoraSaida;
    private Boolean bolAtivo;
    private Boolean bolRealizado;
    private Boolean bolDisponibilizado;
    private Boolean bolEscalaTerceiro;
	
    public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getIdGestoraSaude() {
		return idGestoraSaude;
	}
	
	public void setIdGestoraSaude(Integer idGestoraSaude) {
		this.idGestoraSaude = idGestoraSaude;
	}
	
	public String getNomeGestoraSaude() {
		return nomeGestoraSaude;
	}
	
	public void setNomeGestoraSaude(String nomeGestoraSaude) {
		this.nomeGestoraSaude = nomeGestoraSaude;
	}
	
	public Integer getIdUnidadeSaude() {
		return idUnidadeSaude;
	}
	
	public void setIdUnidadeSaude(Integer idUnidadeSaude) {
		this.idUnidadeSaude = idUnidadeSaude;
	}
	
	public String getNomeUnidadeSaude() {
		return nomeUnidadeSaude;
	}
	
	public void setNomeUnidadeSaude(String nomeUnidadeSaude) {
		this.nomeUnidadeSaude = nomeUnidadeSaude;
	}
	
	public Integer getIdProfissional() {
		return idProfissional;
	}
	
	public void setIdProfissional(Integer idProfissional) {
		this.idProfissional = idProfissional;
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
	
	public Boolean getBolEscalaTerceiro() {
		return bolEscalaTerceiro;
	}
	
	public void setBolEscalaTerceiro(Boolean bolEscalaTerceiro) {
		this.bolEscalaTerceiro = bolEscalaTerceiro;
	}
	
	public Boolean getBolDisponibilizado() {
		return bolDisponibilizado;
	}

	public void setBolDisponibilizado(Boolean bolDisponibilizado) {
		this.bolDisponibilizado = bolDisponibilizado;
	}

	@Override
	public String toString() {
		return "EscalaDTO [id=" + id + ", idGestoraSaude=" + idGestoraSaude + ", nomeGestoraSaude=" + nomeGestoraSaude
				+ ", idUnidadeSaude=" + idUnidadeSaude + ", nomeUnidadeSaude=" + nomeUnidadeSaude + ", idProfissional="
				+ idProfissional + ", dataHoraEntrada=" + dataHoraEntrada + ", dataHoraSaida=" + dataHoraSaida
				+ ", bolAtivo=" + bolAtivo + ", bolRealizado=" + bolRealizado + ", bolDisponibilizado="
				+ bolDisponibilizado + ", bolEscalaTerceiro=" + bolEscalaTerceiro + "]";
	}
}