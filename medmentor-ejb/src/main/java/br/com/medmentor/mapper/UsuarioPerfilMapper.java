package br.com.medmentor.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.medmentor.dto.UsuarioPerfilDTO;
import br.com.medmentor.model.UsuarioPerfil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class UsuarioPerfilMapper {
	
	@Inject 
    private UsuarioMapper usuarioMapper;
		
	@Inject 
    private PerfilMapper perfilMapper;

    public UsuarioPerfilDTO toDto(UsuarioPerfil usuarioPerfil) {
        if (usuarioPerfil == null) {
            return null;
        }
        UsuarioPerfilDTO dto = new UsuarioPerfilDTO();
        dto.setId(usuarioPerfil.getId());
        
        if (usuarioPerfil.getPerfil()!=null)
        	dto.setPerfilDTO(perfilMapper.toDto(usuarioPerfil.getPerfil()));
        
        if (usuarioPerfil.getUsuario()!=null)
        	dto.setUsuarioDTO(usuarioMapper.toDto(usuarioPerfil.getUsuario()));
        
        return dto;
    }

    public List<UsuarioPerfilDTO> toListDto(List<UsuarioPerfil> listaUsuarioPerfil) {
        if (listaUsuarioPerfil == null) {
            return new ArrayList<>();
        }
        return listaUsuarioPerfil.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public UsuarioPerfil toEntity(UsuarioPerfilDTO dto) {
        if (dto == null) {
            return null;
        }
        UsuarioPerfil usuarioPerfil = new UsuarioPerfil();
        usuarioPerfil.setId(dto.getId());
        
        if (dto.getPerfilDTO()!=null)
        	usuarioPerfil.setPerfil(perfilMapper.toEntity(dto.getPerfilDTO()));
        
        if (dto.getUsuarioDTO()!=null)
        	usuarioPerfil.setUsuario(usuarioMapper.toEntity(dto.getUsuarioDTO()));
        return usuarioPerfil;
    }

    public List<UsuarioPerfil> toListEntity(List<UsuarioPerfilDTO> listaDto) {
        if (listaDto == null) {
            return new ArrayList<>();
        }
        return listaDto.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}