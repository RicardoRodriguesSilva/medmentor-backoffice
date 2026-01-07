package br.com.medmentor.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import br.com.medmentor.enums.Canal;
import br.com.medmentor.enums.TipoSolicitacaoAcesso;

public class SolicitacaoAcesso implements Serializable {

	private static final long serialVersionUID = 1L;

    private Integer id;
    private Usuario usuario;
	private LocalDateTime dataHoraSolicitacao;
	private String senhaAnterior;
	private Canal canal;
	private TipoSolicitacaoAcesso tipoSolicitacaoAcesso;
	private String emailUtilizado;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public LocalDateTime getDataHoraSolicitacao() {
		return dataHoraSolicitacao;
	}
	
	public void setDataHoraSolicitacao(LocalDateTime dataHoraSolicitacao) {
		this.dataHoraSolicitacao = dataHoraSolicitacao;
	}
	
	public String getSenhaAnterior() {
		return senhaAnterior;
	}
	
	public void setSenhaAnterior(String senhaAnterior) {
		this.senhaAnterior = senhaAnterior;
	}
	
	public Canal getCanal() {
		return canal;
	}
	
	public void setCanal(Canal canal) {
		this.canal = canal;
	}
	
	public TipoSolicitacaoAcesso getTipoSolicitacaoAcesso() {
		return tipoSolicitacaoAcesso;
	}

	public void setTipoSolicitacaoAcesso(TipoSolicitacaoAcesso tipoSolicitacaoAcesso) {
		this.tipoSolicitacaoAcesso = tipoSolicitacaoAcesso;
	}

	public String getEmailUtilizado() {
		return emailUtilizado;
	}

	public void setEmailUtilizado(String emailUtilizado) {
		this.emailUtilizado = emailUtilizado;
	}

	@Override
	public String toString() {
		return "SolicitacaoAcesso [id=" + id + ", usuario=" + usuario + ", dataHoraSolicitacao=" + dataHoraSolicitacao
				+ ", senhaAnterior=" + senhaAnterior + ", canal=" + canal + ", tipoSolicitacaoAcesso="
				+ tipoSolicitacaoAcesso + ", emailUtilizado=" + emailUtilizado + "]";
	}	
}