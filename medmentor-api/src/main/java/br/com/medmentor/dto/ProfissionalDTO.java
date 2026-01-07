package br.com.medmentor.dto;

public class ProfissionalDTO extends GenericDTO {
	
    private static final long serialVersionUID = 1L;

    private Integer id;
    private PessoaFisicaDTO pessoaFisicaDTO;
	
    public Integer getId() {
		return id;
	}
    
	public void setId(Integer id) {
		this.id = id;
	}
	
	public PessoaFisicaDTO getPessoaFisicaDTO() {
		return pessoaFisicaDTO;
	}
	
	public void setPessoaFisicaDTO(PessoaFisicaDTO pessoaFisicaDTO) {
		this.pessoaFisicaDTO = pessoaFisicaDTO;
	}

	@Override
	public String toString() {
		return "ProfissionalDTO [id=" + id + ", pessoaFisicaDTO=" + pessoaFisicaDTO + "]";
	}
}