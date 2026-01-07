package br.com.medmentor.dto;

import java.util.Objects;

public class MenuDTO extends GenericDTO
{
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nomeMenu;
    private String descricaoMenu;
    private String nomeCaminhoMenu;
    private TipoMenuDTO tipoMenuDTO;
    private Integer numeroOrdem;
    private Boolean bolAtivo;

    public MenuDTO() {
    } 

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeMenu() {
        return nomeMenu;
    }

    public void setNomeMenu(String nomeMenu) {
        this.nomeMenu = nomeMenu;
    }

    public String getDescricaoMenu() {
        return descricaoMenu;
    }

    public void setDescricaoMenu(String descricaoMenu) {
        this.descricaoMenu = descricaoMenu;
    }

    public String getNomeCaminhoMenu() {
        return nomeCaminhoMenu;
    }

    public void setNomeCaminhoMenu(String nomeCaminhoMenu) {
        this.nomeCaminhoMenu = nomeCaminhoMenu;
    }

    public Boolean getBolAtivo() {
        return bolAtivo;
    }

    public void setBolAtivo(Boolean bolAtivo) {
        this.bolAtivo = bolAtivo;
    }   

	public TipoMenuDTO getTipoMenuDTO() {
		return tipoMenuDTO;
	}

	public void setTipoMenuDTO(TipoMenuDTO tipoMenuDTO) {
		this.tipoMenuDTO = tipoMenuDTO;
	}

	public Integer getNumeroOrdem() {
		return numeroOrdem;
	}

	public void setNumeroOrdem(Integer numeroOrdem) {
		this.numeroOrdem = numeroOrdem;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuDTO menuDTO = (MenuDTO) o;
        return Objects.equals(id, menuDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

	@Override
	public String toString() {
		return "MenuDTO [id=" + id + ", nomeMenu=" + nomeMenu + ", descricaoMenu=" + descricaoMenu
				+ ", nomeCaminhoMenu=" + nomeCaminhoMenu + ", tipoMenuDTO=" + tipoMenuDTO + ", numeroOrdem="
				+ numeroOrdem + ", bolAtivo=" + bolAtivo + "]";
	}
}