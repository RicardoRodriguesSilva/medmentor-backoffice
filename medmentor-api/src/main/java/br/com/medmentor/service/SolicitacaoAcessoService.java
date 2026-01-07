package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.SolicitacaoAcessoDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface SolicitacaoAcessoService extends GenericService {
    
    SolicitacaoAcessoDTO incluirSolicitacaoAcesso(SolicitacaoAcessoDTO solicitacaoAcessoDTO) throws MedmentorException;
    
    void excluirSolicitacaoAcesso(Integer id) throws MedmentorException;
    
    void alterarSolicitacaoAcesso(SolicitacaoAcessoDTO solicitacaoAcessoDTO) throws MedmentorException;
    
    SolicitacaoAcessoDTO recuperarSolicitacaoAcessoPorId(Integer id) throws MedmentorException;
    
    List<SolicitacaoAcessoDTO> recuperarListaSolicitacaoAcesso() throws MedmentorException;
    
}