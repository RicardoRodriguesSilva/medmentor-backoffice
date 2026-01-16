package br.com.medmentor.mobile.service.impl;

import br.com.medmentor.dto.EmpresaProfissionalDTO;
import br.com.medmentor.dto.ProfissionalDTO;
import br.com.medmentor.dto.ProfissionalRegistroDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.mobile.dto.DadosBancariosDTO;
import br.com.medmentor.mobile.dto.EmpresaDTO;
import br.com.medmentor.mobile.dto.MedicoDTO;
import br.com.medmentor.mobile.dto.RegistroDTO;
import br.com.medmentor.mobile.mapper.DadosBancariosMapper;
import br.com.medmentor.mobile.mapper.EmpresaMapper;
import br.com.medmentor.mobile.mapper.MedicoMapper;
import br.com.medmentor.mobile.mapper.RegistroMapper;
import br.com.medmentor.mobile.service.CadastroService;
import br.com.medmentor.service.EmpresaProfissionalService;
import br.com.medmentor.service.ProfissionalRegistroService;
import br.com.medmentor.service.ProfissionalService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

public class CadastroServiceImpl implements CadastroService {
	
	@Inject
	private EmpresaProfissionalService empresaProfissionalService;
	
	@Inject
	private ProfissionalService profissionalService;
	
	@Inject
	private ProfissionalRegistroService profissionalRegistroService;
	
	@Inject
    private DadosBancariosMapper dadosBancariosMapper;
	
	@Inject
    private EmpresaMapper empresaMapper;
	
	@Inject
    private MedicoMapper medicoMapper;
	
	@Inject
    private RegistroMapper registroMapper;
		
	@Override
	public MedicoDTO recuperarMedico(Integer id) throws MedmentorException {		
		try {
			ProfissionalDTO profissionalDTO = profissionalService.recuperarProfissionalPorId(id);
			MedicoDTO medicoDTO = medicoMapper.toMedicoDTO(profissionalDTO);
			return medicoDTO;
		} catch (MedmentorException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}		
	}

	@Override
	public void alterarMedico(Integer id, MedicoDTO medicoDTO) throws MedmentorException {
		try {
			ProfissionalDTO profissionalDTO = profissionalService.recuperarProfissionalPorId(id);
			profissionalDTO = medicoMapper.toProfissionalDTO(medicoDTO, profissionalDTO);
			profissionalService.alterarProfissional(profissionalDTO);
		} catch (MedmentorException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public RegistroDTO recuperarRegistro(Integer id) throws MedmentorException {
		try {
			ProfissionalRegistroDTO profissionalRegistroDTO = profissionalRegistroService.recuperarProfissionalRegistroPorId(id);
			RegistroDTO registroDTO = registroMapper.toRegistroDTO(profissionalRegistroDTO);
			return registroDTO;
		} catch (MedmentorException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}	
	}

	@Override
	public void alterarRegistro(Integer id, RegistroDTO registroDTO) throws MedmentorException {
		try {
			ProfissionalRegistroDTO profissionalRegistroDTO = profissionalRegistroService.recuperarProfissionalRegistroPorId(registroDTO.getId());
			profissionalRegistroDTO = registroMapper.toProfissionalRegistroDTO(registroDTO, profissionalRegistroDTO);
			profissionalRegistroService.alterarProfissionalRegistro(profissionalRegistroDTO);
		} catch (MedmentorException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public EmpresaDTO recuperarEmpresa(Integer id) throws MedmentorException {
		try {
			EmpresaProfissionalDTO empresaProfissionalDTO = empresaProfissionalService.recuperarEmpresaProfissionalPorProfissional(id);
			EmpresaDTO empresaDTO = empresaMapper.toEmpresaDTO(empresaProfissionalDTO);
			return empresaDTO;
		} catch (MedmentorException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}	
	}

	@Override
	public void alterarEmpresa(Integer id, EmpresaDTO empresaDTO) throws MedmentorException {
		try {
			EmpresaProfissionalDTO empresaProfissionalDTO = empresaProfissionalService.recuperarEmpresaProfissionalPorProfissional(id);
			empresaProfissionalDTO = empresaMapper.toEmpresaProfissionalDTO(empresaDTO, empresaProfissionalDTO);
			empresaProfissionalService.alterarEmpresaProfissional(empresaProfissionalDTO);
		} catch (MedmentorException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}	
	}

	@Override
	public DadosBancariosDTO recuperarDadosBancarios(Integer id) throws MedmentorException {
		try {
			EmpresaProfissionalDTO empresaProfissionalDTO = empresaProfissionalService.recuperarEmpresaProfissionalPorProfissional(id);
			DadosBancariosDTO dadosBancariosDTO = dadosBancariosMapper.toDadosBancariosDTO(empresaProfissionalDTO);
			return dadosBancariosDTO;
		} catch (MedmentorException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public void alterarDadosBancarios(Integer id, DadosBancariosDTO dadosBancariosDTO) throws MedmentorException {
		try {
			EmpresaProfissionalDTO empresaProfissionalDTO = empresaProfissionalService.recuperarEmpresaProfissionalPorProfissional(id);
			empresaProfissionalDTO = dadosBancariosMapper.toEmpresaProfissionalDTO(dadosBancariosDTO, empresaProfissionalDTO);
			empresaProfissionalService.alterarEmpresaProfissional(empresaProfissionalDTO);
		} catch (MedmentorException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}	
	}

}
