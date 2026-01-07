package br.com.medmentor.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.medmentor.dao.MudancaEscalaTrabalhoDAO;
import br.com.medmentor.dto.MudancaEscalaTrabalhoDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.mapper.MudancaEscalaTrabalhoMapper;
import br.com.medmentor.model.MudancaEscalaTrabalho;
import br.com.medmentor.service.MudancaEscalaTrabalhoService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless 
public class MudancaEscalaTrabalhoServiceImpl implements MudancaEscalaTrabalhoService {

    @Inject 
    private MudancaEscalaTrabalhoDAO mudancaEscalaTrabalhoDAO;
    
    @Inject
    private MudancaEscalaTrabalhoMapper mudancaEscalaTrabalhoMapper;

    public MudancaEscalaTrabalhoServiceImpl() {

	}

	@Override
	public MudancaEscalaTrabalhoDTO incluirMudancaEscalaTrabalho(MudancaEscalaTrabalhoDTO mudancaEscalaTrabalhoDTO) throws MedmentorException {
		MudancaEscalaTrabalho mudancaEscalaTrabalho = mudancaEscalaTrabalhoMapper.toEntity(mudancaEscalaTrabalhoDTO);
		
		try {
			mudancaEscalaTrabalho = mudancaEscalaTrabalhoDAO.create(mudancaEscalaTrabalho);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}		

		return mudancaEscalaTrabalhoMapper.toDto(mudancaEscalaTrabalho);
	}

	@Override
	public void excluirMudancaEscalaTrabalho(Integer id) throws MedmentorException {
		try {
			mudancaEscalaTrabalhoDAO.delete(id);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}				
	}

	@Override
	public void alterarMudancaEscalaTrabalho(MudancaEscalaTrabalhoDTO mudancaEscalaTrabalhoDTO) throws MedmentorException {
		
		MudancaEscalaTrabalho mudancaEscalaTrabalho = mudancaEscalaTrabalhoMapper.toEntity(mudancaEscalaTrabalhoDTO);
		try {
			mudancaEscalaTrabalhoDAO.update(mudancaEscalaTrabalho);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}	
	}

	@Override
	public MudancaEscalaTrabalhoDTO recuperarMudancaEscalaTrabalhoPorId(Integer id) throws MedmentorException {
		MudancaEscalaTrabalho mudancaEscalaTrabalho;
		try {
			mudancaEscalaTrabalho = mudancaEscalaTrabalhoDAO.findById(id);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		
		return mudancaEscalaTrabalhoMapper.toDto(mudancaEscalaTrabalho);
	}

	@Override
	public List<MudancaEscalaTrabalhoDTO> recuperarListaMudancaEscalaTrabalho() throws MedmentorException {
		List<MudancaEscalaTrabalhoDTO> listaDto = new ArrayList<MudancaEscalaTrabalhoDTO>();
		try {
			listaDto = mudancaEscalaTrabalhoMapper.toListDto(mudancaEscalaTrabalhoDAO.findAll());
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		return listaDto;
	}
    

}
