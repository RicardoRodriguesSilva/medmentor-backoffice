package br.com.medmentor.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.medmentor.model.UnidadeFederacao;

public interface UnidadeFederacaoDAO {

    UnidadeFederacao create(UnidadeFederacao uf) throws SQLException;
    
    UnidadeFederacao findById(Integer id) throws SQLException;
    
    List<UnidadeFederacao> findAll() throws SQLException;
    
    UnidadeFederacao update(UnidadeFederacao uf) throws SQLException;
    
    void delete(Integer id) throws SQLException;
}