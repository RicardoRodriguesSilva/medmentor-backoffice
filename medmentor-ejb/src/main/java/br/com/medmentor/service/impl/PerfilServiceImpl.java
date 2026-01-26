package br.com.medmentor.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import br.com.medmentor.dao.PerfilDAO;
import br.com.medmentor.dto.PerfilDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.mapper.PerfilMapper;
import br.com.medmentor.model.Perfil;
import br.com.medmentor.service.PerfilService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless  
public class PerfilServiceImpl implements PerfilService {

    @Inject 
    private PerfilDAO perfilDAO;
    
    @Inject
    private PerfilMapper perfilMapper;

    public PerfilServiceImpl() {
    }

    @Override
    public PerfilDTO incluiPerfil(PerfilDTO perfilDTO) throws MedmentorException {
        Perfil perfil = perfilMapper.toEntity(perfilDTO);
        
        try {
            perfil = perfilDAO.create(perfil);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }		

        return perfilMapper.toDto(perfil);
    }

    @Override
    public void excluiPerfil(Integer id) throws MedmentorException {
        try {
            perfilDAO.delete(id);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }				
    }

    @Override
    public void alteraPerfil(PerfilDTO perfilDTO) throws MedmentorException {
        Perfil perfil = perfilMapper.toEntity(perfilDTO);
        try {
            perfilDAO.update(perfil);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }	
    }

    @Override
    public PerfilDTO recuperaPerfilPorId(Integer id) throws MedmentorException {
        Perfil perfil;
        try {
            perfil = perfilDAO.findById(id);
            if (perfil == null) {
                throw new MedmentorException("Perfil com ID " + id + " n�o encontrado.");
            }
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }
        
        return perfilMapper.toDto(perfil);
    }

    @Override
    public List<PerfilDTO> recuperaListaPerfil() throws MedmentorException {
        List<PerfilDTO> listaDto = new ArrayList<>();
        try {
            listaDto = perfilMapper.toListDto(perfilDAO.findAll());
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }
        return listaDto;
    }

	@Override
	public String recuperaPerfilPorUsuario(String nome) throws MedmentorException {
        String nomePerfil = "";
        try {
        	nomePerfil = perfilDAO.recuperaPerfilPorUsuario(nome);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }
        return nomePerfil;
	}
}