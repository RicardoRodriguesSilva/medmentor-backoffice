package br.com.medmentor.model;

import java.io.Serializable;
import java.util.Objects;

public class TipoMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nomeTipoMenu;
    private Boolean bolAtivo;

    public TipoMenu() {
    } 

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeTipoMenu() {
        return nomeTipoMenu;
    }

    public void setNomeTipoMenu(String nomeTipoMenu) {
        this.nomeTipoMenu = nomeTipoMenu;
    }

    public Boolean getBolAtivo() {
        return bolAtivo;
    }

    public void setBolAtivo(Boolean bolAtivo) {
        this.bolAtivo = bolAtivo;
    }   

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoMenu menuDTO = (TipoMenu) o;
        return Objects.equals(id, menuDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

	@Override
	public String toString() {
		return "TipoMenu [id=" + id + ", nomeTipoMenu=" + nomeTipoMenu + ", bolAtivo=" + bolAtivo + "]";
	}
}