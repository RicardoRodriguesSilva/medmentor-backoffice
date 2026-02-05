package br.com.medmentor.mobile.mapper;

import java.util.ArrayList;
import java.util.List;

import br.com.medmentor.dto.SolicitacaoMudancaEscalaDTO;
import br.com.medmentor.mobile.dto.SolicitacaoMudancaDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class SolicitacaoMudancaMapper {
	
	@Inject
	EscalaMapper escalaMapper;
	
	@Inject
	EmpresaMapper empresaMapper;

    public List<SolicitacaoMudancaDTO> toListDto(List<SolicitacaoMudancaEscalaDTO> listaDTO, Integer idProfissional) {
    	List<SolicitacaoMudancaDTO> listaSolicitacaoMudancaDTO = new ArrayList<SolicitacaoMudancaDTO>();
    	
    	if ((listaDTO!=null)&&(listaDTO.size()>0)) {
    		for (SolicitacaoMudancaEscalaDTO dto: listaDTO) {
    			
    			SolicitacaoMudancaDTO solicitacaoMudancaDTO = new SolicitacaoMudancaDTO();
    			solicitacaoMudancaDTO.setId(dto.getId());
    			solicitacaoMudancaDTO.setBolAcatada(dto.getBolAcatada());
    			solicitacaoMudancaDTO.setBolAtiva(dto.getBolAtiva());
    			
    			if (dto.getEmpresaProfissionalSolicitanteDTO().getProfissionalDTO().getId() == idProfissional) {
    				solicitacaoMudancaDTO.setBolSolicitacaoTerceiro(true);
    			} else {
    				solicitacaoMudancaDTO.setBolSolicitacaoTerceiro(false);
    			}
    			
    			solicitacaoMudancaDTO.setDataHoraAtualizacao(dto.getDataHoraAtualizacao());
    			solicitacaoMudancaDTO.setDataHoraSolicitacao(dto.getDataHoraSolicitacao());
    			solicitacaoMudancaDTO.setEmpresaProfissionalSolicitanteDTO(empresaMapper.toEmpresaDto(dto.getEmpresaProfissionalSolicitanteDTO()));
    			solicitacaoMudancaDTO.setEscalaSolicitadaDTO(escalaMapper.toEscalaDto(dto.getEscalaTrabalhoSolicitadaDTO(), idProfissional));
    			
    			listaSolicitacaoMudancaDTO.add(solicitacaoMudancaDTO);
    		}
    	}
   	
    	return listaSolicitacaoMudancaDTO;    	
    }

}