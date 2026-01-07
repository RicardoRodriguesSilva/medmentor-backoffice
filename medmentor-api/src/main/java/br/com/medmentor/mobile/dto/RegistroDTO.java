package br.com.medmentor.mobile.dto;

import br.com.medmentor.dto.GenericDTO;

public class RegistroDTO extends GenericDTO {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
    private String instituicao;
    private Integer anoFormacao;
    private String registro;
    
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
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
}
