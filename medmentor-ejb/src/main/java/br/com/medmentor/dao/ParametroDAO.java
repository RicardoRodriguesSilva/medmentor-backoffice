package br.com.medmentor.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.medmentor.model.Parametro;

public interface ParametroDAO {

    Parametro create(Parametro Parametro) throws SQLException;
    
    Parametro findById(Integer id) throws SQLException;
    
    Parametro update(Parametro Parametro) throws SQLException;
    
    void delete(Integer id) throws SQLException;
    
    List<Parametro> findAll() throws SQLException;	
}