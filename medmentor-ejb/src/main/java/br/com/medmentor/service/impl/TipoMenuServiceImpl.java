package br.com.medmentor.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import br.com.medmentor.dao.TipoMenuDAO;
import br.com.medmentor.dto.TipoMenuDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.mapper.TipoMenuMapper;
import br.com.medmentor.model.TipoMenu;
import br.com.medmentor.service.TipoMenuService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless 
public class TipoMenuServiceImpl implements TipoMenuService {

    @Inject 
    private TipoMenuDAO tipoTipoMenuDAO;
    
    @Inject
    private TipoMenuMapper tipoTipoMenuMapper; 

    public TipoMenuServiceImpl() {
    }

    @Override
    public TipoMenuDTO incluirTipoMenu(TipoMenuDTO tipoTipoMenuDTO) throws MedmentorException {
        TipoMenu tipoTipoMenu = tipoTipoMenuMapper.toEntity(tipoTipoMenuDTO);
        
        try {
            tipoTipoMenu = tipoTipoMenuDAO.create(tipoTipoMenu);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }		

        return tipoTipoMenuMapper.toDto(tipoTipoMenu);
    }

    @Override
    public void excluirTipoMenu(Integer id) throws MedmentorException {
        try {
            tipoTipoMenuDAO.delete(id);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }				
    }

    @Override
    public void alterarTipoMenu(TipoMenuDTO tipoTipoMenuDTO) throws MedmentorException {
        TipoMenu tipoTipoMenu = tipoTipoMenuMapper.toEntity(tipoTipoMenuDTO);
        try {
            tipoTipoMenuDAO.update(tipoTipoMenu);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }	
    }

    @Override
    public TipoMenuDTO recuperarTipoMenuPorId(Integer id) throws MedmentorException {
        TipoMenu tipoTipoMenu;
        try {
            tipoTipoMenu = tipoTipoMenuDAO.findById(id);
            if (tipoTipoMenu == null) {
                throw new MedmentorException("TipoMenu com ID " + id + " n�o encontrado.");
            }
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }
        
        return tipoTipoMenuMapper.toDto(tipoTipoMenu);
    }

    @Override
    public List<TipoMenuDTO> recuperarListaTipoMenu() throws MedmentorException {
        List<TipoMenuDTO> listaDto = new ArrayList<>();
        try {
            listaDto = tipoTipoMenuMapper.toListDto(tipoTipoMenuDAO.findAll());
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }
        return listaDto;
    }
}