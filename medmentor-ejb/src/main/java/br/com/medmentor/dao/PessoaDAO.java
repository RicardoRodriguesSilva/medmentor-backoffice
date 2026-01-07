package br.com.medmentor.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.medmentor.model.Pessoa;

public interface PessoaDAO {

	Pessoa create(Pessoa pessoa) throws SQLException;
	
	Pessoa findById(Integer id) throws SQLException;
	
	Pessoa update(Pessoa pessoa) throws SQLException;
	
	void delete(Integer id) throws SQLException;
	
	List<Pessoa> findAll() throws SQLException;
}