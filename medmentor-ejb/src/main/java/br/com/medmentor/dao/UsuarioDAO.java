package br.com.medmentor.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.medmentor.model.Usuario;

public interface UsuarioDAO {

    Usuario create(Usuario usuario) throws SQLException;
    
    Usuario findById(Integer id) throws SQLException;
    
    Usuario findByNome(String nome) throws SQLException;
    
    Usuario update(Usuario usuario) throws SQLException;
    
    void delete(Integer id) throws SQLException;
    
    List<Usuario> findAll() throws SQLException;	
    
    Usuario autenticaUsuario(String nome) throws SQLException;
}