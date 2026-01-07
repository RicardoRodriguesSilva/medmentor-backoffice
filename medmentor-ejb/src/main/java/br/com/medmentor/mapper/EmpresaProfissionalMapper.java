package br.com.medmentor.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.medmentor.dto.EmpresaProfissionalDTO;
import br.com.medmentor.model.EmpresaProfissional;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class EmpresaProfissionalMapper {

    @Inject
    private EmpresaMapper empresaMapper;
    
    @Inject
    private EmpresaGestaoMapper empresaGestaoMapper;
    
    @Inject 
    private ProfissionalMapper profissionalMapper;

    public EmpresaProfissionalDTO toDto(EmpresaProfissional empresaProfissional) {
        if (empresaProfissional == null) {
            return null;
        }
        
        EmpresaProfissionalDTO dto = new EmpresaProfissionalDTO();
        dto.setId(empresaProfissional.getId());
        dto.setEmpresaDTO(empresaMapper.toDto(empresaProfissional.getEmpresa()));
        dto.setProfissionalDTO(profissionalMapper.toDto(empresaProfissional.getProfissional()));
        dto.setEmpresaGestaoDTO(empresaGestaoMapper.toDto(empresaProfissional.getEmpresaGestao()));
        dto.setDescricaoChavePix(empresaProfissional.getDescricaoChavePix());
        dto.setNumeroAgencia(empresaProfissional.getNumeroAgencia());
        dto.setNumeroBanco(empresaProfissional.getNumeroBanco());
        dto.setNumeroConta(empresaProfissional.getNumeroConta());
        
        return dto;
    }

    public List<EmpresaProfissionalDTO> toListDto(List<EmpresaProfissional> listaEmpresaProfissional) {
        if (listaEmpresaProfissional == null) {
            return new ArrayList<>();
        }

        return listaEmpresaProfissional.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public EmpresaProfissional toEntity(EmpresaProfissionalDTO dto) {
        
    	if (dto == null) {
            return null;
        }

    	EmpresaProfissional empresaProfissional = new EmpresaProfissional();
    	empresaProfissional.setId(dto.getId());
    	empresaProfissional.setEmpresa(empresaMapper.toEntity(dto.getEmpresaDTO()));
    	empresaProfissional.setProfissional(profissionalMapper.toEntity(dto.getProfissionalDTO()));
    	empresaProfissional.setEmpresaGestao(empresaGestaoMapper.toEntity(dto.getEmpresaGestaoDTO()));
    	empresaProfissional.setDescricaoChavePix(dto.getDescricaoChavePix());
    	empresaProfissional.setNumeroAgencia(dto.getNumeroAgencia());
    	empresaProfissional.setNumeroBanco(dto.getNumeroBanco());
    	empresaProfissional.setNumeroConta(dto.getNumeroConta());    	
        
        return empresaProfissional;
    }

    public List<EmpresaProfissional> toListEntity(List<EmpresaProfissionalDTO> listaDto) {
        if (listaDto == null) {
            return new ArrayList<>();
        }

        return listaDto.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}