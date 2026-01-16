package br.com.medmentor.mobile.service.impl;

import java.util.List;

import br.com.medmentor.dao.EmpresaGestaoDAO;
import br.com.medmentor.dao.EmpresaProfissionalDAO;
import br.com.medmentor.dao.EscalaTrabalhoDAO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.mobile.dto.EscalaDTO;
import br.com.medmentor.mobile.dto.GestoraSaudeDTO;
import br.com.medmentor.mobile.filtro.dto.FiltroEscalaDTO;
import br.com.medmentor.mobile.service.EscalaService;
import br.com.medmentor.service.EmpresaGestaoService;
import br.com.medmentor.service.EscalaTrabalhoService;
import jakarta.inject.Inject;

public class EscalaServiceImpl implements EscalaService {

	@Inject
	private EmpresaProfissionalDAO empresaProfissionalDAO;
	
	@Inject
	private EscalaTrabalhoDAO escalaTrabalhoDAO;

	@Override
	public List<GestoraSaudeDTO> recuperaListaGestoraSaudePorProfissional(Integer id) throws MedmentorException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EscalaDTO> recuperaEscalaPorFiltro(FiltroEscalaDTO filtroEscalaDTO) throws MedmentorException {
		// TODO Auto-generated method stub
		return null;
	}

}
