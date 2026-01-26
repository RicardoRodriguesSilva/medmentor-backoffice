package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.ParametroDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface ParametroService extends GenericService {
    
    ParametroDTO incluiParametro(ParametroDTO parametroDTO) throws MedmentorException;
    
    void excluiParametro(Integer id) throws MedmentorException;
    
    void alteraParametro(ParametroDTO parametroDTO) throws MedmentorException;
    
    ParametroDTO recuperaParametroPorId(Integer id) throws MedmentorException;
    
    List<ParametroDTO> recuperaListaParametro() throws MedmentorException;
    
}