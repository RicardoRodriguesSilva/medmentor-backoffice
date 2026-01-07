package br.com.medmentor.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.medmentor.model.Profissional;

public interface ProfissionalDAO {

	Profissional create(Profissional profissional) throws SQLException;
	
	Profissional findById(Integer id) throws SQLException;
	
	Profissional update(Profissional profissional) throws SQLException;
	
	void delete(Integer id) throws SQLException;
	
	List<Profissional> findAll() throws SQLException;
	
	List<Profissional> findByCidade(Integer idCidade) throws SQLException;
	
	List<Profissional> findByNome(String nome) throws SQLException;
}