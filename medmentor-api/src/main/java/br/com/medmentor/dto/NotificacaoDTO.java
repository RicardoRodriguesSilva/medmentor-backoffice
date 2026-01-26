package br.com.medmentor.dto;

import java.time.LocalDateTime;

public class NotificacaoDTO extends GenericDTO {
	   
	private static final long serialVersionUID = 1L;

	private Integer id;
	private LocalDateTime dataHora;
	private String descricao;
    private EmpresaProfissionalDTO empresaProfissionalDTO;
    private EmpresaUnidadeGestaoDTO empresaUnidadeGestaoDTO;
    private Boolean bolLida;
	
    public NotificacaoDTO() {
    	
    }
    
    public NotificacaoDTO(Integer id, LocalDateTime dataHora, String descricao,
			EmpresaProfissionalDTO empresaProfissionalDTO, EmpresaUnidadeGestaoDTO empresaUnidadeGestaoDTO,
			Boolean bolLida) {
		super();
		this.id = id;
		this.dataHora = dataHora;
		this.descricao = descricao;
		this.empresaProfissionalDTO = empresaProfissionalDTO;
		this.empresaUnidadeGestaoDTO = empresaUnidadeGestaoDTO;
		this.bolLida = bolLida;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public EmpresaProfissionalDTO getEmpresaProfissionalDTO() {
		return empresaProfissionalDTO;
	}

	public void setEmpresaProfissionalDTO(EmpresaProfissionalDTO empresaProfissionalDTO) {
		this.empresaProfissionalDTO = empresaProfissionalDTO;
	}

	public EmpresaUnidadeGestaoDTO getEmpresaUnidadeGestaoDTO() {
		return empresaUnidadeGestaoDTO;
	}

	public void setEmpresaUnidadeGestaoDTO(EmpresaUnidadeGestaoDTO empresaUnidadeGestaoDTO) {
		this.empresaUnidadeGestaoDTO = empresaUnidadeGestaoDTO;
	}

	public Boolean getBolLida() {
		return bolLida;
	}

	public void setBolLida(Boolean bolLida) {
		this.bolLida = bolLida;
	}

	@Override
	public String toString() {
		return "NotificacaoDTO [id=" + id + ", dataHora=" + dataHora + ", descricao=" + descricao
				+ ", empresaProfissionalDTO=" + empresaProfissionalDTO + ", empresaUnidadeGestaoDTO="
				+ empresaUnidadeGestaoDTO + ", bolLida=" + bolLida + "]";
	}
}