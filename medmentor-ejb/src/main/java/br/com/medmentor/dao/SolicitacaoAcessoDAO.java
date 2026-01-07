package br.com.medmentor.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.medmentor.model.SolicitacaoAcesso;

public interface SolicitacaoAcessoDAO {

    SolicitacaoAcesso create(SolicitacaoAcesso solicitacaoAcesso) throws SQLException;
    
    SolicitacaoAcesso findById(Integer id) throws SQLException;
    
    SolicitacaoAcesso update(SolicitacaoAcesso solicitacaoAcesso) throws SQLException;
    
    void delete(Integer id) throws SQLException;
    
    List<SolicitacaoAcesso> findAll() throws SQLException;	
}