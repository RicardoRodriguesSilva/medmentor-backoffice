package br.com.medmentor.model;

import java.io.Serializable;

public class Cidade implements Serializable {
	
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nomeCidade;
    private Integer idUnidadeFederacao; 
    private UnidadeFederacao unidadeFederacao; 

    public Cidade() {} 

    public Cidade(Integer id, String nomeCidade, Integer idUnidadeFederacao) {
        this.id = id;
        this.nomeCidade = nomeCidade;
        this.idUnidadeFederacao = idUnidadeFederacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public Integer getIdUnidadeFederacao() {
        return idUnidadeFederacao;
    }

    public void setIdUnidadeFederacao(Integer idUnidadeFederacao) {
        this.idUnidadeFederacao = idUnidadeFederacao;
    }

    public UnidadeFederacao getUnidadeFederacao() {
        return unidadeFederacao;
    }

    public void setUnidadeFederacao(UnidadeFederacao unidadeFederacao) {
        this.unidadeFederacao = unidadeFederacao;
        if (unidadeFederacao != null) {
            this.idUnidadeFederacao = unidadeFederacao.getIdUnidadeFederacao();
        }
    }

    @Override
    public String toString() {
        return "Cidade{" +
               "id=" + id +
               ", nomeCidade='" + nomeCidade + '\'' +
               ", idUnidadeFederacao=" + idUnidadeFederacao +
               ", unidadeFederacao=" + (unidadeFederacao != null ? unidadeFederacao.getSiglaUnidadeFederacao() : "null") +
               '}';
    }
}