package br.com.medmentor.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.medmentor.dao.EscalaTrabalhoDAO;
import br.com.medmentor.dto.EscalaTrabalhoDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.mapper.EscalaTrabalhoMapper;
import br.com.medmentor.model.EscalaTrabalho;
import br.com.medmentor.service.EscalaTrabalhoService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless 
public class EscalaTrabalhoServiceImpl implements EscalaTrabalhoService {

    @Inject 
    private EscalaTrabalhoDAO escalaTrabalhoDAO;
    
    @Inject
    private EscalaTrabalhoMapper escalaTrabalhoMapper;

    public EscalaTrabalhoServiceImpl() {

	}

	@Override
	public EscalaTrabalhoDTO incluirEscalaTrabalho(EscalaTrabalhoDTO escalaTrabalhoDTO) throws MedmentorException {
		EscalaTrabalho escalaTrabalho = escalaTrabalhoMapper.toEntity(escalaTrabalhoDTO);
		
		try {
			escalaTrabalho = escalaTrabalhoDAO.create(escalaTrabalho);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}		

		return escalaTrabalhoMapper.toDto(escalaTrabalho);
	}

	@Override
	public void excluirEscalaTrabalho(Integer id) throws MedmentorException {
		try {
			escalaTrabalhoDAO.delete(id);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}				
	}

	@Override
	public void alterarEscalaTrabalho(EscalaTrabalhoDTO escalaTrabalhoDTO) throws MedmentorException {
		
		EscalaTrabalho escalaTrabalho = escalaTrabalhoMapper.toEntity(escalaTrabalhoDTO);
		try {
			escalaTrabalhoDAO.update(escalaTrabalho);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}	
	}

	@Override
	public EscalaTrabalhoDTO recuperarEscalaTrabalhoPorId(Integer id) throws MedmentorException {
		EscalaTrabalho escalaTrabalho;
		try {
			escalaTrabalho = escalaTrabalhoDAO.findById(id);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		
		return escalaTrabalhoMapper.toDto(escalaTrabalho);
	}

	@Override
	public List<EscalaTrabalhoDTO> recuperarListaEscalaTrabalho() throws MedmentorException {
		List<EscalaTrabalhoDTO> listaDto = new ArrayList<EscalaTrabalhoDTO>();
		try {
			listaDto = escalaTrabalhoMapper.toListDto(escalaTrabalhoDAO.findAll());
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		return listaDto;
	}

	@Override
	public List<EscalaTrabalhoDTO> recuperarListaEscalaTrabalhoPorDataInicioEDataFim(Date dataInicio, Date dataFim)
			throws MedmentorException {
		List<EscalaTrabalhoDTO> listaDto = new ArrayList<EscalaTrabalhoDTO>();
		try {
			listaDto = escalaTrabalhoMapper.toListDto(escalaTrabalhoDAO.
					findByDataInicioEDataFim(dataInicio, dataFim));
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		return listaDto;
	}
}
