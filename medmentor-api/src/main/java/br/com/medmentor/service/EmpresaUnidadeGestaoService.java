package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.EmpresaUnidadeGestaoDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface EmpresaUnidadeGestaoService extends GenericService {
	
	EmpresaUnidadeGestaoDTO incluiEmpresaUnidadeGestao(EmpresaUnidadeGestaoDTO empresaUnidadeGestaoDTO) throws MedmentorException;
	
	void excluiEmpresaUnidadeGestao(Integer id) throws MedmentorException;
	
	void alteraEmpresaUnidadeGestao(EmpresaUnidadeGestaoDTO empresaUnidadeGestaoDTO) throws MedmentorException;
	
	EmpresaUnidadeGestaoDTO recuperaEmpresaUnidadeGestaoPorId(Integer id) throws MedmentorException;
	
	List<EmpresaUnidadeGestaoDTO> recuperaListaEmpresaUnidadeGestao() throws MedmentorException;
	
	List<EmpresaUnidadeGestaoDTO> recuperaListaEmpresaUnidadeGestaoPorIdProfissional(Integer idProfissional) throws MedmentorException;
}
