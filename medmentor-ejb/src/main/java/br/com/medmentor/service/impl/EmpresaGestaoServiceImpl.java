package br.com.medmentor.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.medmentor.dao.EmpresaGestaoDAO;
import br.com.medmentor.dto.EmpresaGestaoDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.mapper.EmpresaGestaoMapper;
import br.com.medmentor.model.EmpresaGestao;
import br.com.medmentor.service.EmpresaGestaoService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Stateless 
public class EmpresaGestaoServiceImpl implements EmpresaGestaoService {

    @Inject 
    private EmpresaGestaoDAO empresaGestaoDAO;
    
    @Inject
    private EmpresaGestaoMapper empresaGestaoMapper;

    public EmpresaGestaoServiceImpl() {

    }

	@Override
	@Transactional
	public EmpresaGestaoDTO incluirEmpresaGestao(EmpresaGestaoDTO empresaGestaoDTO) throws MedmentorException {
		EmpresaGestao empresaGestao = empresaGestaoMapper.toEntity(empresaGestaoDTO);
		
		try {
			empresaGestao = empresaGestaoDAO.create(empresaGestao);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}		

		return empresaGestaoMapper.toDto(empresaGestao);
	}

	@Override
	@Transactional
	public void excluirEmpresaGestao(Integer id) throws MedmentorException {
		try {
			empresaGestaoDAO.delete(id);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}				
	}

	@Override
	@Transactional
	public void alterarEmpresaGestao(EmpresaGestaoDTO empresaGestaoDTO) throws MedmentorException {
		
		EmpresaGestao empresaGestao = empresaGestaoMapper.toEntity(empresaGestaoDTO);
		try {
			empresaGestaoDAO.update(empresaGestao);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}	
	}

	@Override
	public EmpresaGestaoDTO recuperarEmpresaGestaoPorId(Integer id) throws MedmentorException {
		EmpresaGestao empresaGestao;
		try {
			empresaGestao = empresaGestaoDAO.findById(id);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		
		return empresaGestaoMapper.toDto(empresaGestao);
	}

	@Override
	public List<EmpresaGestaoDTO> recuperarListaEmpresaGestao() throws SQLException {
        List<EmpresaGestaoDTO> listaDto = new ArrayList<>();
        try {
            listaDto = empresaGestaoMapper.toListDto(empresaGestaoDAO.findAll());
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }
        return listaDto;
	}
}