package br.com.medmentor.mobile.mapper;

import br.com.medmentor.dto.EmpresaProfissionalDTO;
import br.com.medmentor.mobile.dto.DadosBancariosDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class DadosBancariosMapper {

    public DadosBancariosDTO toDadosBancariosDTO(EmpresaProfissionalDTO empresaProfissionalDTO) {
    	if (empresaProfissionalDTO == null) {
            return null;
        }
        
    	DadosBancariosDTO dto = new DadosBancariosDTO();
    	dto.setDescricaoChavePix(empresaProfissionalDTO.getDescricaoChavePix());
    	dto.setId(empresaProfissionalDTO.getId());
    	dto.setNumeroAgencia(empresaProfissionalDTO.getNumeroAgencia());
    	dto.setNumeroBanco(empresaProfissionalDTO.getNumeroBanco());
    	dto.setNumeroConta(empresaProfissionalDTO.getNumeroConta());
    	return dto;
    }

    public EmpresaProfissionalDTO toEmpresaProfissionalDTO(DadosBancariosDTO dadosBancariosDTO, EmpresaProfissionalDTO empresaProfissionalDTO) {
    	if (empresaProfissionalDTO == null) {
            return null;
        }
    	empresaProfissionalDTO.setDescricaoChavePix(dadosBancariosDTO.getDescricaoChavePix());
    	empresaProfissionalDTO.setNumeroAgencia(dadosBancariosDTO.getNumeroAgencia());
    	empresaProfissionalDTO.setNumeroBanco(dadosBancariosDTO.getNumeroBanco());
    	empresaProfissionalDTO.setNumeroConta(dadosBancariosDTO.getNumeroConta());
    	return empresaProfissionalDTO;
    }
}