package br.com.medmentor.model;

import java.io.Serializable;

public class PerfilMenuAcao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
    private Perfil perfil;
    private Menu menu;
    private Acao acao;

    public PerfilMenuAcao() {
    	
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Acao getAcao() {
		return acao;
	}

	public void setAcao(Acao acao) {
		this.acao = acao;
	}
}