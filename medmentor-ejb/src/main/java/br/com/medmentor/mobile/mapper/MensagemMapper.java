package br.com.medmentor.mobile.mapper;

import java.util.ArrayList;
import java.util.List;

import br.com.medmentor.dto.NotificacaoDTO;
import br.com.medmentor.mobile.dto.MensagemDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class MensagemMapper {
	
	@Inject
	EscalaMapper escalaMapper;
	
	@Inject
	EmpresaMapper empresaMapper;

    public List<MensagemDTO> toListDto(List<NotificacaoDTO> listaDTO) {
    	List<MensagemDTO> listaMensagemDTO = new ArrayList<MensagemDTO>();
    	
    	if ((listaDTO!=null)&&(listaDTO.size()>0)) {
    		for (NotificacaoDTO dto: listaDTO) {
    			
    			MensagemDTO mensagemDTO = new MensagemDTO();
    			mensagemDTO.setId(dto.getId());
    			mensagemDTO.setDataHora(dto.getDataHora());
    			mensagemDTO.setDescricao(dto.getDescricao());
    			
    			listaMensagemDTO.add(mensagemDTO);
    		}
    	}
   	
    	return listaMensagemDTO;    	
    }
    
    public MensagemDTO toMensagemDto(NotificacaoDTO notificacaoDTO) {
    	if (notificacaoDTO == null) {
    		return null;
    	}
    	
		MensagemDTO mensagemDTO = new MensagemDTO();
		mensagemDTO.setId(notificacaoDTO.getId());
		mensagemDTO.setDataHora(notificacaoDTO.getDataHora());
		mensagemDTO.setDescricao(notificacaoDTO.getDescricao());

    	return mensagemDTO;
    }    

}