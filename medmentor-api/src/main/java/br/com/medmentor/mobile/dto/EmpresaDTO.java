package br.com.medmentor.mobile.dto;

import br.com.medmentor.dto.CidadeDTO;
import br.com.medmentor.dto.GenericDTO;

public class EmpresaDTO extends GenericDTO {
	
    private static final long serialVersionUID = 1L;
	
    private Integer id;
    private String razaoSocial;
    private String nomeFantasia;
    private String numeroCnpf;
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
	
	public String getRazaoSocial() {
		return razaoSocial;
	}
	
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	
	public String getNomeFantasia() {
		return nomeFantasia;
	}
	
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	
	public String getNumeroCnpf() {
		return numeroCnpf;
	}
	
	public void setNumeroCnpf(String numeroCnpf) {
		this.numeroCnpf = numeroCnpf;
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
}