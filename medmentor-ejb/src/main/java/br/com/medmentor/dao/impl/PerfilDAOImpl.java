package br.com.medmentor.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.medmentor.dao.PerfilDAO;
import br.com.medmentor.model.Perfil;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class PerfilDAOImpl implements PerfilDAO {

    @Resource(lookup = "java:/jdbc/medmentorDS")
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    } 

    @Override
    public Perfil create(Perfil perfil) throws SQLException {
        String sql = "INSERT INTO \"MED\".PERFIL (NOMEPERFIL, DESCRICAOPERFIL, BOLATIVO) VALUES (?, ?, ?) RETURNING IDPERFIL";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, perfil.getNomePerfil());
            stmt.setString(2, perfil.getDescricaoPerfil());
            stmt.setBoolean(3, perfil.getBolAtivo());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    perfil.setId(rs.getInt(1));
                } else {
                    throw new SQLException("Falha ao obter o ID gerado para Perfil.");
                }
            }
            return perfil;
        }
    }

    @Override
    public Perfil findById(Integer id) throws SQLException {
        String sql = "SELECT IDPERFIL, NOMEPERFIL, DESCRICAOPERFIL, BOLATIVO FROM \"MED\".PERFIL WHERE IDPERFIL = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Perfil perfil = new Perfil();
                    perfil.setId(rs.getInt("IDPERFIL"));
                    perfil.setNomePerfil(rs.getString("NOMEPERFIL"));
                    perfil.setDescricaoPerfil(rs.getString("DESCRICAOPERFIL"));
                    perfil.setBolAtivo(rs.getBoolean("BOLATIVO"));
                    return perfil;
                }
            }
        }
        return null;
    }

    @Override
    public Perfil update(Perfil perfil) throws SQLException {
        String sql = "UPDATE \"MED\".PERFIL SET NOMEPERFIL = ?, DESCRICAOPERFIL = ?, BOLATIVO = ? WHERE IDPERFIL = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, perfil.getNomePerfil());
            stmt.setString(2, perfil.getDescricaoPerfil());
            stmt.setBoolean(3, perfil.getBolAtivo());
            stmt.setInt(4, perfil.getId());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Atualiza��o do Perfil falhou, nenhuma linha afetada para o ID: " + perfil.getId());
            }
            return perfil;
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM \"MED\".PERFIL WHERE IDPERFIL = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    @Override
    public List<Perfil> findAll() throws SQLException {
        List<Perfil> listaPerfil = new ArrayList<>();
        String sql = "SELECT IDPERFIL, NOMEPERFIL, DESCRICAOPERFIL, BOLATIVO FROM \"MED\".PERFIL";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Perfil perfil = new Perfil();
                perfil.setId(rs.getInt("IDPERFIL"));
                perfil.setNomePerfil(rs.getString("NOMEPERFIL"));
                perfil.setDescricaoPerfil(rs.getString("DESCRICAOPERFIL"));
                perfil.setBolAtivo(rs.getBoolean("BOLATIVO"));
                listaPerfil.add(perfil);
            }
        }
        return listaPerfil; 
    }
    
    @Override
    public String recuperaPerfilPorUsuario(String nome) throws SQLException {
		String sql = "select                                                                       "
				+ "	usu.nomeusuario,                                                               "
				+ "	usu.bolativo,	                                                               "
				+ "	per.nomeperfil, per.descricaoperfil, per.bolativo                              "
				+ "from	                                                                           "
				+ "	\"MED\".usuario usu                                                            "
				+ "	inner join \"MED\".usuarioperfil upe	on upe.idusuario = usu.idusuario       "
				+ "	inner join \"MED\".perfil per 			on per.idperfil = upe.idperfil         "
				+ "where                                                                           "
				+ "	1=1                                                                            "
				+ "	and usu.nomeusuario = ?                                                        ";

		String nomePerfil = "";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, nome);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {					
					nomePerfil = rs.getString("nomeperfil");
				}
			}
		}
	
		return nomePerfil;
	}    
}