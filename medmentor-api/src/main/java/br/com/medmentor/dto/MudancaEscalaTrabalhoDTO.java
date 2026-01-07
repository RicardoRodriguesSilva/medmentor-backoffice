package br.com.medmentor.dto;

import java.time.LocalDateTime;

public class MudancaEscalaTrabalhoDTO extends GenericDTO {
	
    private static final long serialVersionUID = 1L;

    private Integer idMudancaEscalaTrabalho;
    private Integer idEscalaTrabalho;
    private LocalDateTime dataHoraSolicitacao;
    private LocalDateTime dataHoraMudanca;
    private Integer idEmpresaProfissionalUnidadeGestaoAnterior;
    private Integer idEmpresaProfissionalUnidadeGestaoAtual;

    public MudancaEscalaTrabalhoDTO() {
    	
    }

    public MudancaEscalaTrabalhoDTO(Integer idMudancaEscalaTrabalho, Integer idEscalaTrabalho, LocalDateTime dataHoraSolicitacao, LocalDateTime dataHoraMudanca, Integer idEmpresaProfissionalUnidadeGestaoAnterior, Integer idEmpresaProfissionalUnidadeGestaoAtual) {
        this.idMudancaEscalaTrabalho = idMudancaEscalaTrabalho;
        this.idEscalaTrabalho = idEscalaTrabalho;
        this.dataHoraSolicitacao = dataHoraSolicitacao;
        this.dataHoraMudanca = dataHoraMudanca;
        this.idEmpresaProfissionalUnidadeGestaoAnterior = idEmpresaProfissionalUnidadeGestaoAnterior;
        this.idEmpresaProfissionalUnidadeGestaoAtual = idEmpresaProfissionalUnidadeGestaoAtual;
    }
    
    public Integer getIdMudancaEscalaTrabalho() {
		return idMudancaEscalaTrabalho;
	}

	public void setIdMudancaEscalaTrabalho(Integer idMudancaEscalaTrabalho) {
		this.idMudancaEscalaTrabalho = idMudancaEscalaTrabalho;
	}

	public Integer getIdEscalaTrabalho() {
		return idEscalaTrabalho;
	}

	public void setIdEscalaTrabalho(Integer idEscalaTrabalho) {
		this.idEscalaTrabalho = idEscalaTrabalho;
	}

	public LocalDateTime getDataHoraSolicitacao() {
		return dataHoraSolicitacao;
	}

	public void setDataHoraSolicitacao(LocalDateTime dataHoraSolicitacao) {
		this.dataHoraSolicitacao = dataHoraSolicitacao;
	}

	public LocalDateTime getDataHoraMudanca() {
		return dataHoraMudanca;
	}

	public void setDataHoraMudanca(LocalDateTime dataHoraMudanca) {
		this.dataHoraMudanca = dataHoraMudanca;
	}

	public Integer getIdEmpresaProfissionalUnidadeGestaoAnterior() {
		return idEmpresaProfissionalUnidadeGestaoAnterior;
	}

	public void setIdEmpresaProfissionalUnidadeGestaoAnterior(Integer idEmpresaProfissionalUnidadeGestaoAnterior) {
		this.idEmpresaProfissionalUnidadeGestaoAnterior = idEmpresaProfissionalUnidadeGestaoAnterior;
	}

	public Integer getIdEmpresaProfissionalUnidadeGestaoAtual() {
		return idEmpresaProfissionalUnidadeGestaoAtual;
	}

	public void setIdEmpresaProfissionalUnidadeGestaoAtual(Integer idEmpresaProfissionalUnidadeGestaoAtual) {
		this.idEmpresaProfissionalUnidadeGestaoAtual = idEmpresaProfissionalUnidadeGestaoAtual;
	}

	@Override
    public String toString() {
       return "MudancaEscalaTrabalho{" +
              "id=" + idMudancaEscalaTrabalho +
              ", idEscala=" + idEscalaTrabalho +
              ", oldEPUG=" + idEmpresaProfissionalUnidadeGestaoAnterior +
              ", newEPUG=" + idEmpresaProfissionalUnidadeGestaoAtual +
              '}';
    }
}