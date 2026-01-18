package br.com.medmentor.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import br.com.medmentor.model.GeracaoEscala;

public interface GeracaoEscalaDAO {

	GeracaoEscala create(GeracaoEscala geracaoEscala) throws SQLException;
	
	GeracaoEscala findById(Integer id) throws SQLException;
	
	GeracaoEscala update(GeracaoEscala geracaoEscala) throws SQLException;
	
	void delete(Integer id) throws SQLException;
	
	List<GeracaoEscala> findAll() throws SQLException;
	
	List<GeracaoEscala> findByFiltros(Integer idEmpresaUnidadeGestao, LocalDate dataInicio, LocalDate dataFim) throws SQLException;
	
	Boolean isEscalaGeradaByEmpresaUndidadeGestaoEData(Integer idEmpresaUnidadeGestao, LocalDate data) throws SQLException;
}