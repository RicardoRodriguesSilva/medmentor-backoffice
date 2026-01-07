// src/main/java/br/com/medmentor/model/UnidadeFederacao.java
package br.com.medmentor.model;

import java.io.Serializable;

public class UnidadeFederacao implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idUnidadeFederacao;
    private String nomeUnidadeFederacao;
    private String siglaUnidadeFederacao;

    // Constructors
    public UnidadeFederacao() {}

    public UnidadeFederacao(Integer idUnidadeFederacao, String nomeUnidadeFederacao, String siglaUnidadeFederacao) {
        this.idUnidadeFederacao = idUnidadeFederacao;
        this.nomeUnidadeFederacao = nomeUnidadeFederacao;
        this.siglaUnidadeFederacao = siglaUnidadeFederacao;
    }

    // Getters and Setters
    public Integer getIdUnidadeFederacao() {
        return idUnidadeFederacao;
    }

    public void setIdUnidadeFederacao(Integer idUnidadeFederacao) {
        this.idUnidadeFederacao = idUnidadeFederacao;
    }

    public String getNomeUnidadeFederacao() {
        return nomeUnidadeFederacao;
    }

    public void setNomeUnidadeFederacao(String nomeUnidadeFederacao) {
        this.nomeUnidadeFederacao = nomeUnidadeFederacao;
    }

    public String getSiglaUnidadeFederacao() {
        return siglaUnidadeFederacao;
    }

    public void setSiglaUnidadeFederacao(String siglaUnidadeFederacao) {
        this.siglaUnidadeFederacao = siglaUnidadeFederacao;
    }

    @Override
    public String toString() {
        return "UnidadeFederacao{" +
               "idUnidadeFederacao=" + idUnidadeFederacao +
               ", nomeUnidadeFederacao='" + nomeUnidadeFederacao + '\'' +
               ", siglaUnidadeFederacao='" + siglaUnidadeFederacao + '\'' +
               '}';
    }
}