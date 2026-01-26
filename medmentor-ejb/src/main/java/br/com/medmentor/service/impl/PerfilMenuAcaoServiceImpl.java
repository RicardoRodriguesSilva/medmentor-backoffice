package br.com.medmentor.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import br.com.medmentor.dao.PerfilMenuAcaoDAO;
import br.com.medmentor.dto.PerfilMenuAcaoDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.mapper.PerfilMenuAcaoMapper;
import br.com.medmentor.model.PerfilMenuAcao;
import br.com.medmentor.service.PerfilMenuAcaoService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless 
public class PerfilMenuAcaoServiceImpl implements PerfilMenuAcaoService {

    @Inject 
    private PerfilMenuAcaoDAO perfilMenuAcaoDAO;
    
    @Inject
    private PerfilMenuAcaoMapper perfilMenuAcaoMapper;

    public PerfilMenuAcaoServiceImpl() {
    }

    @Override
    public PerfilMenuAcaoDTO incluiPerfilMenuAcao(PerfilMenuAcaoDTO perfilMenuAcaoDTO) throws MedmentorException {
        PerfilMenuAcao perfilMenuAcao = perfilMenuAcaoMapper.toEntity(perfilMenuAcaoDTO);
        
        try {
            perfilMenuAcao = perfilMenuAcaoDAO.create(perfilMenuAcao);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }		

        return perfilMenuAcaoMapper.toDto(perfilMenuAcao);
    }

    @Override
    public void excluiPerfilMenuAcao(Integer id) throws MedmentorException {
        try {
            perfilMenuAcaoDAO.delete(id);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }				
    }

    @Override
    public void alteraPerfilMenuAcao(PerfilMenuAcaoDTO perfilMenuAcaoDTO) throws MedmentorException {
        PerfilMenuAcao perfilMenuAcao = perfilMenuAcaoMapper.toEntity(perfilMenuAcaoDTO);
        try {
            perfilMenuAcaoDAO.update(perfilMenuAcao);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }	
    }

    @Override
    public PerfilMenuAcaoDTO recuperaPerfilMenuAcaoPorId(Integer id) throws MedmentorException {
        PerfilMenuAcao perfilMenuAcao;
        try {
            perfilMenuAcao = perfilMenuAcaoDAO.findById(id);
            if (perfilMenuAcao == null) {
                throw new MedmentorException("PerfilMenuAcao com ID " + id + " n�o encontrado.");
            }
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }
        
        return perfilMenuAcaoMapper.toDto(perfilMenuAcao); 
    }

    @Override
    public List<PerfilMenuAcaoDTO> recuperaListaPerfilMenuAcao() throws MedmentorException {
        List<PerfilMenuAcaoDTO> listaDto = new ArrayList<>();
        try {
            listaDto = perfilMenuAcaoMapper.toListDto(perfilMenuAcaoDAO.findAll());
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }
        return listaDto;
    }
}