package br.com.medmentor.mobile.mapper;

import java.util.ArrayList;
import java.util.List;

import br.com.medmentor.dto.EscalaTrabalhoDTO;
import br.com.medmentor.mobile.dto.EscalaDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class EscalaMapper {
	
	public EscalaDTO toEscalaDto(EscalaTrabalhoDTO escalaTrabalhoDTO, Integer idProfissional) {
    	
		if (escalaTrabalhoDTO == null) {
            return null;
        }
		
		EscalaDTO escalaDTO = new EscalaDTO();
		escalaDTO.setBolAtivo(escalaTrabalhoDTO.getBolAtivo());
		escalaDTO.setBolDisponibilizado(escalaTrabalhoDTO.getBolDisponibilizado());
		if (escalaTrabalhoDTO.getEmpresaProfissionalDTO().getProfissionalDTO().getId() == idProfissional) {
			escalaDTO.setBolEscalaTerceiro(false);
		} else {
			escalaDTO.setBolAtivo(true);
		}
		escalaDTO.setBolRealizado(escalaTrabalhoDTO.getBolRealizado());
		escalaDTO.setDataHoraEntrada(escalaTrabalhoDTO.getDataHoraEntrada());
		escalaDTO.setDataHoraSaida(escalaTrabalhoDTO.getDataHoraSaida());
		escalaDTO.setId(escalaTrabalhoDTO.getId());
		escalaDTO.setIdGestoraSaude(escalaTrabalhoDTO.getEmpresaUnidadeGestaoDTO().getEmpresaGestoraDTO().getId());
		escalaDTO.setIdProfissional(escalaTrabalhoDTO.getEmpresaProfissionalDTO().getProfissionalDTO().getId());
		escalaDTO.setIdUnidadeSaude(escalaTrabalhoDTO.getEmpresaUnidadeGestaoDTO().getId());
		escalaDTO.setNomeGestoraSaude(escalaTrabalhoDTO.getEmpresaUnidadeGestaoDTO().getEmpresaGestoraDTO().getEmpresaDTO().getNomeFantasia());
		escalaDTO.setNomeUnidadeSaude(escalaTrabalhoDTO.getEmpresaUnidadeGestaoDTO().getEmpresaDTO().getNomeFantasia());
		
		return escalaDTO;
	}

    public List<EscalaDTO> toListDto(List<EscalaTrabalhoDTO> listaDTO, Integer idProfissional) {
    	List<EscalaDTO> listaEscalaDTO = new ArrayList<EscalaDTO>();
    	
    	if ((listaDTO!=null)&&(listaDTO.size()>0)) {
    		for (EscalaTrabalhoDTO dto: listaDTO) {
    			
    			EscalaDTO escalaDTO = new EscalaDTO();
    			escalaDTO.setId(dto.getId());
    			escalaDTO.setIdGestoraSaude(dto.getEmpresaUnidadeGestaoDTO().getEmpresaGestoraDTO().getEmpresaDTO().getId());
    			escalaDTO.setNomeGestoraSaude(dto.getEmpresaUnidadeGestaoDTO().getEmpresaGestoraDTO().getEmpresaDTO().getNomeFantasia());;
    			escalaDTO.setIdUnidadeSaude(dto.getEmpresaUnidadeGestaoDTO().getEmpresaDTO().getId());
    			escalaDTO.setNomeUnidadeSaude(dto.getEmpresaUnidadeGestaoDTO().getEmpresaDTO().getNomeFantasia());
    			escalaDTO.setIdProfissional(dto.getEmpresaProfissionalDTO().getProfissionalDTO().getPessoaFisicaDTO().getId());;
    			escalaDTO.setDataHoraEntrada(dto.getDataHoraEntrada());
    			escalaDTO.setDataHoraSaida(dto.getDataHoraSaida());
    			escalaDTO.setBolAtivo(dto.getBolAtivo());
    			escalaDTO.setBolRealizado(dto.getBolRealizado());
    			escalaDTO.setBolDisponibilizado(dto.getBolDisponibilizado());
    			if (dto.getEmpresaProfissionalDTO().getProfissionalDTO().getId() == idProfissional) {
    				escalaDTO.setBolEscalaTerceiro(false);
    			} else {
    				escalaDTO.setBolAtivo(true);
    			}
    			
    			listaEscalaDTO.add(escalaDTO);
    		}
    	}
   	
    	return listaEscalaDTO;    	
    }

}