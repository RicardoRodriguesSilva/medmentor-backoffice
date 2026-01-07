package br.com.medmentor.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.medmentor.dto.TipoMenuDTO;
import br.com.medmentor.model.TipoMenu;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class TipoMenuMapper {

    public TipoMenuDTO toDto(TipoMenu tipoMenu) {
        if (tipoMenu == null) {
            return null;
        }
        TipoMenuDTO dto = new TipoMenuDTO();
        dto.setId(tipoMenu.getId());
        dto.setNomeTipoMenu(tipoMenu.getNomeTipoMenu());
        dto.setBolAtivo(tipoMenu.getBolAtivo());
        return dto;
    }

    public List<TipoMenuDTO> toListDto(List<TipoMenu> listaTipoMenu) {
        if (listaTipoMenu == null) {
            return new ArrayList<>();
        }
        return listaTipoMenu.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public TipoMenu toEntity(TipoMenuDTO dto) {
        if (dto == null) {
            return null;
        }
        TipoMenu tipoMenu = new TipoMenu();
        tipoMenu.setId(dto.getId());
        tipoMenu.setNomeTipoMenu(dto.getNomeTipoMenu());
        tipoMenu.setBolAtivo(dto.getBolAtivo());
        return tipoMenu;
    }

    public List<TipoMenu> toListEntity(List<TipoMenuDTO> listaDto) {
        if (listaDto == null) {
            return new ArrayList<>();
        }
        return listaDto.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}