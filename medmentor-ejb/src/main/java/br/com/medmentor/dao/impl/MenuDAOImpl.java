package br.com.medmentor.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import br.com.medmentor.dao.MenuDAO;
import br.com.medmentor.dto.AcessoMenuDTO;
import br.com.medmentor.model.Menu;
import br.com.medmentor.model.TipoMenu;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class MenuDAOImpl implements MenuDAO {

	@Resource(lookup = "java:/jdbc/medmentorDS")
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Menu create(Menu menu) throws SQLException {
		String sql = "INSERT INTO \"MED\".MENU (NOMEMENU, DESCRICAOMENU, NOMECAMINHOMENU, IDTIPOMENU, NUMEROORDEM, BOLATIVO) VALUES (?, ?, ?, ?, ?, ?) RETURNING IDMENU";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, menu.getNomeMenu());
			stmt.setString(2, menu.getDescricaoMenu());
			stmt.setString(3, menu.getNomeCaminhoMenu());
			stmt.setInt(4, menu.getTipoMenu().getId());
			stmt.setInt(5, menu.getNumeroOrdem());
			stmt.setBoolean(6, menu.getBolAtivo());

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					menu.setId(rs.getInt(1));
				} else {
					throw new SQLException("Falha ao obter o ID gerado para Menu.");
				}
			}
			return menu;
		}
	}

	@Override
	public Menu findById(Integer id) throws SQLException {
		String sql = "SELECT MEN.IDMENU, MEN.NOMEMENU, MEN.DESCRICAOMENU, MEN.NOMECAMINHOMENU, MEN.IDTIPOMENU, TIP.NOMETIPOMENU, MEN.NUMEROORDEM, MEN.BOLATIVO FROM \"MED\".MENU MEN" +
				"INNER JOIN 'MED'.TIPOMENU TIP ON TIP.IDTIPOMENU = MEN.IDTIPOMENU WHERE IDMENU = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Menu menu = new Menu();
					menu.setId(rs.getInt("IDMENU"));
					menu.setNomeMenu(rs.getString("NOMEMENU"));
					menu.setDescricaoMenu(rs.getString("DESCRICAOMENU"));
					menu.setNomeCaminhoMenu(rs.getString("NOMECAMINHOMENU"));
					
					TipoMenu tipoMenu = new TipoMenu();
					tipoMenu.setId(rs.getInt("IDTIPOMENU"));
					tipoMenu.setNomeTipoMenu(rs.getString("NOMETIPOMENU"));
					menu.setTipoMenu(tipoMenu);
					
					menu.setNumeroOrdem(rs.getInt("NUMEROORDEM"));
					menu.setBolAtivo(rs.getBoolean("BOLATIVO"));
					return menu;
				}
			}
		}
		return null;
	}

	@Override
	public Menu update(Menu menu) throws SQLException {
		String sql = "UPDATE \"MED\".MENU SET NOMEMENU = ?, DESCRICAOMENU = ?, NOMECAMINHOMENU = ?, IDTIPOMENU = ?, NUMEROORDEM = ?, BOLATIVO = ? WHERE IDMENU = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, menu.getNomeMenu());
			stmt.setString(2, menu.getDescricaoMenu());
			stmt.setString(3, menu.getNomeCaminhoMenu());
			stmt.setInt(4, menu.getTipoMenu().getId());
			stmt.setInt(5, menu.getNumeroOrdem());			
			stmt.setBoolean(6, menu.getBolAtivo());
			stmt.setInt(7, menu.getId());
			int affectedRows = stmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException(
						"Atualiza��o do Menu falhou, nenhuma linha afetada para o ID: " + menu.getId());
			}
			return menu;
		}
	}

	@Override
	public void delete(Integer id) throws SQLException {
		String sql = "DELETE FROM \"MED\".MENU WHERE IDMENU = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
		}
	}

	@Override
	public List<Menu> findAll() throws SQLException {
		List<Menu> listaMenu = new ArrayList<>();
		String sql = "SELECT MEN.IDMENU, MEN.NOMEMENU, MEN.DESCRICAOMENU, MEN.NOMECAMINHOMENU, MEN.IDTIPOMENU, TIP.NOMETIPOMENU, MEN.NUMEROORDEM, MEN.BOLATIVO FROM \"MED\".MENU MEN " +
				"INNER JOIN \"MED\".TIPOMENU TIP ON TIP.IDTIPOMENU = MEN.IDTIPOMENU ORDER BY MEN.NOMEMENU, TIP.NOMETIPOMENU";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				Menu menu = new Menu();
				menu.setId(rs.getInt("IDMENU"));
				menu.setNomeMenu(rs.getString("NOMEMENU"));
				menu.setDescricaoMenu(rs.getString("DESCRICAOMENU"));
				menu.setNomeCaminhoMenu(rs.getString("NOMECAMINHOMENU"));
				
				TipoMenu tipoMenu = new TipoMenu();
				tipoMenu.setId(rs.getInt("IDTIPOMENU"));
				tipoMenu.setNomeTipoMenu(rs.getString("NOMETIPOMENU"));
				menu.setTipoMenu(tipoMenu);
				
				menu.setNumeroOrdem(rs.getInt("NUMEROORDEM"));
				menu.setBolAtivo(rs.getBoolean("BOLATIVO"));
				listaMenu.add(menu);
			}
		}
		return listaMenu;
	}

	@Override
	public List<AcessoMenuDTO> recuperaMenuPorUsuario(String nome) throws SQLException {
		String sql = "select                                                            	           	"
				+ "	pes.nomepessoa,                                                         	       	"
				+ "	usu.nomeusuario,                                                            	   	"
				+ "	usu.bolativo,	                                                               	   	"
				+ "	per.nomeperfil, per.descricaoperfil, per.bolativo,                             	   	"
				+ "	men.nomemenu, men.descricaomenu, men.nomecaminhomenu, men.bolativo,             	"
				+ "	men.idtipomenu, men.numeroordem, tim.nometipomenu,					           		"
				+ "	aca.descricaoacao                                                              		"
				+ "from	                                                                           		"
				+ "	\"MED\".usuario usu                                                              	"
				+ "	inner join \"MED\".usuarioperfil upe	on upe.idusuario = usu.idusuario           	"
				+ "	inner join \"MED\".perfil per 			on per.idperfil = upe.idperfil         		"	
				+ "	inner join \"MED\".perfilmenuacao pea 	on pea.idperfil = per.idperfil         		"
				+ "	inner join \"MED\".menu men 			on men.idmenu = pea.idmenu                 	"
				+ "	inner join \"MED\".tipomenu tim 		on tim.idtipomenu = men.idtipomenu         	"
				+ "	inner join \"MED\".acao aca 			on aca.idacao = pea.idacao                 	"
				+ "	inner join \"MED\".pessoafisica pef 	on pef.idpessoafisica = usu.idpessoafisica 	"
				+ "	inner join \"MED\".pessoa pes 			on pes.idpessoa = pef.idpessoafisica   		"
				+ "where                                                                           		"
				+ "	1=1                                                                            		"
				+ "	and usu.nomeusuario = ?                                                        		";

		Map<String, AcessoMenuDTO> mapaMenu = new HashMap<String, AcessoMenuDTO>();
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, nome);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {

					if (!mapaMenu.containsKey(rs.getString("nomemenu"))) {
						AcessoMenuDTO acessoMenuDTO = new AcessoMenuDTO();
						acessoMenuDTO.setNomeMenu(rs.getString("nomemenu"));
						acessoMenuDTO.setTipoMenu(rs.getString("nometipomenu"));
						acessoMenuDTO.setOrdem(rs.getInt("numeroordem"));
						acessoMenuDTO.setDescricaoMenu(rs.getString("descricaomenu"));
						acessoMenuDTO.setNomeCaminhoMenu(rs.getString("nomecaminhomenu"));

						if (rs.getString("descricaoacao") != null) {
							acessoMenuDTO.getPermissoes().add(rs.getString("descricaoacao"));
						}
						mapaMenu.put(acessoMenuDTO.getNomeMenu(), acessoMenuDTO);
					} else {

						if (rs.getString("descricaoacao") != null) {
							mapaMenu.get(rs.getString("nomemenu")).getPermissoes().add(rs.getString("descricaoacao"));
						}
					}
				}
			}
		}
		
		List<AcessoMenuDTO> listaAcessoMenuDTO = new ArrayList<AcessoMenuDTO>();
		if (mapaMenu.size()>0)
			listaAcessoMenuDTO = new ArrayList<AcessoMenuDTO>(mapaMenu.values());
		
		return listaAcessoMenuDTO;
	}

}