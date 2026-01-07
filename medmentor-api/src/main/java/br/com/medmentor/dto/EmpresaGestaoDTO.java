package br.com.medmentor.dto;

public class EmpresaGestaoDTO extends GenericDTO {
	
    private static final long serialVersionUID = 1L;

    private Integer id; 
    private EmpresaDTO empresaDTO;
    
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

	@Override
	public String toString() {
		return "EmpresaGestaoDTO [id=" + id + ", empresaDTO=" + empresaDTO + "]";
	}  
}