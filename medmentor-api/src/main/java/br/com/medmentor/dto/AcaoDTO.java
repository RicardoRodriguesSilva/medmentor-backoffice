package br.com.medmentor.dto;

import java.util.Objects;

public class AcaoDTO extends GenericDTO {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String descricaoAcao;

    public AcaoDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricaoAcao() {
        return descricaoAcao;
    }

    public void setDescricaoAcao(String descricaoAcao) {
        this.descricaoAcao = descricaoAcao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcaoDTO acaoDTO = (AcaoDTO) o;
        return Objects.equals(id, acaoDTO.id);
    } 

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "AcaoDTO{" +
               "id=" + id +
               ", descricaoAcao='" + descricaoAcao + '\'' +
               '}';
    }
}