package br.com.medmentor.mobile.mapper;

import br.com.medmentor.dto.ProfissionalDTO;
import br.com.medmentor.mobile.dto.MedicoDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class MedicoMapper {

    public MedicoDTO toMedicoDto(ProfissionalDTO profissionalDTO) {
    	if(profissionalDTO == null) {
    		return null;
    	}
    	
    	MedicoDTO dto = new MedicoDTO();
    	dto.setCidadeDTO(profissionalDTO.getPessoaFisicaDTO().getPessoaDTO().getCidadeDTO());
    	dto.setDescricaoBairro(profissionalDTO.getPessoaFisicaDTO().getPessoaDTO().getDescricaoBairro());
    	dto.setDescricaoComplemento(profissionalDTO.getPessoaFisicaDTO().getPessoaDTO().getDescricaoComplemento());
    	dto.setDescricaoEndereco(profissionalDTO.getPessoaFisicaDTO().getPessoaDTO().getDescricaoEndereco());
    	dto.setId(profissionalDTO.getId());
    	dto.setNome(profissionalDTO.getPessoaFisicaDTO().getPessoaDTO().getNomePessoa());
    	dto.setNumeroCep(profissionalDTO.getPessoaFisicaDTO().getPessoaDTO().getNumeroCep());
    	dto.setNumeroCpf(profissionalDTO.getPessoaFisicaDTO().getCpf());
    	dto.setDataNascimento(profissionalDTO.getPessoaFisicaDTO().getDataNascimento());
    	dto.setIdade(profissionalDTO.getPessoaFisicaDTO().getIdade());
    	dto.setDescricaoEmail(profissionalDTO.getPessoaFisicaDTO().getPessoaDTO().getDescricaoEmail());
    	dto.setNumeroCelular(profissionalDTO.getPessoaFisicaDTO().getPessoaDTO().getNumeroCelular());
    	return dto;
    }

    public ProfissionalDTO toProfissionalDto(MedicoDTO medicoDTO, ProfissionalDTO profissionalDTO) {
    	if(profissionalDTO == null) {
    		return null;
    	}
    	
    	profissionalDTO.getPessoaFisicaDTO().getPessoaDTO().setCidadeDTO(medicoDTO.getCidadeDTO());
    	profissionalDTO.getPessoaFisicaDTO().getPessoaDTO().setDescricaoBairro(medicoDTO.getDescricaoBairro());
    	profissionalDTO.getPessoaFisicaDTO().getPessoaDTO().setDescricaoComplemento(medicoDTO.getDescricaoComplemento());
    	profissionalDTO.getPessoaFisicaDTO().getPessoaDTO().setDescricaoEndereco(medicoDTO.getDescricaoEndereco());
    	profissionalDTO.getPessoaFisicaDTO().getPessoaDTO().setId(medicoDTO.getId());
    	profissionalDTO.getPessoaFisicaDTO().getPessoaDTO().setNomePessoa(medicoDTO.getNome());
    	profissionalDTO.getPessoaFisicaDTO().getPessoaDTO().setNumeroCep(medicoDTO.getNumeroCep());
    	profissionalDTO.getPessoaFisicaDTO().setCpf(medicoDTO.getNumeroCpf());
    	profissionalDTO.getPessoaFisicaDTO().setDataNascimento(medicoDTO.getDataNascimento());
    	profissionalDTO.getPessoaFisicaDTO().setIdade(medicoDTO.getIdade());
    	profissionalDTO.getPessoaFisicaDTO().getPessoaDTO().setDescricaoEmail(medicoDTO.getDescricaoEmail());
    	profissionalDTO.getPessoaFisicaDTO().getPessoaDTO().setNumeroCelular(medicoDTO.getNumeroCelular());
    	return profissionalDTO;
    }
}