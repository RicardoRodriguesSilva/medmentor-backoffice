package br.com.medmentor.service;

import java.util.Date;
import java.util.List;

import br.com.medmentor.dto.EscalaTrabalhoDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface EscalaTrabalhoService extends GenericService {
    
	EscalaTrabalhoDTO incluirEscalaTrabalho(EscalaTrabalhoDTO escalaTrabalhoDTO) throws MedmentorException;
	
	void excluirEscalaTrabalho(Integer id) throws MedmentorException;
	
	void alterarEscalaTrabalho(EscalaTrabalhoDTO escalaTrabalhoDTO) throws MedmentorException;
	
	EscalaTrabalhoDTO recuperarEscalaTrabalhoPorId(Integer id) throws MedmentorException;
	
	List<EscalaTrabalhoDTO> recuperarListaEscalaTrabalho() throws MedmentorException;
	
	List<EscalaTrabalhoDTO> recuperarListaEscalaTrabalhoPorDataInicioEDataFim(Date dataInicio, Date dataFim) throws MedmentorException;
	
}