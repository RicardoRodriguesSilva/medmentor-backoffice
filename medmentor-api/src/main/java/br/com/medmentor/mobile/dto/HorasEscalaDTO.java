package br.com.medmentor.mobile.dto;

import br.com.medmentor.dto.GenericDTO;

public class HorasEscalaDTO extends GenericDTO {
	
    private static final long serialVersionUID = 1L;
    
    private EmpresaDTO empresaDTO;
    private GestoraSaudeDTO gestoraSaudeDTO;
    private UnidadeSaudeDTO unidadeSaudeDTO;    
    private String totalHorasTrabalhadas;
    private String totalHorasNaoTrabalhadas;
    private String totalHorasATrabalhar;
    
	public EmpresaDTO getEmpresaDTO() {
		return empresaDTO;
	}
	
	public void setEmpresaDTO(EmpresaDTO empresaDTO) {
		this.empresaDTO = empresaDTO;
	}
	
	public GestoraSaudeDTO getGestoraSaudeDTO() {
		return gestoraSaudeDTO;
	}
	
	public void setGestoraSaudeDTO(GestoraSaudeDTO gestoraSaudeDTO) {
		this.gestoraSaudeDTO = gestoraSaudeDTO;
	}
	
	public UnidadeSaudeDTO getUnidadeSaudeDTO() {
		return unidadeSaudeDTO;
	}
	
	public void setUnidadeSaudeDTO(UnidadeSaudeDTO unidadeSaudeDTO) {
		this.unidadeSaudeDTO = unidadeSaudeDTO;
	}
	
	public String getTotalHorasTrabalhadas() {
		return totalHorasTrabalhadas;
	}
	
	public void setTotalHorasTrabalhadas(String totalHorasTrabalhadas) {
		this.totalHorasTrabalhadas = totalHorasTrabalhadas;
	}
	
	public String getTotalHorasNaoTrabalhadas() {
		return totalHorasNaoTrabalhadas;
	}
	
	public void setTotalHorasNaoTrabalhadas(String totalHorasNaoTrabalhadas) {
		this.totalHorasNaoTrabalhadas = totalHorasNaoTrabalhadas;
	}
	
	public String getTotalHorasATrabalhar() {
		return totalHorasATrabalhar;
	}
	
	public void setTotalHorasATrabalhar(String totalHorasATrabalhar) {
		this.totalHorasATrabalhar = totalHorasATrabalhar;
	}

	@Override
	public String toString() {
		return "HorasEscalaDTO [empresaDTO=" + empresaDTO + ", gestoraSaudeDTO=" + gestoraSaudeDTO
				+ ", unidadeSaudeDTO=" + unidadeSaudeDTO + ", totalHorasTrabalhadas=" + totalHorasTrabalhadas
				+ ", totalHorasNaoTrabalhadas=" + totalHorasNaoTrabalhadas + ", totalHorasATrabalhar="
				+ totalHorasATrabalhar + ", getEmpresaDTO()=" + getEmpresaDTO() + ", getGestoraSaudeDTO()="
				+ getGestoraSaudeDTO() + ", getUnidadeSaudeDTO()=" + getUnidadeSaudeDTO()
				+ ", getTotalHorasTrabalhadas()=" + getTotalHorasTrabalhadas() + ", getTotalHorasNaoTrabalhadas()="
				+ getTotalHorasNaoTrabalhadas() + ", getTotalHorasATrabalhar()=" + getTotalHorasATrabalhar()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
}
