package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.ProfissionalRegistroDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface ProfissionalRegistroService extends GenericService {
    
	ProfissionalRegistroDTO incluiProfissionalRegistro(ProfissionalRegistroDTO profissionalRegistroDTO) throws MedmentorException;
	
	void excluiProfissionalRegistro(Integer idProfissional) throws MedmentorException;
	
	void alteraProfissionalRegistro(ProfissionalRegistroDTO profissionalRegistroDTO) throws MedmentorException;
	
	ProfissionalRegistroDTO recuperaProfissionalRegistroPorId(Integer idProfissional) throws MedmentorException;
	
	List<ProfissionalRegistroDTO> recuperaListaProfissionalRegistro() throws MedmentorException;
	
}