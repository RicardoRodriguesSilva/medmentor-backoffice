package br.com.medmentor.dto;

public class ChaveHorasEscalaTrabalhoDTO extends GenericDTO {

	private static final long serialVersionUID = 1L;
	
	private Integer idEmpresaProfissional;
	private Integer idEmpresaGestao;
	private Integer idEmpresaUnidadeGestao;

	public Integer getIdEmpresaProfissional() {
		return idEmpresaProfissional;
	}

	public void setIdEmpresaProfissional(Integer idEmpresaProfissional) {
		this.idEmpresaProfissional = idEmpresaProfissional;
	}

	public Integer getIdEmpresaGestao() {
		return idEmpresaGestao;
	}
	
	public void setIdEmpresaGestao(Integer idEmpresaGestao) {
		this.idEmpresaGestao = idEmpresaGestao;
	}
	
	public Integer getIdEmpresaUnidadeGestao() {
		return idEmpresaUnidadeGestao;
	}
	
	public void setIdEmpresaUnidadeGestao(Integer idEmpresaUnidadeGestao) {
		this.idEmpresaUnidadeGestao = idEmpresaUnidadeGestao;
	}

	@Override
	public String toString() {
		return "ChaveHorasEscalaTrabalhoDTO [idEmpresaProfissional=" + idEmpresaProfissional + ", idEmpresaGestao=" + idEmpresaGestao
				+ ", idEmpresaUnidadeGestao=" + idEmpresaUnidadeGestao + "]";
	}
}
