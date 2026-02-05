package br.com.medmentor.mobile.service;

import java.util.List;

import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.filtro.dto.FiltroHorasEscalaTrabalhoDTO;
import br.com.medmentor.mobile.dto.EscalaDTO;
import br.com.medmentor.mobile.dto.GestoraSaudeDTO;
import br.com.medmentor.mobile.dto.HorasEscalaDTO;
import br.com.medmentor.mobile.dto.SolicitacaoMudancaDTO;
import br.com.medmentor.mobile.filtro.dto.FiltroEscalaDTO;
import br.com.medmentor.mobile.filtro.dto.FiltroSolicitacaoMudancaDTO;
import br.com.medmentor.service.GenericService;
import jakarta.ejb.Local;

@Local
public interface MovimentacaoService extends GenericService {
	
	List<GestoraSaudeDTO> recuperaListaGestoraSaudePorProfissional(Integer id) throws MedmentorException;
	
	List<EscalaDTO> recuperaListaEscalaPorFiltro(FiltroEscalaDTO filtroEscalaDTO) throws MedmentorException;
	
	List<EscalaDTO> recuperaListaEscalaDisponivelPorFiltro(FiltroEscalaDTO filtroEscalaDTO) throws MedmentorException;
	
	List<HorasEscalaDTO> recuperaListaHorasTrabalhadasPorFiltro(FiltroHorasEscalaTrabalhoDTO filtroHorasTrabalhadasDTO) throws MedmentorException;
	
	void disponibilzaEscalaTrabalho(Integer id) throws MedmentorException;
	
	void indisponibilzaEscalaTrabalho(Integer id) throws MedmentorException;
	
	void confirmaEscalaTrabalho(Integer id) throws MedmentorException;
	
	void cancelaEscalaTrabalho(Integer id) throws MedmentorException;
	
	List<SolicitacaoMudancaDTO> recuperaListaSolicitacaoMudancaPorFiltro(FiltroSolicitacaoMudancaDTO filtroSolicitacaoMudancaDTO) throws MedmentorException;
	
	void incluiSolicitacaoMudanca(Integer idProfissional, Integer idEscala) throws MedmentorException;
	
	void excluiSolicitacaoMudanca(Integer id) throws MedmentorException;
	
	void aceitaSolicitacaoMudanca(Integer id) throws MedmentorException;
	
	void recusaSolicitacaoMudanca(Integer id) throws MedmentorException;	
}
