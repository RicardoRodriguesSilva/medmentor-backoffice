package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.ProfissionalDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface ProfissionalService extends GenericService {
    
	ProfissionalDTO incluiProfissional(ProfissionalDTO profissionalDTO) throws MedmentorException;
	
	void excluiProfissional(Integer id) throws MedmentorException;
	
	void alteraProfissional(ProfissionalDTO profissionalDTO) throws MedmentorException;
	
	ProfissionalDTO recuperaProfissionalPorId(Integer id) throws MedmentorException;
	
	List<ProfissionalDTO> recuperaProfissionalPorCidade(Integer idCidade) throws MedmentorException;
	
	List<ProfissionalDTO> recuperaListaProfissionalPorNome(String nome) throws MedmentorException;
	
	List<ProfissionalDTO> recuperaListaProfissional() throws MedmentorException;
	
}