package br.com.medmentor.service.impl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.medmentor.dao.NotificacaoDAO;
import br.com.medmentor.dto.NotificacaoDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.filtro.dto.FiltroNotificacaoDTO;
import br.com.medmentor.mapper.NotificacaoMapper;
import br.com.medmentor.model.Notificacao;
import br.com.medmentor.service.NotificacaoService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless 
public class NotificacaoServiceImpl implements NotificacaoService {
	
    @Inject 
    private NotificacaoDAO notificacaoDAO; 
    
    @Inject
    private NotificacaoMapper notificacaoMapper;

    public NotificacaoServiceImpl() {

	}

	@Override
	public NotificacaoDTO incluiNotificacao(NotificacaoDTO notificacaoDTO) throws MedmentorException {
		Notificacao notificacao = notificacaoMapper.toEntity(notificacaoDTO);
		
		try {			
			notificacao = notificacaoDAO.create(notificacao);			
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}		

		return notificacaoMapper.toDto(notificacao);
	}

	@Override
	public void excluiNotificacao(Integer id) throws MedmentorException {
		try {
			notificacaoDAO.delete(id);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}				
	}

	@Override
	public void alteraNotificacao(NotificacaoDTO notificacaoDTO) throws MedmentorException {
		
		Notificacao notificacao = notificacaoMapper.toEntity(notificacaoDTO);
		try {
			notificacaoDAO.update(notificacao);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}	
	}

	@Override
	public NotificacaoDTO recuperaNotificacaoPorId(Integer id) throws MedmentorException {
		Notificacao notificacao;
		try {
			notificacao = notificacaoDAO.findById(id);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		
		return notificacaoMapper.toDto(notificacao);
	}

	@Override
	public List<NotificacaoDTO> recuperaListaNotificacao() throws MedmentorException {
		List<NotificacaoDTO> listaDto = new ArrayList<NotificacaoDTO>();
		try {
			listaDto = notificacaoMapper.toListDto(notificacaoDAO.findAll());
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		return listaDto;
	}

	@Override
	public List<NotificacaoDTO> recuperaListaNotificacaoPorFiltro(FiltroNotificacaoDTO filtroNotificacaoDTO)
			throws MedmentorException {
		List<NotificacaoDTO> listaDto = new ArrayList<NotificacaoDTO>();
		try {
			LocalDate dataInicio = null;
			if (filtroNotificacaoDTO.getDataInicio()!=null) {
				dataInicio = LocalDate.parse(filtroNotificacaoDTO.getDataInicio());
			}
			
			LocalDate dataFim = null;
			if (filtroNotificacaoDTO.getDataFim()!=null) {
				dataFim = LocalDate.parse(filtroNotificacaoDTO.getDataFim());
			}
			
			listaDto = notificacaoMapper.toListDto(notificacaoDAO.
					findByFiltros(filtroNotificacaoDTO.getIdEmpresaProfissional(),  
							filtroNotificacaoDTO.getIdEmpresaUnidadeGestao(), dataInicio, dataFim));
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		
		return listaDto;
	}
}
