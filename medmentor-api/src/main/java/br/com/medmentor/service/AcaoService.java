package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.AcaoDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface AcaoService extends GenericService {
    
    AcaoDTO incluirAcao(AcaoDTO acaoDTO) throws MedmentorException;
    
    void excluirAcao(Integer id) throws MedmentorException;
    
    void alterarAcao(AcaoDTO acaoDTO) throws MedmentorException;
    
    AcaoDTO recuperarAcaoPorId(Integer id) throws MedmentorException;
    
    List<AcaoDTO> recuperarListaAcao() throws MedmentorException;
    
}