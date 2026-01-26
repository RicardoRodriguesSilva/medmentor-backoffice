package br.com.medmentor.dto;

import java.time.LocalDateTime;

public class SolicitacaoMudancaEscalaDTO extends GenericDTO {
	
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private LocalDateTime dataHoraSolicitacao;
    private LocalDateTime dataHoraAtualizacao;
    private EscalaTrabalhoDTO escalaTrabalhoSolicitadaDTO;
    private EmpresaProfissionalDTO empresaProfissionalSolicitanteDTO;
	private Boolean bolAcatada;
	private Boolean bolAtiva;

	public SolicitacaoMudancaEscalaDTO() {
		
	}

	public SolicitacaoMudancaEscalaDTO(Integer id, LocalDateTime dataHoraSolicitacao, LocalDateTime dataHoraAtualizacao,
			EscalaTrabalhoDTO escalaTrabalhoSolicitadaDTO, EmpresaProfissionalDTO empresaProfissionalSolicitanteDTO,
			Boolean bolAcatada, Boolean bolAtiva) {
		super();
		this.id = id;
		this.dataHoraSolicitacao = dataHoraSolicitacao;
		this.dataHoraAtualizacao = dataHoraAtualizacao;
		this.escalaTrabalhoSolicitadaDTO = escalaTrabalhoSolicitadaDTO;
		this.empresaProfissionalSolicitanteDTO = empresaProfissionalSolicitanteDTO;
		this.bolAcatada = bolAcatada;
		this.bolAtiva = bolAtiva;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getDataHoraSolicitacao() {
		return dataHoraSolicitacao;
	}

	public void setDataHoraSolicitacao(LocalDateTime dataHoraSolicitacao) {
		this.dataHoraSolicitacao = dataHoraSolicitacao;
	}

	public LocalDateTime getDataHoraAtualizacao() {
		return dataHoraAtualizacao;
	}

	public void setDataHoraAtualizacao(LocalDateTime dataHoraAtualizacao) {
		this.dataHoraAtualizacao = dataHoraAtualizacao;
	}

	public EscalaTrabalhoDTO getEscalaTrabalhoSolicitadaDTO() {
		return escalaTrabalhoSolicitadaDTO;
	}

	public void setEscalaTrabalhoSolicitadaDTO(EscalaTrabalhoDTO escalaTrabalhoSolicitadaDTO) {
		this.escalaTrabalhoSolicitadaDTO = escalaTrabalhoSolicitadaDTO;
	}

	public EmpresaProfissionalDTO getEmpresaProfissionalSolicitanteDTO() {
		return empresaProfissionalSolicitanteDTO;
	}

	public void setEmpresaProfissionalSolicitanteDTO(EmpresaProfissionalDTO empresaProfissionalSolicitanteDTO) {
		this.empresaProfissionalSolicitanteDTO = empresaProfissionalSolicitanteDTO;
	}

	public Boolean getBolAcatada() {
		return bolAcatada;
	}

	public void setBolAcatada(Boolean bolAcatada) {
		this.bolAcatada = bolAcatada;
	}
	
	public Boolean getBolAtiva() {
		return bolAtiva;
	}

	public void setBolAtiva(Boolean bolAtiva) {
		this.bolAtiva = bolAtiva;
	}	

	@Override
	public String toString() {
		return "SolicitacaoMudancaEscalaDTO [id=" + id + ", dataHoraSolicitacao=" + dataHoraSolicitacao
				+ ", dataHoraAtualizacao=" + dataHoraAtualizacao + ", escalaTrabalhoSolicitadaDTO="
				+ escalaTrabalhoSolicitadaDTO + ", empresaProfissionalSolicitanteDTO="
				+ empresaProfissionalSolicitanteDTO + ", bolAcatada=" + bolAcatada + ", bolAtiva=" + bolAtiva + "]";
	}
}
