package br.com.medmentor.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.medmentor.model.TipoMenu;

public interface TipoMenuDAO {

    TipoMenu create(TipoMenu tipoMenu) throws SQLException;
    
    TipoMenu findById(Integer id) throws SQLException;
    
    TipoMenu update(TipoMenu tipoMenu) throws SQLException;
    
    void delete(Integer id) throws SQLException;
    
    List<TipoMenu> findAll() throws SQLException;	
}