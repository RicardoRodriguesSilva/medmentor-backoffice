package br.com.medmentor.mobile.service;

import java.util.List;

import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.mobile.dto.EscalaDTO;
import br.com.medmentor.mobile.dto.GestoraSaudeDTO;
import br.com.medmentor.mobile.filtro.dto.FiltroEscalaDTO;
import br.com.medmentor.service.GenericService;
import jakarta.ejb.Local;

@Local
public interface EscalaService extends GenericService {
	
	List<GestoraSaudeDTO> recuperaListaGestoraSaudePorProfissional(Integer id) throws MedmentorException;
	
	List<EscalaDTO> recuperaEscalaPorFiltro(FiltroEscalaDTO filtroEscalaDTO) throws MedmentorException;
}
