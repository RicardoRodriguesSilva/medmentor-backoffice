package br.com.medmentor.service;

import java.sql.SQLException;
import java.util.List;

import br.com.medmentor.dto.EmpresaGestaoDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface EmpresaGestaoService extends GenericService {
	
	EmpresaGestaoDTO incluirEmpresaGestao(EmpresaGestaoDTO empresaGestaoDTO) throws MedmentorException;
	
	void excluirEmpresaGestao(Integer id) throws MedmentorException;
	
	void alterarEmpresaGestao(EmpresaGestaoDTO empresaGestaoDTO) throws MedmentorException;
	
	EmpresaGestaoDTO recuperarEmpresaGestaoPorId(Integer id) throws MedmentorException;
	
	List<EmpresaGestaoDTO> recuperarListaEmpresaGestao() throws SQLException;
}
