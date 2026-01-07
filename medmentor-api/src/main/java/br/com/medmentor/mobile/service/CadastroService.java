package br.com.medmentor.mobile.service;

import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.mobile.dto.DadosBancariosDTO;
import br.com.medmentor.mobile.dto.EmpresaDTO;
import br.com.medmentor.mobile.dto.MedicoDTO;
import br.com.medmentor.mobile.dto.RegistroDTO;
import br.com.medmentor.service.GenericService;
import jakarta.ejb.Local;

@Local
public interface CadastroService extends GenericService {
	
	MedicoDTO recuperarMedico(Integer id) throws MedmentorException;
	void alterarMedico(Integer id, MedicoDTO medicoDTO) throws MedmentorException;
	
	RegistroDTO recuperarRegistro(Integer id) throws MedmentorException;
	void alterarRegistro(Integer id, RegistroDTO registroDTO) throws MedmentorException;
	
	EmpresaDTO recuperarEmpresa(Integer id) throws MedmentorException;
	void alterarEmpresa(Integer id, EmpresaDTO empresaDTO) throws MedmentorException;
	
	DadosBancariosDTO recuperarDadosBancarios(Integer id) throws MedmentorException;
	void alterarDadosBancarios(Integer id, DadosBancariosDTO DadosBancariosDTO) throws MedmentorException;
	
}