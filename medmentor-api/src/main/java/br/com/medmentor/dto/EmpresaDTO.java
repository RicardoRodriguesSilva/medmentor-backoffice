package br.com.medmentor.dto;

public class EmpresaDTO extends GenericDTO {
	
    private static final long serialVersionUID = 1L;
	
    private Integer id; 
    private PessoaJuridicaDTO pessoaJuridicaDTO;
    private String nomeFantasia;
    private String nomeResponsavel;

    public EmpresaDTO() {}

    public EmpresaDTO(PessoaJuridicaDTO pessoaJuridicaDTO, String nomeFantasia, 
    		String nomeResponsavel) {
        this.pessoaJuridicaDTO = pessoaJuridicaDTO;
        this.nomeFantasia = nomeFantasia;
        this.nomeResponsavel = nomeResponsavel;
    }

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PessoaJuridicaDTO getPessoaJuridicaDTO() {
		return pessoaJuridicaDTO;
	}

	public void setPessoaJuridicaDTO(PessoaJuridicaDTO pessoaJuridicaDTO) {
		this.pessoaJuridicaDTO = pessoaJuridicaDTO;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

	@Override
	public String toString() {
		return "EmpresaDTO [id=" + id + ", pessoaJuridicaDTO=" + pessoaJuridicaDTO + ", nomeFantasia=" + nomeFantasia
				+ ", nomeResponsavel=" + nomeResponsavel + "]";
	}

}