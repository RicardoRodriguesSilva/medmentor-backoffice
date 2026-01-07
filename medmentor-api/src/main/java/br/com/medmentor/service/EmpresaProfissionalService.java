package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.EmpresaProfissionalDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface EmpresaProfissionalService extends GenericService {
	
	EmpresaProfissionalDTO incluirEmpresaProfissional(EmpresaProfissionalDTO EmpresaProfissionalDTO) throws MedmentorException;
	
	void excluirEmpresaProfissional(Integer idEmpresaProfissional) throws MedmentorException;
	
	void alterarEmpresaProfissional(EmpresaProfissionalDTO EmpresaProfissionalDTO) throws MedmentorException;
	
	EmpresaProfissionalDTO recuperarEmpresaProfissionalPorProfissional(Integer idProfissional) throws MedmentorException;
	
	EmpresaProfissionalDTO recuperarEmpresaProfissionalPorId(Integer id) throws MedmentorException;
	
	List<EmpresaProfissionalDTO> recuperarListaEmpresaProfissional() throws MedmentorException; 
}
