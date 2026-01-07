package br.com.medmentor.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.medmentor.model.Acao;

public interface AcaoDAO {

    Acao create(Acao acao) throws SQLException;
    
    Acao findById(Integer id) throws SQLException;
    
    Acao update(Acao acao) throws SQLException;
    
    void delete(Integer id) throws SQLException;
    
    List<Acao> findAll() throws SQLException;	
}