package br.com.medmentor.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import br.com.medmentor.model.EscalaTrabalho;

public interface EscalaTrabalhoDAO {

	EscalaTrabalho create(EscalaTrabalho escalaTrabalho) throws SQLException;
	
	EscalaTrabalho findById(Integer id) throws SQLException;
	
	EscalaTrabalho update(EscalaTrabalho escalaTrabalho) throws SQLException;
	
	void delete(Integer id) throws SQLException;
	
	void deleteAllByEmpresaUndidadeGestaoEData(Integer idEmpresaUnidadeGestao, LocalDate data) throws SQLException;
	
	List<EscalaTrabalho> findAll() throws SQLException;
	
	List<EscalaTrabalho> findByFiltros(Integer idEmpresaProfissional, Integer idEmpresaUnidadeGestao, LocalDate dataInicio, LocalDate dataFim) throws SQLException;
	
	List<EscalaTrabalho> findByFiltros(Integer idEmpresaProfissional, Integer idEmpresaGestao, Integer idEmpresaUnidadeGestao, LocalDate dataInicio, LocalDate dataFim) throws SQLException;
	
	Boolean isEscalaTrabalhoByEmpresaUndidadeGestaoEData(Integer idEmpresaUnidadeGestao, LocalDate data) throws SQLException;
	
	void disponibilzaEscalaTrabalho(Integer id) throws SQLException;
	
	void confirmaEscalaTrabalho(Integer id) throws SQLException;
	
	void cancelaEscalaTrabalho(Integer id) throws SQLException;
}