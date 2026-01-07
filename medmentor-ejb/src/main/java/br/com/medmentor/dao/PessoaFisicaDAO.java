package br.com.medmentor.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.medmentor.model.PessoaFisica;

public interface PessoaFisicaDAO {

	PessoaFisica create(PessoaFisica pessoaFisica) throws SQLException;
	
	PessoaFisica findById(Integer id) throws SQLException;
	
	PessoaFisica update(PessoaFisica pessoaFisica) throws SQLException;
	
	void delete(Integer id) throws SQLException;
	
	List<PessoaFisica> findByCidade(Integer idCidade) throws SQLException;
	
	List<PessoaFisica> findAll() throws SQLException;	
}