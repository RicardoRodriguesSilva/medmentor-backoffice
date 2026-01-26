package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.PerfilMenuAcaoDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface PerfilMenuAcaoService extends GenericService {
    
    PerfilMenuAcaoDTO incluiPerfilMenuAcao(PerfilMenuAcaoDTO perfilMenuAcaoDTO) throws MedmentorException;
    
    void excluiPerfilMenuAcao(Integer id) throws MedmentorException;
    
    void alteraPerfilMenuAcao(PerfilMenuAcaoDTO perfilMenuAcaoDTO) throws MedmentorException;
    
    PerfilMenuAcaoDTO recuperaPerfilMenuAcaoPorId(Integer id) throws MedmentorException;
    
    List<PerfilMenuAcaoDTO> recuperaListaPerfilMenuAcao() throws MedmentorException;
    
} 