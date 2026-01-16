package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.GeracaoEscalaDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.filtro.dto.FiltroGeracaoEscalaDTO;
import jakarta.ejb.Local;

@Local
public interface GeracaoEscalaService extends GenericService {
    
	GeracaoEscalaDTO incluirGeracaoEscala(GeracaoEscalaDTO geracaoEscalaDTO) throws MedmentorException;
	
	void excluirGeracaoEscala(Integer id) throws MedmentorException;
	
	void alterarGeracaoEscala(GeracaoEscalaDTO geracaoEscalaDTO) throws MedmentorException;
	
	GeracaoEscalaDTO recuperarGeracaoEscalaPorId(Integer id) throws MedmentorException;
	
	List<GeracaoEscalaDTO> recuperarListaGeracaoEscala() throws MedmentorException;
	
	List<GeracaoEscalaDTO> recuperarListaGeracaoEscalaPorFiltro(FiltroGeracaoEscalaDTO filtroGeracaoEscalaDTO) throws MedmentorException;
}