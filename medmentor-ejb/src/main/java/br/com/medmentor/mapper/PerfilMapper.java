package br.com.medmentor.mapper;

import br.com.medmentor.dto.PerfilDTO;
import br.com.medmentor.model.Perfil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ApplicationScoped
public class PerfilMapper {

    public PerfilDTO toDto(Perfil perfil) {
        if (perfil == null) {
            return null;
        }
        PerfilDTO dto = new PerfilDTO();
        dto.setId(perfil.getId());
        dto.setNomePerfil(perfil.getNomePerfil());
        dto.setDescricaoPerfil(perfil.getDescricaoPerfil());
        dto.setBolAtivo(perfil.getBolAtivo());
        return dto;
    }

    public List<PerfilDTO> toListDto(List<Perfil> listaPerfil) {
        if (listaPerfil == null) {
            return new ArrayList<>();
        }
        return listaPerfil.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Perfil toEntity(PerfilDTO dto) {
        if (dto == null) {
            return null;
        }
        Perfil perfil = new Perfil();
        perfil.setId(dto.getId());
        perfil.setNomePerfil(dto.getNomePerfil());
        perfil.setDescricaoPerfil(dto.getDescricaoPerfil());
        perfil.setBolAtivo(dto.getBolAtivo());
        return perfil;
    }

    public List<Perfil> toListEntity(List<PerfilDTO> listaDto) {
        if (listaDto == null) {
            return new ArrayList<>();
        }
        return listaDto.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
} 