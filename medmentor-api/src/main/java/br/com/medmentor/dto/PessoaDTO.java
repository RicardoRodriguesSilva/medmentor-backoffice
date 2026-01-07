package br.com.medmentor.dto;

import br.com.medmentor.enums.TipoPessoa;

public class PessoaDTO extends GenericDTO
{
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nomePessoa;
    private TipoPessoa codTipoPessoa; 
    private String descricaoEndereco;
    private String descricaoComplemento;
    private String descricaoBairro;
    private String numeroCep;
    private CidadeDTO cidadeDTO;
    private String numeroCelular;
    private String descricaoEmail;
    
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
	
	public CidadeDTO getCidadeDTO() {
		return cidadeDTO;
	}
	
	public void setCidadeDTO(CidadeDTO cidadeDTO) {
		this.cidadeDTO = cidadeDTO;
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
		return "PessoaDTO [id=" + id + ", nomePessoa=" + nomePessoa + ", codTipoPessoa=" + codTipoPessoa
				+ ", descricaoEndereco=" + descricaoEndereco + ", descricaoComplemento=" + descricaoComplemento
				+ ", descricaoBairro=" + descricaoBairro + ", numeroCep=" + numeroCep + ", cidadeDTO=" + cidadeDTO
				+ ", numeroCelular=" + numeroCelular + ", descricaoEmail=" + descricaoEmail + "]";
	}
}