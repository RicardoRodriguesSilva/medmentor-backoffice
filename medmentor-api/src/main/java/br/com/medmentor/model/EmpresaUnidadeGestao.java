package br.com.medmentor.model;

import java.io.Serializable;

public class EmpresaUnidadeGestao implements Serializable {
    
	private static final long serialVersionUID = 1L;

    private Integer id;   
    private Empresa	empresa;
    private EmpresaGestao empresaGestora;
    private Integer numeroPlantonistaDia;
    private Integer numeroPlantonistaPeriodo;
	
    public Integer getId() {
		return id;
	}
    
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}
	
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public EmpresaGestao getEmpresaGestora() {
		return empresaGestora;
	}
	
	public void setEmpresaGestora(EmpresaGestao empresaGestora) {
		this.empresaGestora = empresaGestora;
	}

	public Integer getNumeroPlantonistaDia() {
		return numeroPlantonistaDia;
	}

	public void setNumeroPlantonistaDia(Integer numeroPlantonistaDia) {
		this.numeroPlantonistaDia = numeroPlantonistaDia;
	}

	public Integer getNumeroPlantonistaPeriodo() {
		return numeroPlantonistaPeriodo;
	}

	public void setNumeroPlantonistaPeriodo(Integer numeroPlantonistaPeriodo) {
		this.numeroPlantonistaPeriodo = numeroPlantonistaPeriodo;
	} 
}