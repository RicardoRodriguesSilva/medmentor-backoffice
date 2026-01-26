package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.UnidadeFederacaoDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface UnidadeFederacaoService extends GenericService {
	
    UnidadeFederacaoDTO incluiUnidadeFederacao(UnidadeFederacaoDTO unidadeFederacaoDTO) throws MedmentorException;
    
    void excluiUnidadeFederacao(Integer id) throws MedmentorException;
    
    void alteraUnidadeFederacao(UnidadeFederacaoDTO unidadeFederacaoDTO) throws MedmentorException;
    
    UnidadeFederacaoDTO recuperaUnidadeFederacaoPorId(Integer id) throws MedmentorException;
    
	List<UnidadeFederacaoDTO> listaTodos() throws MedmentorException;
}