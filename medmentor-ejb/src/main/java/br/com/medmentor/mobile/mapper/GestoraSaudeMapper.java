package br.com.medmentor.mobile.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.medmentor.dto.EmpresaUnidadeGestaoDTO;
import br.com.medmentor.mobile.dto.GestoraSaudeDTO;
import br.com.medmentor.mobile.dto.UnidadeSaudeDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class GestoraSaudeMapper {

    public List<GestoraSaudeDTO> toListDTO(List<EmpresaUnidadeGestaoDTO> listaDTO) {
    	List<GestoraSaudeDTO> listaGestoraSaudeDTO = new ArrayList<GestoraSaudeDTO>();
    	Map<Integer, GestoraSaudeDTO> mapaGestoraSaude = new HashMap<Integer, GestoraSaudeDTO>();
    	
    	if ((listaDTO!=null)&&(listaDTO.size()>0)) {
    		for (EmpresaUnidadeGestaoDTO dto: listaDTO) {
    			if (mapaGestoraSaude.get(dto.getEmpresaGestoraDTO().getId())!=null) {
    				UnidadeSaudeDTO unidadeSaudeDTO = new UnidadeSaudeDTO();
    				unidadeSaudeDTO.setId(dto.getId());
    				unidadeSaudeDTO.setNome(dto.getEmpresaDTO().getNomeFantasia());
    				mapaGestoraSaude.get(dto.getEmpresaGestoraDTO().getId()).getListaUnidadeSaudeDTO().add(unidadeSaudeDTO);
    			} else {
    				GestoraSaudeDTO gestoraSaudeDTO = new GestoraSaudeDTO();
    				gestoraSaudeDTO.setId(dto.getEmpresaGestoraDTO().getId());
    				gestoraSaudeDTO.setNome(dto.getEmpresaGestoraDTO().getEmpresaDTO().getNomeFantasia());
    				
    				UnidadeSaudeDTO unidadeSaudeDTO = new UnidadeSaudeDTO();
    				unidadeSaudeDTO.setId(dto.getId());
    				unidadeSaudeDTO.setNome(dto.getEmpresaDTO().getNomeFantasia());
    				gestoraSaudeDTO.getListaUnidadeSaudeDTO().add(unidadeSaudeDTO);
    				mapaGestoraSaude.put(gestoraSaudeDTO.getId(), gestoraSaudeDTO);
    			}
    		}
    	}
    	
    	if (mapaGestoraSaude.size()>0) {
	    	for (Map.Entry<Integer, GestoraSaudeDTO> entrada : mapaGestoraSaude.entrySet()) {
	    		listaGestoraSaudeDTO.add(entrada.getValue());
	        }
    	}
    	
    	return listaGestoraSaudeDTO;    	
    }


}