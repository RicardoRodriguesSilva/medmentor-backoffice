package br.com.medmentor.dao.impl;

import br.com.medmentor.dao.UnidadeFederacaoDAO;
import br.com.medmentor.model.UnidadeFederacao;
import jakarta.annotation.Resource; // Import correto para Jakarta EE
import jakarta.enterprise.context.ApplicationScoped; // Exemplo de escopo CDI, �til para DAOs
import jakarta.inject.Named;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource; // Import correto para DataSource

@Named 
@ApplicationScoped 
public class UnidadeFederacaoDAOImpl implements UnidadeFederacaoDAO {

    @Resource(lookup = "java:/jdbc/medmentorDS") // Injeta o DataSource configurado no servidor
    private DataSource dataSource;

    // M�todos para setar o DataSource para testes (n�o usado em ambiente EJB real)
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public UnidadeFederacao create(UnidadeFederacao unidadeFederacao) throws SQLException {
        String sql = "INSERT INTO \"MED\".UNIDADEFEDERACAO (IDUNIDADEFEDERACAO, NOMEUNIDADEFEDERACAO, SIGLAUNIDADEFEDERACAO) VALUES (?, ?, ?) RETURNING IDUNIDADEFEDERACAO";
        try (Connection conn = dataSource.getConnection(); // Obt�m a conex�o do pool
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, unidadeFederacao.getIdUnidadeFederacao());
            stmt.setString(2, unidadeFederacao.getNomeUnidadeFederacao());
            stmt.setString(3, unidadeFederacao.getSiglaUnidadeFederacao());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    unidadeFederacao.setIdUnidadeFederacao(rs.getInt(1)); // Se o ID fosse auto-gerado pelo DB, seria setado aqui
                }
            }
            return unidadeFederacao;
        }
    }

    @Override
    public UnidadeFederacao findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM \"MED\".UNIDADEFEDERACAO WHERE IDUNIDADEFEDERACAO = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new UnidadeFederacao(
                            rs.getInt("IDUNIDADEFEDERACAO"),
                            rs.getString("NOMEUNIDADEFEDERACAO"),
                            rs.getString("SIGLAUNIDADEFEDERACAO")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<UnidadeFederacao> findAll() throws SQLException {
        List<UnidadeFederacao> ufs = new ArrayList<>();
        String sql = "SELECT * FROM \"MED\".UNIDADEFEDERACAO ORDER BY NOMEUNIDADEFEDERACAO";
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ufs.add(new UnidadeFederacao(
                        rs.getInt("IDUNIDADEFEDERACAO"),
                        rs.getString("NOMEUNIDADEFEDERACAO"),
                        rs.getString("SIGLAUNIDADEFEDERACAO")
                ));
            }
        }
        return ufs;
    }

    @Override
    public UnidadeFederacao update(UnidadeFederacao unidadeFederacao) throws SQLException {
        String sql = "UPDATE \"MED\".UNIDADEFEDERACAO SET NOMEUNIDADEFEDERACAO = ?, SIGLAUNIDADEFEDERACAO = ? WHERE IDUNIDADEFEDERACAO = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, unidadeFederacao.getNomeUnidadeFederacao());
            stmt.setString(2, unidadeFederacao.getSiglaUnidadeFederacao());
            stmt.setInt(3, unidadeFederacao.getIdUnidadeFederacao());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Atualiza��o de UnidadeFederacao falhou, nenhuma linha afetada.");
            }
            return unidadeFederacao; 
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM \"MED\".UNIDADEFEDERACAO WHERE IDUNIDADEFEDERACAO = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Exclus�o de UnidadeFederacao falhou, nenhuma linha afetada.");
            }
        }
    }
}