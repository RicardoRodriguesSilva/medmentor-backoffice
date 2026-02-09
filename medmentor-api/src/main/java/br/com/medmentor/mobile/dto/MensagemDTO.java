package br.com.medmentor.mobile.dto;

import java.time.LocalDateTime;

import br.com.medmentor.dto.GenericDTO;

public class MensagemDTO extends GenericDTO {
	
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private LocalDateTime dataHora;
	private String descricao;
	private Boolean bolLida;
	
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
	
	public Boolean getBolLida() {
		return bolLida;
	}
	
	public void setBolLida(Boolean bolLida) {
		this.bolLida = bolLida;
	}

	@Override
	public String toString() {
		return "MensagemDTO [id=" + id + ", dataHora=" + dataHora + ", descricao=" + descricao + ", bolLida=" + bolLida
				+ "]";
	}
}
