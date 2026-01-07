package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.UnidadeFederacaoDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface UnidadeFederacaoService extends GenericService {
	
    UnidadeFederacaoDTO incluirUnidadeFederacao(UnidadeFederacaoDTO unidadeFederacaoDTO) throws MedmentorException;
    
    void excluirUnidadeFederacao(Integer id) throws MedmentorException;
    
    void alterarUnidadeFederacao(UnidadeFederacaoDTO unidadeFederacaoDTO) throws MedmentorException;
    
    UnidadeFederacaoDTO recuperarUnidadeFederacaoPorId(Integer id) throws MedmentorException;
    
	List<UnidadeFederacaoDTO> listarTodos() throws MedmentorException;
}