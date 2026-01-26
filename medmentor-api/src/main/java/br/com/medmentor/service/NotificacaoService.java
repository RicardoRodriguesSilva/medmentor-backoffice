package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.NotificacaoDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.filtro.dto.FiltroNotificacaoDTO;
import jakarta.ejb.Local;

@Local
public interface NotificacaoService extends GenericService {
    
	NotificacaoDTO incluiNotificacao(NotificacaoDTO notificacaoDTO) throws MedmentorException;
	
	void excluiNotificacao(Integer id) throws MedmentorException;
	
	void alteraNotificacao(NotificacaoDTO notificacaoDTO) throws MedmentorException;
	
	NotificacaoDTO recuperaNotificacaoPorId(Integer id) throws MedmentorException;
	
	List<NotificacaoDTO> recuperaListaNotificacao() throws MedmentorException;
	
	List<NotificacaoDTO> recuperaListaNotificacaoPorFiltro(FiltroNotificacaoDTO filtroNotificacaoDTO) throws MedmentorException;
	
}
