package br.com.medmentor.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.medmentor.dto.EscalaTrabalhoDTO;
import br.com.medmentor.model.EscalaTrabalho;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named 
@ApplicationScoped 
public class EscalaTrabalhoMapper {
	
	@Inject
    private EmpresaProfissionalMapper empresaProfissionalMapper;
	
	@Inject
	private EmpresaUnidadeGestaoMapper empresaUnidadeGestaoMapper;
	
	public EscalaTrabalhoDTO toDto(EscalaTrabalho escalaTrabalho) {
	     if (escalaTrabalho == null) {
	         return null;
	     }
	
	     EscalaTrabalhoDTO dto = new EscalaTrabalhoDTO();
	     
     	 dto.setBolAtivo(escalaTrabalho.getBolAtivo());
    	 dto.setBolRealizado(escalaTrabalho.getBolRealizado());
    	 dto.setBolDisponibilizado(escalaTrabalho.getBolDisponibilizado());
    	 dto.setDataHoraEntrada(escalaTrabalho.getDataHoraEntrada());
    	 dto.setDataHoraSaida(escalaTrabalho.getDataHoraSaida());
    	 dto.setEmpresaProfissionalDTO(empresaProfissionalMapper.toDto(escalaTrabalho.getEmpresaProfissional()));
    	 dto.setEmpresaUnidadeGestaoDTO(empresaUnidadeGestaoMapper.toDto(escalaTrabalho.getEmpresaUnidadeGestao()));
         dto.setId(escalaTrabalho.getId());
	
	     return dto;
	 }

	 public List<EscalaTrabalhoDTO> toListDto(List<EscalaTrabalho> listaPessoaRegistro) {
	     if (listaPessoaRegistro == null) {
	         return new ArrayList<>(); 
	     }
	
	     return listaPessoaRegistro.stream().map(this::toDto).collect(Collectors.toList());
	 }

	 public EscalaTrabalho toEntity(EscalaTrabalhoDTO dto) {
		 
	     if (dto == null) {
	         return null;
	     }
	
	     EscalaTrabalho escalaTrabalho = new EscalaTrabalho();
	     
	     escalaTrabalho.setBolAtivo(dto.getBolAtivo());
	     escalaTrabalho.setBolRealizado(dto.getBolRealizado());
	     escalaTrabalho.setBolDisponibilizado(dto.getBolDisponibilizado());
	     escalaTrabalho.setDataHoraEntrada(dto.getDataHoraEntrada());
	     escalaTrabalho.setDataHoraSaida(dto.getDataHoraSaida());
	     escalaTrabalho.setEmpresaProfissional(empresaProfissionalMapper.toEntity(dto.getEmpresaProfissionalDTO()));
	     escalaTrabalho.setEmpresaUnidadeGestao(empresaUnidadeGestaoMapper.toEntity(dto.getEmpresaUnidadeGestaoDTO()));
	     escalaTrabalho.setId(dto.getId());
	
	     return escalaTrabalho;
	 }

	 public List<EscalaTrabalho> toListEntity(List<EscalaTrabalhoDTO> listaDto) {
	     if (listaDto == null) {
	         return new ArrayList<>(); 
	     }
	
	     return listaDto.stream().map(this::toEntity).collect(Collectors.toList());
	 }
}