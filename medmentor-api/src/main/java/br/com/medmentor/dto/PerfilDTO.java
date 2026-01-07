package br.com.medmentor.dto;

import java.util.Objects;

public class PerfilDTO extends GenericDTO {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nomePerfil;
    private String descricaoPerfil;
    private Boolean bolAtivo;

    public PerfilDTO() {
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
        PerfilDTO perfilDTO = (PerfilDTO) o;
        return Objects.equals(id, perfilDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override 
    public String toString() {
        return "PerfilDTO{" +
               "id=" + id +
               ", nomePerfil='" + nomePerfil + '\'' +
               ", bolAtivo=" + bolAtivo +
               '}';
    }
}