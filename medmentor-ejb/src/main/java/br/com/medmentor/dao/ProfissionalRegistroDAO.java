package br.com.medmentor.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.medmentor.model.ProfissionalRegistro;

public interface ProfissionalRegistroDAO {

	ProfissionalRegistro create(ProfissionalRegistro profissionalRegistro) throws SQLException;
	
	ProfissionalRegistro findById(Integer idProfissional) throws SQLException;
	
	List<ProfissionalRegistro> findAll() throws SQLException;
	
	ProfissionalRegistro update(ProfissionalRegistro profissionalRegistro) throws SQLException;
	
	void delete(Integer idProfissional) throws SQLException;
}