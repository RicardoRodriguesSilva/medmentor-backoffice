package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.AcaoDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface AcaoService extends GenericService {
    
    AcaoDTO incluiAcao(AcaoDTO acaoDTO) throws MedmentorException;
    
    void excluiAcao(Integer id) throws MedmentorException;
    
    void alteraAcao(AcaoDTO acaoDTO) throws MedmentorException;
    
    AcaoDTO recuperaAcaoPorId(Integer id) throws MedmentorException;
    
    List<AcaoDTO> recuperaListaAcao() throws MedmentorException;
    
}