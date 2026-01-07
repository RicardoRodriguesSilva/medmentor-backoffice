package br.com.medmentor.dto;

import java.time.LocalDateTime;

import br.com.medmentor.enums.Canal;
import br.com.medmentor.enums.TipoSolicitacaoAcesso;

public class SolicitacaoAcessoDTO extends GenericDTO {
    
	private static final long serialVersionUID = 1L;

    private Integer id;
    private UsuarioDTO usuarioDTO;
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
	
	public UsuarioDTO getUsuarioDTO() {
		return usuarioDTO;
	}
	
	public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
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
		return "SolicitacaoAcessoDTO [id=" + id + ", usuarioDTO=" + usuarioDTO + ", dataHoraSolicitacao="
				+ dataHoraSolicitacao + ", senhaAnterior=" + senhaAnterior + ", canal=" + canal
				+ ", tipoSolicitacaoAcesso=" + tipoSolicitacaoAcesso + ", emailUtilizado=" + emailUtilizado + "]";
	}

}