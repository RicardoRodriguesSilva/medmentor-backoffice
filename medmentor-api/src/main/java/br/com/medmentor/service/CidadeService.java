package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.CidadeDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface CidadeService extends GenericService {
	
    CidadeDTO incluiCidade(CidadeDTO cidadeDTO) throws MedmentorException;
    
    void excluiCidade(Integer id) throws MedmentorException;
    
    void alteraCidade(CidadeDTO cidadeDTO) throws MedmentorException;
    
    CidadeDTO recuperaCidadePorId(Integer id) throws MedmentorException;
    
    List<CidadeDTO> recuperaListaCidade() throws MedmentorException;	
	    
	List<CidadeDTO> listaTodasPorUnidadeFederacao(Integer idUnidadeFederacao) throws MedmentorException;
	
	List<CidadeDTO> listaTodasPorUnidadeFederacaoENome(Integer idUnidadeFederacao, String nome) throws MedmentorException;
}