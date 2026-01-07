package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.MudancaEscalaTrabalhoDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface MudancaEscalaTrabalhoService extends GenericService {
    
	MudancaEscalaTrabalhoDTO incluirMudancaEscalaTrabalho(MudancaEscalaTrabalhoDTO mudancaEscalaTrabalhoDTO) throws MedmentorException;
	
	void excluirMudancaEscalaTrabalho(Integer id) throws MedmentorException;
	
	void alterarMudancaEscalaTrabalho(MudancaEscalaTrabalhoDTO mudancaEscalaTrabalhoDTO) throws MedmentorException;
	
	MudancaEscalaTrabalhoDTO recuperarMudancaEscalaTrabalhoPorId(Integer id) throws MedmentorException;
	
	List<MudancaEscalaTrabalhoDTO> recuperarListaMudancaEscalaTrabalho() throws MedmentorException;
	
}