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
	
	MedicoDTO recuperaMedico(Integer id) throws MedmentorException;
	
	void alteraMedico(Integer id, MedicoDTO medicoDTO) throws MedmentorException;
	
	RegistroDTO recuperaRegistro(Integer id) throws MedmentorException;
	
	void alteraRegistro(Integer id, RegistroDTO registroDTO) throws MedmentorException;
	
	EmpresaDTO recuperaEmpresa(Integer id) throws MedmentorException;
	
	void alteraEmpresa(Integer id, EmpresaDTO empresaDTO) throws MedmentorException;
	
	DadosBancariosDTO recuperaDadosBancarios(Integer id) throws MedmentorException;
	
	void alteraDadosBancarios(Integer id, DadosBancariosDTO DadosBancariosDTO) throws MedmentorException;
	
}