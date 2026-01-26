package br.com.medmentor.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.medmentor.dao.NotificacaoDAO;
import br.com.medmentor.model.Empresa;
import br.com.medmentor.model.EmpresaGestao;
import br.com.medmentor.model.EmpresaProfissional;
import br.com.medmentor.model.EmpresaUnidadeGestao;
import br.com.medmentor.model.Notificacao;
import br.com.medmentor.model.Pessoa;
import br.com.medmentor.model.PessoaFisica;
import br.com.medmentor.model.PessoaJuridica;
import br.com.medmentor.model.Profissional;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class NotificacaoDAOImpl implements NotificacaoDAO {

	@Resource(lookup = "java:/jdbc/medmentorDS")
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Notificacao create(Notificacao notificacao) throws SQLException {
		String sql = "insert into \"MED\".notificacao "
				+ "(datahora, descricao, idempresaprofissional, idempresaunidadegestao, bollida) "
				+ "values (?, ?, ?, ?, ?) RETURNING idnotificacao";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setObject(1, notificacao.getDataHora());
			stmt.setString(2, notificacao.getDescricao());
			
			if (notificacao.getEmpresaProfissional()!=null) {
				stmt.setInt(3, notificacao.getEmpresaProfissional().getId());
			} else {
				stmt.setNull(3, java.sql.Types.INTEGER);
			}
			
			if (notificacao.getEmpresaUnidadeGestao()!=null) {
				stmt.setInt(4, notificacao.getEmpresaUnidadeGestao().getId());
			} else {
				stmt.setNull(4, java.sql.Types.INTEGER);
			}
			
			stmt.setBoolean(5, notificacao.getBolLida());

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					notificacao.setId(rs.getInt(1));
				} else {
					throw new SQLException("Falha ao criar Notificacao, ID não obtido.");
				}
			}
			return notificacao;
		}
	}

	public Notificacao findById(Integer id) throws SQLException {
		String sql = this.recuperaNotificacaoSQL() + " where idnotificacao = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Notificacao notificacao = this.recuperaNotificacao(rs);
					return notificacao;
				}
			}
		}
		return null;
	}

	@Override 
	public Notificacao update(Notificacao notificacao) throws SQLException {
		String sql = "update \"MED\".notificacao SET datahora=?, descricao=?, idempresaprofissional=?, idempresaunidadegestao=?, " +
				"bollida = ? WHERE idnotificacao = ?";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setObject(1, notificacao.getDataHora());
			ps.setString(2, notificacao.getDescricao());
			ps.setInt(3, notificacao.getEmpresaProfissional().getId());
			ps.setInt(4, notificacao.getEmpresaUnidadeGestao().getId());
	        ps.setBoolean(5, notificacao.getBolLida());
	        ps.setInt(6, notificacao.getId());

	        int affectedRows = ps.executeUpdate();
	        if (affectedRows == 0) {
	            throw new SQLException("Falha ao atualizar a notificacao, nenhuma linha encontrada para o ID: " + notificacao.getId());
	        }
		}
		return notificacao;
	}

	@Override
	public void delete(Integer id) throws SQLException {
		String sql = "delete from \"MED\".notificacao where idnotificacao = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Falha ao deletar notificacao, nenhuma linha encontrada para o ID: " + id);
            }
        }
	}	

	@Override
	public List<Notificacao> findAll() throws SQLException {
        List<Notificacao> listaEscala = new ArrayList<Notificacao>();
        String sql = this.recuperaNotificacaoSQL();
        sql = sql + " order by ntf.datahora";
        try (Connection conn = dataSource.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql);
        	ResultSet rs = ps.executeQuery()) {
        	while (rs.next()) {
                Notificacao escala = this.recuperaNotificacao(rs);
                listaEscala.add(escala);
        	}
        }
        return listaEscala;
	}
	
	@Override
	public List<Notificacao> findByFiltros(Integer idEmpresaProfissional, Integer idEmpresaUnidadeGestao, LocalDate dataInicio, LocalDate dataFim) throws SQLException {
        
		List<Notificacao> listaEscala = new ArrayList<Notificacao>();
                
        String sql = this.recuperaNotificacaoSQL() + " where 1 = 1 ";
        if ((idEmpresaProfissional !=null)&&(idEmpresaProfissional!=0)) {
        	sql = sql + " AND ntf.idEmpresaProfissional = " + idEmpresaProfissional;
        }        
        
        if ((idEmpresaUnidadeGestao !=null)&&(idEmpresaUnidadeGestao!=0)) {
        	sql = sql + " AND ntf.idEmpresaUnidadeGestao = " + idEmpresaUnidadeGestao;
        }
        
        if ((dataInicio!=null)&&(dataFim!=null)) {
            sql = sql + " AND ntf.datahora between '" + dataInicio + "' and '" + dataFim + "'";
        }        
        
        sql = sql + " order by ntf.datahora";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Notificacao notificacao = this.recuperaNotificacao(rs);
					listaEscala.add(notificacao);
				}
			}
		}
        return listaEscala;
	}
	
	private String recuperaNotificacaoSQL() {
    	String sql = "SELECT \r\n"
    			+ "	ntf.idnotificacao, ntf.idempresaprofissional, ntf.idempresaunidadegestao, ntf.datahora, \r\n"
    			+ "	ntf.descricao, ntf.bollida, \r\n"
    			+ "	epp.idempresaprofissional as idempresaprofissionalescala,\r\n"
    			+ "	emp.idempresa, emp.nomefantasia,\r\n"
    			+ "	pju.idpessoajuridica, pju.numerocnpj, pju.nomerazaosocial,\r\n"
    			+ "	pro.idprofissional,\r\n"
    			+ "	pfi.idpessoafisica, pfi.numerocpf,\r\n"
    			+ "	pes.idpessoa, pes.nomepessoa,\r\n"
    			+ "	uni.idempresaunidadegestao,\r\n"
    			+ "	emp1.idempresa as idempresaunidade, emp1.nomefantasia as nomefantasiaunidade,\r\n"
    			+ "	pju1.idpessoajuridica as idpessoajuridicaunidade, pju1.numerocnpj as numerocnpjunidade, pju1.nomerazaosocial as nomerazaosocialunidade,\r\n"
    			+ "	pes1.idpessoa as idpessoaunidade, pes1.nomepessoa as nomepessoaunidade,\r\n"
    			+ "	ges.idempresagestao,\r\n"
    			+ "	emp2.idempresa as idempresagestora, emp2.nomefantasia as nomefantasiagestora,\r\n"
    			+ "	pju2.idpessoajuridica as idpessoajuridicagestora, pju2.numerocnpj as numerocnpjgestora, pju2.nomerazaosocial as nomerazaosocialgestora,\r\n"
    			+ "	pes2.idpessoa as idpessoagestora, pes2.nomepessoa as nomepessoagestora	\r\n"
    			+ "FROM \r\n"
    			+ "	\"MED\".notificacao ntf\r\n"
    			+ "	left join \"MED\".empresaprofissional epp 	on epp.idempresaprofissional = ntf.idempresaprofissional \r\n"
    			+ "	left join \"MED\".empresa emp				on emp.idempresa = epp.idempresaprofissional \r\n"
    			+ "	left join \"MED\".pessoajuridica pju 		on pju.idpessoajuridica = emp.idempresa \r\n"
    			+ "	left join \"MED\".profissional pro 		on pro.idprofissional = epp.idprofissional \r\n"
    			+ "	left join \"MED\".pessoafisica pfi 		on pfi.idpessoafisica = pro.idprofissional \r\n"
    			+ "	left join \"MED\".pessoa pes 				on pes.idpessoa = pfi.idpessoafisica 	\r\n"
    			+ "	inner join \"MED\".empresaunidadegestao uni on uni.idempresaunidadegestao = ntf.idempresaunidadegestao \r\n"
    			+ "	inner join \"MED\".empresa emp1				on emp1.idempresa = uni.idempresaunidadegestao  \r\n"
    			+ "	inner join \"MED\".pessoajuridica pju1 		on pju1.idpessoajuridica = emp1.idempresa\r\n"
    			+ "	inner join \"MED\".pessoa pes1 				on pes1.idpessoa = pju1.idpessoajuridica \r\n"
    			+ "	inner join \"MED\".empresagestao ges 		on ges.idempresagestao = uni.idempresagestao \r\n"
    			+ "	inner join \"MED\".empresa emp2				on emp2.idempresa = ges.idempresagestao   \r\n"
    			+ "	inner join \"MED\".pessoajuridica pju2 		on pju2.idpessoajuridica = emp2.idempresa\r\n"
    			+ "	inner join \"MED\".pessoa pes2 				on pes2.idpessoa = pju2.idpessoajuridica";
    	return sql;
    }
    
	private Notificacao recuperaNotificacao(ResultSet rs) throws SQLException {

		Pessoa pessoa = new Pessoa();
		pessoa.setId(rs.getInt("idpessoa"));
		pessoa.setNomePessoa(rs.getString("nomepessoa"));
		
		PessoaFisica pessoaFisica = new PessoaFisica();
		pessoaFisica.setId(rs.getInt("idpessoafisica"));
		pessoaFisica.setCpf(rs.getString("numerocpf"));
		pessoaFisica.setPessoa(pessoa);
		
		Profissional profissional = new Profissional();
		profissional.setId(rs.getInt("idprofissional"));
		profissional.setPessoaFisica(pessoaFisica);
		
		PessoaJuridica pessoaJuridicaProfissional = new PessoaJuridica();
		pessoaJuridicaProfissional.setId(rs.getInt("idpessoajuridica"));
		pessoaJuridicaProfissional.setCnpj(rs.getString("numerocnpj"));
		pessoaJuridicaProfissional.setRazaoSocial(rs.getString("nomerazaosocial"));

		Empresa empresa = new Empresa();
		empresa.setId(rs.getInt("idempresa"));
		empresa.setNomeFantasia(rs.getString("nomefantasia"));	
		empresa.setPessoaJuridica(pessoaJuridicaProfissional);
		
		EmpresaProfissional empresaProfissional = new EmpresaProfissional();
		empresaProfissional.setId(rs.getInt("idempresaprofissional"));
		empresaProfissional.setEmpresa(empresa);
		empresaProfissional.setProfissional(profissional);
		
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
		
		Notificacao notificacao = new Notificacao();
		notificacao.setId(rs.getInt("idnotificacao"));
		notificacao.setBolLida(rs.getBoolean("bollida"));
		notificacao.setDescricao(rs.getString("descricao"));
		notificacao.setDataHora(rs.getTimestamp("datahora").toLocalDateTime());
		empresaProfissional.setEmpresaGestao(empresaGestao);
		notificacao.setEmpresaProfissional(empresaProfissional);
		notificacao.setEmpresaUnidadeGestao(empresaUnidadeGestao);
				
		return notificacao;
	}
}