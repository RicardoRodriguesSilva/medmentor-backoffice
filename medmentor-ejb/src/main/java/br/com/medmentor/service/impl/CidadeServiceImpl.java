package br.com.medmentor.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.medmentor.dao.CidadeDAO;
import br.com.medmentor.dto.CidadeDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.mapper.CidadeMapper;
import br.com.medmentor.model.Cidade;
import br.com.medmentor.service.CidadeService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless 
public class CidadeServiceImpl implements CidadeService {

    @Inject 
    private CidadeDAO cidadeDAO;
    
    @Inject
    private CidadeMapper cidadeMapper;

    public CidadeServiceImpl() {

	}
    
    @Override
    public CidadeDTO incluirCidade(CidadeDTO cidadeDTO) throws MedmentorException {
        Cidade cidade = cidadeMapper.toEntity(cidadeDTO);
        
        try {
            cidade = cidadeDAO.create(cidade);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }		

        return cidadeMapper.toDto(cidade);
    }

    @Override
    public void excluirCidade(Integer id) throws MedmentorException {
        try {
            cidadeDAO.delete(id);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }				
    }

    @Override
    public void alterarCidade(CidadeDTO cidadeDTO) throws MedmentorException {
        Cidade cidade = cidadeMapper.toEntity(cidadeDTO);
        try {
            cidadeDAO.update(cidade);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }	
    }

    @Override
    public CidadeDTO recuperarCidadePorId(Integer id) throws MedmentorException {
        Cidade cidade;
        try {
            cidade = cidadeDAO.findById(id);
            if (cidade == null) {
                throw new MedmentorException("Cidade com ID " + id + " n�o encontrado.");
            }
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }
        
        return cidadeMapper.toDto(cidade);
    }

    @Override
    public List<CidadeDTO> recuperarListaCidade() throws MedmentorException {
        List<CidadeDTO> listaDto = new ArrayList<>();
        try {
            listaDto = cidadeMapper.toListDto(cidadeDAO.findAll());
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }
        return listaDto;
    }    

	@Override
	public List<CidadeDTO> listarTodasPorUnidadeFederacao(Integer idUnidadeFederacao) throws MedmentorException {
		List<CidadeDTO> listaDto = new ArrayList<CidadeDTO>();
		try {
			listaDto = cidadeMapper.toListDto(cidadeDAO.findByUnidadeFederacao(idUnidadeFederacao));
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		return listaDto;
	}
	
	@Override
	public List<CidadeDTO> listarTodasPorUnidadeFederacaoENome(Integer idUnidadeFederacao, String nome) throws MedmentorException {
		List<CidadeDTO> listaDto = new ArrayList<CidadeDTO>();
		try {
			listaDto = cidadeMapper.toListDto(cidadeDAO.findByUnidadeFederacaoENome(idUnidadeFederacao, nome));
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		return listaDto;
	}	
}
