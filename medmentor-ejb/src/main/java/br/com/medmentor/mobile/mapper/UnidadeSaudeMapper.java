package br.com.medmentor.mobile.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.medmentor.dto.EmpresaUnidadeGestaoDTO;
import br.com.medmentor.mobile.dto.UnidadeSaudeDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class UnidadeSaudeMapper {

	public UnidadeSaudeDTO toUnidadeSaudeDto(EmpresaUnidadeGestaoDTO empresaUnidadeGestaoDTO) {
    	if(empresaUnidadeGestaoDTO == null) {
    		return null;
    	}
    	
    	UnidadeSaudeDTO dto = new UnidadeSaudeDTO();
    	dto.setId(empresaUnidadeGestaoDTO.getId());
    	dto.setNome(empresaUnidadeGestaoDTO.getEmpresaDTO().getNomeFantasia());
    	return dto;
    }  
	
    public List<UnidadeSaudeDTO> toListDto(List<EmpresaUnidadeGestaoDTO> listaEmpresaUnidadeSaude) {
        if (listaEmpresaUnidadeSaude == null) {
            return new ArrayList<>(); 
        }

        return listaEmpresaUnidadeSaude.stream().map(this::toUnidadeSaudeDto).collect(Collectors.toList());
    }	

}