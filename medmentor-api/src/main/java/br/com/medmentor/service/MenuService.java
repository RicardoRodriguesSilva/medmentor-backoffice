package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.AcessoMenuDTO;
import br.com.medmentor.dto.MenuDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface MenuService extends GenericService {
    
    MenuDTO incluiMenu(MenuDTO menuDTO) throws MedmentorException;
    
    void excluiMenu(Integer id) throws MedmentorException;
    
    void alteraMenu(MenuDTO menuDTO) throws MedmentorException;
    
    MenuDTO recuperaMenuPorId(Integer id) throws MedmentorException;
    
    List<MenuDTO> recuperaListaMenu() throws MedmentorException;
    
    List<AcessoMenuDTO> recuperaMenuPorUsuario(String nome) throws MedmentorException;
    
} 