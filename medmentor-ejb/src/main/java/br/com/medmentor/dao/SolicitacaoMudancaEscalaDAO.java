package br.com.medmentor.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import br.com.medmentor.model.SolicitacaoMudancaEscala;

public interface SolicitacaoMudancaEscalaDAO {

	SolicitacaoMudancaEscala create(SolicitacaoMudancaEscala solicitacaoMudancaEscala) throws SQLException;
	
	SolicitacaoMudancaEscala findById(Integer id) throws SQLException;
	
	SolicitacaoMudancaEscala update(SolicitacaoMudancaEscala solicitacaoMudancaEscala) throws SQLException;
	
	void delete(Integer id) throws SQLException;
	
	List<SolicitacaoMudancaEscala> findAll() throws SQLException;
	
	List<SolicitacaoMudancaEscala> findByFiltros(Integer idEmpresaUnidadeGestao, LocalDate dataInicio, LocalDate dataFim) throws SQLException;	
	
	List<SolicitacaoMudancaEscala> findByFiltros(Integer idEmpresaProfissional, Integer idEscalaTrabalho, LocalDate dataInicio, LocalDate dataFim) throws SQLException;
}