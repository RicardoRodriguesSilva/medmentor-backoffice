// src/main/java/br/com/medmentor/dto/CidadeDTO.java
package br.com.medmentor.dto;

import java.io.Serializable; 

public class CidadeDTO implements Serializable {
    
	private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;
    private UnidadeFederacaoDTO unidadeFederacaoDTO; // Objeto DTO da UF aninhado

    // Construtor padr�o
    public CidadeDTO() {
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public UnidadeFederacaoDTO getUnidadeFederacaoDTO() {
        return unidadeFederacaoDTO;
    }

    public void setUnidadeFederacaoDTO(UnidadeFederacaoDTO unidadeFederacaoDTO) {
        this.unidadeFederacaoDTO = unidadeFederacaoDTO;
    }

    @Override
    public String toString() {
        return "CidadeDTO{" +
               "id=" + id +
               ", nome='" + nome + '\'' +
               ", unidadeFederacao=" + (unidadeFederacaoDTO != null ? unidadeFederacaoDTO.getSigla() : "null") +
               '}';
    }
}