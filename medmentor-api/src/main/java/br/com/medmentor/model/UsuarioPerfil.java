package br.com.medmentor.model;

import java.io.Serializable;

public class UsuarioPerfil implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
    private Usuario usuario;
    private Perfil perfil;

    public UsuarioPerfil() {
    	
    }

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

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	@Override
	public String toString() {
		return "UsuarioPerfil [id=" + id + ", usuario=" + usuario + ", perfil=" + perfil + "]";
	}
}