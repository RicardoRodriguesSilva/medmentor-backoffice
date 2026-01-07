package br.com.medmentor.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.medmentor.dao.AcaoDAO;
import br.com.medmentor.model.Acao;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class AcaoDAOImpl implements AcaoDAO {

    @Resource(lookup = "java:/jdbc/medmentorDS")
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }  

    @Override
    public Acao create(Acao acao) throws SQLException {
        String sql = "INSERT INTO \"MED\".ACAO (DESCRICAOACAO) VALUES (?) RETURNING IDACAO";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, acao.getDescricaoAcao());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    acao.setId(rs.getInt(1));
                } else {
                    throw new SQLException("Falha ao obter o ID gerado para Acao.");
                }
            }
            return acao;
        }
    }

    @Override
    public Acao findById(Integer id) throws SQLException {
        String sql = "SELECT IDACAO, DESCRICAOACAO FROM \"MED\".ACAO WHERE IDACAO = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Acao acao = new Acao();
                    acao.setId(rs.getInt("IDACAO"));
                    acao.setDescricaoAcao(rs.getString("DESCRICAOACAO"));
                    return acao;
                }
            }
        }
        return null;
    }

    @Override
    public Acao update(Acao acao) throws SQLException {
        String sql = "UPDATE \"MED\".ACAO SET DESCRICAOACAO = ? WHERE IDACAO = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, acao.getDescricaoAcao());
            stmt.setInt(2, acao.getId());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Atualiza��o da Acao falhou, nenhuma linha afetada para o ID: " + acao.getId());
            }
            return acao;
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM \"MED\".ACAO WHERE IDACAO = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    @Override
    public List<Acao> findAll() throws SQLException {
        List<Acao> listaAcao = new ArrayList<>();
        String sql = "SELECT IDACAO, DESCRICAOACAO FROM \"MED\".ACAO";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Acao acao = new Acao();
                acao.setId(rs.getInt("IDACAO"));
                acao.setDescricaoAcao(rs.getString("DESCRICAOACAO"));
                listaAcao.add(acao);
            }
        }
        return listaAcao;
    }
}