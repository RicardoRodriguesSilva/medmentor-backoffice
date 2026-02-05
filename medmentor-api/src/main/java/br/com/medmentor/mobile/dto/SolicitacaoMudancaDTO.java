package br.com.medmentor.mobile.dto;

import java.time.LocalDateTime;

import br.com.medmentor.dto.GenericDTO;

public class SolicitacaoMudancaDTO extends GenericDTO {
	
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private LocalDateTime dataHoraSolicitacao;
    private LocalDateTime dataHoraAtualizacao;
    private EscalaDTO escalaSolicitadaDTO;
    private EmpresaDTO empresaProfissionalSolicitanteDTO;
	private Boolean bolAcatada;
	private Boolean bolAtiva;
	private Boolean bolSolicitacaoTerceiro;
	
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
	public EscalaDTO getEscalaSolicitadaDTO() {
		return escalaSolicitadaDTO;
	}
	
	public void setEscalaSolicitadaDTO(EscalaDTO escalaSolicitadaDTO) {
		this.escalaSolicitadaDTO = escalaSolicitadaDTO;
	}
	
	public EmpresaDTO getEmpresaProfissionalSolicitanteDTO() {
		return empresaProfissionalSolicitanteDTO;
	}
	
	public void setEmpresaProfissionalSolicitanteDTO(EmpresaDTO empresaProfissionalSolicitanteDTO) {
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

	public Boolean getBolSolicitacaoTerceiro() {
		return bolSolicitacaoTerceiro;
	}

	public void setBolSolicitacaoTerceiro(Boolean bolSolicitacaoTerceiro) {
		this.bolSolicitacaoTerceiro = bolSolicitacaoTerceiro;
	}

	@Override
	public String toString() {
		return "SolicitacaoMudancaDTO [id=" + id + ", dataHoraSolicitacao=" + dataHoraSolicitacao
				+ ", dataHoraAtualizacao=" + dataHoraAtualizacao + ", escalaSolicitadaDTO=" + escalaSolicitadaDTO
				+ ", empresaProfissionalSolicitanteDTO=" + empresaProfissionalSolicitanteDTO + ", bolAcatada="
				+ bolAcatada + ", bolAtiva=" + bolAtiva + ", bolSolicitacaoTerceiro=" + bolSolicitacaoTerceiro + "]";
	}

}