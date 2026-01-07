package br.com.medmentor.model;

import java.io.Serializable;
import java.util.Objects;

public class Acao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
    private String descricaoAcao;

    public Acao() {
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
        Acao acao = (Acao) o;
        return Objects.equals(id, acao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Acao{" +
               "idAcao=" + id +
               ", descricaoAcao='" + descricaoAcao + '\'' +
               '}';
    }
}