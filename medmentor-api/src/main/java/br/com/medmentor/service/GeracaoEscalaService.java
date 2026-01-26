package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.GeracaoEscalaDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.filtro.dto.FiltroGeracaoEscalaDTO;
import jakarta.ejb.Local;

@Local
public interface GeracaoEscalaService extends GenericService {
    
	GeracaoEscalaDTO incluiGeracaoEscala(GeracaoEscalaDTO geracaoEscalaDTO) throws MedmentorException;
	
	void excluiGeracaoEscala(Integer id) throws MedmentorException;
	
	void alteraGeracaoEscala(GeracaoEscalaDTO geracaoEscalaDTO) throws MedmentorException;
	
	GeracaoEscalaDTO recuperaGeracaoEscalaPorId(Integer id) throws MedmentorException;
	
	List<GeracaoEscalaDTO> recuperaListaGeracaoEscala() throws MedmentorException;
	
	List<GeracaoEscalaDTO> recuperaListaGeracaoEscalaPorFiltro(FiltroGeracaoEscalaDTO filtroGeracaoEscalaDTO) throws MedmentorException;
}