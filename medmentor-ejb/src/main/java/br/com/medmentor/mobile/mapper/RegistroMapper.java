package br.com.medmentor.mobile.mapper;

import br.com.medmentor.dto.ProfissionalRegistroDTO;
import br.com.medmentor.mobile.dto.RegistroDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class RegistroMapper {

    public RegistroDTO toRegistroDto(ProfissionalRegistroDTO profissionalRegistroDTO) {
    	if (profissionalRegistroDTO == null) {
    		return null;
    	}
    	
    	RegistroDTO dto = new RegistroDTO();
    	dto.setAnoFormacao(profissionalRegistroDTO.getAnoFormacao());
    	dto.setInstituicao(profissionalRegistroDTO.getRegistro());
    	dto.setRegistro(profissionalRegistroDTO.getRegistro());
    	dto.setId(profissionalRegistroDTO.getProfissionalDTO().getId());

    	return dto;
    	
    }

    public ProfissionalRegistroDTO toProfissionalRegistroDto(RegistroDTO registroDTO, ProfissionalRegistroDTO profissionalRegistroDTO) {
    	if (profissionalRegistroDTO == null) {
    		return null;
    	}
    	
    	profissionalRegistroDTO.setAnoFormacao(registroDTO.getAnoFormacao());
    	profissionalRegistroDTO.setInstituicao(registroDTO.getRegistro());
    	profissionalRegistroDTO.setRegistro(registroDTO.getRegistro());

    	return profissionalRegistroDTO;
    }
}