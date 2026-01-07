package br.com.medmentor.mapper;

import br.com.medmentor.dto.AcaoDTO;
import br.com.medmentor.model.Acao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ApplicationScoped
public class AcaoMapper {

    public AcaoDTO toDto(Acao acao) {
        if (acao == null) {
            return null;
        }
        AcaoDTO dto = new AcaoDTO();
        dto.setId(acao.getId());
        dto.setDescricaoAcao(acao.getDescricaoAcao());
        return dto;
    }

    public List<AcaoDTO> toListDto(List<Acao> listaAcao) {
        if (listaAcao == null) {
            return new ArrayList<>();
        }
        return listaAcao.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Acao toEntity(AcaoDTO dto) {
        if (dto == null) { 
            return null;
        }
        Acao acao = new Acao();
        acao.setId(dto.getId());
        acao.setDescricaoAcao(dto.getDescricaoAcao());
        return acao;
    }

    public List<Acao> toListEntity(List<AcaoDTO> listaDto) {
        if (listaDto == null) {
            return new ArrayList<>();
        }
        return listaDto.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}