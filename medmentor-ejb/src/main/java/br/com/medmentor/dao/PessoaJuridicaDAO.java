package br.com.medmentor.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.medmentor.model.PessoaJuridica;

public interface PessoaJuridicaDAO {

	PessoaJuridica create(PessoaJuridica pessoaJuridica) throws SQLException;
	
	PessoaJuridica findById(Integer id) throws SQLException;
	
	PessoaJuridica update(PessoaJuridica pessoaJuridica) throws SQLException;
	
	void delete(Integer id) throws SQLException;
	
	List<PessoaJuridica> findAll() throws SQLException;	
}