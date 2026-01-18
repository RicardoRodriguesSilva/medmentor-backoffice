package br.com.medmentor.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.medmentor.dao.GeracaoEscalaDAO;
import br.com.medmentor.model.Empresa;
import br.com.medmentor.model.EmpresaGestao;
import br.com.medmentor.model.EmpresaUnidadeGestao;
import br.com.medmentor.model.GeracaoEscala;
import br.com.medmentor.model.Pessoa;
import br.com.medmentor.model.PessoaJuridica;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class GeracaoEscalaDAOImpl implements GeracaoEscalaDAO {

	@Resource(lookup = "java:/jdbc/medmentorDS")
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public GeracaoEscala create(GeracaoEscala geracaoEscala) throws SQLException {
		String sql = "insert into \"MED\".geracaoescala "
				+ "(idempresaunidadegestao, datageracao) "
				+ "values (?, ?) RETURNING idgeracaoescala";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, geracaoEscala.getEmpresaUnidadeGestao().getId());
			stmt.setDate(2, Date.valueOf(geracaoEscala.getDataGeracao()));
			
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					geracaoEscala.setId(rs.getInt(1));
				} else {
					throw new SQLException("Falha ao criar GeracaoEscala, ID não obtido.");
				}
			}
			return geracaoEscala;
		}
	}

	public GeracaoEscala findById(Integer id) throws SQLException {
		String sql = this.recuperaGeracaoEscalaSQL() + " where idgeracaoescala = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					GeracaoEscala geracaoEscala = this.recuperaGeracaoEscala(rs);
					return geracaoEscala;
				}
			}
		}
		return null;
	}

	@Override
	public GeracaoEscala update(GeracaoEscala geracaoEscala) throws SQLException {
		String sql = "update \"MED\".geracaoescala SET idempresaunidadegestao=?, datageracao=? WHERE idgeracaoescala = ?";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, geracaoEscala.getEmpresaUnidadeGestao().getId());
	        ps.setDate(2, Date.valueOf(geracaoEscala.getDataGeracao()));
	        ps.setInt(7, geracaoEscala.getId());

	        int affectedRows = ps.executeUpdate();
	        if (affectedRows == 0) {
	            throw new SQLException("Falha ao atualizar geracao escala, nenhuma linha encontrada para o ID: " + geracaoEscala.getId());
	        }
		}
		return geracaoEscala;
	}

	@Override
	public void delete(Integer id) throws SQLException {
		String sql = "delete from \"MED\".geracaoescala where idgeracaoescala = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Falha ao deletar geracao escala, nenhuma linha encontrada para o ID: " + id);
            }
        }
	}

	@Override
	public List<GeracaoEscala> findAll() throws SQLException {
        List<GeracaoEscala> listaEscala = new ArrayList<GeracaoEscala>();
        String sql = this.recuperaGeracaoEscalaSQL();
        try (Connection conn = dataSource.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql);
        	ResultSet rs = ps.executeQuery()) {
        	while (rs.next()) {
                GeracaoEscala escala = this.recuperaGeracaoEscala(rs);
                listaEscala.add(escala);
        	}
        }
        return listaEscala;
	}
	
	@Override
	public List<GeracaoEscala> findByFiltros(Integer idEmpresaUnidadeGestao, LocalDate dataInicio, LocalDate dataFim) throws SQLException {
        List<GeracaoEscala> listaEscala = new ArrayList<GeracaoEscala>();        
        
        String sql = this.recuperaGeracaoEscalaSQL() + " where 1 = 1 ";
        if ((idEmpresaUnidadeGestao !=null)&&(idEmpresaUnidadeGestao!=0)) {
        	sql = sql + " AND ger.idEmpresaUnidadeGestao = " + idEmpresaUnidadeGestao;
        }
        
        if ((dataInicio!=null)&&(dataFim!=null)) {
            sql = sql + " AND ger.datageracao between '" + dataInicio + "' and '" + dataFim + "'";
        }
        
        sql = sql + " order by ger.datageracao ";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					GeracaoEscala geracaoEscala = this.recuperaGeracaoEscala(rs);
					listaEscala.add(geracaoEscala);
				}
			}
		}
        return listaEscala;
	}	
	
	@Override
	public Boolean isEscalaGeradaByEmpresaUndidadeGestaoEData(Integer idEmpresaUnidadeGestao, LocalDate data) throws SQLException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
		String dataFormatada = formatter.format(data);
		Boolean isEscalaGerada = false;
        String sql = this.recuperaGeracaoEscalaSQL() + " where to_char(ger.datageracao, 'YYYY-MM') = ? and ger.idempresaunidadegestao = ? order by ger.datageracao";
        try (
        	Connection conn = dataSource.getConnection(); 
        	PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, dataFormatada);
			stmt.setInt(2, idEmpresaUnidadeGestao);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					isEscalaGerada = true;
				}
			}
        }
        
        return isEscalaGerada;
	}	
	
	private String recuperaGeracaoEscalaSQL() {
    	String sql = "SELECT \r\n"
    			+ "	ger.idgeracaoescala, ger.datageracao,\r\n"
    			+ "	uni.idempresaunidadegestao,\r\n"
    			+ "	emp1.idempresa as idempresaunidade, emp1.nomefantasia as nomefantasiaunidade,\r\n"
    			+ "	pju1.idpessoajuridica as idpessoajuridicaunidade, pju1.numerocnpj as numerocnpjunidade, pju1.nomerazaosocial as nomerazaosocialunidade,\r\n"
    			+ "	pes1.idpessoa as idpessoaunidade, pes1.nomepessoa as nomepessoaunidade,\r\n"
    			+ "	ges.idempresagestao,\r\n"
    			+ "	emp2.idempresa as idempresagestora, emp2.nomefantasia as nomefantasiagestora,\r\n"
    			+ "	pju2.idpessoajuridica as idpessoajuridicagestora, pju2.numerocnpj as numerocnpjgestora, pju2.nomerazaosocial as nomerazaosocialgestora,\r\n"
    			+ "	pes2.idpessoa as idpessoagestora, pes2.nomepessoa as nomepessoagestora	\r\n"
    			+ "FROM \r\n"
    			+ "	\"MED\".geracaoescala ger\r\n"
    			+ "	inner join \"MED\".empresaunidadegestao uni on uni.idempresaunidadegestao = ger.idempresaunidadegestao \r\n"
    			+ "	inner join \"MED\".empresa emp1				on emp1.idempresa = uni.idempresaunidadegestao  \r\n"
    			+ "	inner join \"MED\".pessoajuridica pju1 		on pju1.idpessoajuridica = emp1.idempresa\r\n"
    			+ "	inner join \"MED\".pessoa pes1 				on pes1.idpessoa = pju1.idpessoajuridica \r\n"
    			+ "	inner join \"MED\".empresagestao ges 		on ges.idempresagestao = uni.idempresagestao \r\n"
    			+ "	inner join \"MED\".empresa emp2				on emp2.idempresa = ges.idempresagestao   \r\n"
    			+ "	inner join \"MED\".pessoajuridica pju2 		on pju2.idpessoajuridica = emp2.idempresa\r\n"
    			+ "	inner join \"MED\".pessoa pes2 				on pes2.idpessoa = pju2.idpessoajuridica";
    	return sql;
    }
    
	private GeracaoEscala recuperaGeracaoEscala(ResultSet rs) throws SQLException {
		
		Pessoa pessoaUnidade = new Pessoa();
		pessoaUnidade.setId(rs.getInt("idpessoaunidade"));
		pessoaUnidade.setNomePessoa(rs.getString("nomepessoaunidade"));
		
		PessoaJuridica pessoaJuridicaUnidade = new PessoaJuridica();
		pessoaJuridicaUnidade.setId(rs.getInt("idpessoajuridicaunidade"));
		pessoaJuridicaUnidade.setCnpj(rs.getString("numerocnpjunidade"));
		pessoaJuridicaUnidade.setRazaoSocial(rs.getString("nomerazaosocialunidade"));
		pessoaJuridicaUnidade.setPessoa(pessoaUnidade);
		
		Empresa empresaUnidade = new Empresa();
		empresaUnidade.setId(rs.getInt("idempresaunidade"));
		empresaUnidade.setNomeFantasia(rs.getString("nomefantasiaunidade"));	
		empresaUnidade.setPessoaJuridica(pessoaJuridicaUnidade);
		
		Pessoa pessoaGestora = new Pessoa();
		pessoaGestora.setId(rs.getInt("idpessoagestora"));
		pessoaGestora.setNomePessoa(rs.getString("nomepessoagestora"));
		
		PessoaJuridica pessoaJuridicaGestora = new PessoaJuridica();
		pessoaJuridicaGestora.setId(rs.getInt("idpessoajuridicagestora"));
		pessoaJuridicaGestora.setCnpj(rs.getString("numerocnpjgestora"));
		pessoaJuridicaGestora.setRazaoSocial(rs.getString("nomerazaosocialgestora"));
		pessoaJuridicaGestora.setPessoa(pessoaUnidade);
		
		Empresa empresaGestora = new Empresa();
		empresaGestora.setId(rs.getInt("idempresagestora"));
		empresaGestora.setNomeFantasia(rs.getString("nomefantasiagestora"));	
		empresaGestora.setPessoaJuridica(pessoaJuridicaUnidade);				
		
		EmpresaGestao empresaGestao = new EmpresaGestao();
		empresaGestao.setId(rs.getInt("idempresagestao"));
		empresaGestao.setEmpresa(empresaGestora);
		
		EmpresaUnidadeGestao empresaUnidadeGestao = new EmpresaUnidadeGestao();
		empresaUnidadeGestao.setId(rs.getInt("idempresaunidadegestao"));
		empresaUnidadeGestao.setEmpresa(empresaUnidade);
		empresaUnidadeGestao.setEmpresaGestora(empresaGestao);
		
		GeracaoEscala geracaoEscala = new GeracaoEscala();
		geracaoEscala.setId(rs.getInt("idgeracaoescala"));
		geracaoEscala.setEmpresaUnidadeGestao(empresaUnidadeGestao);
		geracaoEscala.setDataGeracao(rs.getDate("datageracao").toLocalDate());
				
		return geracaoEscala;
	}
}