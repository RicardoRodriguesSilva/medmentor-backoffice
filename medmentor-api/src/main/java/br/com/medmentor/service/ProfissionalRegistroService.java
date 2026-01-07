package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.ProfissionalRegistroDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface ProfissionalRegistroService extends GenericService {
    
	ProfissionalRegistroDTO incluirProfissionalRegistro(ProfissionalRegistroDTO profissionalRegistroDTO) throws MedmentorException;
	
	void excluirProfissionalRegistro(Integer idProfissional) throws MedmentorException;
	
	void alterarProfissionalRegistro(ProfissionalRegistroDTO profissionalRegistroDTO) throws MedmentorException;
	
	ProfissionalRegistroDTO recuperarProfissionalRegistroPorId(Integer idProfissional) throws MedmentorException;
	
	List<ProfissionalRegistroDTO> recuperarListaProfissionalRegistro() throws MedmentorException;
	
}