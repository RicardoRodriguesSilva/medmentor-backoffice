package br.com.medmentor.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AcessoMenuDTO implements Serializable {
    
	private static final long serialVersionUID = 1L;
		
	private String nomeMenu;
	private String tipoMenu;
	private Integer ordem;
    private String descricaoMenu;
    private String nomeCaminhoMenu;
    private List<String> permissoes;
    
    public AcessoMenuDTO() {
    	this.permissoes = new ArrayList<String>();
    }
	
    public String getNomeMenu() {
		return nomeMenu;
	}
	
	public void setNomeMenu(String nomeMenu) {
		this.nomeMenu = nomeMenu;
	}
	
	public String getDescricaoMenu() {
		return descricaoMenu;
	}
	
	public void setDescricaoMenu(String descricaoMenu) {
		this.descricaoMenu = descricaoMenu;
	}
	
	public String getNomeCaminhoMenu() {
		return nomeCaminhoMenu;
	}
	
	public void setNomeCaminhoMenu(String nomeCaminhoMenu) {
		this.nomeCaminhoMenu = nomeCaminhoMenu;
	}
	
	public List<String> getPermissoes() {
		return permissoes;
	}
	
	public void Permissoes(List<String> permissoes) {
		this.permissoes = permissoes;
	}

	public String getTipoMenu() {
		return tipoMenu;
	}

	public void setTipoMenu(String tipoMenu) {
		this.tipoMenu = tipoMenu;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

}
