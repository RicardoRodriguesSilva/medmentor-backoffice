package br.com.medmentor.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SolicitacaoMudancaEscala implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
    private LocalDateTime dataHoraSolicitacao;
    private LocalDateTime dataHoraAtualizacao;
    private EscalaTrabalho escalaTrabalhoSolicitada;
    private EmpresaProfissional empresaProfissionalSolicitante;
	private Boolean bolAcatada;
	private Boolean bolAtiva;
	
	public SolicitacaoMudancaEscala() {
		
	}
	
	public SolicitacaoMudancaEscala(Integer id, LocalDateTime dataHoraSolicitacao, LocalDateTime dataHoraAtualizacao,
			EscalaTrabalho escalaTrabalhoSolicitada, EmpresaProfissional empresaProfissionalSolicitante,
			Boolean bolAcatada, Boolean bolAtiva) {
		super();
		this.id = id;
		this.dataHoraSolicitacao = dataHoraSolicitacao;
		this.dataHoraAtualizacao = dataHoraAtualizacao;
		this.escalaTrabalhoSolicitada = escalaTrabalhoSolicitada;
		this.empresaProfissionalSolicitante = empresaProfissionalSolicitante;
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

	public EscalaTrabalho getEscalaTrabalhoSolicitada() {
		return escalaTrabalhoSolicitada;
	}

	public void setEscalaTrabalhoSolicitada(EscalaTrabalho escalaTrabalhoSolicitada) {
		this.escalaTrabalhoSolicitada = escalaTrabalhoSolicitada;
	}

	public EmpresaProfissional getEmpresaProfissionalSolicitante() {
		return empresaProfissionalSolicitante;
	}

	public void setEmpresaProfissionalSolicitante(EmpresaProfissional empresaProfissionalSolicitante) {
		this.empresaProfissionalSolicitante = empresaProfissionalSolicitante;
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
		return "SolicitacaoMudancaEscala [id=" + id + ", dataHoraSolicitacao=" + dataHoraSolicitacao
				+ ", dataHoraAtualizacao=" + dataHoraAtualizacao + ", escalaTrabalhoSolicitada="
				+ escalaTrabalhoSolicitada + ", empresaProfissionalSolicitante=" + empresaProfissionalSolicitante
				+ ", bolAcatada=" + bolAcatada + ", bolAtiva =" + bolAtiva + "]";
	}
}