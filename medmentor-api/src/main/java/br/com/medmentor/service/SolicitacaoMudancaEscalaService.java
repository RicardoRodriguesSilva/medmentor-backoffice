package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.SolicitacaoMudancaEscalaDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.filtro.dto.FiltroSolicitacaoMudancaEscalaDTO;
import jakarta.ejb.Local;

@Local
public interface SolicitacaoMudancaEscalaService extends GenericService {
    
	SolicitacaoMudancaEscalaDTO incluiSolicitacaoMudancaEscala(SolicitacaoMudancaEscalaDTO solicitacaoMudancaEscalaDTO) throws MedmentorException;
	
	void excluiSolicitacaoMudancaEscala(Integer id) throws MedmentorException;
	
	void aceitaSolicitacaoMudancaEscala(Integer id) throws MedmentorException;
	
	void recusaSolicitacaoMudancaEscala(Integer id) throws MedmentorException;
	
	SolicitacaoMudancaEscalaDTO recuperaSolicitacaoMudancaEscalaPorId(Integer id) throws MedmentorException;
	
	List<SolicitacaoMudancaEscalaDTO> recuperaListaSolicitacaoMudancaEscala() throws MedmentorException;
	
	List<SolicitacaoMudancaEscalaDTO> recuperaListaSolicitacaoMudancaEscalaPorFiltro(FiltroSolicitacaoMudancaEscalaDTO filtroSolicitacaoMudancaEscalaDTO) throws MedmentorException;	
}
