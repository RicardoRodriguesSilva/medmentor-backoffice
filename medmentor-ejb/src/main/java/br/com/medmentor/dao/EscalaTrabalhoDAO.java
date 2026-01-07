package br.com.medmentor.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.com.medmentor.model.EscalaTrabalho;

public interface EscalaTrabalhoDAO {

	EscalaTrabalho create(EscalaTrabalho escalaTrabalho) throws SQLException;
	
	EscalaTrabalho findById(Integer id) throws SQLException;
	
	EscalaTrabalho update(EscalaTrabalho escalaTrabalho) throws SQLException;
	
	void delete(Integer id) throws SQLException;
	
	List<EscalaTrabalho> findAll() throws SQLException;
	
	List<EscalaTrabalho> findByDataInicioEDataFim(Date dataInicio, Date DataFim) throws SQLException;
}