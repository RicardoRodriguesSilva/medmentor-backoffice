package br.com.medmentor.model;

import java.io.Serializable;

import br.com.medmentor.enums.TipoPessoa;

public class Pessoa implements Serializable {
	
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nomePessoa;
    private TipoPessoa codTipoPessoa; 
    private String descricaoEndereco;
    private String descricaoComplemento;
    private String descricaoBairro;
    private String numeroCep;
    private Cidade cidade; 
    private String numeroCelular;
    private String descricaoEmail;
    
    public Pessoa() {}

    public Pessoa(String nomePessoa, TipoPessoa codTipoPessoa, String descricaoEndereco,
                  String descricaoComplemento, String descricaoBairro, String numeroCep, Cidade cidade,
                  String numeroCelular, String descricaoEmail) {
        this.nomePessoa = nomePessoa;
        this.codTipoPessoa = codTipoPessoa;
        this.descricaoEndereco = descricaoEndereco;
        this.descricaoComplemento = descricaoComplemento;
        this.descricaoBairro = descricaoBairro;
        this.numeroCep = numeroCep;
        this.cidade = cidade;
        this.numeroCelular = numeroCelular;
        this.descricaoEmail = descricaoEmail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public TipoPessoa getCodTipoPessoa() {
        return codTipoPessoa;
    }

    public void setCodTipoPessoa(TipoPessoa codTipoPessoa) {
        this.codTipoPessoa = codTipoPessoa;
    }

    public String getDescricaoEndereco() {
        return descricaoEndereco;
    }

    public void setDescricaoEndereco(String descricaoEndereco) {
        this.descricaoEndereco = descricaoEndereco;
    }

    public String getDescricaoComplemento() {
        return descricaoComplemento;
    }

    public void setDescricaoComplemento(String descricaoComplemento) {
        this.descricaoComplemento = descricaoComplemento;
    }

    public String getDescricaoBairro() {
        return descricaoBairro;
    }

    public void setDescricaoBairro(String descricaoBairro) {
        this.descricaoBairro = descricaoBairro;
    }

    public String getNumeroCep() {
        return numeroCep;
    }

    public void setNumeroCep(String numeroCep) {
        this.numeroCep = numeroCep;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
        if (cidade != null) {
            this.cidade = cidade;
        }
    }

    public String getNumeroCelular() {
		return numeroCelular;
	}

	public void setNumeroCelular(String numeroCelular) {
		this.numeroCelular = numeroCelular;
	}

	public String getDescricaoEmail() {
		return descricaoEmail;
	}

	public void setDescricaoEmail(String descricaoEmail) {
		this.descricaoEmail = descricaoEmail;
	}

	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", nomePessoa=" + nomePessoa + ", codTipoPessoa=" + codTipoPessoa
				+ ", descricaoEndereco=" + descricaoEndereco + ", descricaoComplemento=" + descricaoComplemento
				+ ", descricaoBairro=" + descricaoBairro + ", numeroCep=" + numeroCep + ", cidade=" + cidade
				+ ", numeroCelular=" + numeroCelular + ", descricaoEmail=" + descricaoEmail + "]";
	}
}