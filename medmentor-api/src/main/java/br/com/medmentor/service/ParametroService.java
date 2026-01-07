package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.ParametroDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface ParametroService extends GenericService {
    
    ParametroDTO incluirParametro(ParametroDTO parametroDTO) throws MedmentorException;
    
    void excluirParametro(Integer id) throws MedmentorException;
    
    void alterarParametro(ParametroDTO parametroDTO) throws MedmentorException;
    
    ParametroDTO recuperarParametroPorId(Integer id) throws MedmentorException;
    
    List<ParametroDTO> recuperarListaParametro() throws MedmentorException;
    
}