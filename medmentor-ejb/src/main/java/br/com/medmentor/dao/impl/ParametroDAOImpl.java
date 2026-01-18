package br.com.medmentor.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.medmentor.dao.ParametroDAO;
import br.com.medmentor.model.Parametro;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class ParametroDAOImpl implements ParametroDAO {

    @Resource(lookup = "java:/jdbc/medmentorDS")
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }  

    @Override
    public Parametro create(Parametro parametro) throws SQLException {
        String sql = "INSERT INTO \"MED\".acao (nomeparametro, valorparametro) VALUES (?) RETURNING idparametro";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, parametro.getNome());
            stmt.setString(2, parametro.getValor());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    parametro.setId(rs.getInt(1));
                } else {
                    throw new SQLException("Falha ao obter o ID gerado para Parametro.");
                }
            }
            return parametro;
        }
    }

    @Override
    public Parametro findById(Integer id) throws SQLException {
        String sql = "SELECT idparametro, nomeparametro, valorparametro FROM \"MED\".parametro WHERE idparametro = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Parametro parametro = new Parametro();
                    parametro.setId(rs.getInt("idparametro"));
                    parametro.setNome(rs.getString("nomeparametro"));
                    parametro.setValor(rs.getString("valorparametro"));
                    return parametro;
                }
            }
        }
        return null;
    }

    @Override
    public Parametro update(Parametro parametro) throws SQLException {
        String sql = "UPDATE \"MED\".parametro SET nomeparametro = ?, valorparametro = ? WHERE idparametro = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, parametro.getNome());
            stmt.setString(2, parametro.getValor());
            stmt.setInt(3, parametro.getId());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Atualiza��o da Parametro falhou, nenhuma linha afetada para o ID: " + parametro.getId());
            }
            return parametro;
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM \"MED\".parametro WHERE idparametro = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    @Override
    public List<Parametro> findAll() throws SQLException {
        List<Parametro> listaParametro = new ArrayList<>();
        String sql = "SELECT idparametro, nomeparametro, valorparametro FROM \"MED\".parametro";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Parametro parametro = new Parametro();
                parametro.setId(rs.getInt("idparametro"));
                parametro.setNome(rs.getString("nomeparametro"));
                parametro.setValor(rs.getString("valorparametro"));
                listaParametro.add(parametro);
            }
        }
        return listaParametro;
    }
}