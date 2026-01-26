package br.com.medmentor.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import br.com.medmentor.dao.UsuarioPerfilDAO;
import br.com.medmentor.dto.UsuarioPerfilDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.filtro.dto.FiltroUsuarioPerfilDTO;
import br.com.medmentor.mapper.UsuarioPerfilMapper;
import br.com.medmentor.model.UsuarioPerfil;
import br.com.medmentor.service.UsuarioPerfilService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless 
public class UsuarioPerfilServiceImpl implements UsuarioPerfilService {

    @Inject 
    private UsuarioPerfilDAO usuarioPerfilDAO;
    
    @Inject
    private UsuarioPerfilMapper usuarioPerfilMapper;

    public UsuarioPerfilServiceImpl() {
    }

    @Override
    public UsuarioPerfilDTO incluiUsuarioPerfil(UsuarioPerfilDTO usuarioPerfilDTO) throws MedmentorException {
        UsuarioPerfil usuarioPerfil = usuarioPerfilMapper.toEntity(usuarioPerfilDTO);
        
        try {
            usuarioPerfil = usuarioPerfilDAO.create(usuarioPerfil);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }		

        return usuarioPerfilMapper.toDto(usuarioPerfil);
    }

    @Override
    public void excluiUsuarioPerfil(Integer id) throws MedmentorException {
        try {
            usuarioPerfilDAO.delete(id);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }				
    }

    @Override
    public void alteraUsuarioPerfil(UsuarioPerfilDTO usuarioPerfilDTO) throws MedmentorException {
        UsuarioPerfil usuarioPerfil = usuarioPerfilMapper.toEntity(usuarioPerfilDTO);
        try {
            usuarioPerfilDAO.update(usuarioPerfil);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }	
    }

    @Override
    public UsuarioPerfilDTO recuperaUsuarioPerfilPorId(Integer id) throws MedmentorException {
        UsuarioPerfil usuarioPerfil;
        try {
            usuarioPerfil = usuarioPerfilDAO.findById(id);
            if (usuarioPerfil == null) {
                throw new MedmentorException("UsuarioPerfil com ID " + id + " não encontrado.");
            }
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }
        
        return usuarioPerfilMapper.toDto(usuarioPerfil);
    }

    @Override
    public List<UsuarioPerfilDTO> recuperaListaUsuarioPerfil() throws MedmentorException {
        List<UsuarioPerfilDTO> listaDto = new ArrayList<>();
        try {
            listaDto = usuarioPerfilMapper.toListDto(usuarioPerfilDAO.findAll());
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }
        return listaDto;
    }

	@Override
	public List<UsuarioPerfilDTO> recuperaListaUsuarioPerfilPorFiltro(FiltroUsuarioPerfilDTO filtroUsuarioPerfilDTO)
			throws MedmentorException {
        List<UsuarioPerfilDTO> listaDto = new ArrayList<>();
        try {
            listaDto = usuarioPerfilMapper.toListDto(usuarioPerfilDAO.findByFiltros(filtroUsuarioPerfilDTO.getIdUsuario(), 
            		filtroUsuarioPerfilDTO.getIdPerfil()));
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }
        return listaDto;
	}
}