package br.com.medmentor.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import br.com.medmentor.model.Notificacao;

public interface NotificacaoDAO {

	Notificacao create(Notificacao notificacao) throws SQLException;
	
	Notificacao findById(Integer id) throws SQLException;
	
	Notificacao update(Notificacao notificacao) throws SQLException;
	
	void delete(Integer id) throws SQLException;
	
	List<Notificacao> findAll() throws SQLException;
	
	List<Notificacao> findByFiltros(Integer idEmpresaProfissional, Integer idEmpresaUnidadeGestao, LocalDate dataInicio, LocalDate dataFim) throws SQLException;
	
}