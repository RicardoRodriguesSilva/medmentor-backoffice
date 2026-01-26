package br.com.medmentor.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.medmentor.dao.SolicitacaoMudancaEscalaDAO;
import br.com.medmentor.model.Empresa;
import br.com.medmentor.model.EmpresaGestao;
import br.com.medmentor.model.EmpresaProfissional;
import br.com.medmentor.model.EmpresaUnidadeGestao;
import br.com.medmentor.model.EscalaTrabalho;
import br.com.medmentor.model.SolicitacaoMudancaEscala;
import br.com.medmentor.model.Pessoa;
import br.com.medmentor.model.PessoaFisica;
import br.com.medmentor.model.PessoaJuridica;
import br.com.medmentor.model.Profissional;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class SolicitacaoMudancaEscalaDAOImpl implements SolicitacaoMudancaEscalaDAO {

	@Resource(lookup = "java:/jdbc/medmentorDS")
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public SolicitacaoMudancaEscala create(SolicitacaoMudancaEscala solicitacaoMudancaEscala) throws SQLException {
		String sql = "insert into \"MED\".solicitacaoMudancaEscala "
				+ "(datahorasolicitacao, datahoraatualizacao, idescalatrabalhosolicitada, idempresaprofissionalsolicitante, bolacatada, bolativa) "
				+ "values (?, ?, ?, ?, ?, ?) RETURNING idsolicitacaoMudancaEscala";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setObject(1, solicitacaoMudancaEscala.getDataHoraSolicitacao());
			stmt.setObject(2, solicitacaoMudancaEscala.getDataHoraAtualizacao());
			
			if (solicitacaoMudancaEscala.getEscalaTrabalhoSolicitada()!=null) {
				stmt.setInt(3, solicitacaoMudancaEscala.getEscalaTrabalhoSolicitada().getId());
			} else {
				stmt.setNull(3, java.sql.Types.INTEGER);
			}
			
			if (solicitacaoMudancaEscala.getEmpresaProfissionalSolicitante()!=null) {
				stmt.setInt(4, solicitacaoMudancaEscala.getEmpresaProfissionalSolicitante().getId());
			} else {
				stmt.setNull(4, java.sql.Types.INTEGER);
			}
			
			stmt.setBoolean(5, solicitacaoMudancaEscala.getBolAcatada());
			stmt.setBoolean(6, true);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					solicitacaoMudancaEscala.setId(rs.getInt(1));
				} else {
					throw new SQLException("Falha ao criar solicitacao mundanca de escala, ID não obtido.");
				}
			}
			return solicitacaoMudancaEscala;
		}
	}

	public SolicitacaoMudancaEscala findById(Integer id) throws SQLException {
		String sql = this.recuperaSolicitacaoMudancaEscalaSQL() + " where idsolicitacaoMudancaEscala = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					SolicitacaoMudancaEscala solicitacaoMudancaEscala = this.recuperaSolicitacaoMudancaEscala(rs);
					return solicitacaoMudancaEscala;
				}
			}
		}
		return null;
	}

	@Override 
	public SolicitacaoMudancaEscala update(SolicitacaoMudancaEscala solicitacaoMudancaEscala) throws SQLException {
		String sql = "update \"MED\".solicitacaoMudancaEscala SET datahorasolicitacao = ?, datahoraatualizacao = ?, idescalatrabalhosolicitada = ?, "
				+ " idempresaprofissionalsolicitante = ?, bolacatada=?, bolativa=? WHERE idsolicitacaomudancaescala = ?";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setObject(1, solicitacaoMudancaEscala.getDataHoraSolicitacao());
			ps.setObject(2, solicitacaoMudancaEscala.getDataHoraAtualizacao());			
			ps.setInt(3, solicitacaoMudancaEscala.getEscalaTrabalhoSolicitada().getId());
			ps.setInt(4, solicitacaoMudancaEscala.getEmpresaProfissionalSolicitante().getId());
			ps.setBoolean(5, solicitacaoMudancaEscala.getBolAcatada());
			ps.setBoolean(6, solicitacaoMudancaEscala.getBolAtiva());
			ps.setInt(7, solicitacaoMudancaEscala.getId());
			
	        int affectedRows = ps.executeUpdate();
	        if (affectedRows == 0) {
	            throw new SQLException("Falha ao atualizar a solicitacao mundanca de escala, nenhuma linha encontrada para o ID: " + solicitacaoMudancaEscala.getId());
	        }
		}
		return solicitacaoMudancaEscala;
	}

	@Override
	public void delete(Integer id) throws SQLException {
		String sql = "delete from \"MED\".solicitacaoMudancaEscala where idsolicitacaoMudancaEscala = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Falha ao deletar solicitacaoMudancaEscala, nenhuma linha encontrada para o ID: " + id);
            }
        }
	}	

	@Override
	public List<SolicitacaoMudancaEscala> findAll() throws SQLException {
        List<SolicitacaoMudancaEscala> listaEscala = new ArrayList<SolicitacaoMudancaEscala>();
        String sql = this.recuperaSolicitacaoMudancaEscalaSQL();
        sql = sql + " order by sme.datahorasolicitacao";
        try (Connection conn = dataSource.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql);
        	ResultSet rs = ps.executeQuery()) {
        	while (rs.next()) {
                SolicitacaoMudancaEscala escala = this.recuperaSolicitacaoMudancaEscala(rs);
                listaEscala.add(escala);
        	}
        }
        return listaEscala;
	}
	
	@Override
	public List<SolicitacaoMudancaEscala> findByFiltros(Integer idEmpresaProfissional, Integer idEscalaTrabalho, LocalDate dataInicio, LocalDate dataFim) throws SQLException {
        
		List<SolicitacaoMudancaEscala> listaEscala = new ArrayList<SolicitacaoMudancaEscala>();
                
        String sql = this.recuperaSolicitacaoMudancaEscalaSQL() + " where 1 = 1 ";
        if ((idEmpresaProfissional !=null)&&(idEmpresaProfissional!=0)) {
        	sql = sql + " AND sme.idempresaprofissionalsolicitante = " + idEmpresaProfissional;
        }        
        
        if ((idEscalaTrabalho !=null)&&(idEscalaTrabalho!=0)) {
        	sql = sql + " AND sme.idescalatrabalhosolicitada = " + idEscalaTrabalho;
        }
        
        if ((dataInicio!=null)&&(dataFim!=null)) {
            sql = sql + " AND sme.datahorasolicitacao between '" + dataInicio + "' and '" + dataFim + "'";
        }        
        
        //sql = sql + " AND esc.bolativo = true ";
        sql = sql + " order by sme.datahorasolicitacao";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					SolicitacaoMudancaEscala solicitacaoMudancaEscala = this.recuperaSolicitacaoMudancaEscala(rs);
					listaEscala.add(solicitacaoMudancaEscala);
				}
			}
		}
        return listaEscala;
	}
	
	
	private String recuperaSolicitacaoMudancaEscalaSQL() {
    	String sql = "SELECT \r\n"
    			+ " sme.idsolicitacaomudancaescala, sme.datahorasolicitacao, sme.idescalatrabalhosolicitada, "
    			+ " sme.idempresaprofissionalsolicitante, sme.datahoraatualizacao, sme.bolacatada, sme.bolativa, "  
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
    			+ "	pes2.idpessoa as idpessoagestora, pes2.nomepessoa as nomepessoagestora,	\r\n"

				+ "	epp3.idempresaprofissional as idempresaprofissionalsolicitante,\r\n"
    			+ "	emp3.idempresa as idempresasolicitante, emp3.nomefantasia as nomefantasiasolicitante,\r\n"
    			+ "	pju3.idpessoajuridica as idpessoajuridicasolicitante, pju3.numerocnpj as numerocnpjsolicitante, pju3.nomerazaosocial as nomerazaosocialsolicitante,\r\n"
    			+ "	pro3.idprofissional as idprofissionalsolicitante ,\r\n"
    			+ "	pfi3.idpessoafisica as idpessoafisicasolicitante, pfi3.numerocpf as numerocpfsolicitante,\r\n"
    			+ "	pes3.idpessoa as idpessoasolicitante, pes3.nomepessoa as nomepessoasolicitante \r\n"
    			
    			+ "FROM \r\n"
    			+ "	\"MED\".solicitacaomudancaescala sme \r\n"
    			+ "	left join \"MED\".escalatrabalho esc 		on esc.idescalatrabalho = sme.idescalatrabalhosolicitada \r\n"
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
    			+ "	inner join \"MED\".pessoa pes2 				on pes2.idpessoa = pju2.idpessoajuridica"
    			
    			
    			+ "	left join \"MED\".empresaprofissional epp3 	on epp3.idempresaprofissional = sme.idempresaprofissionalsolicitante \r\n"
    			+ "	left join \"MED\".empresa emp3				on emp3.idempresa = epp3.idempresaprofissional \r\n"
    			+ "	left join \"MED\".pessoajuridica pju3 		on pju3.idpessoajuridica = emp3.idempresa \r\n"
    			+ "	left join \"MED\".profissional pro3 		on pro3.idprofissional = epp3.idprofissional \r\n"
    			+ "	left join \"MED\".pessoafisica pfi3 		on pfi3.idpessoafisica = pro3.idprofissional \r\n"
    			+ "	left join \"MED\".pessoa pes3 				on pes3.idpessoa = pfi3.idpessoafisica 	\r\n";    	
    	
    	return sql;
    }
    
	private SolicitacaoMudancaEscala recuperaSolicitacaoMudancaEscala(ResultSet rs) throws SQLException {

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
		
		
		Pessoa pessoaSolicitante = new Pessoa();
		pessoaSolicitante.setId(rs.getInt("idpessoasolicitante"));
		pessoaSolicitante.setNomePessoa(rs.getString("nomepessoasolicitante"));
		
		PessoaFisica pessoaFisicaSolicitante = new PessoaFisica();
		pessoaFisicaSolicitante.setId(rs.getInt("idpessoafisicasolicitante"));
		pessoaFisicaSolicitante.setCpf(rs.getString("numerocpfsolicitante"));
		pessoaFisicaSolicitante.setPessoa(pessoaSolicitante);
		
		Profissional profissionalSolicitante = new Profissional();
		profissionalSolicitante.setId(rs.getInt("idprofissionalsolicitante"));
		profissionalSolicitante.setPessoaFisica(pessoaFisicaSolicitante);
		
		PessoaJuridica pessoaJuridicaSolicitante = new PessoaJuridica();
		pessoaJuridicaSolicitante.setId(rs.getInt("idpessoajuridicasolicitante"));
		pessoaJuridicaSolicitante.setCnpj(rs.getString("numerocnpjsolicitante"));
		pessoaJuridicaSolicitante.setRazaoSocial(rs.getString("nomerazaosocialsolicitante"));

		Empresa empresaSolicitante = new Empresa();
		empresaSolicitante.setId(rs.getInt("idempresasolicitante"));
		empresaSolicitante.setNomeFantasia(rs.getString("nomefantasiasolicitante"));	
		empresaSolicitante.setPessoaJuridica(pessoaJuridicaSolicitante);
		
		EmpresaProfissional empresaProfissionalSolicitante = new EmpresaProfissional();
		empresaProfissionalSolicitante.setId(rs.getInt("idempresaprofissionalsolicitante"));
		empresaProfissionalSolicitante.setEmpresa(empresaSolicitante);
		empresaProfissionalSolicitante.setProfissional(profissionalSolicitante);		
		
		SolicitacaoMudancaEscala solicitacaoMudancaEscala = new SolicitacaoMudancaEscala();
		solicitacaoMudancaEscala.setId(rs.getInt("idsolicitacaomudancaescala"));
		solicitacaoMudancaEscala.setBolAcatada(rs.getBoolean("bolAcatada"));
		solicitacaoMudancaEscala.setBolAtiva(rs.getBoolean("bolAtiva"));
		solicitacaoMudancaEscala.setDataHoraAtualizacao(rs.getTimestamp("datahoraAtualizacao").toLocalDateTime());
		solicitacaoMudancaEscala.setDataHoraSolicitacao(rs.getTimestamp("datahorasolicitacao").toLocalDateTime());
		solicitacaoMudancaEscala.setEmpresaProfissionalSolicitante(empresaProfissionalSolicitante);
		solicitacaoMudancaEscala.setEscalaTrabalhoSolicitada(escalaTrabalho);
				
		return solicitacaoMudancaEscala;
	}
}