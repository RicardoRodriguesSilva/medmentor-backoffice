package br.com.medmentor.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.medmentor.dao.ProfissionalDAO;
import br.com.medmentor.dto.ProfissionalDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.mapper.ProfissionalMapper;
import br.com.medmentor.model.Profissional;
import br.com.medmentor.service.ProfissionalService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Stateless 
public class ProfissionalServiceImpl implements ProfissionalService {

    @Inject 
    private ProfissionalDAO profissionalDAO;
    
    @Inject
    private ProfissionalMapper profissionalMapper;

    public ProfissionalServiceImpl() {

	}

	@Override
	@Transactional
	public ProfissionalDTO incluirProfissional(ProfissionalDTO profissionalDTO) throws MedmentorException {
		Profissional profissional = profissionalMapper.toEntity(profissionalDTO);
		
		try {
			profissional = profissionalDAO.create(profissional);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}		

		return profissionalMapper.toDto(profissional);
	}

	@Override
	@Transactional
	public void excluirProfissional(Integer id) throws MedmentorException {
		try {
			profissionalDAO.delete(id);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}				
	}

	@Override
	@Transactional
	public void alterarProfissional(ProfissionalDTO profissionalDTO) throws MedmentorException {
		
		Profissional profissional = profissionalMapper.toEntity(profissionalDTO);
		try {
			profissionalDAO.update(profissional);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}	
	}

	@Override
	public ProfissionalDTO recuperarProfissionalPorId(Integer id) throws MedmentorException {
		Profissional profissional;
		try {
			profissional = profissionalDAO.findById(id);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		
		return profissionalMapper.toDto(profissional);
	}

	@Override
	public List<ProfissionalDTO> recuperarProfissionalPorCidade(Integer idCidade) throws MedmentorException {
		List<ProfissionalDTO> listaDto = new ArrayList<ProfissionalDTO>();
		try {
			listaDto = profissionalMapper.toListDto(profissionalDAO.findByCidade(idCidade));
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		return listaDto;
	}

	@Override
	public List<ProfissionalDTO> recuperarListaProfissionalPorNome(String nome) throws MedmentorException {
		List<ProfissionalDTO> listaDto = new ArrayList<ProfissionalDTO>();
		try {
			listaDto = profissionalMapper.toListDto(profissionalDAO.findByNome(nome));
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		return listaDto;
	}

	@Override
	public List<ProfissionalDTO> recuperarListaProfissional() throws MedmentorException {
		List<ProfissionalDTO> listaDto = new ArrayList<ProfissionalDTO>();
		try {
			listaDto = profissionalMapper.toListDto(profissionalDAO.findAll());
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		return listaDto;
	}



}
