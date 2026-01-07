package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.AcessoMenuDTO;
import br.com.medmentor.dto.MenuDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface MenuService extends GenericService {
    
    MenuDTO incluirMenu(MenuDTO menuDTO) throws MedmentorException;
    
    void excluirMenu(Integer id) throws MedmentorException;
    
    void alterarMenu(MenuDTO menuDTO) throws MedmentorException;
    
    MenuDTO recuperarMenuPorId(Integer id) throws MedmentorException;
    
    List<MenuDTO> recuperarListaMenu() throws MedmentorException;
    
    List<AcessoMenuDTO> recuperaMenuPorUsuario(String nome) throws MedmentorException;
    
} 