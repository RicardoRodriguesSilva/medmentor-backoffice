package br.com.medmentor.mapper;

import br.com.medmentor.dto.ParametroDTO;
import br.com.medmentor.model.Parametro;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ApplicationScoped
public class ParametroMapper {

    public ParametroDTO toDto(Parametro parametro) {
        if (parametro == null) {
            return null;
        }
        ParametroDTO dto = new ParametroDTO();
        dto.setId(parametro.getId());
        dto.setNome(parametro.getNome());
        dto.setValor(parametro.getValor());
        return dto;
    }

    public List<ParametroDTO> toListDto(List<Parametro> listaParametro) {
        if (listaParametro == null) {
            return new ArrayList<>();
        }
        return listaParametro.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Parametro toEntity(ParametroDTO dto) {
        if (dto == null) { 
            return null;
        }
        Parametro parametro = new Parametro();
        parametro.setId(dto.getId());
        parametro.setNome(dto.getNome());
        parametro.setValor(dto.getValor());
        
        return parametro;
    }

    public List<Parametro> toListEntity(List<ParametroDTO> listaDto) {
        if (listaDto == null) {
            return new ArrayList<>();
        }
        return listaDto.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}