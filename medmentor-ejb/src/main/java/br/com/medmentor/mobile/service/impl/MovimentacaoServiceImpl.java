package br.com.medmentor.mobile.service.impl;

import java.util.ArrayList;
import java.util.List;

import br.com.medmentor.dto.EmpresaUnidadeGestaoDTO;
import br.com.medmentor.dto.EscalaTrabalhoDTO;
import br.com.medmentor.dto.HorasEscalaTrabalhoDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.filtro.dto.FiltroHorasEscalaTrabalhoDTO;
import br.com.medmentor.mobile.dto.EscalaDTO;
import br.com.medmentor.mobile.dto.GestoraSaudeDTO;
import br.com.medmentor.mobile.dto.HorasEscalaDTO;
import br.com.medmentor.mobile.filtro.dto.FiltroEscalaDTO;
import br.com.medmentor.mobile.mapper.EscalaMapper;
import br.com.medmentor.mobile.mapper.GestoraSaudeMapper;
import br.com.medmentor.mobile.mapper.HorasEscalaMapper;
import br.com.medmentor.mobile.service.MovimentacaoService;
import br.com.medmentor.service.EmpresaUnidadeGestaoService;
import br.com.medmentor.service.EscalaTrabalhoService;
import jakarta.inject.Inject;

public class MovimentacaoServiceImpl implements MovimentacaoService {


	@Inject
	private EmpresaUnidadeGestaoService empresaUnidadeGestaoService;
	
	@Inject
	private EscalaTrabalhoService escalaTrabalhoService;
	
	@Inject
	private GestoraSaudeMapper gestoraSaudeMapper;
	
	@Inject
	private EscalaMapper escalaMapper;
	
	@Inject 
	private HorasEscalaMapper horasEscalaMapper;

	@Override
	public List<GestoraSaudeDTO> recuperaListaGestoraSaudePorProfissional(Integer id) throws MedmentorException {		
		List<GestoraSaudeDTO> listaGestoraSaudeDTO  = new ArrayList<GestoraSaudeDTO>();
		
		try {
			List<EmpresaUnidadeGestaoDTO> listaEmpresUnidadeGestaoDTO = empresaUnidadeGestaoService.recuperaListaEmpresaUnidadeGestaoPorIdProfissional(id);
			listaGestoraSaudeDTO = gestoraSaudeMapper.toListDto(listaEmpresUnidadeGestaoDTO);

		} catch (MedmentorException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}		
		return listaGestoraSaudeDTO;
	}

	@Override
	public List<EscalaDTO> recuperaListaEscalaPorFiltro(FiltroEscalaDTO filtroEscalaDTO) throws MedmentorException {
		List<EscalaDTO> listaEscalaDTO = new ArrayList<EscalaDTO>();
		
		try {
			List<EscalaTrabalhoDTO> listaEscalaTrabalhoDTO = escalaTrabalhoService.recuperaListaEscalaTrabalhoPorFiltro(filtroEscalaDTO);
			listaEscalaDTO = escalaMapper.toListDto(listaEscalaTrabalhoDTO, filtroEscalaDTO.getIdProfissional());

		} catch (MedmentorException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}		
		return listaEscalaDTO;
	}

	@Override
	public List<HorasEscalaDTO> recuperaListaHorasTrabalhadasPorFiltro(FiltroHorasEscalaTrabalhoDTO filtroHorasTrabalhadasDTO)
			throws MedmentorException {		
		List<HorasEscalaDTO> listaHorasEscalaDTO = new ArrayList<HorasEscalaDTO>();		
		
		try {
		
			List<HorasEscalaTrabalhoDTO> listaHorasEscalaTrabalhoDTO = escalaTrabalhoService.recuperaListaHorasEscalaTrabalhoPorFiltro(filtroHorasTrabalhadasDTO);
			listaHorasEscalaDTO = horasEscalaMapper.toListDto(listaHorasEscalaTrabalhoDTO);
		} catch (MedmentorException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}		
		return listaHorasEscalaDTO;
	}

	@Override
	public void disponibilzaEscalaTrabalho(Integer id) throws MedmentorException {
		try {
			escalaTrabalhoService.disponibilzaEscalaTrabalho(id);
		} catch (MedmentorException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}	
	}

	@Override
	public void confirmaEscalaTrabalhoEfetuado(Integer id) throws MedmentorException {
		try {
			escalaTrabalhoService.confirmaEscalaTrabalhoEfetuado(id);
		} catch (MedmentorException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}			
	}

}
