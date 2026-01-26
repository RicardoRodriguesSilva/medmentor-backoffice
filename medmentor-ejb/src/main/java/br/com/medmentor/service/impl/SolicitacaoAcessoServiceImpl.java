package br.com.medmentor.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.medmentor.dao.SolicitacaoAcessoDAO;
import br.com.medmentor.dto.SolicitacaoAcessoDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.mapper.SolicitacaoAcessoMapper;
import br.com.medmentor.model.SolicitacaoAcesso;
import br.com.medmentor.service.SolicitacaoAcessoService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless 
public class SolicitacaoAcessoServiceImpl implements SolicitacaoAcessoService {

    @Inject 
    private SolicitacaoAcessoDAO acaoDAO;
    
    @Inject
    private SolicitacaoAcessoMapper acaoMapper;

    public SolicitacaoAcessoServiceImpl() {
    }

    @Override
    public SolicitacaoAcessoDTO incluiSolicitacaoAcesso(SolicitacaoAcessoDTO solicitacaoAcessoDTO) throws MedmentorException {
        SolicitacaoAcesso acao = acaoMapper.toEntity(solicitacaoAcessoDTO);
        
        try {
            acao = acaoDAO.create(acao);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }		

        return acaoMapper.toDto(acao);
    }

    @Override
    public void excluiSolicitacaoAcesso(Integer id) throws MedmentorException {
        try {
            acaoDAO.delete(id);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }				
    }

    @Override
    public void alteraSolicitacaoAcesso(SolicitacaoAcessoDTO solicitacaoAcessoDTO) throws MedmentorException {
        SolicitacaoAcesso acao = acaoMapper.toEntity(solicitacaoAcessoDTO);
        try {
            acaoDAO.update(acao);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }	
    }

    @Override
    public SolicitacaoAcessoDTO recuperaSolicitacaoAcessoPorId(Integer id) throws MedmentorException {
        SolicitacaoAcesso acao;
        try {
            acao = acaoDAO.findById(id);
            if (acao == null) {
                throw new MedmentorException("A��o com ID " + id + " n�o encontrada.");
            }
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }
        
        return acaoMapper.toDto(acao); 
    }

    @Override
    public List<SolicitacaoAcessoDTO> recuperaListaSolicitacaoAcesso() throws MedmentorException {
        List<SolicitacaoAcessoDTO> listaDto = new ArrayList<>();
        try {
            listaDto = acaoMapper.toListDto(acaoDAO.findAll());
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }
        return listaDto;
    } 
}