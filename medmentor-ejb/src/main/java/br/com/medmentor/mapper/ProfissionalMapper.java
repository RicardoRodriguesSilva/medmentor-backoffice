package br.com.medmentor.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.medmentor.dto.ProfissionalDTO;
import br.com.medmentor.model.Profissional;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class ProfissionalMapper {

    @Inject
    private PessoaFisicaMapper pessoaFisicaMapper;

    public ProfissionalDTO toDto(Profissional profissional) {
        if (profissional.getPessoaFisica() == null) {
            return null;
        }
        
        ProfissionalDTO dto = new ProfissionalDTO();
        dto.setId(profissional.getId());
        dto.setPessoaFisicaDTO(pessoaFisicaMapper.toDto(profissional.getPessoaFisica()));
        
        return dto;
    }

    public List<ProfissionalDTO> toListDto(List<Profissional> listaProfissional) {
        if (listaProfissional == null) {
            return new ArrayList<>();
        }

        return listaProfissional.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Profissional toEntity(ProfissionalDTO dto) {
        
    	if (dto == null) {
            return null;
        }

    	Profissional profissional = new Profissional();
    	profissional.setId(dto.getId());
    	profissional.setPessoaFisica(pessoaFisicaMapper.toEntity(dto.getPessoaFisicaDTO()));

        return profissional;
    }

    public List<Profissional> toListEntity(List<ProfissionalDTO> listaDto) {
        if (listaDto == null) {
            return new ArrayList<>();
        }

        return listaDto.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}