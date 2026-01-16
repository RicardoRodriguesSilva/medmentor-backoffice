package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.EscalaTrabalhoDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.filtro.dto.FiltroEscalaTrabalhoDTO;
import br.com.medmentor.mobile.filtro.dto.FiltroEscalaDTO;
import jakarta.ejb.Local;

@Local
public interface EscalaTrabalhoService extends GenericService {
    
	EscalaTrabalhoDTO incluirEscalaTrabalho(EscalaTrabalhoDTO escalaTrabalhoDTO) throws MedmentorException;
	
	void excluirEscalaTrabalho(Integer id) throws MedmentorException;
	
	void alterarEscalaTrabalho(EscalaTrabalhoDTO escalaTrabalhoDTO) throws MedmentorException;
	
	EscalaTrabalhoDTO recuperarEscalaTrabalhoPorId(Integer id) throws MedmentorException;
	
	List<EscalaTrabalhoDTO> recuperarListaEscalaTrabalho() throws MedmentorException;
	
	List<EscalaTrabalhoDTO> recuperarListaEscalaTrabalhoPorFiltro(FiltroEscalaTrabalhoDTO filtroEscalaTrabalhoDTO) throws MedmentorException;
	
	List<EscalaTrabalhoDTO> recuperarListaEscalaTrabalhoPorFiltro(FiltroEscalaDTO filtroEscalaDTO) throws MedmentorException;
	
}