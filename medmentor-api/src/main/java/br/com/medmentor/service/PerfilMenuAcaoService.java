package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.PerfilMenuAcaoDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface PerfilMenuAcaoService extends GenericService {
    
    PerfilMenuAcaoDTO incluirPerfilMenuAcao(PerfilMenuAcaoDTO perfilMenuAcaoDTO) throws MedmentorException;
    
    void excluirPerfilMenuAcao(Integer id) throws MedmentorException;
    
    void alterarPerfilMenuAcao(PerfilMenuAcaoDTO perfilMenuAcaoDTO) throws MedmentorException;
    
    PerfilMenuAcaoDTO recuperarPerfilMenuAcaoPorId(Integer id) throws MedmentorException;
    
    List<PerfilMenuAcaoDTO> recuperarListaPerfilMenuAcao() throws MedmentorException;
    
} 