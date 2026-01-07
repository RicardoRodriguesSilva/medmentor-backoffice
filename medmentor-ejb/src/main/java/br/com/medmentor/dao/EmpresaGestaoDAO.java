package br.com.medmentor.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.medmentor.model.EmpresaGestao;

public interface EmpresaGestaoDAO {

	EmpresaGestao create(EmpresaGestao empresaGestao) throws SQLException;
	
	EmpresaGestao findById(Integer id) throws SQLException;
	
	EmpresaGestao update(EmpresaGestao empresaGestao) throws SQLException;
	
	void delete(Integer id) throws SQLException;
	
	List<EmpresaGestao> findAll() throws SQLException;
}