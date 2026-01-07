package br.com.medmentor.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.medmentor.dao.MudancaEscalaTrabalhoDAO;
import br.com.medmentor.model.MudancaEscalaTrabalho;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class MudancaEscalaTrabalhoDAOImpl implements MudancaEscalaTrabalhoDAO {

	@Resource(lookup = "java:/jdbc/medmentorDS")
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public MudancaEscalaTrabalho create(MudancaEscalaTrabalho mudancaEscalaTrabalho) throws SQLException {
		String sql = "INSERT INTO \"MED\".MUDANCAESCALATRABALHO "
				+ "(IDESCALATRABALHO, DATAHORASOLICITACAO, DATAHORAMUDANCA, IDEMPRESAPROFISSIONALUNIDADEGESTAOANTERIOR, IDEMPRESAPROFISSIONALUNIDADEGESTAOATUAL) "
				+ "VALUES (?, ?, ?, ?, ?) RETURNING IDMUDANCAESCALATRABALHO";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, mudancaEscalaTrabalho.getIdEscalaTrabalho());
			stmt.setObject(2, mudancaEscalaTrabalho.getDataHoraSolicitacao());
			stmt.setObject(3, mudancaEscalaTrabalho.getDataHoraMudanca());
			stmt.setInt(4, mudancaEscalaTrabalho.getIdEmpresaProfissionalUnidadeGestaoAnterior());
			stmt.setInt(5, mudancaEscalaTrabalho.getIdEmpresaProfissionalUnidadeGestaoAtual());

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					mudancaEscalaTrabalho.setIdMudancaEscalaTrabalho(rs.getInt(1));
				} else {
					throw new SQLException("Falha ao criar MudancaEscalaTrabalho, ID não obtido.");
				}
			}
			return mudancaEscalaTrabalho;
		}
	}

	public MudancaEscalaTrabalho findById(Integer id) throws SQLException {
		String sql = "SELECT IDMUDANCAESCALATRABALHO, IDESCALATRABALHO, DATAHORASOLICITACAO, DATAHORAMUDANCA, IDEMPRESAPROFISSIONALUNIDADEGESTAOANTERIOR, IDEMPRESAPROFISSIONALUNIDADEGESTAOATUAL "
				+ "FROM \"MED\".MUDANCAESCALATRABALHO WHERE IDMUDANCAESCALATRABALHO = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					MudancaEscalaTrabalho mudancaEscalaTrabalho = new MudancaEscalaTrabalho();
					mudancaEscalaTrabalho.setIdMudancaEscalaTrabalho(rs.getInt("IDMUDANCAESCALATRABALHO"));
					mudancaEscalaTrabalho.setIdEscalaTrabalho(rs.getInt("IDESCALATRABALHO"));
					mudancaEscalaTrabalho.setDataHoraSolicitacao(rs.getObject("DATAHORASOLICITACAO", LocalDateTime.class));
					mudancaEscalaTrabalho.setDataHoraMudanca(rs.getObject("DATAHORAMUDANCA", LocalDateTime.class));
					mudancaEscalaTrabalho.setIdEmpresaProfissionalUnidadeGestaoAnterior(
							rs.getInt("IDEMPRESAPROFISSIONALUNIDADEGESTAOANTERIOR"));
					mudancaEscalaTrabalho.setIdEmpresaProfissionalUnidadeGestaoAtual(
							rs.getInt("IDEMPRESAPROFISSIONALUNIDADEGESTAOATUAL"));
					return mudancaEscalaTrabalho;
				}
			}
		}
		return null;
	}

	@Override
	public MudancaEscalaTrabalho update(MudancaEscalaTrabalho mudancaEscalaTrabalho) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) throws SQLException {
		String sql = "DELETE FROM \"MED\".MUDANCAESCALATRABALHO WHERE IDMUDANCAESCALATRABALHO = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Falha ao deletar escala de trabalho, nenhuma linha encontrada para o ID: " + id);
            }
        }
	}

	@Override
	public List<MudancaEscalaTrabalho> findAll() throws SQLException {
		List<MudancaEscalaTrabalho> listaMudanca = new ArrayList<MudancaEscalaTrabalho>();
		String sql = "SELECT IDMUDANCAESCALATRABALHO, IDESCALATRABALHO, DATAHORASOLICITACAO, DATAHORAMUDANCA, IDEMPRESAPROFISSIONALUNIDADEGESTAOANTERIOR, IDEMPRESAPROFISSIONALUNIDADEGESTAOATUAL "
				+ "FROM \"MED\".MUDANCAESCALATRABALHO WHERE IDMUDANCAESCALATRABALHO = ?";
        try (Connection conn = dataSource.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql);
        	ResultSet rs = ps.executeQuery()) {
        	while (rs.next()) {
                MudancaEscalaTrabalho mudanca = new MudancaEscalaTrabalho();
                mudanca.setIdMudancaEscalaTrabalho(rs.getInt("IDMUDANCAESCALATRABALHO"));
                mudanca.setDataHoraMudanca(rs.getTimestamp("DATAHORAMUDANCA").toLocalDateTime());
                mudanca.setDataHoraSolicitacao(rs.getTimestamp("DATAHORASOLICITACAO").toLocalDateTime());
                mudanca.setIdEmpresaProfissionalUnidadeGestaoAnterior(rs.getInt("IDEMPRESAPROFISSIONALUNIDADEGESTAOANTERIOR"));
                mudanca.setIdEmpresaProfissionalUnidadeGestaoAtual(rs.getInt("IDEMPRESAPROFISSIONALUNIDADEGESTAOATUAL"));
                mudanca.setIdEscalaTrabalho(rs.getInt("IDESCALATRABALHO"));
                listaMudanca.add(mudanca);        	
        	}
        }
        return listaMudanca;		
	}
}