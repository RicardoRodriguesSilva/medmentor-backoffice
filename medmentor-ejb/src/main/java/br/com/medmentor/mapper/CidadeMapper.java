package br.com.medmentor.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.medmentor.dto.CidadeDTO;
import br.com.medmentor.model.Cidade;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named 
@ApplicationScoped 
public class CidadeMapper {

    @Inject 
    private UnidadeFederacaoMapper unidadeFederacaoMapper;

    public CidadeDTO toDto(Cidade cidade) {
        if (cidade == null) {
            return null;
        }

        CidadeDTO dto = new CidadeDTO();
        dto.setId(cidade.getId());
        dto.setNome(cidade.getNomeCidade());
        
        if (cidade.getUnidadeFederacao() != null) {
            dto.setUnidadeFederacaoDTO(unidadeFederacaoMapper.toDto(cidade.getUnidadeFederacao()));
        }

        return dto;
    }

    public List<CidadeDTO> toListDto(List<Cidade> listaCidade) {
        if (listaCidade == null) {
            return new ArrayList<>(); 
        }

        return listaCidade.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Cidade toEntity(CidadeDTO dto) {
        if (dto == null) {
            return null;
        }

        Cidade cidade = new Cidade();
        cidade.setId(dto.getId());
        cidade.setNomeCidade(dto.getNome());        

        if (dto.getUnidadeFederacaoDTO() != null) {
        	cidade.setUnidadeFederacao(unidadeFederacaoMapper.toEntity(dto.getUnidadeFederacaoDTO()));        
        }

        return cidade;
    }

    public List<Cidade> toListEntity(List<CidadeDTO> listaDto) {
        if (listaDto == null) {
            return new ArrayList<>(); 
        }

        return listaDto.stream().map(this::toEntity).collect(Collectors.toList());
    }
}