package br.com.medmentor.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.medmentor.dto.EmpresaDTO;
import br.com.medmentor.model.Empresa;
import jakarta.inject.Inject;

public class EmpresaMapper {
	
	@Inject 
	PessoaJuridicaMapper pessoaJuridicaMapper;

    public EmpresaDTO toDto(Empresa empresa) {
        if (empresa == null) {
            return null;
        }

        EmpresaDTO dto = new EmpresaDTO();
        dto.setId(empresa.getId());
        dto.setNomeFantasia(empresa.getNomeFantasia());
        dto.setPessoaJuridicaDTO(pessoaJuridicaMapper.toDto(empresa.getPessoaJuridica()));
        dto.setNomeResponsavel(empresa.getNomeResponsavel());
        return dto;
    }

    public List<EmpresaDTO> toListDto(List<Empresa> listaPessoa) {
        if (listaPessoa == null) {
            return new ArrayList<>(); 
        }

        return listaPessoa.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Empresa toEntity(EmpresaDTO dto) {
        if (dto == null) {
            return null;
        }

        Empresa empresa = new Empresa();
        empresa.setId(dto.getId());
        empresa.setNomeFantasia(dto.getNomeFantasia());
        empresa.setPessoaJuridica(pessoaJuridicaMapper.toEntity(dto.getPessoaJuridicaDTO()));
        empresa.setNomeResponsavel(dto.getNomeResponsavel());
        return empresa;
    }

    public List<Empresa> toListEntity(List<EmpresaDTO> listaDto) {
        if (listaDto == null) {
            return new ArrayList<>(); 
        }

        return listaDto.stream().map(this::toEntity).collect(Collectors.toList());
    }
}