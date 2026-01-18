package br.com.medmentor.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.medmentor.dto.GeracaoEscalaDTO;
import br.com.medmentor.model.GeracaoEscala;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named 
@ApplicationScoped 
public class GeracaoEscalaMapper {
	

	@Inject
	private EmpresaUnidadeGestaoMapper empresaUnidadeGestaoMapper;
	
	public GeracaoEscalaDTO toDto(GeracaoEscala geracaoEscala) {
	     if (geracaoEscala == null) {
	         return null;
	     }
	
	     GeracaoEscalaDTO dto = new GeracaoEscalaDTO();
	     
     	 dto.setDataGeracao(geracaoEscala.getDataGeracao());
    	 dto.setEmpresaUnidadeGestaoDTO(empresaUnidadeGestaoMapper.toDto(geracaoEscala.getEmpresaUnidadeGestao()));
         dto.setId(geracaoEscala.getId());
	
	     return dto;
	 }

	 public List<GeracaoEscalaDTO> toListDto(List<GeracaoEscala> listaPessoaRegistro) {
	     if (listaPessoaRegistro == null) {
	         return new ArrayList<>(); 
	     }
	
	     return listaPessoaRegistro.stream().map(this::toDto).collect(Collectors.toList());
	 }

	 public GeracaoEscala toEntity(GeracaoEscalaDTO dto) {
		 
	     if (dto == null) {
	         return null;
	     }
	
	     GeracaoEscala geracaoEscala = new GeracaoEscala();
	     
	     geracaoEscala.setDataGeracao(dto.getDataGeracao());
	     geracaoEscala.setEmpresaUnidadeGestao(empresaUnidadeGestaoMapper.toEntity(dto.getEmpresaUnidadeGestaoDTO()));
	     geracaoEscala.setId(dto.getId());
	
	     return geracaoEscala;
	 }

	 public List<GeracaoEscala> toListEntity(List<GeracaoEscalaDTO> listaDto) {
	     if (listaDto == null) {
	         return new ArrayList<>(); 
	     }
	
	     return listaDto.stream().map(this::toEntity).collect(Collectors.toList());
	 }
}