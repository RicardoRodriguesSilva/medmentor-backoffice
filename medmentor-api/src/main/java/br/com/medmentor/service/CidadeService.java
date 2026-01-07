package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.CidadeDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface CidadeService extends GenericService {
	
    CidadeDTO incluirCidade(CidadeDTO cidadeDTO) throws MedmentorException;
    
    void excluirCidade(Integer id) throws MedmentorException;
    
    void alterarCidade(CidadeDTO cidadeDTO) throws MedmentorException;
    
    CidadeDTO recuperarCidadePorId(Integer id) throws MedmentorException;
    
    List<CidadeDTO> recuperarListaCidade() throws MedmentorException;	
	    
	List<CidadeDTO> listarTodasPorUnidadeFederacao(Integer idUnidadeFederacao) throws MedmentorException;
	
	List<CidadeDTO> listarTodasPorUnidadeFederacaoENome(Integer idUnidadeFederacao, String nome) throws MedmentorException;
}