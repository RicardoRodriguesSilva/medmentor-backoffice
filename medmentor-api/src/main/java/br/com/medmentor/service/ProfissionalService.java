package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.ProfissionalDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface ProfissionalService extends GenericService {
    
	ProfissionalDTO incluirProfissional(ProfissionalDTO profissionalDTO) throws MedmentorException;
	
	void excluirProfissional(Integer id) throws MedmentorException;
	
	void alterarProfissional(ProfissionalDTO profissionalDTO) throws MedmentorException;
	
	ProfissionalDTO recuperarProfissionalPorId(Integer id) throws MedmentorException;
	
	List<ProfissionalDTO> recuperarProfissionalPorCidade(Integer idCidade) throws MedmentorException;
	
	List<ProfissionalDTO> recuperarListaProfissionalPorNome(String nome) throws MedmentorException;
	
	List<ProfissionalDTO> recuperarListaProfissional() throws MedmentorException;
	
}