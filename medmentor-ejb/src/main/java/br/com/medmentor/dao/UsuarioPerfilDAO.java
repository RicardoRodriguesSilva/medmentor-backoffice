package br.com.medmentor.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.medmentor.model.UsuarioPerfil;

public interface UsuarioPerfilDAO {

    UsuarioPerfil create(UsuarioPerfil usuarioPerfil) throws SQLException;
    
    UsuarioPerfil findById(Integer id) throws SQLException;
    
    UsuarioPerfil update(UsuarioPerfil usuarioPerfil) throws SQLException;
    
    void delete(Integer id) throws SQLException;
    
    List<UsuarioPerfil> findAll() throws SQLException;	
    
    List<UsuarioPerfil> findByFiltros(Integer idUsuario, Integer idPerfil) throws SQLException;
}