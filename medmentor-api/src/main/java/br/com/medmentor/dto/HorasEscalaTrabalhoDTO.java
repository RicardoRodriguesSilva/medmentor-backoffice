package br.com.medmentor.dto;

public class HorasEscalaTrabalhoDTO extends GenericDTO {
	
    private static final long serialVersionUID = 1L;
    
    private EmpresaProfissionalDTO empresaProfissionalDTO;
    private EmpresaGestaoDTO empresaGestaoDTO;
    private EmpresaUnidadeGestaoDTO empresaUnidadeGestaoDTO;    
    private String totalHorasTrabalhadas;
    private String totalHorasNaoTrabalhadas;
    private String totalHorasATrabalhar;
    
	public EmpresaProfissionalDTO getEmpresaProfissionalDTO() {
		return empresaProfissionalDTO;
	}
	
	public void setEmpresaProfissionalDTO(EmpresaProfissionalDTO empresaProfissionalDTO) {
		this.empresaProfissionalDTO = empresaProfissionalDTO;
	}
	
	public EmpresaGestaoDTO getEmpresaGestaoDTO() {
		return empresaGestaoDTO;
	}
	
	public void setEmpresaGestaoDTO(EmpresaGestaoDTO empresaGestaoDTO) {
		this.empresaGestaoDTO = empresaGestaoDTO;
	}
	
	public EmpresaUnidadeGestaoDTO getEmpresaUnidadeGestaoDTO() {
		return empresaUnidadeGestaoDTO;
	}
	
	public void setEmpresaUnidadeGestaoDTO(EmpresaUnidadeGestaoDTO empresaUnidadeGestaoDTO) {
		this.empresaUnidadeGestaoDTO = empresaUnidadeGestaoDTO;
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
		return "HorasEscalaTrabalhoDTO [empresaProfissionalDTO=" + empresaProfissionalDTO + ", empresaGestaoDTO="
				+ empresaGestaoDTO + ", empresaUnidadeGestaoDTO=" + empresaUnidadeGestaoDTO + ", totalHorasTrabalhadas="
				+ totalHorasTrabalhadas + ", totalHorasNaoTrabalhadas=" + totalHorasNaoTrabalhadas
				+ ", totalHorasATrabalhar=" + totalHorasATrabalhar + "]";
	} 
}
