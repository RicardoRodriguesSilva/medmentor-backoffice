package br.com.medmentor.mobile.service;

import java.util.List;

import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.filtro.dto.FiltroHorasEscalaTrabalhoDTO;
import br.com.medmentor.mobile.dto.EscalaDTO;
import br.com.medmentor.mobile.dto.GestoraSaudeDTO;
import br.com.medmentor.mobile.dto.HorasEscalaDTO;
import br.com.medmentor.mobile.filtro.dto.FiltroEscalaDTO;
import br.com.medmentor.service.GenericService;
import jakarta.ejb.Local;

@Local
public interface MovimentacaoService extends GenericService {
	
	List<GestoraSaudeDTO> recuperaListaGestoraSaudePorProfissional(Integer id) throws MedmentorException;
	
	List<EscalaDTO> recuperaListaEscalaPorFiltro(FiltroEscalaDTO filtroEscalaDTO) throws MedmentorException;
	
	List<HorasEscalaDTO> recuperaListaHorasTrabalhadasPorFiltro(FiltroHorasEscalaTrabalhoDTO filtroHorasTrabalhadasDTO) throws MedmentorException;
	
	void disponibilzaEscalaTrabalho(Integer id) throws MedmentorException;
	
	void confirmaEscalaTrabalhoEfetuado(Integer id) throws MedmentorException;
}
