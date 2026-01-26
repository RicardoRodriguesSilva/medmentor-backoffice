package br.com.medmentor.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.medmentor.dto.SolicitacaoMudancaEscalaDTO;
import br.com.medmentor.model.SolicitacaoMudancaEscala;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named 
@ApplicationScoped 
public class SolicitacaoMudancaEscalaMapper {
	
	@Inject
	private EmpresaProfissionalMapper empresaProfissionalMapper;
	
	@Inject
	private EscalaTrabalhoMapper escalaTrabalhoMapper;
	
	public SolicitacaoMudancaEscalaDTO toDto(SolicitacaoMudancaEscala solicitacaoMudancaEscala) {
		
		if (solicitacaoMudancaEscala == null) {
	        return null;
	    }
	
	    SolicitacaoMudancaEscalaDTO dto = new SolicitacaoMudancaEscalaDTO();
	     
     	dto.setId(solicitacaoMudancaEscala.getId());
     	dto.setDataHoraAtualizacao(solicitacaoMudancaEscala.getDataHoraAtualizacao());
     	dto.setDataHoraSolicitacao(solicitacaoMudancaEscala.getDataHoraSolicitacao());
     	dto.setBolAcatada(solicitacaoMudancaEscala.getBolAcatada());
     	dto.setBolAtiva(solicitacaoMudancaEscala.getBolAtiva());
     	dto.setEmpresaProfissionalSolicitanteDTO(empresaProfissionalMapper.toDto(solicitacaoMudancaEscala.getEmpresaProfissionalSolicitante()));
     	dto.setEscalaTrabalhoSolicitadaDTO(escalaTrabalhoMapper.toDto(solicitacaoMudancaEscala.getEscalaTrabalhoSolicitada()));
	
	     return dto;
	 }

	 public List<SolicitacaoMudancaEscalaDTO> toListDto(List<SolicitacaoMudancaEscala> listaPessoaRegistro) {
	     if (listaPessoaRegistro == null) {
	         return new ArrayList<>(); 
	     }
	
	     return listaPessoaRegistro.stream().map(this::toDto).collect(Collectors.toList());
	 }

	 public SolicitacaoMudancaEscala toEntity(SolicitacaoMudancaEscalaDTO dto) {
		 
	     if (dto == null) {
	         return null;
	     }
	
	     SolicitacaoMudancaEscala solicitacaoMudancaEscala = new SolicitacaoMudancaEscala();
	     
	     solicitacaoMudancaEscala.setId(dto.getId());
	     solicitacaoMudancaEscala.setDataHoraAtualizacao(dto.getDataHoraAtualizacao());
	     solicitacaoMudancaEscala.setDataHoraSolicitacao(dto.getDataHoraSolicitacao());
	     solicitacaoMudancaEscala.setBolAcatada(dto.getBolAcatada());
	     solicitacaoMudancaEscala.setBolAtiva(dto.getBolAtiva());
	     solicitacaoMudancaEscala.setEmpresaProfissionalSolicitante(empresaProfissionalMapper.toEntity(dto.getEmpresaProfissionalSolicitanteDTO()));
	     solicitacaoMudancaEscala.setEscalaTrabalhoSolicitada(escalaTrabalhoMapper.toEntity(dto.getEscalaTrabalhoSolicitadaDTO()));
	
	     return solicitacaoMudancaEscala;
	 }

	 public List<SolicitacaoMudancaEscala> toListEntity(List<SolicitacaoMudancaEscalaDTO> listaDto) {
	     if (listaDto == null) {
	         return new ArrayList<>(); 
	     }
	
	     return listaDto.stream().map(this::toEntity).collect(Collectors.toList());
	 }
}