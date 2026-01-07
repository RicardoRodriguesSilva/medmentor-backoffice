package br.com.medmentor.dto;

public class PessoaJuridicaDTO extends GenericDTO {
	
    private static final long serialVersionUID = 1L;

    private Integer id;
    private PessoaDTO pessoaDTO; 
    private String cnpj;
    private String razaoSocial;
    
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public PessoaDTO getPessoaDTO() {
		return pessoaDTO;
	}
	
	public void setPessoaDTO(PessoaDTO pessoaDTO) {
		this.pessoaDTO = pessoaDTO;
	}
	
	public String getCnpj() {
		return cnpj;
	}
	
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public String getRazaoSocial() {
		return razaoSocial;
	}
	
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	} 
}