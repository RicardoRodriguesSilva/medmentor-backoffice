package br.com.medmentor.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.medmentor.dao.ProfissionalRegistroDAO;
import br.com.medmentor.dto.ProfissionalRegistroDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.mapper.ProfissionalRegistroMapper;
import br.com.medmentor.model.ProfissionalRegistro;
import br.com.medmentor.service.ProfissionalRegistroService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless 
public class ProfissionalRegistroServiceImpl implements ProfissionalRegistroService {

    @Inject 
    private ProfissionalRegistroDAO profissionalRegistroDAO;
    
    @Inject
    private ProfissionalRegistroMapper profissionalRegistroMapper;

    public ProfissionalRegistroServiceImpl() {
		
	}

	@Override
	public ProfissionalRegistroDTO incluirProfissionalRegistro(ProfissionalRegistroDTO profissionalRegistroDTO)
			throws MedmentorException {
		ProfissionalRegistro profissionalRegistro = profissionalRegistroMapper.toEntity(profissionalRegistroDTO);
		
		try {
			profissionalRegistro = profissionalRegistroDAO.create(profissionalRegistro);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}		

		return profissionalRegistroMapper.toDto(profissionalRegistro);
	}

	@Override
	public void excluirProfissionalRegistro(Integer idProfissional) throws MedmentorException {
		try {
			profissionalRegistroDAO.delete(idProfissional);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}	
	}

	@Override
	public void alterarProfissionalRegistro(ProfissionalRegistroDTO profissionalRegistroDTO) throws MedmentorException {
		ProfissionalRegistro profissionalRegistro = profissionalRegistroMapper.toEntity(profissionalRegistroDTO);		
		try {
			profissionalRegistroDAO.update(profissionalRegistro);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public ProfissionalRegistroDTO recuperarProfissionalRegistroPorId(Integer idProfissional)
			throws MedmentorException {
		ProfissionalRegistro profissionalRegistro;
		try {
			profissionalRegistro = profissionalRegistroDAO.findById(idProfissional);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		
		return profissionalRegistroMapper.toDto(profissionalRegistro);
	}

	@Override
	public List<ProfissionalRegistroDTO> recuperarListaProfissionalRegistro() throws MedmentorException {
		List<ProfissionalRegistroDTO> listaDto = new ArrayList<ProfissionalRegistroDTO>();
		try {
			listaDto = profissionalRegistroMapper.toListDto(profissionalRegistroDAO.findAll());
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		return listaDto;
	}
}