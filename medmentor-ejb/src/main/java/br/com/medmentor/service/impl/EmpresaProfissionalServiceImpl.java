package br.com.medmentor.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.medmentor.dao.EmpresaProfissionalDAO;
import br.com.medmentor.dto.EmpresaProfissionalDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.mapper.EmpresaProfissionalMapper;
import br.com.medmentor.model.EmpresaProfissional;
import br.com.medmentor.service.EmpresaProfissionalService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Stateless 
public class EmpresaProfissionalServiceImpl implements EmpresaProfissionalService {

    @Inject 
    private EmpresaProfissionalDAO empresaProfissionalDAO;
    
    @Inject
    private EmpresaProfissionalMapper empresaProfissionalMapper;

    public EmpresaProfissionalServiceImpl() {

    }

	@Override
	@Transactional
	public EmpresaProfissionalDTO incluiEmpresaProfissional(EmpresaProfissionalDTO EmpresaProfissionalDTO) throws MedmentorException {
		EmpresaProfissional empresaProfissional = empresaProfissionalMapper.toEntity(EmpresaProfissionalDTO);
		
		try {
			empresaProfissional = empresaProfissionalDAO.create(empresaProfissional);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}		

		return empresaProfissionalMapper.toDto(empresaProfissional);
	}

	@Override
	@Transactional
	public void excluiEmpresaProfissional(Integer idEmppresaProfissional) throws MedmentorException {
		try {
			empresaProfissionalDAO.delete(idEmppresaProfissional);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}				
	}

	@Override
	@Transactional
	public void alteraEmpresaProfissional(EmpresaProfissionalDTO EmpresaProfissionalDTO) throws MedmentorException {
		
		EmpresaProfissional EmpresaProfissional = empresaProfissionalMapper.toEntity(EmpresaProfissionalDTO);
		try {
			empresaProfissionalDAO.update(EmpresaProfissional);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}	
	}

	@Override
	public EmpresaProfissionalDTO recuperaEmpresaProfissionalPorProfissional(Integer idProfissional) throws MedmentorException {
		EmpresaProfissional EmpresaProfissional;
		try {
			EmpresaProfissional = empresaProfissionalDAO.findByProfissional(idProfissional);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		
		return empresaProfissionalMapper.toDto(EmpresaProfissional);
	}

	@Override
	public EmpresaProfissionalDTO recuperaEmpresaProfissionalPorId(Integer id)
			throws MedmentorException {
		EmpresaProfissional EmpresaProfissional;
		try {
			EmpresaProfissional = empresaProfissionalDAO.findById(id);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		
		return empresaProfissionalMapper.toDto(EmpresaProfissional);
	}

	@Override
	public List<EmpresaProfissionalDTO> recuperaListaEmpresaProfissional() throws MedmentorException {
        List<EmpresaProfissionalDTO> listaDto = new ArrayList<>();
        try {
            listaDto = empresaProfissionalMapper.toListDto(empresaProfissionalDAO.findAll());
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }
        return listaDto;
	}
}
