package br.com.medmentor.mobile.dto;

import java.time.LocalDate;

import br.com.medmentor.dto.CidadeDTO;
import br.com.medmentor.dto.GenericDTO;

public class MedicoDTO extends GenericDTO {
	
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String nome;
    private LocalDate dataNascimento;
    private Integer idade;
    private String numeroCpf;
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
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public Integer getIdade() {
		return idade;
	}
	
	public void setIdade(Integer idade) {
		this.idade = idade;
	}
	
	public String getNumeroCpf() {
		return numeroCpf;
	}
	
	public void setNumeroCpf(String numeroCpf) {
		this.numeroCpf = numeroCpf;
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
