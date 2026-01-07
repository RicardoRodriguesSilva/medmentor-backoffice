package br.com.medmentor.dto;

public class EmpresaDTO extends GenericDTO {
	
    private static final long serialVersionUID = 1L;
	
    private Integer id; 
    private PessoaJuridicaDTO pessoaJuridicaDTO;
    private String nomeFantasia;

    public EmpresaDTO() {}

    public EmpresaDTO(PessoaJuridicaDTO pessoaJuridicaDTO, String nomeFantasia) {
        this.pessoaJuridicaDTO = pessoaJuridicaDTO;
        this.nomeFantasia = nomeFantasia;
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

	@Override
    public String toString() {
        return "Empresa{" +
               "id=" + id +
               ", idPessoaJuridica=" + (pessoaJuridicaDTO != null ? pessoaJuridicaDTO.getId() : "null") +
               ", nomeFantasia='" + nomeFantasia + '\'' +
               '}';
    }
}