package br.com.medmentor.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.medmentor.dao.ParametroDAO;
import br.com.medmentor.dto.ParametroDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.mapper.ParametroMapper;
import br.com.medmentor.model.Parametro;
import br.com.medmentor.service.ParametroService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless 
public class ParametroServiceImpl implements ParametroService {

    @Inject 
    private ParametroDAO parametroDAO;
    
    @Inject
    private ParametroMapper parametroMapper;

    public ParametroServiceImpl() {
    }

    @Override
    public ParametroDTO incluiParametro(ParametroDTO parametroDTO) throws MedmentorException {
        Parametro parametro = parametroMapper.toEntity(parametroDTO);
        
        try {
            parametro = parametroDAO.create(parametro);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }		

        return parametroMapper.toDto(parametro);
    }

    @Override
    public void excluiParametro(Integer id) throws MedmentorException {
        try {
            parametroDAO.delete(id);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }				
    }

    @Override
    public void alteraParametro(ParametroDTO parametroDTO) throws MedmentorException {
        Parametro parametro = parametroMapper.toEntity(parametroDTO);
        try {
            parametroDAO.update(parametro);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }	
    }

    @Override
    public ParametroDTO recuperaParametroPorId(Integer id) throws MedmentorException {
        Parametro parametro;
        try {
            parametro = parametroDAO.findById(id);
            if (parametro == null) {
                throw new MedmentorException("A��o com ID " + id + " n�o encontrada.");
            }
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }
        
        return parametroMapper.toDto(parametro); 
    }

    @Override
    public List<ParametroDTO> recuperaListaParametro() throws MedmentorException {
        List<ParametroDTO> listaDto = new ArrayList<>();
        try {
            listaDto = parametroMapper.toListDto(parametroDAO.findAll());
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }
        return listaDto;
    } 
}