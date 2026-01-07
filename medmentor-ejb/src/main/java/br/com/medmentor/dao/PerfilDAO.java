package br.com.medmentor.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.medmentor.model.Perfil;

public interface PerfilDAO {

    Perfil create(Perfil perfil) throws SQLException;
    
    Perfil findById(Integer id) throws SQLException;
    
    Perfil update(Perfil perfil) throws SQLException;
    
    void delete(Integer id) throws SQLException;
    
    List<Perfil> findAll() throws SQLException;	
    
    String recuperaPerfilPorUsuario(String nome) throws SQLException;
}