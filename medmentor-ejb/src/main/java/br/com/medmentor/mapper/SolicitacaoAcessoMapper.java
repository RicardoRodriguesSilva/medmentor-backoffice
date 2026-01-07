package br.com.medmentor.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.medmentor.dto.SolicitacaoAcessoDTO;
import br.com.medmentor.model.SolicitacaoAcesso;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class SolicitacaoAcessoMapper {
	
	@Inject 
	UsuarioMapper usuarioMapper;

    public SolicitacaoAcessoDTO toDto(SolicitacaoAcesso solicitacaoAcesso) {
        if (solicitacaoAcesso == null) {
            return null;
        }
        SolicitacaoAcessoDTO dto = new SolicitacaoAcessoDTO();
		dto.setId(solicitacaoAcesso.getId());
		dto.setCanal(solicitacaoAcesso.getCanal());
		dto.setDataHoraSolicitacao(solicitacaoAcesso.getDataHoraSolicitacao());
		dto.setSenhaAnterior(solicitacaoAcesso.getSenhaAnterior());
		dto.setUsuarioDTO(usuarioMapper.toDto(solicitacaoAcesso.getUsuario()));
		dto.setTipoSolicitacaoAcesso(solicitacaoAcesso.getTipoSolicitacaoAcesso());
		dto.setEmailUtilizado(solicitacaoAcesso.getEmailUtilizado());
        return dto;
    }

    public List<SolicitacaoAcessoDTO> toListDto(List<SolicitacaoAcesso> listaAcao) {
        if (listaAcao == null) {
            return new ArrayList<>();
        }
        return listaAcao.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public SolicitacaoAcesso toEntity(SolicitacaoAcessoDTO dto) {
        if (dto == null) { 
            return null;
        }
        SolicitacaoAcesso solicitacaoAcesso = new SolicitacaoAcesso();
        solicitacaoAcesso.setId(dto.getId());
        solicitacaoAcesso.setCanal(dto.getCanal());
        solicitacaoAcesso.setDataHoraSolicitacao(dto.getDataHoraSolicitacao());
		solicitacaoAcesso.setSenhaAnterior(dto.getSenhaAnterior());
		solicitacaoAcesso.setUsuario(usuarioMapper.toEntity(dto.getUsuarioDTO()));
		solicitacaoAcesso.setTipoSolicitacaoAcesso(dto.getTipoSolicitacaoAcesso());
		solicitacaoAcesso.setEmailUtilizado(dto.getEmailUtilizado());
        return solicitacaoAcesso;
    }

    public List<SolicitacaoAcesso> toListEntity(List<SolicitacaoAcessoDTO> listaDto) {
        if (listaDto == null) {
            return new ArrayList<>();
        }
        return listaDto.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}