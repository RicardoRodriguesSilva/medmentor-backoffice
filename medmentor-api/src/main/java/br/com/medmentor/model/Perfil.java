package br.com.medmentor.model;

import java.io.Serializable;
import java.util.Objects;

public class Perfil implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
    private String nomePerfil;
    private String descricaoPerfil;
    private Boolean bolAtivo;

    public Perfil() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomePerfil() {
        return nomePerfil;
    }

    public void setNomePerfil(String nomePerfil) {
        this.nomePerfil = nomePerfil;
    }

    public String getDescricaoPerfil() {
        return descricaoPerfil;
    }

    public void setDescricaoPerfil(String descricaoPerfil) {
        this.descricaoPerfil = descricaoPerfil;
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
        Perfil perfil = (Perfil) o;
        return Objects.equals(id, perfil.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Perfil{" +
               "id=" + id +
               ", nomePerfil='" + nomePerfil + '\'' +
               ", bolAtivo=" + bolAtivo +
               '}';
    }
}