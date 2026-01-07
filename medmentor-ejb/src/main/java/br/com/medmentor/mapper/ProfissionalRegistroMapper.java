package br.com.medmentor.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.medmentor.dto.ProfissionalRegistroDTO;
import br.com.medmentor.model.ProfissionalRegistro;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named 
@ApplicationScoped 
public class ProfissionalRegistroMapper {
	
	@Inject
	private ProfissionalMapper profissionalMapper;
	
	public ProfissionalRegistroDTO toDto(ProfissionalRegistro profissionalRegistro) {
	     if (profissionalRegistro == null) {
	         return null;
	     }
	
	     ProfissionalRegistroDTO dto = new ProfissionalRegistroDTO();
	     dto.setInstituicao(profissionalRegistro.getNomeInstituicao());
	     dto.setAnoFormacao(profissionalRegistro.getNumeroAnoFormacao());
	     dto.setRegistro(profissionalRegistro.getNumeroRegistro());
	     dto.setProfissionalDTO(profissionalMapper.toDto(profissionalRegistro.getProfissional()));
	
	     return dto;
	 }

	 public List<ProfissionalRegistroDTO> toListDto(List<ProfissionalRegistro> listaPessoaRegistro) {
	     if (listaPessoaRegistro == null) {
	         return new ArrayList<>(); 
	     }
	
	     return listaPessoaRegistro.stream().map(this::toDto).collect(Collectors.toList());
	 }

	 public ProfissionalRegistro toEntity(ProfissionalRegistroDTO dto) {
		 
	     if (dto == null) {
	         return null;
	     }
	
	     ProfissionalRegistro profissionalRegistro = new ProfissionalRegistro();	     
	     profissionalRegistro.setNomeInstituicao(dto.getInstituicao());
	     profissionalRegistro.setNumeroAnoFormacao(dto.getAnoFormacao());
	     profissionalRegistro.setNumeroRegistro(dto.getRegistro());
	     profissionalRegistro.setProfissional(profissionalMapper.toEntity(dto.getProfissionalDTO()));
	
	     return profissionalRegistro;
	 }

	 public List<ProfissionalRegistro> toListEntity(List<ProfissionalRegistroDTO> listaDto) {
	     if (listaDto == null) {
	         return new ArrayList<>(); 
	     }
	
	     return listaDto.stream().map(this::toEntity).collect(Collectors.toList());
	 }
}