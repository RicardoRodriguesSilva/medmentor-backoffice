package br.com.medmentor.mobile.mapper;

import java.util.ArrayList;
import java.util.List;

import br.com.medmentor.dto.HorasEscalaTrabalhoDTO;
import br.com.medmentor.mobile.dto.HorasEscalaDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class HorasEscalaMapper {
	
	@Inject
	EmpresaMapper empresaMapper;
	
	@Inject
	GestoraSaudeMapper gestoraSaudeMapper;
	
	@Inject
	UnidadeSaudeMapper unidadeSaudeMapper;
	

    public List<HorasEscalaDTO> toListDto(List<HorasEscalaTrabalhoDTO> listaDTO) {
    	List<HorasEscalaDTO> listaHorasEscalaDTO = new ArrayList<HorasEscalaDTO>();
    	
    	if ((listaDTO!=null)&&(listaDTO.size()>0)) {
    		for (HorasEscalaTrabalhoDTO dto: listaDTO) {
    			
    			HorasEscalaDTO horasEscalaDTO = new HorasEscalaDTO();
    			horasEscalaDTO.setEmpresaDTO(empresaMapper.toEmpresaDto(dto.getEmpresaProfissionalDTO()));
    			horasEscalaDTO.setGestoraSaudeDTO(gestoraSaudeMapper.toGestoraSaudeDto(dto.getEmpresaGestaoDTO()));
    			horasEscalaDTO.setUnidadeSaudeDTO(unidadeSaudeMapper.toUnidadeSaudeDto(dto.getEmpresaUnidadeGestaoDTO()));
    			horasEscalaDTO.setTotalHorasATrabalhar(dto.getTotalHorasATrabalhar());
    			horasEscalaDTO.setTotalHorasNaoTrabalhadas(dto.getTotalHorasNaoTrabalhadas());
    			horasEscalaDTO.setTotalHorasTrabalhadas(dto.getTotalHorasTrabalhadas());
    		}
    	}
   	
    	return listaHorasEscalaDTO;    	
    }
}