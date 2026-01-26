package br.com.medmentor.filtro.dto;

import br.com.medmentor.dto.GenericDTO;
import jakarta.ws.rs.QueryParam;

public class FiltroSolicitacaoMudancaEscalaDTO extends GenericDTO {
	
	private static final long serialVersionUID = 1L;
	
	@QueryParam("idEmpresaProfissional")
	private Integer idEmpresaProfissional;
	
	@QueryParam("idEscalaTrabalho")
	private Integer idEscalaTrabalho;
	
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

	public Integer getIdEscalaTrabalho() {
		return idEscalaTrabalho;
	}

	public void setIdEscalaTrabalho(Integer idEscalaTrabalho) {
		this.idEscalaTrabalho = idEscalaTrabalho;
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
		return "FiltroNotificacaoDTO [idEmpresaProfissional=" + idEmpresaProfissional + ", idEscalaTrabalho="
				+ idEscalaTrabalho + ", dataInicio=" + dataInicio + ", dataFim=" + dataFim + "]";
	}	
}
