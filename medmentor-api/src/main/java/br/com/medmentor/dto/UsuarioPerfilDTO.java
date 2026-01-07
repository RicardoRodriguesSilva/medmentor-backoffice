package br.com.medmentor.dto;

public class UsuarioPerfilDTO extends GenericDTO {
	
    private static final long serialVersionUID = 1L;

    private Integer id;
    private UsuarioDTO usuarioDTO;
    private PerfilDTO perfilDTO;

    public UsuarioPerfilDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public UsuarioDTO getUsuarioDTO() {
		return usuarioDTO;
	}

	public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	public PerfilDTO getPerfilDTO() {
		return perfilDTO;
	}

	public void setPerfilDTO(PerfilDTO perfilDTO) {
		this.perfilDTO = perfilDTO;
	}
}