package br.com.medmentor.dto;

public class PerfilMenuAcaoDTO extends GenericDTO {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private PerfilDTO perfilDTO; 
    private MenuDTO menuDTO;   
    private AcaoDTO acaoDTO;   

    public PerfilMenuAcaoDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public PerfilDTO getPerfilDTO() {
		return perfilDTO;
	}

	public void setPerfilDTO(PerfilDTO perfilDTO) {
		this.perfilDTO = perfilDTO;
	}

	public MenuDTO getMenuDTO() {
		return menuDTO;
	}

	public void setMenuDTO(MenuDTO menuDTO) {
		this.menuDTO = menuDTO;
	}

	public AcaoDTO getAcaoDTO() {
		return acaoDTO;
	}

	public void setAcaoDTO(AcaoDTO acaoDTO) {
		this.acaoDTO = acaoDTO;
	}

	@Override
	public String toString() {
		return "PerfilMenuAcaoDTO [id=" + id + ", perfilDTO=" + perfilDTO + ", menuDTO=" + menuDTO + ", acaoDTO="
				+ acaoDTO + "]";
	}

}