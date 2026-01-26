package br.com.medmentor.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.medmentor.dao.EscalaTrabalhoDAO;
import br.com.medmentor.model.Empresa;
import br.com.medmentor.model.EmpresaGestao;
import br.com.medmentor.model.EmpresaProfissional;
import br.com.medmentor.model.EmpresaUnidadeGestao;
import br.com.medmentor.model.EscalaTrabalho;
import br.com.medmentor.model.Pessoa;
import br.com.medmentor.model.PessoaFisica;
import br.com.medmentor.model.PessoaJuridica;
import br.com.medmentor.model.Profissional;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class EscalaTrabalhoDAOImpl implements EscalaTrabalhoDAO {

	@Resource(lookup = "java:/jdbc/medmentorDS")
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public EscalaTrabalho create(EscalaTrabalho escalaTrabalho) throws SQLException {
		String sql = "insert into \"MED\".escalatrabalho "
				+ "(idempresaprofissional, idempresaunidadegestao, datahoraentrada, datahorasaida, bolativo, bolrealizado, boldisponibilizado) "
				+ "values (?, ?, ?, ?, ?, ?, ?) RETURNING idescalatrabalho";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			if (escalaTrabalho.getEmpresaProfissional()!=null) {
				stmt.setInt(1, escalaTrabalho.getEmpresaProfissional().getId());
			} else {
				stmt.setNull(1, java.sql.Types.INTEGER);
			}
			
			stmt.setInt(2, escalaTrabalho.getEmpresaUnidadeGestao().getId());
			stmt.setObject(3, escalaTrabalho.getDataHoraEntrada());
			stmt.setObject(4, escalaTrabalho.getDataHoraSaida());
			stmt.setBoolean(5, escalaTrabalho.getBolAtivo());
			stmt.setBoolean(6, escalaTrabalho.getBolRealizado());
			stmt.setBoolean(7, escalaTrabalho.getBolDisponibilizado());

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					escalaTrabalho.setId(rs.getInt(1));
				} else {
					throw new SQLException("Falha ao criar EscalaTrabalho, ID não obtido.");
				}
			}
			return escalaTrabalho;
		}
	}

	public EscalaTrabalho findById(Integer id) throws SQLException {
		String sql = this.recuperaEscalaTrabalhoSQL() + " where idescalatrabalho = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					EscalaTrabalho escalaTrabalho = this.recuperaEscalaTrabalho(rs);
					return escalaTrabalho;
				}
			}
		}
		return null;
	}

	@Override
	public EscalaTrabalho update(EscalaTrabalho escalaTrabalho) throws SQLException {
		String sql = "update \"MED\".escalatrabalho SET idempresaprofissional=?, idempresaunidadegestao=?, datahoraentrada=?, datahorasaida=?, " +
				"bolativo = ?, bolrealizado = ?, boldisponibilizado = ? WHERE idescalatrabalho = ?";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, escalaTrabalho.getEmpresaProfissional().getId());
			ps.setInt(2, escalaTrabalho.getEmpresaUnidadeGestao().getId());
	        ps.setTimestamp(3, Timestamp.valueOf(escalaTrabalho.getDataHoraEntrada()));
	        ps.setTimestamp(4, Timestamp.valueOf(escalaTrabalho.getDataHoraSaida()));
	        ps.setBoolean(5, escalaTrabalho.getBolAtivo());
	        ps.setBoolean(6, escalaTrabalho.getBolRealizado());
	        ps.setBoolean(7, escalaTrabalho.getBolDisponibilizado());
	        ps.setInt(8, escalaTrabalho.getId());

	        int affectedRows = ps.executeUpdate();
	        if (affectedRows == 0) {
	            throw new SQLException("Falha ao atualizar escala de trabalho, nenhuma linha encontrada para o ID: " + escalaTrabalho.getId());
	        }
		}
		return escalaTrabalho;
	}

	@Override
	public void delete(Integer id) throws SQLException {
		String sql = "delete from \"MED\".escalatrabalho where idescalatrabalho = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Falha ao deletar escala de trabalho, nenhuma linha encontrada para o ID: " + id);
            }
        }
	}
	
	@Override
	public void deleteAllByEmpresaUndidadeGestaoEData(Integer idEmpresaUnidadeGestao, LocalDate data) throws SQLException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
		String dataFormatada = formatter.format(data);
		String sql = "delete from \"MED\".escalatrabalho where idempresaunidadegestao = ? and to_char(datahoraentrada, 'YYYY-MM') = ? ";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEmpresaUnidadeGestao);
            ps.setString(2, dataFormatada);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Falha ao deletar escala de trabalho, nenhuma linha encontrada para o idEmpresaUnidadeGestao: " + idEmpresaUnidadeGestao + " e data " + data);
            }
        }
	}	

	@Override
	public List<EscalaTrabalho> findAll() throws SQLException {
        List<EscalaTrabalho> listaEscala = new ArrayList<EscalaTrabalho>();
        String sql = this.recuperaEscalaTrabalhoSQL();
        sql = sql + " order by pju2.nomerazaosocial, pju1.nomerazaosocial, esc.datahoraentrada, pes.nomepessoa";
        try (Connection conn = dataSource.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql);
        	ResultSet rs = ps.executeQuery()) {
        	while (rs.next()) {
                EscalaTrabalho escala = this.recuperaEscalaTrabalho(rs);
                listaEscala.add(escala);
        	}
        }
        return listaEscala;
	}
	
	@Override
	public List<EscalaTrabalho> findByFiltros(Integer idEmpresaProfissional, Integer idEmpresaUnidadeGestao, LocalDate dataInicio, LocalDate dataFim) throws SQLException {
        
		List<EscalaTrabalho> listaEscala = new ArrayList<EscalaTrabalho>();
                
        String sql = this.recuperaEscalaTrabalhoSQL() + " where 1 = 1 ";
        if ((idEmpresaProfissional !=null)&&(idEmpresaProfissional!=0)) {
        	sql = sql + " AND esc.idEmpresaProfissional = " + idEmpresaProfissional;
        }        
        
        if ((idEmpresaUnidadeGestao !=null)&&(idEmpresaUnidadeGestao!=0)) {
        	sql = sql + " AND esc.idEmpresaUnidadeGestao = " + idEmpresaUnidadeGestao;
        }
        
        if ((dataInicio!=null)&&(dataFim!=null)) {
            sql = sql + " AND esc.datahoraentrada between '" + dataInicio + "' and '" + dataFim + "'";
        }        
        
        sql = sql + " order by pju2.nomerazaosocial, pju1.nomerazaosocial, esc.datahoraentrada, pes.nomepessoa";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					EscalaTrabalho escalaTrabalho = this.recuperaEscalaTrabalho(rs);
					listaEscala.add(escalaTrabalho);
				}
			}
		}
        return listaEscala;
	}
	
	@Override
	public List<EscalaTrabalho> findByFiltros(Integer idProfissional, Integer idEmpresaGestao,
			Integer idEmpresaUnidadeGestao, LocalDate dataInicio, LocalDate dataFim) throws SQLException {
		List<EscalaTrabalho> listaEscala = new ArrayList<EscalaTrabalho>();
		
		String sql = this.recuperaEscalaTrabalhoSQL() + " where 1 = 1 ";
        if ((idProfissional !=null)&&(idProfissional!=0)) {
        	//Verificar para também escalas de outros da mesma unidade gestão
        	//sql = sql + " AND esc.idEmpresaProfissional = " + idEmpresaProfissional;
        }      
        
        if ((idEmpresaGestao !=null)&&(idEmpresaGestao!=0)) {
        	sql = sql + " AND ges.idEmpresaGestao = " + idEmpresaGestao;
        }
        
        if ((idEmpresaUnidadeGestao !=null)&&(idEmpresaUnidadeGestao!=0)) {
        	sql = sql + " AND esc.idEmpresaUnidadeGestao = " + idEmpresaUnidadeGestao;
        }
        
        if ((dataInicio!=null)&&(dataFim!=null)) {
            sql = sql + " AND esc.datahoraentrada between '" + dataInicio + "' and '" + dataFim + "'";
        }        
        
        sql = sql + " order by pju2.nomerazaosocial, pju1.nomerazaosocial, esc.datahoraentrada, pes.nomepessoa";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					EscalaTrabalho escalaTrabalho = this.recuperaEscalaTrabalho(rs);
					listaEscala.add(escalaTrabalho);
				}
			}
		}
        return listaEscala;
	}	
	
	@Override
	public Boolean isEscalaTrabalhoByEmpresaUndidadeGestaoEData(Integer idEmpresaUnidadeGestao, LocalDate data)
			throws SQLException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
		String dataFormatada = formatter.format(data);
		Boolean isEscalaTrabalho = false;
        String sql = this.recuperaEscalaTrabalhoSQL() + " where to_char(esc.datahoraentrada, 'YYYY-MM') = ? and esc.idempresaunidadegestao = ?";
        try (
        	Connection conn = dataSource.getConnection(); 
        	PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, dataFormatada);
			stmt.setInt(2, idEmpresaUnidadeGestao);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					isEscalaTrabalho = true;
				}
			}
        }
        
        return isEscalaTrabalho;
	}
	
	@Override
	public void disponibilzaEscalaTrabalho(Integer id) throws SQLException {
		String sql = "update \"MED\".escalatrabalho SET boldisponibilizado = true WHERE idescalatrabalho = ?";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);

	        int affectedRows = ps.executeUpdate();
	        if (affectedRows == 0) {
	            throw new SQLException("Falha ao disponibilizar escala de trabalho, nenhuma linha encontrada para o ID: " + id);
	        }
		}	
	}	
	

	@Override
	public void confirmaEscalaTrabalhoEfetuado(Integer id) throws SQLException {
		String sql = "update \"MED\".escalatrabalho SET bolrealizado = true WHERE idescalatrabalho = ?";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);

	        int affectedRows = ps.executeUpdate();
	        if (affectedRows == 0) {
	            throw new SQLException("Falha ao confirmar escala de trabalho, nenhuma linha encontrada para o ID: " + id);
	        }
		}	
	}	
	
	private String recuperaEscalaTrabalhoSQL() {
    	String sql = "SELECT \r\n"
    			+ "	esc.idescalatrabalho, esc.idempresaprofissional, esc.idempresaunidadegestao, esc.datahoraentrada, \r\n"
    			+ "	esc.datahorasaida, esc.bolativo, esc.bolrealizado, esc.boldisponibilizado, \r\n"
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
    			+ "	\"MED\".escalatrabalho esc\r\n"
    			+ "	left join \"MED\".empresaprofissional epp 	on epp.idempresaprofissional = esc.idempresaprofissional \r\n"
    			+ "	left join \"MED\".empresa emp				on emp.idempresa = epp.idempresaprofissional \r\n"
    			+ "	left join \"MED\".pessoajuridica pju 		on pju.idpessoajuridica = emp.idempresa \r\n"
    			+ "	left join \"MED\".profissional pro 		on pro.idprofissional = epp.idprofissional \r\n"
    			+ "	left join \"MED\".pessoafisica pfi 		on pfi.idpessoafisica = pro.idprofissional \r\n"
    			+ "	left join \"MED\".pessoa pes 				on pes.idpessoa = pfi.idpessoafisica 	\r\n"
    			+ "	inner join \"MED\".empresaunidadegestao uni on uni.idempresaunidadegestao = esc.idempresaunidadegestao \r\n"
    			+ "	inner join \"MED\".empresa emp1				on emp1.idempresa = uni.idempresaunidadegestao  \r\n"
    			+ "	inner join \"MED\".pessoajuridica pju1 		on pju1.idpessoajuridica = emp1.idempresa\r\n"
    			+ "	inner join \"MED\".pessoa pes1 				on pes1.idpessoa = pju1.idpessoajuridica \r\n"
    			+ "	inner join \"MED\".empresagestao ges 		on ges.idempresagestao = uni.idempresagestao \r\n"
    			+ "	inner join \"MED\".empresa emp2				on emp2.idempresa = ges.idempresagestao   \r\n"
    			+ "	inner join \"MED\".pessoajuridica pju2 		on pju2.idpessoajuridica = emp2.idempresa\r\n"
    			+ "	inner join \"MED\".pessoa pes2 				on pes2.idpessoa = pju2.idpessoajuridica";
    	return sql;
    }
    
	private EscalaTrabalho recuperaEscalaTrabalho(ResultSet rs) throws SQLException {

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
		
		EscalaTrabalho escalaTrabalho = new EscalaTrabalho();
		escalaTrabalho.setId(rs.getInt("idescalatrabalho"));
		escalaTrabalho.setBolAtivo(rs.getBoolean("bolativo"));
		escalaTrabalho.setBolRealizado(rs.getBoolean("bolrealizado"));
		escalaTrabalho.setBolDisponibilizado(rs.getBoolean("boldisponibilizado"));
		escalaTrabalho.setDataHoraEntrada(rs.getTimestamp("datahoraentrada").toLocalDateTime());
		escalaTrabalho.setDataHoraSaida(rs.getTimestamp("datahorasaida").toLocalDateTime());
		empresaProfissional.setEmpresaGestao(empresaGestao);
		escalaTrabalho.setEmpresaProfissional(empresaProfissional);
		escalaTrabalho.setEmpresaUnidadeGestao(empresaUnidadeGestao);
				
		return escalaTrabalho;
	}
}