package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.EmpresaProfissionalDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface EmpresaProfissionalService extends GenericService {
	
	EmpresaProfissionalDTO incluiEmpresaProfissional(EmpresaProfissionalDTO EmpresaProfissionalDTO) throws MedmentorException;
	
	void excluiEmpresaProfissional(Integer idEmpresaProfissional) throws MedmentorException;
	
	void alteraEmpresaProfissional(EmpresaProfissionalDTO EmpresaProfissionalDTO) throws MedmentorException;
	
	EmpresaProfissionalDTO recuperaEmpresaProfissionalPorProfissional(Integer idProfissional) throws MedmentorException;
	
	EmpresaProfissionalDTO recuperaEmpresaProfissionalPorId(Integer id) throws MedmentorException;
	
	List<EmpresaProfissionalDTO> recuperaListaEmpresaProfissional() throws MedmentorException; 
}
