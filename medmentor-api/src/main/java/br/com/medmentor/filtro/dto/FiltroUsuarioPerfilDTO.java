package br.com.medmentor.filtro.dto;

import br.com.medmentor.dto.GenericDTO;
import jakarta.ws.rs.QueryParam;

public class FiltroUsuarioPerfilDTO extends GenericDTO {
	   
	private static final long serialVersionUID = 1L;
	
	@QueryParam("idUsuario")
	private Integer idUsuario;
	
	@QueryParam("idPerfil")
	private Integer idPerfil;

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Integer idPerfil) {
		this.idPerfil = idPerfil;
	}

	@Override
	public String toString() {
		return "FiltroUsuarioPerfilDTO [idUsuario=" + idUsuario + ", idPerfil=" + idPerfil + "]";
	}
}