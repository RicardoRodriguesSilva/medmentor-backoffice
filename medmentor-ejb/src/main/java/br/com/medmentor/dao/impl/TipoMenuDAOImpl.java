package br.com.medmentor.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.medmentor.dao.TipoMenuDAO;
import br.com.medmentor.model.TipoMenu;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class TipoMenuDAOImpl implements TipoMenuDAO {

	@Resource(lookup = "java:/jdbc/medmentorDS")
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public TipoMenu create(TipoMenu tipoMenu) throws SQLException {
		String sql = "INSERT INTO \"MED\".TIPOMENU (NOMETIPOMENU, BOLATIVO) VALUES (?, ?) RETURNING IDTIPOMENU";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, tipoMenu.getNomeTipoMenu());
			stmt.setBoolean(2, tipoMenu.getBolAtivo());

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					tipoMenu.setId(rs.getInt(1));
				} else {
					throw new SQLException("Falha ao obter o ID gerado para TipoMenu.");
				}
			}
			return tipoMenu;
		}
	}

	@Override
	public TipoMenu findById(Integer id) throws SQLException {
		String sql = "SELECT IDTIPOMENU, NOMETIPOMENU, BOLATIVO FROM \"MED\".TIPOMENU WHERE IDTIPOMENU = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					TipoMenu tipoMenu = new TipoMenu();
					tipoMenu.setId(rs.getInt("IDTIPOMENU"));
					tipoMenu.setNomeTipoMenu(rs.getString("NOMETIPOMENU"));
					tipoMenu.setBolAtivo(rs.getBoolean("BOLATIVO"));
					return tipoMenu;
				}
			}
		}
		return null;
	}

	@Override
	public TipoMenu update(TipoMenu tipoMenu) throws SQLException {
		String sql = "UPDATE \"MED\".TIPOMENU SET NOMETIPOMENU = ?, BOLATIVO = ? WHERE IDTIPOMENU = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, tipoMenu.getNomeTipoMenu());	
			stmt.setBoolean(2, tipoMenu.getBolAtivo());
			stmt.setInt(3, tipoMenu.getId());
			int affectedRows = stmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException(
						"Atualiza��o do TipoMenu falhou, nenhuma linha afetada para o ID: " + tipoMenu.getId());
			}
			return tipoMenu;
		}
	}

	@Override
	public void delete(Integer id) throws SQLException {
		String sql = "DELETE FROM \"MED\".TIPOMENU WHERE IDTIPOMENU = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
		}
	}

	@Override
	public List<TipoMenu> findAll() throws SQLException {
		List<TipoMenu> listaTipoMenu = new ArrayList<>();
		String sql = "SELECT IDTIPOMENU, NOMETIPOMENU, BOLATIVO FROM \"MED\".TIPOMENU ORDER BY NOMETIPOMENU";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				TipoMenu tipoMenu = new TipoMenu();
				tipoMenu.setId(rs.getInt("IDTIPOMENU"));
				tipoMenu.setNomeTipoMenu(rs.getString("NOMETIPOMENU"));
				tipoMenu.setBolAtivo(rs.getBoolean("BOLATIVO"));
				listaTipoMenu.add(tipoMenu);
			}
		}
		return listaTipoMenu;
	}

}