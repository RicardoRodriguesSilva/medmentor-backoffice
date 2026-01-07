package br.com.medmentor.dao;

import java.sql.SQLException;
import java.util.List;
import br.com.medmentor.model.PerfilMenuAcao;

public interface PerfilMenuAcaoDAO {

    PerfilMenuAcao create(PerfilMenuAcao perfilMenuAcao) throws SQLException;
    
    PerfilMenuAcao findById(Integer id) throws SQLException;
    
    PerfilMenuAcao update(PerfilMenuAcao perfilMenuAcao) throws SQLException;
    
    void delete(Integer id) throws SQLException;
    
    List<PerfilMenuAcao> findAll() throws SQLException;	
}