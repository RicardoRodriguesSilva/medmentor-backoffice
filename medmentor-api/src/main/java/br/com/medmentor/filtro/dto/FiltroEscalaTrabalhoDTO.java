package br.com.medmentor.filtro.dto;

import br.com.medmentor.dto.GenericDTO;
import jakarta.ws.rs.QueryParam;

public class FiltroEscalaTrabalhoDTO extends GenericDTO {
	   
	private static final long serialVersionUID = 1L;
	
	@QueryParam("idEmpresaProfissional")
	private Integer idEmpresaProfissional;
	
	@QueryParam("idEmpresaUnidadeGestao")
	private Integer idEmpresaUnidadeGestao;
	
	@QueryParam("dataInicio")
	private String dataInicio;
	
	@QueryParam("dataFim")
	private String dataFim;
	
	public Integer getIdEmpresaProfissional() {
		return idEmpresaProfissional;
	}
	
	public void setIdEmpresaProfissional(Integer idEmpresaProfissional) {
		this.idEmpresaProfissional = idEmpresaProfissional;
	}
	
	public Integer getIdEmpresaUnidadeGestao() {
		return idEmpresaUnidadeGestao;
	}
	
	public void setIdEmpresaUnidadeGestao(Integer idEmpresaUnidadeGestao) {
		this.idEmpresaUnidadeGestao = idEmpresaUnidadeGestao;
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
		return "FiltroGeracaoEscalaDTO [idEmpresaProfissional=" + idEmpresaProfissional + ", idEmpresaUnidadeGestao="
				+ idEmpresaUnidadeGestao + ", dataInicio=" + dataInicio + ", dataFim=" + dataFim + "]";
	}

}
