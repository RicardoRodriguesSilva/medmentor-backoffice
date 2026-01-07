package br.com.medmentor.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.medmentor.dto.MudancaEscalaTrabalhoDTO;
import br.com.medmentor.model.MudancaEscalaTrabalho;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named 
@ApplicationScoped 
public class MudancaEscalaTrabalhoMapper {
	
	public MudancaEscalaTrabalhoDTO toDto(MudancaEscalaTrabalho mudancaEscalaTrabalho) {
	     if (mudancaEscalaTrabalho == null) {
	         return null;
	     }
	
	     MudancaEscalaTrabalhoDTO dto = new MudancaEscalaTrabalhoDTO();
	     
	     dto.setDataHoraMudanca(mudancaEscalaTrabalho.getDataHoraMudanca());
	     dto.setDataHoraSolicitacao(mudancaEscalaTrabalho.getDataHoraSolicitacao());
	     dto.setIdEmpresaProfissionalUnidadeGestaoAnterior(mudancaEscalaTrabalho.getIdEmpresaProfissionalUnidadeGestaoAnterior());
	     dto.setIdEmpresaProfissionalUnidadeGestaoAtual(mudancaEscalaTrabalho.getIdEmpresaProfissionalUnidadeGestaoAtual());
	     dto.setIdEscalaTrabalho(mudancaEscalaTrabalho.getIdEscalaTrabalho());
	     dto.setIdMudancaEscalaTrabalho(mudancaEscalaTrabalho.getIdMudancaEscalaTrabalho());
	
	     return dto;
	 }

	 public List<MudancaEscalaTrabalhoDTO> toListDto(List<MudancaEscalaTrabalho> listaPessoaRegistro) {
	     if (listaPessoaRegistro == null) {
	         return new ArrayList<>(); 
	     }
	
	     return listaPessoaRegistro.stream().map(this::toDto).collect(Collectors.toList());
	 }

	 public MudancaEscalaTrabalho toEntity(MudancaEscalaTrabalhoDTO dto) {
		 
	     if (dto == null) {
	         return null;
	     }
	
	     MudancaEscalaTrabalho mudancaEscalaTrabalho = new MudancaEscalaTrabalho();
	     
	     mudancaEscalaTrabalho.setDataHoraMudanca(dto.getDataHoraMudanca());
	     mudancaEscalaTrabalho.setDataHoraSolicitacao(dto.getDataHoraSolicitacao());
	     mudancaEscalaTrabalho.setIdEmpresaProfissionalUnidadeGestaoAnterior(dto.getIdEmpresaProfissionalUnidadeGestaoAnterior());
	     mudancaEscalaTrabalho.setIdEmpresaProfissionalUnidadeGestaoAtual(dto.getIdEmpresaProfissionalUnidadeGestaoAtual());
	     mudancaEscalaTrabalho.setIdEscalaTrabalho(dto.getIdEscalaTrabalho());
	     mudancaEscalaTrabalho.setIdMudancaEscalaTrabalho(dto.getIdMudancaEscalaTrabalho());
	
	     return mudancaEscalaTrabalho;
	 }

	 public List<MudancaEscalaTrabalho> toListEntity(List<MudancaEscalaTrabalhoDTO> listaDto) {
	     if (listaDto == null) {
	         return new ArrayList<>(); 
	     }
	
	     return listaDto.stream().map(this::toEntity).collect(Collectors.toList());
	 }
}