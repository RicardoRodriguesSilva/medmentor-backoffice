package br.com.medmentor.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import br.com.medmentor.dao.MenuDAO;
import br.com.medmentor.dto.AcessoMenuDTO;
import br.com.medmentor.dto.MenuDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.mapper.MenuMapper;
import br.com.medmentor.model.Menu;
import br.com.medmentor.service.MenuService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless 
public class MenuServiceImpl implements MenuService {

    @Inject 
    private MenuDAO menuDAO;
    
    @Inject
    private MenuMapper menuMapper; 

    public MenuServiceImpl() {
    }

    @Override
    public MenuDTO incluiMenu(MenuDTO menuDTO) throws MedmentorException {
        Menu menu = menuMapper.toEntity(menuDTO);
        
        try {
            menu = menuDAO.create(menu);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }		

        return menuMapper.toDto(menu);
    }

    @Override
    public void excluiMenu(Integer id) throws MedmentorException {
        try {
            menuDAO.delete(id);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }				
    }

    @Override
    public void alteraMenu(MenuDTO menuDTO) throws MedmentorException {
        Menu menu = menuMapper.toEntity(menuDTO);
        try {
            menuDAO.update(menu);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }	
    }

    @Override
    public MenuDTO recuperaMenuPorId(Integer id) throws MedmentorException {
        Menu menu;
        try {
            menu = menuDAO.findById(id);
            if (menu == null) {
                throw new MedmentorException("Menu com ID " + id + " n�o encontrado.");
            }
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }
        
        return menuMapper.toDto(menu);
    }

    @Override
    public List<MenuDTO> recuperaListaMenu() throws MedmentorException {
        List<MenuDTO> listaDto = new ArrayList<>();
        try {
            listaDto = menuMapper.toListDto(menuDAO.findAll());
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }
        return listaDto;
    }

	@Override
	public List<AcessoMenuDTO> recuperaMenuPorUsuario(String nome) throws MedmentorException {
        List<AcessoMenuDTO> listaDto = new ArrayList<>();
        try {
            listaDto = menuDAO.recuperaMenuPorUsuario(nome);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }
        return listaDto;
	}
}