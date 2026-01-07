package br.com.medmentor.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.medmentor.dto.PessoaJuridicaDTO;
import br.com.medmentor.model.PessoaJuridica;
import br.com.medmentor.util.NormalizadorDadosUtil;
import jakarta.inject.Inject;

public class PessoaJuridicaMapper {
	
	@Inject 
	PessoaMapper pessoaMapper;

    public PessoaJuridicaDTO toDto(PessoaJuridica pessoaJuridica) {
        if (pessoaJuridica == null) {
            return null;
        }

        PessoaJuridicaDTO dto = new PessoaJuridicaDTO();
        dto.setId(pessoaJuridica.getId());
        dto.setCnpj(pessoaJuridica.getCnpj());
        dto.setRazaoSocial(pessoaJuridica.getRazaoSocial());
        dto.setPessoaDTO(pessoaMapper.toDto(pessoaJuridica.getPessoa()));
        
        return dto;
    }

    public List<PessoaJuridicaDTO> toListDto(List<PessoaJuridica> listaPessoa) {
        if (listaPessoa == null) {
            return new ArrayList<>(); 
        }

        return listaPessoa.stream().map(this::toDto).collect(Collectors.toList());
    }

    public PessoaJuridica toEntity(PessoaJuridicaDTO dto) {
        if (dto == null) {
            return null;
        }

        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setId(dto.getId());
        pessoaJuridica.setCnpj(NormalizadorDadosUtil.removerFormatacaoCNPJ(dto.getCnpj()));
        pessoaJuridica.setRazaoSocial(dto.getRazaoSocial());
        pessoaJuridica.setPessoa(pessoaMapper.toEntity(dto.getPessoaDTO()));

        return pessoaJuridica;
    }

    public List<PessoaJuridica> toListEntity(List<PessoaJuridicaDTO> listaDto) {
        if (listaDto == null) {
            return new ArrayList<>(); 
        }

        return listaDto.stream().map(this::toEntity).collect(Collectors.toList());
    }
}