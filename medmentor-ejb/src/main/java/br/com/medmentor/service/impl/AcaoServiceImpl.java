package br.com.medmentor.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.medmentor.dao.AcaoDAO;
import br.com.medmentor.dto.AcaoDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.mapper.AcaoMapper;
import br.com.medmentor.model.Acao;
import br.com.medmentor.service.AcaoService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless 
public class AcaoServiceImpl implements AcaoService {

    @Inject 
    private AcaoDAO acaoDAO;
    
    @Inject
    private AcaoMapper acaoMapper;

    public AcaoServiceImpl() {
    }

    @Override
    public AcaoDTO incluirAcao(AcaoDTO acaoDTO) throws MedmentorException {
        Acao acao = acaoMapper.toEntity(acaoDTO);
        
        try {
            acao = acaoDAO.create(acao);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }		

        return acaoMapper.toDto(acao);
    }

    @Override
    public void excluirAcao(Integer id) throws MedmentorException {
        try {
            acaoDAO.delete(id);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }				
    }

    @Override
    public void alterarAcao(AcaoDTO acaoDTO) throws MedmentorException {
        Acao acao = acaoMapper.toEntity(acaoDTO);
        try {
            acaoDAO.update(acao);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }	
    }

    @Override
    public AcaoDTO recuperarAcaoPorId(Integer id) throws MedmentorException {
        Acao acao;
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
    public List<AcaoDTO> recuperarListaAcao() throws MedmentorException {
        List<AcaoDTO> listaDto = new ArrayList<>();
        try {
            listaDto = acaoMapper.toListDto(acaoDAO.findAll());
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }
        return listaDto;
    } 
}