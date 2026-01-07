package br.com.medmentor.dto;

public class ProfissionalRegistroDTO extends GenericDTO {

	private static final long serialVersionUID = 1L;
	
	private ProfissionalDTO profissionalDTO;
    private String instituicao;
    private Integer anoFormacao;
    private String registro;

	public ProfissionalDTO getProfissionalDTO() {
		return profissionalDTO;
	}
	
	public void setProfissionalDTO(ProfissionalDTO profissionalDTO) {
		this.profissionalDTO = profissionalDTO;
	}
	
	public String getInstituicao() {
		return instituicao;
	}
	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}
	
	public Integer getAnoFormacao() {
		return anoFormacao;
	}
	
	public void setAnoFormacao(Integer anoFormacao) {
		this.anoFormacao = anoFormacao;
	}
	
	public String getRegistro() {
		return registro;
	}
	
	public void setRegistro(String registro) {
		this.registro = registro;
	}

	@Override
	public String toString() {
		return "RegistroProfissionalDTO [profissionalDTO=" + profissionalDTO + ", instituicao=" + instituicao
				+ ", anoFormacao=" + anoFormacao + ", registro=" + registro + "]";
	}	
}
