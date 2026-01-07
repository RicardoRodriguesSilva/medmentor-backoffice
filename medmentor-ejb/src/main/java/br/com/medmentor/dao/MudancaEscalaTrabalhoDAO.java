package br.com.medmentor.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.medmentor.model.MudancaEscalaTrabalho;

public interface MudancaEscalaTrabalhoDAO {

	MudancaEscalaTrabalho create(MudancaEscalaTrabalho mudancaEscalaTrabalho) throws SQLException;
	
	MudancaEscalaTrabalho findById(Integer id) throws SQLException;
	
	MudancaEscalaTrabalho update(MudancaEscalaTrabalho mudancaEscalaTrabalho) throws SQLException;
	
	void delete(Integer id) throws SQLException;
	
	List<MudancaEscalaTrabalho> findAll() throws SQLException;	
}