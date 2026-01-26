package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.SolicitacaoAcessoDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface SolicitacaoAcessoService extends GenericService {
    
    SolicitacaoAcessoDTO incluiSolicitacaoAcesso(SolicitacaoAcessoDTO solicitacaoAcessoDTO) throws MedmentorException;
    
    void excluiSolicitacaoAcesso(Integer id) throws MedmentorException;
    
    void alteraSolicitacaoAcesso(SolicitacaoAcessoDTO solicitacaoAcessoDTO) throws MedmentorException;
    
    SolicitacaoAcessoDTO recuperaSolicitacaoAcessoPorId(Integer id) throws MedmentorException;
    
    List<SolicitacaoAcessoDTO> recuperaListaSolicitacaoAcesso() throws MedmentorException;
    
}