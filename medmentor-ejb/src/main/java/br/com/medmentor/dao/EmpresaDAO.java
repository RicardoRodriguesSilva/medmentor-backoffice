package br.com.medmentor.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.medmentor.model.Empresa;

public interface EmpresaDAO {

	Empresa create(Empresa empresa) throws SQLException;
	
	Empresa findById(Integer id) throws SQLException;
	
	Empresa update(Empresa empresa) throws SQLException;
	
	void delete(Integer id) throws SQLException;
	
	List<Empresa> findAll() throws SQLException;	
}