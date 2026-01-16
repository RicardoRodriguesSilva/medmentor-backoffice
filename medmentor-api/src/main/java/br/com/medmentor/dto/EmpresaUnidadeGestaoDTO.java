package br.com.medmentor.dto;

public class EmpresaUnidadeGestaoDTO extends GenericDTO {
	
    private static final long serialVersionUID = 1L;

    private Integer id;   
    private EmpresaDTO empresaDTO;
    private EmpresaGestaoDTO empresaGestoraDTO;
    private Integer numeroPlantonistaDia;
    private Integer numeroPlantonistaPeriodo;
    
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public EmpresaDTO getEmpresaDTO() {
		return empresaDTO;
	}
	
	public void setEmpresaDTO(EmpresaDTO empresaDTO) {
		this.empresaDTO = empresaDTO;
	}
	
	public EmpresaGestaoDTO getEmpresaGestoraDTO() {
		return empresaGestoraDTO;
	}
	
	public void setEmpresaGestoraDTO(EmpresaGestaoDTO empresaGestoraDTO) {
		this.empresaGestoraDTO = empresaGestoraDTO;
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

	@Override
	public String toString() {
		return "EmpresaUnidadeGestaoDTO [id=" + id + ", empresaDTO=" + empresaDTO + ", empresaGestoraDTO="
				+ empresaGestoraDTO + ", numeroPlantonistaDia=" + numeroPlantonistaDia + ", numeroPlantonistaPeriodo="
				+ numeroPlantonistaPeriodo + "]";
	}
}