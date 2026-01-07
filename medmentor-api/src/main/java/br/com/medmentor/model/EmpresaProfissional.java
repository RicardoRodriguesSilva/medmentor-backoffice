package br.com.medmentor.model;

import java.io.Serializable;

public class EmpresaProfissional implements Serializable {
    
	private static final long serialVersionUID = 1L;
 
	private Integer id;
	private Empresa empresa;
    private Profissional profissional;
    private String numeroBanco;
    private String numeroAgencia;
    private String numeroConta;
    private String descricaoChavePix;
    private EmpresaGestao empresaGestao;
    
    public EmpresaProfissional() {
    	
    }

	public EmpresaProfissional(Integer id, Empresa empresa, Profissional profissional, String numeroBanco,
			String numeroAgencia, String numeroConta, String descricaoChavePix, EmpresaGestao empresaGestao) {
		super();
		this.id = id;
		this.empresa = empresa;
		this.profissional = profissional;
		this.numeroBanco = numeroBanco;
		this.numeroAgencia = numeroAgencia;
		this.numeroConta = numeroConta;
		this.descricaoChavePix = descricaoChavePix;
		this.empresaGestao = empresaGestao;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Profissional getProfissional() {
		return profissional;
	}

	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumeroBanco() {
		return numeroBanco;
	}

	public void setNumeroBanco(String numeroBanco) {
		this.numeroBanco = numeroBanco;
	}

	public String getNumeroAgencia() {
		return numeroAgencia;
	}

	public void setNumeroAgencia(String numeroAgencia) {
		this.numeroAgencia = numeroAgencia;
	}

	public String getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}

	public String getDescricaoChavePix() {
		return descricaoChavePix;
	}

	public void setDescricaoChavePix(String descricaoChavePix) {
		this.descricaoChavePix = descricaoChavePix;
	}
	
	public EmpresaGestao getEmpresaGestao() {
		return empresaGestao;
	}

	public void setEmpresaGestao(EmpresaGestao empresaGestao) {
		this.empresaGestao = empresaGestao;
	}

	@Override
	public String toString() {
		return "EmpresaProfissional [id=" + id + ", empresa=" + empresa + ", profissional=" + profissional
				+ ", numeroBanco=" + numeroBanco + ", numeroAgencia=" + numeroAgencia + ", numeroConta=" + numeroConta
				+ ", descricaoChavePix=" + descricaoChavePix + ", empresaGestao=" + empresaGestao + "]";
	}	

}