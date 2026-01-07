package br.com.medmentor.dto;

public class EmpresaProfissionalDTO extends GenericDTO {
	
    private static final long serialVersionUID = 1L;

	private Integer id;
	private EmpresaDTO empresaDTO;
    private ProfissionalDTO profissionalDTO;
    private String numeroBanco;
    private String numeroAgencia;
    private String numeroConta;
    private String descricaoChavePix;
    private EmpresaGestaoDTO empresaGestaoDTO; 
    
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public EmpresaDTO getEmpresaDTO() {
		return empresaDTO;
	}
	
	public void setEmpresaDTO(EmpresaDTO empresaDTO) {
		this.empresaDTO = empresaDTO;
	}
	
	public ProfissionalDTO getProfissionalDTO() {
		return profissionalDTO;
	}
	
	public void setProfissionalDTO(ProfissionalDTO profissionalDTO) {
		this.profissionalDTO = profissionalDTO;
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


	public EmpresaGestaoDTO getEmpresaGestaoDTO() {
		return empresaGestaoDTO;
	}

	public void setEmpresaGestaoDTO(EmpresaGestaoDTO empresaGestaoDTO) {
		this.empresaGestaoDTO = empresaGestaoDTO;
	}

	@Override
	public String toString() {
		return "EmpresaProfissionalDTO [id=" + id + ", empresaDTO=" + empresaDTO + ", profissionalDTO="
				+ profissionalDTO + ", numeroBanco=" + numeroBanco + ", numeroAgencia=" + numeroAgencia
				+ ", numeroConta=" + numeroConta + ", descricaoChavePix=" + descricaoChavePix + ", empresaGestaoDTO="
				+ empresaGestaoDTO + "]";
	}
}