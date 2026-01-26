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
	public MedicoDTO recuperaMedico(Integer id) throws MedmentorException {		
		try {
			ProfissionalDTO profissionalDTO = profissionalService.recuperaProfissionalPorId(id);
			MedicoDTO medicoDTO = medicoMapper.toMedicoDto(profissionalDTO);
			return medicoDTO;
		} catch (MedmentorException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}		
	}

	@Override
	public void alteraMedico(Integer id, MedicoDTO medicoDTO) throws MedmentorException {
		try {
			ProfissionalDTO profissionalDTO = profissionalService.recuperaProfissionalPorId(id);
			profissionalDTO = medicoMapper.toProfissionalDto(medicoDTO, profissionalDTO);
			profissionalService.alteraProfissional(profissionalDTO);
		} catch (MedmentorException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public RegistroDTO recuperaRegistro(Integer id) throws MedmentorException {
		try {
			ProfissionalRegistroDTO profissionalRegistroDTO = profissionalRegistroService.recuperaProfissionalRegistroPorId(id);
			RegistroDTO registroDTO = registroMapper.toRegistroDto(profissionalRegistroDTO);
			return registroDTO;
		} catch (MedmentorException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}	
	}

	@Override
	public void alteraRegistro(Integer id, RegistroDTO registroDTO) throws MedmentorException {
		try {
			ProfissionalRegistroDTO profissionalRegistroDTO = profissionalRegistroService.recuperaProfissionalRegistroPorId(registroDTO.getId());
			profissionalRegistroDTO = registroMapper.toProfissionalRegistroDto(registroDTO, profissionalRegistroDTO);
			profissionalRegistroService.alteraProfissionalRegistro(profissionalRegistroDTO);
		} catch (MedmentorException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public EmpresaDTO recuperaEmpresa(Integer id) throws MedmentorException {
		try {
			EmpresaProfissionalDTO empresaProfissionalDTO = empresaProfissionalService.recuperaEmpresaProfissionalPorProfissional(id);
			EmpresaDTO empresaDTO = empresaMapper.toEmpresaDto(empresaProfissionalDTO);
			return empresaDTO;
		} catch (MedmentorException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}	
	}

	@Override
	public void alteraEmpresa(Integer id, EmpresaDTO empresaDTO) throws MedmentorException {
		try {
			EmpresaProfissionalDTO empresaProfissionalDTO = empresaProfissionalService.recuperaEmpresaProfissionalPorProfissional(id);
			empresaProfissionalDTO = empresaMapper.toEmpresaProfissionalDto(empresaDTO, empresaProfissionalDTO);
			empresaProfissionalService.alteraEmpresaProfissional(empresaProfissionalDTO);
		} catch (MedmentorException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}	
	}

	@Override
	public DadosBancariosDTO recuperaDadosBancarios(Integer id) throws MedmentorException {
		try {
			EmpresaProfissionalDTO empresaProfissionalDTO = empresaProfissionalService.recuperaEmpresaProfissionalPorProfissional(id);
			DadosBancariosDTO dadosBancariosDTO = dadosBancariosMapper.toDadosBancariosDto(empresaProfissionalDTO);
			return dadosBancariosDTO;
		} catch (MedmentorException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public void alteraDadosBancarios(Integer id, DadosBancariosDTO dadosBancariosDTO) throws MedmentorException {
		try {
			EmpresaProfissionalDTO empresaProfissionalDTO = empresaProfissionalService.recuperaEmpresaProfissionalPorProfissional(id);
			empresaProfissionalDTO = dadosBancariosMapper.toEmpresaProfissionalDto(dadosBancariosDTO, empresaProfissionalDTO);
			empresaProfissionalService.alteraEmpresaProfissional(empresaProfissionalDTO);
		} catch (MedmentorException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}	
	}

}
