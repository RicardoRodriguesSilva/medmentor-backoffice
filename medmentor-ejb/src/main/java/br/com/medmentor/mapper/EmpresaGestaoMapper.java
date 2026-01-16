package br.com.medmentor.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.medmentor.dto.EmpresaGestaoDTO;
import br.com.medmentor.model.EmpresaGestao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class EmpresaGestaoMapper {

    @Inject
    private EmpresaMapper empresaMapper;

    public EmpresaGestaoDTO toDto(EmpresaGestao empresaGestao) {
    	
        if (empresaGestao == null) {
            return null;
        }
        
        EmpresaGestaoDTO dto = new EmpresaGestaoDTO();
        dto.setId(empresaGestao.getId());
        dto.setEmpresaDTO(empresaMapper.toDto(empresaGestao.getEmpresa()));

        return dto;
    }

    public List<EmpresaGestaoDTO> toListDto(List<EmpresaGestao> listaEmpresaGestao) {
        if (listaEmpresaGestao == null) {
            return new ArrayList<>();
        }

        return listaEmpresaGestao.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public EmpresaGestao toEntity(EmpresaGestaoDTO dto) {
        
    	if (dto == null) {
            return null;
        }

    	EmpresaGestao empresaGestao = new EmpresaGestao();
    	empresaGestao.setId(dto.getId());
    	empresaGestao.setEmpresa(empresaMapper.toEntity(dto.getEmpresaDTO()));

        return empresaGestao;
    }

    public List<EmpresaGestao> toListEntity(List<EmpresaGestaoDTO> listaDto) {
        if (listaDto == null) {
            return new ArrayList<>();
        }

        return listaDto.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}