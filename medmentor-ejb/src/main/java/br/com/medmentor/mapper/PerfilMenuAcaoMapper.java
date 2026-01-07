package br.com.medmentor.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.medmentor.dto.AcaoDTO;
import br.com.medmentor.dto.MenuDTO;
import br.com.medmentor.dto.PerfilDTO;
import br.com.medmentor.dto.PerfilMenuAcaoDTO;
import br.com.medmentor.model.Acao;
import br.com.medmentor.model.Menu;
import br.com.medmentor.model.Perfil;
import br.com.medmentor.model.PerfilMenuAcao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class PerfilMenuAcaoMapper {
	
	@Inject
	private AcaoMapper acaoMapper;
	
	@Inject
	private MenuMapper menuMapper;
	
	@Inject
	private PerfilMapper perfilMapper;

    public PerfilMenuAcaoDTO toDto(PerfilMenuAcao perfilMenuAcao) {
        if (perfilMenuAcao == null) {
            return null;
        }
        PerfilMenuAcaoDTO dto = new PerfilMenuAcaoDTO();
        dto.setId(perfilMenuAcao.getId());
        
        AcaoDTO acaoDto = new AcaoDTO();
        acaoDto = acaoMapper.toDto(perfilMenuAcao.getAcao());
        
        MenuDTO menuDto = new MenuDTO();
        menuDto = menuMapper.toDto(perfilMenuAcao.getMenu());
        
        PerfilDTO perfilDto = new PerfilDTO();
        perfilDto = perfilMapper.toDto(perfilMenuAcao.getPerfil());
        
        dto.setAcaoDTO(acaoDto);
        dto.setMenuDTO(menuDto);
        dto.setPerfilDTO(perfilDto);
        
        return dto;
    }

    public List<PerfilMenuAcaoDTO> toListDto(List<PerfilMenuAcao> listaPerfilMenuAcao) {
        if (listaPerfilMenuAcao == null) {
            return new ArrayList<>();
        }
        return listaPerfilMenuAcao.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public PerfilMenuAcao toEntity(PerfilMenuAcaoDTO dto) {
        if (dto == null) {
            return null;
        }
        PerfilMenuAcao perfilMenuAcao = new PerfilMenuAcao();
        perfilMenuAcao.setId(dto.getId());
        
        Acao acao = new Acao();
        acao = acaoMapper.toEntity(dto.getAcaoDTO());
        
        Menu menu = new Menu();
        menu = menuMapper.toEntity(dto.getMenuDTO());
        
        Perfil perfil = new Perfil();
        perfil = perfilMapper.toEntity(dto.getPerfilDTO());
        
        perfilMenuAcao.setAcao(acao);
        perfilMenuAcao.setMenu(menu);
        perfilMenuAcao.setPerfil(perfil);
        
        return perfilMenuAcao;
    }

    public List<PerfilMenuAcao> toListEntity(List<PerfilMenuAcaoDTO> listaDto) {
        if (listaDto == null) {
            return new ArrayList<>();
        }
        return listaDto.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}