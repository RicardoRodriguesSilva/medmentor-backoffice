package br.com.medmentor.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.medmentor.dto.UsuarioDTO;
import br.com.medmentor.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class UsuarioMapper {
	
	@Inject 
    private PessoaFisicaMapper pessoaFisicaMapper;

    public UsuarioDTO toDto(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNomeUsuario(usuario.getNomeUsuario());
        dto.setSenhaUsuario(usuario.getSenhaUsuario());
        dto.setBolAtivo(usuario.getBolAtivo());

        if (usuario.getPessoaFisica() != null) {
            dto.setPessoaFisicaDTO(pessoaFisicaMapper.toDto(usuario.getPessoaFisica()));        
        }
        return dto;
    }

    public List<UsuarioDTO> toListDto(List<Usuario> listaUsuario) {
        if (listaUsuario == null) {
            return new ArrayList<>();
        }
        return listaUsuario.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNomeUsuario(dto.getNomeUsuario());
        usuario.setSenhaUsuario(dto.getSenhaUsuario());
        usuario.setBolAtivo(dto.getBolAtivo());

        if (dto.getPessoaFisicaDTO() != null)
        	usuario.setPessoaFisica(pessoaFisicaMapper.toEntity(dto.getPessoaFisicaDTO())); 

        return usuario;  
    }

    public List<Usuario> toListEntity(List<UsuarioDTO> listaDto) {
        if (listaDto == null) {
            return new ArrayList<>();
        }
        return listaDto.stream()
                .map(this::toEntity) 
                .collect(Collectors.toList());
    }
}