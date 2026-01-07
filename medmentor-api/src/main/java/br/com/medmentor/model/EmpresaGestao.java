package br.com.medmentor.model;

import java.io.Serializable;

public class EmpresaGestao implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
    private Integer id; 
    private Empresa empresa; 

    public EmpresaGestao() {}

    public EmpresaGestao(Empresa empresa) {
        this.empresa = empresa;
        if (empresa != null) {
            this.id = empresa.getId();
        }
    }
    
    public EmpresaGestao(Integer id, Empresa empresa) {
        this.id = id;
        this.empresa = empresa;
    }

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
        if (empresa != null) {
            this.id = empresa.getId();
        }
    }

    @Override
    public String toString() {
        return "EmpresaGestao{" +
               "id=" + id +
               ", empresa=" + (empresa != null ? empresa.getId() : "null") +
               '}';
    }
}