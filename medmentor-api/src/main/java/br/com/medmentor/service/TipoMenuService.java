package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.TipoMenuDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface TipoMenuService extends GenericService {
    
    TipoMenuDTO incluirTipoMenu(TipoMenuDTO tipoMenuDTO) throws MedmentorException;
    
    void excluirTipoMenu(Integer id) throws MedmentorException;
    
    void alterarTipoMenu(TipoMenuDTO tipoMenuDTO) throws MedmentorException;
    
    TipoMenuDTO recuperarTipoMenuPorId(Integer id) throws MedmentorException;
    
    List<TipoMenuDTO> recuperarListaTipoMenu() throws MedmentorException;
} 