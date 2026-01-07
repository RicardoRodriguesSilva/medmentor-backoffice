package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.EmpresaUnidadeGestaoDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface EmpresaUnidadeGestaoService extends GenericService {
	
	EmpresaUnidadeGestaoDTO incluirEmpresaUnidadeGestao(EmpresaUnidadeGestaoDTO empresaUnidadeGestaoDTO) throws MedmentorException;
	
	void excluirEmpresaUnidadeGestao(Integer id) throws MedmentorException;
	
	void alterarEmpresaUnidadeGestao(EmpresaUnidadeGestaoDTO empresaUnidadeGestaoDTO) throws MedmentorException;
	
	EmpresaUnidadeGestaoDTO recuperarEmpresaUnidadeGestaoPorId(Integer id) throws MedmentorException;
	
	List<EmpresaUnidadeGestaoDTO> recuperarListaEmpresaUnidadeGestao() throws MedmentorException; 
}
