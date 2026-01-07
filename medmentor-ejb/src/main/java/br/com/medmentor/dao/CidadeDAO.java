// src/main/java/br/com/medmentor/dao/CidadeDAOImpl.java
package br.com.medmentor.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.medmentor.model.Cidade; 

public interface CidadeDAO {

    Cidade create(Cidade cidade) throws SQLException;
    
    Cidade findById(Integer id) throws SQLException;
    
    List<Cidade> findAll() throws SQLException;

    Cidade update(Cidade cidade) throws SQLException;

    void delete(Integer id) throws SQLException;
    
    List<Cidade> findByUnidadeFederacao(Integer idUnidadeFederacao) throws SQLException;
    
    List<Cidade> findByUnidadeFederacaoENome(Integer idUnidadeFederacao, String nome) throws SQLException;  
}