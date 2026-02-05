package br.com.medmentor.mobile.filtro.dto;

import br.com.medmentor.dto.GenericDTO;
import jakarta.ws.rs.QueryParam;

public class FiltroSolicitacaoMudancaDTO extends GenericDTO {
	
private static final long serialVersionUID = 1L;
	
	@QueryParam("idProfissional")
	private Integer idProfissional;
	
	@QueryParam("idGestoraSaude")
	private Integer idGestoraSaude;
	
	@QueryParam("idUnidadeSaude")
	private Integer idUnidadeSaude;
	
	@QueryParam("dataInicio")
	private String dataInicio;
	
	@QueryParam("dataFim")
	private String dataFim;

	public Integer getIdProfissional() {
		return idProfissional;
	}

	public void setIdProfissional(Integer idProfissional) {
		this.idProfissional = idProfissional;
	}

	public Integer getIdGestoraSaude() {
		return idGestoraSaude;
	}

	public void setIdGestoraSaude(Integer idGestoraSaude) {
		this.idGestoraSaude = idGestoraSaude;
	}

	public Integer getIdUnidadeSaude() {
		return idUnidadeSaude;
	}

	public void setIdUnidadeSaude(Integer idUnidadeSaude) {
		this.idUnidadeSaude = idUnidadeSaude;
	}

	public String getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getDataFim() {
		return dataFim;
	}

	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}

	@Override
	public String toString() {
		return "FiltroEscalaDTO [idProfissional=" + idProfissional + ", idGestoraSaude=" + idGestoraSaude
				+ ", idUnidadeSaude=" + idUnidadeSaude + ", dataInicio=" + dataInicio + ", dataFim=" + dataFim + "]";
	}
}
