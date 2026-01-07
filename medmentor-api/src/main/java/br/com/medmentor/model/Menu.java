package br.com.medmentor.model;

import java.io.Serializable;
import java.util.Objects;

public class Menu implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
    private String nomeMenu;
    private String descricaoMenu;
    private String nomeCaminhoMenu;
    private TipoMenu tipoMenu;
    private Integer numeroOrdem;
    private Boolean bolAtivo;

    public Menu() {
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
    
    public TipoMenu getTipoMenu() {
		return tipoMenu;
	}

	public void setTipoMenu(TipoMenu tipoMenu) {
		this.tipoMenu = tipoMenu;
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
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

	@Override
	public String toString() {
		return "Menu [id=" + id + ", nomeMenu=" + nomeMenu + ", descricaoMenu=" + descricaoMenu
				+ ", nomeCaminhoMenu=" + nomeCaminhoMenu + ", tipoMenu=" + tipoMenu + ", numeroOrdem=" + numeroOrdem
				+ ", bolAtivo=" + bolAtivo + "]";
	}
}