package br.com.medmentor.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.medmentor.dto.NotificacaoDTO;
import br.com.medmentor.model.Notificacao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named 
@ApplicationScoped 
public class NotificacaoMapper {
	
	@Inject
	private EmpresaProfissionalMapper empresaProfissionalMapper;
	
	@Inject
	private EmpresaUnidadeGestaoMapper empresaUnidadeGestaoMapper;
	
	public NotificacaoDTO toDto(Notificacao notificacao) {
	     if (notificacao == null) {
	         return null;
	     }
	
	     NotificacaoDTO dto = new NotificacaoDTO();
	     
     	 dto.setId(notificacao.getId());
     	 dto.setDataHora(notificacao.getDataHora());
     	 dto.setDescricao(notificacao.getDescricao());
    	 dto.setEmpresaUnidadeGestaoDTO(empresaUnidadeGestaoMapper.toDto(notificacao.getEmpresaUnidadeGestao()));
    	 dto.setEmpresaProfissionalDTO(empresaProfissionalMapper.toDto(notificacao.getEmpresaProfissional()));
         dto.setBolLida(notificacao.getBolLida());
	
	     return dto;
	 }

	 public List<NotificacaoDTO> toListDto(List<Notificacao> listaPessoaRegistro) {
	     if (listaPessoaRegistro == null) {
	         return new ArrayList<>(); 
	     }
	
	     return listaPessoaRegistro.stream().map(this::toDto).collect(Collectors.toList());
	 }

	 public Notificacao toEntity(NotificacaoDTO dto) {
		 
	     if (dto == null) {
	         return null;
	     }
	
	     Notificacao notificacao = new Notificacao();
	     
	     notificacao.setId(dto.getId());
	     notificacao.setDataHora(dto.getDataHora());
	     notificacao.setDescricao(dto.getDescricao());
	     notificacao.setEmpresaUnidadeGestao(empresaUnidadeGestaoMapper.toEntity(dto.getEmpresaUnidadeGestaoDTO()));
	     notificacao.setEmpresaProfissional(empresaProfissionalMapper.toEntity(dto.getEmpresaProfissionalDTO()));
	     notificacao.setBolLida(dto.getBolLida());
	
	     return notificacao;
	 }

	 public List<Notificacao> toListEntity(List<NotificacaoDTO> listaDto) {
	     if (listaDto == null) {
	         return new ArrayList<>(); 
	     }
	
	     return listaDto.stream().map(this::toEntity).collect(Collectors.toList());
	 }
}