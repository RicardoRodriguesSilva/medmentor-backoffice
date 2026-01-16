package br.com.medmentor.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.medmentor.model.EmpresaUnidadeGestao;

public interface EmpresaUnidadeGestaoDAO {

	EmpresaUnidadeGestao create(EmpresaUnidadeGestao empresaUnidadeGestao) throws SQLException;
	
	EmpresaUnidadeGestao findById(Integer idUnidadeGestao) throws SQLException;
	
	List<EmpresaUnidadeGestao> findAll() throws SQLException;
	
	EmpresaUnidadeGestao update(EmpresaUnidadeGestao empresaUnidadeGestao) throws SQLException;
	
	void delete(Integer idUnidadeGestao) throws SQLException;
	
	List<EmpresaUnidadeGestao> findByIdProfissional(Integer idProfissional) throws SQLException;
}