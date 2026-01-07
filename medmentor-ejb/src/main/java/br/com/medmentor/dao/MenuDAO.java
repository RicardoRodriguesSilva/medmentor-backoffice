package br.com.medmentor.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.medmentor.dto.AcessoMenuDTO;
import br.com.medmentor.model.Menu;

public interface MenuDAO {

    Menu create(Menu menu) throws SQLException;
    
    Menu findById(Integer id) throws SQLException;
    
    Menu update(Menu menu) throws SQLException;
    
    void delete(Integer id) throws SQLException;
    
    List<Menu> findAll() throws SQLException;	
    
    List<AcessoMenuDTO> recuperaMenuPorUsuario(String nome) throws SQLException;
}