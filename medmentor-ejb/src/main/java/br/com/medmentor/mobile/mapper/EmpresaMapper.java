package br.com.medmentor.mobile.mapper;

import br.com.medmentor.dto.EmpresaProfissionalDTO;
import br.com.medmentor.mobile.dto.EmpresaDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class EmpresaMapper {

    public EmpresaDTO toEmpresaDto(EmpresaProfissionalDTO empresaProfissionalDTO) {
    	if(empresaProfissionalDTO == null) {
    		return null;
    	}
    	
    	EmpresaDTO dto = new EmpresaDTO();
    	if(empresaProfissionalDTO.getEmpresaDTO().getPessoaJuridicaDTO().getPessoaDTO() != null) {
	    	dto.setCidadeDTO(empresaProfissionalDTO.getEmpresaDTO().getPessoaJuridicaDTO().getPessoaDTO().getCidadeDTO());
	    	dto.setDescricaoBairro(empresaProfissionalDTO.getEmpresaDTO().getPessoaJuridicaDTO().getPessoaDTO().getDescricaoBairro());
	    	dto.setDescricaoComplemento(empresaProfissionalDTO.getEmpresaDTO().getPessoaJuridicaDTO().getPessoaDTO().getDescricaoComplemento());
	    	dto.setDescricaoEndereco(empresaProfissionalDTO.getEmpresaDTO().getPessoaJuridicaDTO().getPessoaDTO().getDescricaoEndereco());
	    	dto.setNumeroCep(empresaProfissionalDTO.getEmpresaDTO().getPessoaJuridicaDTO().getPessoaDTO().getNumeroCep());
	    	dto.setDescricaoEmail(empresaProfissionalDTO.getEmpresaDTO().getPessoaJuridicaDTO().getPessoaDTO().getDescricaoEmail());
	    	dto.setNumeroCelular(empresaProfissionalDTO.getEmpresaDTO().getPessoaJuridicaDTO().getPessoaDTO().getNumeroCelular());
    	}
    	dto.setId(empresaProfissionalDTO.getId());
    	dto.setNomeFantasia(empresaProfissionalDTO.getEmpresaDTO().getNomeFantasia());
    	dto.setNumeroCnpf(empresaProfissionalDTO.getEmpresaDTO().getPessoaJuridicaDTO().getCnpj());
    	dto.setRazaoSocial(empresaProfissionalDTO.getEmpresaDTO().getPessoaJuridicaDTO().getRazaoSocial());
    	
    	return dto;
    }

    public EmpresaProfissionalDTO toEmpresaProfissionalDto(EmpresaDTO empresaDTO, EmpresaProfissionalDTO empresaProfissionalDTO) {
    	if(empresaProfissionalDTO == null) {
    		return null;
    	}
    	
    	empresaProfissionalDTO.getEmpresaDTO().getPessoaJuridicaDTO().getPessoaDTO().setCidadeDTO(empresaDTO.getCidadeDTO());
    	empresaProfissionalDTO.getEmpresaDTO().getPessoaJuridicaDTO().getPessoaDTO().setDescricaoBairro(empresaDTO.getDescricaoBairro());
    	empresaProfissionalDTO.getEmpresaDTO().getPessoaJuridicaDTO().getPessoaDTO().setDescricaoComplemento(empresaDTO.getDescricaoComplemento());
    	empresaProfissionalDTO.getEmpresaDTO().getPessoaJuridicaDTO().getPessoaDTO().setDescricaoEndereco(empresaDTO.getDescricaoEndereco());
    	empresaProfissionalDTO.setId(empresaDTO.getId());
    	empresaProfissionalDTO.getEmpresaDTO().setNomeFantasia(empresaDTO.getNomeFantasia());
    	empresaProfissionalDTO.getEmpresaDTO().getPessoaJuridicaDTO().getPessoaDTO().setNumeroCep(empresaDTO.getNumeroCep());
    	empresaProfissionalDTO.getEmpresaDTO().getPessoaJuridicaDTO().setCnpj(empresaDTO.getNumeroCnpf());
    	empresaProfissionalDTO.getEmpresaDTO().getPessoaJuridicaDTO().setRazaoSocial(empresaDTO.getRazaoSocial());
    	empresaProfissionalDTO.getEmpresaDTO().getPessoaJuridicaDTO().getPessoaDTO().setDescricaoEmail(empresaDTO.getDescricaoEmail());
    	empresaProfissionalDTO.getEmpresaDTO().getPessoaJuridicaDTO().getPessoaDTO().setNumeroCelular(empresaDTO.getNumeroCelular());
    	return empresaProfissionalDTO;
    }
}