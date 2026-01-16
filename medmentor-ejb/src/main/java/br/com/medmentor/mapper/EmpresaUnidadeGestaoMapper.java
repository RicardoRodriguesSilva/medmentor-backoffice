package br.com.medmentor.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.medmentor.dto.EmpresaUnidadeGestaoDTO;
import br.com.medmentor.model.EmpresaUnidadeGestao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class EmpresaUnidadeGestaoMapper {

    @Inject
    private EmpresaMapper empresaMapper;
    
    @Inject EmpresaGestaoMapper empresaGestaoMapper;

    public EmpresaUnidadeGestaoDTO toDto(EmpresaUnidadeGestao empresaUnidadeGestao) {
        if (empresaUnidadeGestao == null) {
            return null;
        }
        
        EmpresaUnidadeGestaoDTO dto = new EmpresaUnidadeGestaoDTO();
        dto.setId(empresaUnidadeGestao.getId());
        dto.setEmpresaDTO(empresaMapper.toDto(empresaUnidadeGestao.getEmpresa()));
        dto.setEmpresaGestoraDTO(empresaGestaoMapper.toDto(empresaUnidadeGestao.getEmpresaGestora()));
        dto.setNumeroPlantonistaDia(empresaUnidadeGestao.getNumeroPlantonistaDia());
        dto.setNumeroPlantonistaPeriodo(empresaUnidadeGestao.getNumeroPlantonistaPeriodo());
        
        return dto;
    }

    public List<EmpresaUnidadeGestaoDTO> toListDto(List<EmpresaUnidadeGestao> listaEmpresaGestao) {
        if (listaEmpresaGestao == null) {
            return new ArrayList<>();
        }

        return listaEmpresaGestao.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public EmpresaUnidadeGestao toEntity(EmpresaUnidadeGestaoDTO dto) {
        
    	if (dto == null) {
            return null;
        }

    	EmpresaUnidadeGestao empresaUnidadeGestao = new EmpresaUnidadeGestao();
    	empresaUnidadeGestao.setId(dto.getId());
    	empresaUnidadeGestao.setEmpresa(empresaMapper.toEntity(dto.getEmpresaDTO()));
    	empresaUnidadeGestao.setEmpresaGestora(empresaGestaoMapper.toEntity(dto.getEmpresaGestoraDTO()));
    	empresaUnidadeGestao.setNumeroPlantonistaDia(dto.getNumeroPlantonistaDia());
    	empresaUnidadeGestao.setNumeroPlantonistaPeriodo(dto.getNumeroPlantonistaPeriodo());

        return empresaUnidadeGestao;
    }

    public List<EmpresaUnidadeGestao> toListEntity(List<EmpresaUnidadeGestaoDTO> listaDto) {
        if (listaDto == null) {
            return new ArrayList<>();
        }

        return listaDto.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}