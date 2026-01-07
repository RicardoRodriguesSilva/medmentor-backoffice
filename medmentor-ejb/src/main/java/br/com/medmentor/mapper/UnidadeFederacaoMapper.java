package br.com.medmentor.mapper;

import java.util.ArrayList;
import java.util.List;

import br.com.medmentor.dto.UnidadeFederacaoDTO;
import br.com.medmentor.model.UnidadeFederacao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named 
@ApplicationScoped 
public class UnidadeFederacaoMapper {

	public UnidadeFederacaoDTO toDto(UnidadeFederacao unidadeFederacao) {
        if (unidadeFederacao == null) {
            return null;
        }
        
        UnidadeFederacaoDTO dto = new UnidadeFederacaoDTO();
        dto.setId(unidadeFederacao.getIdUnidadeFederacao());
        dto.setNome(unidadeFederacao.getNomeUnidadeFederacao());
        dto.setSigla(unidadeFederacao.getSiglaUnidadeFederacao());
        return dto;
    }
	
	public List<UnidadeFederacaoDTO> toListDto(List<UnidadeFederacao> listaUnidadeFderacao) {
		if (listaUnidadeFderacao == null) {
			return new ArrayList<>();
		}
		
		return listaUnidadeFderacao.stream().map(this::toDto).toList();
	}

    public UnidadeFederacao toEntity(UnidadeFederacaoDTO dto) {
        if (dto == null) {
            return null;
        }
        UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
        unidadeFederacao.setIdUnidadeFederacao(dto.getId());
        unidadeFederacao.setNomeUnidadeFederacao(dto.getNome());
        unidadeFederacao.setSiglaUnidadeFederacao(dto.getSigla());
        return unidadeFederacao;
    }
    
	public List<UnidadeFederacao> toListEntity(List<UnidadeFederacaoDTO> listaDto) {
		if (listaDto == null) {
			return new ArrayList<>(); 
		}
		
		return listaDto.stream().map(this::toEntity).toList();
	}    
}