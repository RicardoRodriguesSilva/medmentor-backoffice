package br.com.medmentor.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.medmentor.dto.PessoaFisicaDTO;
import br.com.medmentor.model.PessoaFisica;
import br.com.medmentor.util.NormalizadorDadosUtil;
import jakarta.inject.Inject;

public class PessoaFisicaMapper {
	
	@Inject 
	PessoaMapper pessoaMapper;

    public PessoaFisicaDTO toDto(PessoaFisica pessoaFisica) {
        if (pessoaFisica == null) {
            return null;
        }

        PessoaFisicaDTO dto = new PessoaFisicaDTO();
        dto.setId(pessoaFisica.getId());
        dto.setCpf(NormalizadorDadosUtil.removerFormatacaoCPF(pessoaFisica.getCpf()));
        dto.setDataNascimento(pessoaFisica.getDataNascimento());
        dto.setIdade(pessoaFisica.getIdade());
        dto.setPessoaDTO(pessoaMapper.toDto(pessoaFisica.getPessoa()));
        
        return dto;
    }

    public List<PessoaFisicaDTO> toListDto(List<PessoaFisica> listaPessoa) {
        if (listaPessoa == null) {
            return new ArrayList<>(); 
        }

        return listaPessoa.stream().map(this::toDto).collect(Collectors.toList());
    }

    public PessoaFisica toEntity(PessoaFisicaDTO dto) {
        if (dto == null) {
            return null;
        }

        PessoaFisica pessoaFisica = new PessoaFisica();
        pessoaFisica.setId(dto.getId());
        pessoaFisica.setCpf(dto.getCpf());
        pessoaFisica.setDataNascimento(dto.getDataNascimento());
        pessoaFisica.setIdade(dto.getIdade());
        pessoaFisica.setPessoa(pessoaMapper.toEntity(dto.getPessoaDTO()));

        return pessoaFisica;
    }

    public List<PessoaFisica> toListEntity(List<PessoaFisicaDTO> listaDto) {
        if (listaDto == null) {
            return new ArrayList<>(); 
        }

        return listaDto.stream().map(this::toEntity).collect(Collectors.toList());
    }
}