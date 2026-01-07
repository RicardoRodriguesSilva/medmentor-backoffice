package br.com.medmentor.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.medmentor.model.EmpresaProfissional;

public interface EmpresaProfissionalDAO {

	EmpresaProfissional create(EmpresaProfissional empresaProfissional) throws SQLException;
	
	EmpresaProfissional findById(Integer id) throws SQLException;
	
	List<EmpresaProfissional> findAll() throws SQLException;
	
	EmpresaProfissional update(EmpresaProfissional empresaProfissional) throws SQLException;
	
	void delete(Integer idEmpresaProfissional) throws SQLException;
	
	EmpresaProfissional findByProfissional(Integer idProfissional) throws SQLException;
}