package br.com.medmentor.mobile.dto;

import br.com.medmentor.dto.GenericDTO;

public class DadosBancariosDTO extends GenericDTO {
	
    private static final long serialVersionUID = 1L;
	
    private Integer id;
    private String numeroBanco;
    private String numeroAgencia;
    private String numeroConta;
    private String descricaoChavePix;
    
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNumeroBanco() {
		return numeroBanco;
	}
	
	public void setNumeroBanco(String numeroBanco) {
		this.numeroBanco = numeroBanco;
	}
	
	public String getNumeroAgencia() {
		return numeroAgencia;
	}
	
	public void setNumeroAgencia(String numeroAgencia) {
		this.numeroAgencia = numeroAgencia;
	}
	
	public String getNumeroConta() {
		return numeroConta;
	}
	
	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}
	
	public String getDescricaoChavePix() {
		return descricaoChavePix;
	}
	
	public void setDescricaoChavePix(String descricaoChavePix) {
		this.descricaoChavePix = descricaoChavePix;
	}    
}