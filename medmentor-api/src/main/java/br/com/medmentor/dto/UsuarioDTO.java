package br.com.medmentor.dto;

public class UsuarioDTO extends GenericDTO {
    private static final long serialVersionUID = 1L;

    private Integer id; 
    private String nomeUsuario;
    private String senhaUsuario;
    private Boolean bolAtivo;
    private PessoaFisicaDTO pessoaFisicaDTO;

    public UsuarioDTO() {
    	
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSenhaUsuario() {
		return senhaUsuario;
	}

	public void setSenhaUsuario(String senhaUsuario) {
		this.senhaUsuario = senhaUsuario;
	}

	public Boolean getBolAtivo() {
		return bolAtivo;
	}

	public void setBolAtivo(Boolean bolAtivo) {
		this.bolAtivo = bolAtivo;
	}

	public PessoaFisicaDTO getPessoaFisicaDTO() {
		return pessoaFisicaDTO;
	}

	public void setPessoaFisicaDTO(PessoaFisicaDTO pessoaFisicaDTO) {
		this.pessoaFisicaDTO = pessoaFisicaDTO;
	}

	@Override
	public String toString() {
		return "UsuarioDTO [id=" + id + ", nomeUsuario=" + nomeUsuario + ", senhaUsuario=" + senhaUsuario
				+ ", bolAtivo=" + bolAtivo + ", pessoaFisicaDTO=" + pessoaFisicaDTO + "]";
	}
}