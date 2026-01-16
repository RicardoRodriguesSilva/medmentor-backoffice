package br.com.medmentor.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.medmentor.dao.EmpresaUnidadeGestaoDAO;
import br.com.medmentor.dto.EmpresaUnidadeGestaoDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.mapper.EmpresaUnidadeGestaoMapper;
import br.com.medmentor.model.EmpresaUnidadeGestao;
import br.com.medmentor.service.EmpresaUnidadeGestaoService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Stateless 
public class EmpresaUnidadeGestaoServiceImpl implements EmpresaUnidadeGestaoService {

    @Inject 
    private EmpresaUnidadeGestaoDAO empresaUnidadeGestaoDAO;
    
    @Inject
    private EmpresaUnidadeGestaoMapper empresaUnidadeGestaoMapper;

    public EmpresaUnidadeGestaoServiceImpl() {

    }

	@Override
	@Transactional
	public EmpresaUnidadeGestaoDTO incluirEmpresaUnidadeGestao(EmpresaUnidadeGestaoDTO empresaUnidadeGestaoDTO) throws MedmentorException {
		EmpresaUnidadeGestao empresaUnidadeGestao = empresaUnidadeGestaoMapper.toEntity(empresaUnidadeGestaoDTO);
		
		try {
			empresaUnidadeGestao = empresaUnidadeGestaoDAO.create(empresaUnidadeGestao);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}		

		return empresaUnidadeGestaoMapper.toDto(empresaUnidadeGestao);
	}

	@Override
	@Transactional
	public void excluirEmpresaUnidadeGestao(Integer id) throws MedmentorException {
		try {
			empresaUnidadeGestaoDAO.delete(id);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}				
	}

	@Override
	@Transactional
	public void alterarEmpresaUnidadeGestao(EmpresaUnidadeGestaoDTO empresaUnidadeGestaoDTO) throws MedmentorException {
		
		EmpresaUnidadeGestao empresaUnidadeGestao = empresaUnidadeGestaoMapper.toEntity(empresaUnidadeGestaoDTO);
		try {
			empresaUnidadeGestaoDAO.update(empresaUnidadeGestao);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}	
	}

	@Override
	public EmpresaUnidadeGestaoDTO recuperarEmpresaUnidadeGestaoPorId(Integer id) throws MedmentorException {
		EmpresaUnidadeGestao empresaUnidadeGestao;
		try {
			empresaUnidadeGestao = empresaUnidadeGestaoDAO.findById(id);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		
		return empresaUnidadeGestaoMapper.toDto(empresaUnidadeGestao);
	}
	
	@Override
	public List<EmpresaUnidadeGestaoDTO> recuperarListaEmpresaUnidadeGestao() throws MedmentorException {
        List<EmpresaUnidadeGestaoDTO> listaDto = new ArrayList<>();
        try {
            listaDto = empresaUnidadeGestaoMapper.toListDto(empresaUnidadeGestaoDAO.findAll());
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }
        return listaDto;
	}

	@Override
	public List<EmpresaUnidadeGestaoDTO> recuperarListaEmpresaUnidadeGestaoPorIdProfissional(Integer idProfissional)
			throws MedmentorException {
        List<EmpresaUnidadeGestaoDTO> listaDto = new ArrayList<>();
        try {
            listaDto = empresaUnidadeGestaoMapper.toListDto(empresaUnidadeGestaoDAO.findByIdProfissional(idProfissional));
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }
        return listaDto;
	}	
}