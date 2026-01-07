package br.com.medmentor.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.medmentor.dao.PerfilMenuAcaoDAO;
import br.com.medmentor.model.Acao;
import br.com.medmentor.model.Menu;
import br.com.medmentor.model.Perfil;
import br.com.medmentor.model.PerfilMenuAcao;
import br.com.medmentor.model.TipoMenu;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class PerfilMenuAcaoDAOImpl implements PerfilMenuAcaoDAO {

    @Resource(lookup = "java:/jdbc/medmentorDS")
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    } 

    @Override
    public PerfilMenuAcao create(PerfilMenuAcao perfilMenuAcao) throws SQLException {
        String sql = "INSERT INTO \"MED\".PERFILMENUACAO (IDPERFIL, IDMENU, IDACAO) VALUES (?, ?, ?) RETURNING IDPERFILMENUACAO";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, perfilMenuAcao.getPerfil().getId());
            stmt.setInt(2, perfilMenuAcao.getMenu().getId());
            stmt.setInt(3, perfilMenuAcao.getAcao().getId());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    perfilMenuAcao.setId(rs.getInt(1));
                } else {
                    throw new SQLException("Falha ao obter o ID gerado para PerfilMenuAcao.");
                }
            }
            return perfilMenuAcao;
        }
    }

    @Override
    public PerfilMenuAcao findById(Integer id) throws SQLException {
        String sql = 
        		" SELECT 																" +
        		"	pma.IDPERFILMENUACAO, pma.IDPERFIL, pma.IDMENU, pma.IDACAO,			" +
        		"	per.nomeperfil, men.nomemenu, aca.descricaoacao, 					" +
        		"	tip.idtipomenu, tip.nometipomenu 									" +
        		" FROM                                                                  " +
        		"	\"MED\".PERFILMENUACAO pma                                          " +
        		"	inner join \"MED\".acao aca 	on aca.idacao = pma.idacao          " +
        		"	inner join \"MED\".menu men 	on men.idmenu = pma.idmenu          " +
        		"	inner join \"MED\".tipomenu tip	on tip.idtipomenu = men.idtipomenu " +
        		"	inner join \"MED\".perfil per 	on per.idperfil = pma.idperfil      " +
        		" WHERE IDPERFILMENUACAO = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    PerfilMenuAcao perfilMenuAcao = new PerfilMenuAcao();
                    perfilMenuAcao.setId(rs.getInt("IDPERFILMENUACAO"));
                    
                    Acao acao = new Acao();
                    acao.setId(rs.getInt("IDACAO"));
                    acao.setDescricaoAcao(rs.getString("descricaoacao"));
                    perfilMenuAcao.setAcao(acao);
                    
                    Menu menu = new Menu();
                    menu.setId(rs.getInt("IDMENU"));
                    menu.setNomeMenu(rs.getString("nomemenu"));
                    perfilMenuAcao.setMenu(menu);
                    
                    TipoMenu tipoMenu = new TipoMenu();
                    tipoMenu.setId(rs.getInt("idtipomenu"));
                    tipoMenu.setNomeTipoMenu(rs.getString("nometipomenu"));
                    menu.setTipoMenu(tipoMenu);                    
                    
                    Perfil perfil = new Perfil();
                    perfil.setId(rs.getInt("IDPERFIL"));
                    perfil.setNomePerfil(rs.getString("nomeperfil"));
                    perfilMenuAcao.setPerfil(perfil);
                    
                    return perfilMenuAcao;
                }
            }
        }
        return null;
    }

    @Override
    public PerfilMenuAcao update(PerfilMenuAcao perfilMenuAcao) throws SQLException {
        String sql = "UPDATE \"MED\".PERFILMENUACAO SET IDPERFIL = ?, IDMENU = ?, IDACAO = ? WHERE IDPERFILMENUACAO = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, perfilMenuAcao.getPerfil().getId());
            stmt.setInt(2, perfilMenuAcao.getMenu().getId());
            stmt.setInt(3, perfilMenuAcao.getAcao().getId());
            stmt.setInt(4, perfilMenuAcao.getId());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Atualiza��o de PerfilMenuAcao falhou, nenhuma linha afetada para o ID: " + perfilMenuAcao.getId());
            }
            return perfilMenuAcao;
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM \"MED\".PERFILMENUACAO WHERE IDPERFILMENUACAO = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    @Override
    public List<PerfilMenuAcao> findAll() throws SQLException {
        List<PerfilMenuAcao> listaPerfilMenuAcao = new ArrayList<>();
        String sql = 
		" SELECT 																" +
		"	pma.IDPERFILMENUACAO, pma.IDPERFIL, pma.IDMENU, pma.IDACAO,			" +
		"	per.nomeperfil, men.nomemenu, aca.descricaoacao, 					" +
		"	tip.idtipomenu, tip.nometipomenu 									" +
		" FROM                                                                  " +
		"	\"MED\".PERFILMENUACAO pma                                          " +
		"	inner join \"MED\".acao aca 	on aca.idacao = pma.idacao          " +
		"	inner join \"MED\".menu men 	on men.idmenu = pma.idmenu          " +
		"	inner join \"MED\".tipomenu tip	on tip.idtipomenu = men.idtipomenu " +
		"	inner join \"MED\".perfil per 	on per.idperfil = pma.idperfil      ";        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PerfilMenuAcao perfilMenuAcao = new PerfilMenuAcao();
                perfilMenuAcao.setId(rs.getInt("IDPERFILMENUACAO"));
                
                Acao acao = new Acao();
                acao.setId(rs.getInt("IDACAO"));
                acao.setDescricaoAcao(rs.getString("descricaoacao"));
                perfilMenuAcao.setAcao(acao);
                
                Menu menu = new Menu();
                menu.setId(rs.getInt("IDMENU"));
                menu.setNomeMenu(rs.getString("nomemenu"));
                perfilMenuAcao.setMenu(menu);
                
                TipoMenu tipoMenu = new TipoMenu();
                tipoMenu.setId(rs.getInt("idtipomenu"));
                tipoMenu.setNomeTipoMenu(rs.getString("nometipomenu"));
                menu.setTipoMenu(tipoMenu);
                
                Perfil perfil = new Perfil();
                perfil.setId(rs.getInt("IDPERFIL"));
                perfil.setNomePerfil(rs.getString("nomeperfil"));
                perfilMenuAcao.setPerfil(perfil);

                listaPerfilMenuAcao.add(perfilMenuAcao);
            }
        }
        return listaPerfilMenuAcao;
    }
}