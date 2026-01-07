package br.com.medmentor.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.medmentor.dto.PessoaDTO;
import br.com.medmentor.model.Pessoa;
import br.com.medmentor.util.NormalizadorDadosUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named 
@ApplicationScoped 
public class PessoaMapper {
	
	@Inject 
	CidadeMapper cidadeMapper;

    public PessoaDTO toDto(Pessoa pessoa) {
        if (pessoa == null) {
            return null;
        }

        PessoaDTO dto = new PessoaDTO();
        dto.setId(pessoa.getId());
        dto.setCidadeDTO(cidadeMapper.toDto(pessoa.getCidade()));
        dto.setCodTipoPessoa(pessoa.getCodTipoPessoa());
        dto.setDescricaoBairro(pessoa.getDescricaoBairro());
        dto.setDescricaoComplemento(pessoa.getDescricaoComplemento());
        dto.setDescricaoEndereco(pessoa.getDescricaoEndereco());
        dto.setNomePessoa(pessoa.getNomePessoa());
        dto.setNumeroCep(pessoa.getNumeroCep());
        dto.setNumeroCelular(pessoa.getNumeroCelular());
		dto.setDescricaoEmail(pessoa.getDescricaoEmail());

        return dto;
    }

    public List<PessoaDTO> toListDto(List<Pessoa> listaPessoa) {
        if (listaPessoa == null) {
            return new ArrayList<>(); 
        }

        return listaPessoa.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Pessoa toEntity(PessoaDTO dto) {
        if (dto == null) {
            return null;
        }

        Pessoa pessoa = new Pessoa();
        pessoa.setId(dto.getId());
        pessoa.setCidade(cidadeMapper.toEntity(dto.getCidadeDTO()));
        pessoa.setCodTipoPessoa(dto.getCodTipoPessoa());
        pessoa.setDescricaoBairro(dto.getDescricaoBairro());
        pessoa.setDescricaoComplemento(dto.getDescricaoComplemento());
        pessoa.setDescricaoEndereco(dto.getDescricaoEndereco());
        pessoa.setNomePessoa(dto.getNomePessoa());
        pessoa.setNumeroCep(NormalizadorDadosUtil.removerFormatacaoCEP(dto.getNumeroCep()));
        pessoa.setNumeroCelular(dto.getNumeroCelular());
        pessoa.setDescricaoEmail(dto.getDescricaoEmail());

        return pessoa;
    }

    public List<Pessoa> toListEntity(List<PessoaDTO> listaDto) {
        if (listaDto == null) {
            return new ArrayList<>(); 
        }

        return listaDto.stream().map(this::toEntity).collect(Collectors.toList());
    }
}