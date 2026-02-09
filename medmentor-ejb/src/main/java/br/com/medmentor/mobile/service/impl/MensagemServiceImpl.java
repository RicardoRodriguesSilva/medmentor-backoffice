package br.com.medmentor.mobile.service.impl;

import java.util.ArrayList;
import java.util.List;

import br.com.medmentor.dto.NotificacaoDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.mobile.dto.MensagemDTO;
import br.com.medmentor.mobile.filtro.dto.FiltroMensagemDTO;
import br.com.medmentor.mobile.mapper.MensagemMapper;
import br.com.medmentor.mobile.service.MensagemService;
import br.com.medmentor.service.NotificacaoService;
import jakarta.inject.Inject;

public class MensagemServiceImpl implements MensagemService {
	
	@Inject
	private NotificacaoService notificacaoService;
	
	@Inject
	private MensagemMapper mensagemMapper;

	@Override
	public void marcarMensagemLida(Integer id) throws MedmentorException {
		try {
			NotificacaoDTO notificacaoDTO = notificacaoService.recuperaNotificacaoPorId(id);
			notificacaoDTO.setBolLida(true);
			notificacaoService.alteraNotificacao(notificacaoDTO);
		
		} catch (MedmentorException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}			
		
	}

	@Override
	public List<MensagemDTO> recuperaListaMensagemPorFiltro(FiltroMensagemDTO filtroMensagemDTO)
			throws MedmentorException {
		List<MensagemDTO> listaMensagemDTO = new ArrayList<MensagemDTO>();
		
		try {
			List<NotificacaoDTO> listaNotificacaoDTO = notificacaoService.recuperaListaNotificacaoPorFiltro(filtroMensagemDTO);
			listaMensagemDTO = mensagemMapper.toListDto(listaNotificacaoDTO);

		} catch (MedmentorException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}		
		return listaMensagemDTO;
	}

}
