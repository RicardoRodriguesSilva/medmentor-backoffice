package br.com.medmentor.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.medmentor.dao.UnidadeFederacaoDAO;
import br.com.medmentor.dto.UnidadeFederacaoDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.mapper.UnidadeFederacaoMapper;
import br.com.medmentor.model.UnidadeFederacao;
import br.com.medmentor.service.UnidadeFederacaoService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless 
public class UnidadeFederacaoServiceImpl implements UnidadeFederacaoService {

    @Inject 
    private UnidadeFederacaoDAO unidadeFederacaoDAO;
    
    @Inject
    private UnidadeFederacaoMapper unidadeFederacaoMapper;

    public UnidadeFederacaoServiceImpl() {
		
	}
    
    @Override
    public UnidadeFederacaoDTO incluirUnidadeFederacao(UnidadeFederacaoDTO unidadeFederacaoDTO) throws MedmentorException {
        UnidadeFederacao unidadeFederacao = unidadeFederacaoMapper.toEntity(unidadeFederacaoDTO);
        
        try {
            unidadeFederacao = unidadeFederacaoDAO.create(unidadeFederacao);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }		

        return unidadeFederacaoMapper.toDto(unidadeFederacao);
    }

    @Override
    public void excluirUnidadeFederacao(Integer id) throws MedmentorException {
        try {
            unidadeFederacaoDAO.delete(id);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }				
    }

    @Override
    public void alterarUnidadeFederacao(UnidadeFederacaoDTO unidadeFederacaoDTO) throws MedmentorException {
        UnidadeFederacao unidadeFederacao = unidadeFederacaoMapper.toEntity(unidadeFederacaoDTO);
        try {
            unidadeFederacaoDAO.update(unidadeFederacao);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }	
    }

    @Override
    public UnidadeFederacaoDTO recuperarUnidadeFederacaoPorId(Integer id) throws MedmentorException {
        UnidadeFederacao unidadeFederacao;
        try {
            unidadeFederacao = unidadeFederacaoDAO.findById(id);
            if (unidadeFederacao == null) {
                throw new MedmentorException("UnidadeFederacao com ID " + id + " n�o encontrado.");
            }
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }
        
        return unidadeFederacaoMapper.toDto(unidadeFederacao);
    }    
    
	@Override
	public List<UnidadeFederacaoDTO> listarTodos() throws MedmentorException {
		List<UnidadeFederacaoDTO> listaDto = new ArrayList<UnidadeFederacaoDTO>();
		try {
			listaDto = unidadeFederacaoMapper.toListDto(unidadeFederacaoDAO.findAll());
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		return listaDto;
	}
}
