package br.com.medmentor.mapper;

import br.com.medmentor.dto.MenuDTO;
import br.com.medmentor.dto.TipoMenuDTO;
import br.com.medmentor.model.Menu;
import br.com.medmentor.model.TipoMenu;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ApplicationScoped
public class MenuMapper {

    public MenuDTO toDto(Menu menu) {
        if (menu == null) {
            return null;
        }
        MenuDTO dto = new MenuDTO();
        dto.setId(menu.getId());
        dto.setNomeMenu(menu.getNomeMenu());
        dto.setDescricaoMenu(menu.getDescricaoMenu());
        dto.setNomeCaminhoMenu(menu.getNomeCaminhoMenu());
        TipoMenuDTO tipoMenuDTO = new TipoMenuDTO();
        tipoMenuDTO.setId(menu.getTipoMenu().getId());
        tipoMenuDTO.setNomeTipoMenu(menu.getTipoMenu().getNomeTipoMenu());
        dto.setTipoMenuDTO(tipoMenuDTO);
        dto.setBolAtivo(menu.getBolAtivo());
        dto.setNumeroOrdem(menu.getNumeroOrdem());
        return dto;
    }

    public List<MenuDTO> toListDto(List<Menu> listaMenu) {
        if (listaMenu == null) {
            return new ArrayList<>();
        }
        return listaMenu.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Menu toEntity(MenuDTO dto) {
        if (dto == null) {
            return null;
        }
        Menu menu = new Menu();
        menu.setId(dto.getId());
        menu.setNomeMenu(dto.getNomeMenu());
        menu.setDescricaoMenu(dto.getDescricaoMenu());
        menu.setNomeCaminhoMenu(dto.getNomeCaminhoMenu());
        
        TipoMenu tipoMenu = new TipoMenu();
        tipoMenu.setId(dto.getTipoMenuDTO().getId());
        tipoMenu.setNomeTipoMenu(dto.getTipoMenuDTO().getNomeTipoMenu());
        menu.setTipoMenu(tipoMenu);
        menu.setNumeroOrdem(dto.getNumeroOrdem());
        menu.setBolAtivo(dto.getBolAtivo());
        menu.setNumeroOrdem(dto.getNumeroOrdem());
        return menu;
    }

    public List<Menu> toListEntity(List<MenuDTO> listaDto) {
        if (listaDto == null) {
            return new ArrayList<>();
        }
        return listaDto.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}