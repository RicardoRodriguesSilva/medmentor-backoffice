package br.com.medmentor.mobile.service;

import java.util.List;

import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.mobile.dto.MensagemDTO;
import br.com.medmentor.mobile.filtro.dto.FiltroMensagemDTO;
import br.com.medmentor.service.GenericService;
import jakarta.ejb.Local;

@Local
public interface MensagemService extends GenericService {
	
	void marcarMensagemLida(Integer id) throws MedmentorException;

	List<MensagemDTO> recuperaListaMensagemPorFiltro(FiltroMensagemDTO filtroMensagemDTO) throws MedmentorException;
}
