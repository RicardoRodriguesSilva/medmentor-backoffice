package br.com.medmentor.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.medmentor.dao.CidadeDAO;
import br.com.medmentor.dao.UnidadeFederacaoDAO;
import br.com.medmentor.model.Cidade;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named; 

@Named
@ApplicationScoped
public class CidadeDAOImpl implements CidadeDAO {

    @Resource(lookup = "java:/jdbc/medmentorDS")
    private DataSource dataSource;

    @Inject 
    private UnidadeFederacaoDAO unidadeFederacaoDAO;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Cidade create(Cidade cidade) throws SQLException {
        String sql = "INSERT INTO \"MED\".CIDADE (NOMECIDADE, IDUNIDADEFEDERACAO) VALUES (?, ?) RETURNING IDCIDADE";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, cidade.getNomeCidade());
            stmt.setInt(2, cidade.getIdUnidadeFederacao());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cidade.setId(rs.getInt(1));
                } else {
                    throw new SQLException("Falha ao obter o ID gerado para Cidade.");
                }
            }
            if (cidade.getIdUnidadeFederacao() != null) {
                cidade.setUnidadeFederacao(unidadeFederacaoDAO.findById(cidade.getIdUnidadeFederacao()));
            }
            return cidade;
        }
    }

    @Override
    public Cidade findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM \"MED\".CIDADE WHERE IDCIDADE = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cidade cidade = new Cidade(
                            rs.getInt("IDCIDADE"),
                            rs.getString("NOMECIDADE"),
                            rs.getInt("IDUNIDADEFEDERACAO")
                    );
                    cidade.setUnidadeFederacao(unidadeFederacaoDAO.findById(cidade.getIdUnidadeFederacao()));
                    return cidade;
                }
            }
        }
        return null;
    }
    
    @Override
    public List<Cidade> findAll() throws SQLException {
        List<Cidade> cidades = new ArrayList<>();
        String sql = "SELECT * FROM \"MED\".CIDADE ORDER BY NOMECIDADE";
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cidade cidade = new Cidade(
                        rs.getInt("IDCIDADE"),
                        rs.getString("NOMECIDADE"),
                        rs.getInt("IDUNIDADEFEDERACAO")
                );

                cidade.setUnidadeFederacao(unidadeFederacaoDAO.findById(cidade.getIdUnidadeFederacao()));
                cidades.add(cidade);
            }
        }
        return cidades;
    }

    @Override
    public Cidade update(Cidade cidade) throws SQLException {
        String sql = "UPDATE \"MED\".CIDADE SET NOMECIDADE = ?, IDUNIDADEFEDERACAO = ? WHERE IDCIDADE = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cidade.getNomeCidade());
            stmt.setInt(2, cidade.getIdUnidadeFederacao());
            stmt.setInt(3, cidade.getId());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Atualiza��o de Cidade falhou, nenhuma linha afetada.");
            }

            if (cidade.getIdUnidadeFederacao() != null) {
                cidade.setUnidadeFederacao(unidadeFederacaoDAO.findById(cidade.getIdUnidadeFederacao()));
            }
            return cidade;
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM \"MED\".CIDADE WHERE IDCIDADE = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Exclus�o de Cidade falhou, nenhuma linha afetada.");
            }
        }
    }
    
    public List<Cidade> findByUnidadeFederacao(Integer idUnidadeFederacao) throws SQLException {
        List<Cidade> cidades = new ArrayList<>();
        String sql = "SELECT * FROM \"MED\".CIDADE WHERE IDUNIDADEFEDERACAO = ? ORDER BY NOMECIDADE";
        try (Connection conn = dataSource.getConnection();
        	 PreparedStatement stmt = conn.prepareStatement(sql)) {
        		
        	stmt.setInt(1, idUnidadeFederacao);
        	ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Cidade cidade = new Cidade(
                        rs.getInt("IDCIDADE"),
                        rs.getString("NOMECIDADE"),
                        rs.getInt("IDUNIDADEFEDERACAO")
                );

                cidade.setUnidadeFederacao(unidadeFederacaoDAO.findById(cidade.getIdUnidadeFederacao()));
                cidades.add(cidade);
            }
        }
        return cidades;
    }     
    
    public List<Cidade> findByUnidadeFederacaoENome(Integer idUnidadeFederacao, String nome) throws SQLException {
        List<Cidade> cidades = new ArrayList<>();
        String sql = "SELECT * FROM \"MED\".CIDADE WHERE IDUNIDADEFEDERACAO = ? AND NOMECIDADE LIKE ? ORDER BY NOMECIDADE";
        try (Connection conn = dataSource.getConnection();
        	 PreparedStatement stmt = conn.prepareStatement(sql)) {
        		
        	stmt.setInt(1, idUnidadeFederacao);
        	stmt.setString(2, "%" + nome + "%");
        	ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Cidade cidade = new Cidade(
                        rs.getInt("IDCIDADE"),
                        rs.getString("NOMECIDADE"),
                        rs.getInt("IDUNIDADEFEDERACAO")
                );

                cidade.setUnidadeFederacao(unidadeFederacaoDAO.findById(cidade.getIdUnidadeFederacao()));
                cidades.add(cidade);
            }
        }
        return cidades;
    }       
}