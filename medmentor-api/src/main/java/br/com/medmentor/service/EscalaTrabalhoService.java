package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.EscalaTrabalhoDTO;
import br.com.medmentor.dto.HorasEscalaTrabalhoDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.filtro.dto.FiltroEscalaTrabalhoDTO;
import br.com.medmentor.filtro.dto.FiltroHorasEscalaTrabalhoDTO;
import br.com.medmentor.mobile.filtro.dto.FiltroEscalaDTO;
import jakarta.ejb.Local;

@Local
public interface EscalaTrabalhoService extends GenericService {
    
	EscalaTrabalhoDTO incluiEscalaTrabalho(EscalaTrabalhoDTO escalaTrabalhoDTO) throws MedmentorException;
	
	void excluiEscalaTrabalho(Integer id) throws MedmentorException;
	
	void alteraEscalaTrabalho(EscalaTrabalhoDTO escalaTrabalhoDTO) throws MedmentorException;
	
	EscalaTrabalhoDTO recuperaEscalaTrabalhoPorId(Integer id) throws MedmentorException;
	
	List<EscalaTrabalhoDTO> recuperaListaEscalaTrabalho() throws MedmentorException;
	
	List<EscalaTrabalhoDTO> recuperaListaEscalaTrabalhoPorFiltro(FiltroEscalaTrabalhoDTO filtroEscalaTrabalhoDTO) throws MedmentorException;
	
	List<EscalaTrabalhoDTO> recuperaListaEscalaTrabalhoPorFiltro(FiltroEscalaDTO filtroEscalaDTO) throws MedmentorException;		
	
	List<HorasEscalaTrabalhoDTO> recuperaListaHorasEscalaTrabalhoPorFiltro(FiltroHorasEscalaTrabalhoDTO filtroHorasTrabalhadasDTO) throws MedmentorException;
	
	void disponibilzaEscalaTrabalho(Integer id) throws MedmentorException;
	
	void confirmaEscalaTrabalhoEfetuado(Integer id) throws MedmentorException;
	
}