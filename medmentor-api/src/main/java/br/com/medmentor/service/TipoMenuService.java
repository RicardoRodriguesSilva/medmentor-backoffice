package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.TipoMenuDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface TipoMenuService extends GenericService {
    
    TipoMenuDTO incluiTipoMenu(TipoMenuDTO tipoMenuDTO) throws MedmentorException;
    
    void excluiTipoMenu(Integer id) throws MedmentorException;
    
    void alteraTipoMenu(TipoMenuDTO tipoMenuDTO) throws MedmentorException;
    
    TipoMenuDTO recuperaTipoMenuPorId(Integer id) throws MedmentorException;
    
    List<TipoMenuDTO> recuperaListaTipoMenu() throws MedmentorException;
} 