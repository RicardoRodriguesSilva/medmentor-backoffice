package br.com.medmentor.service;

import java.sql.SQLException;
import java.util.List;

import br.com.medmentor.dto.EmpresaGestaoDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface EmpresaGestaoService extends GenericService {
	
	EmpresaGestaoDTO incluiEmpresaGestao(EmpresaGestaoDTO empresaGestaoDTO) throws MedmentorException;
	
	void excluiEmpresaGestao(Integer id) throws MedmentorException;
	
	void alteraEmpresaGestao(EmpresaGestaoDTO empresaGestaoDTO) throws MedmentorException;
	
	EmpresaGestaoDTO recuperaEmpresaGestaoPorId(Integer id) throws MedmentorException;
	
	List<EmpresaGestaoDTO> recuperaListaEmpresaGestao() throws SQLException;
}
