package br.com.medmentor.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.medmentor.dao.EmpresaDAO;
import br.com.medmentor.dao.EmpresaGestaoDAO;
import br.com.medmentor.dao.EmpresaUnidadeGestaoDAO;
import br.com.medmentor.enums.TipoPessoa;
import br.com.medmentor.model.Cidade;
import br.com.medmentor.model.Empresa;
import br.com.medmentor.model.EmpresaGestao;
import br.com.medmentor.model.EmpresaUnidadeGestao;
import br.com.medmentor.model.Pessoa;
import br.com.medmentor.model.PessoaJuridica;
import br.com.medmentor.model.UnidadeFederacao;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class EmpresaUnidadeGestaoDAOImpl implements EmpresaUnidadeGestaoDAO {

	@Resource(lookup = "java:/jdbc/medmentorDS")
	private DataSource dataSource;

	@Inject
	private EmpresaDAO empresaDAO; 
	
	@Inject
	private EmpresaGestaoDAO empresaGestaoDAO; 

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public EmpresaUnidadeGestao create(EmpresaUnidadeGestao empresaUnidadeGestao) throws SQLException {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			Empresa empresaUnidadeCriada = empresaDAO.create(empresaUnidadeGestao.getEmpresa());
			empresaUnidadeGestao.setEmpresa(empresaUnidadeCriada);
			empresaUnidadeGestao.setId(empresaUnidadeCriada.getId());

			if (empresaUnidadeGestao.getEmpresaGestora() == null
					|| empresaUnidadeGestao.getEmpresaGestora().getId() == null) {
				throw new IllegalArgumentException(
						"Empresa Gestora deve ser fornecida (com ID) para criar uma Unidade de Gestão.");
			}
			EmpresaGestao gestoraExistente = empresaGestaoDAO
					.findById(empresaUnidadeGestao.getEmpresaGestora().getId());
			if (gestoraExistente == null) {
				throw new SQLException("Empresa Gestora com ID "
						+ empresaUnidadeGestao.getEmpresaGestora().getId() + " não encontrada.");
			}
			empresaUnidadeGestao.setEmpresaGestora(gestoraExistente);

			String sql = "INSERT INTO \"MED\".EMPRESAUNIDADEGESTAO (IDEMPRESAUNIDADEGESTAO, IDEMPRESAGESTAO, numeroplantonistadia, numeroplantonistaperiodo) VALUES (?, ?, ?, ?)";
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, empresaUnidadeGestao.getId());
				stmt.setInt(2, empresaUnidadeGestao.getEmpresaGestora().getId());
				stmt.setInt(3, empresaUnidadeGestao.getNumeroPlantonistaDia());
				stmt.setInt(4, empresaUnidadeGestao.getNumeroPlantonistaPeriodo());
				stmt.executeUpdate(); 
			}

			return empresaUnidadeGestao;
		} catch (SQLException e) {
			throw new SQLException("Erro ao criar UnidadeGestao e sua hierarquia: " + e.getMessage(), e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public EmpresaUnidadeGestao findById(Integer idUnidadeGestao) throws SQLException {
		Connection conn = null;
		EmpresaUnidadeGestao empresaUnidadeGestao = null;
		try {
			conn = dataSource.getConnection();
			String sql = this.recuperaEmpresaGestaoSQL() + " where epu.idempresaunidadegestao = ?";
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, idUnidadeGestao);
				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next()) {
						empresaUnidadeGestao = this.recuperaEmpresaGestao(rs);
						return empresaUnidadeGestao;
					}
				}
			}
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return empresaUnidadeGestao;
	}

	@Override
	public List<EmpresaUnidadeGestao> findAll() throws SQLException {
		List<EmpresaUnidadeGestao> listaUnidadeGestao = new ArrayList<EmpresaUnidadeGestao>();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			String sql = this.recuperaEmpresaGestaoSQL();

			try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {

					EmpresaUnidadeGestao empresaUnidadeGestao = this.recuperaEmpresaGestao(rs);
					listaUnidadeGestao.add(empresaUnidadeGestao);
				}
			}
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return listaUnidadeGestao;
	}

	@Override
	public EmpresaUnidadeGestao update(EmpresaUnidadeGestao empresaUnidadeGestao) throws SQLException {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			
			if (empresaUnidadeGestao.getEmpresa() != null) {
				empresaDAO.update(empresaUnidadeGestao.getEmpresa());
			}

			if (empresaUnidadeGestao.getEmpresaGestora() != null) {
				empresaGestaoDAO.update(empresaUnidadeGestao.getEmpresaGestora());
			}

			String sql = "update \"MED\".empresaunidadegestao set idempresagestao = ?, numeroplantonistadia = ?, numeroplantonistaperiodo = ? where idempresaunidadegestao = ?";
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, empresaUnidadeGestao.getEmpresaGestora().getId()); 
				stmt.setInt(2, empresaUnidadeGestao.getNumeroPlantonistaDia());				
				stmt.setInt(3, empresaUnidadeGestao.getNumeroPlantonistaPeriodo());
				stmt.setInt(4, empresaUnidadeGestao.getId()); // Valor atual da PK				

				int affectedRows = stmt.executeUpdate();
				if (affectedRows == 0) {
					throw new SQLException(
							"Atualização da UnidadeGestao falhou, registro não encontrado ou ID alterado. IDUnidade: "
									+ empresaUnidadeGestao.getId() + ", IDEmpresaGestao: "
									+ empresaUnidadeGestao.getEmpresaGestora().getId());
				}
			}

			return empresaUnidadeGestao;
		} catch (SQLException e) {
			throw new SQLException("Erro ao atualizar UnidadeGestao e sua hierarquia: " + e.getMessage(), e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public void delete(Integer idUnidadeGestao) throws SQLException {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			EmpresaUnidadeGestao empresaUnidadeGestao = findById(idUnidadeGestao);
			if (empresaUnidadeGestao == null) {
				conn.rollback();
				return;
			}

			String sql = "delete from \"MED\".empresaunidadegestao where idempresaunidadegestao = ?";
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, idUnidadeGestao);
				stmt.executeUpdate();
			}

			empresaDAO.delete(empresaUnidadeGestao.getEmpresa().getId());
		} catch (SQLException e) {
			throw new SQLException("Erro ao deletar UnidadeGestao e sua hierarquia: " + e.getMessage(), e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	@Override
	public List<EmpresaUnidadeGestao> findByIdProfissional(Integer idProfissional) throws SQLException {
		List<EmpresaUnidadeGestao> listaUnidadeGestao = new ArrayList<EmpresaUnidadeGestao>();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			String sql = this.recuperaEmpresaGestaoSQL();
			sql = sql + " inner join \"MED\".empresaprofissional epp 	on epp.idempresagestao = ges.idempresagestao ";
			sql = sql + " where epp.idprofissional = " + idProfissional;
			sql = sql + " order by emp1.nomefantasia, emp.nomefantasia ";
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					EmpresaUnidadeGestao empresaUnidadeGestao = this.recuperaEmpresaGestao(rs);
					listaUnidadeGestao.add(empresaUnidadeGestao);
				}
			}
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return listaUnidadeGestao;
	}	
	
	private String recuperaEmpresaGestaoSQL() {
    	String sql = "select "
    			+ "	pes.idpessoa, pes.nomepessoa, pes.codtipopessoa, pes.descricaoendereco, "
    			+ "	pes.descricaocomplemento, pes.descricaobairro, pes.numerocep, pes.idcidade, "
    			+ "	pes.numerocelular, pes.descricaoemail, "
    			+ "	pju.idpessoajuridica, pju.nomerazaosocial, pju.numerocnpj, "
    			+ " epu.idempresaunidadegestao, epu.numeroplantonistadia, epu.numeroplantonistaperiodo, "
    			+ "	emp.idempresa, ges.idempresagestao, emp.nomefantasia, emp.nomeresponsavel, emp1.idempresa as idempreagestora, " 
    			+ " emp1.nomefantasia as nomefantasiagestora, emp1.nomeresponsavel as nomeresponsavelgestora, pju1.nomerazaosocial as nomerazaosocialgestora, "
    			+ "	cid.nomecidade, ufu.idunidadefederacao, ufu.nomeunidadefederacao, ufu.siglaunidadefederacao "
    			+ "from "
    			+ "	\"MED\".pessoa pes "
    			+ "	inner join \"MED\".pessoajuridica pju 	on pju.idpessoajuridica = pes.idpessoa "
    			+ " inner join \"MED\".empresa emp 			on emp.idempresa = pju.idpessoajuridica "
    			+ " inner join \"MED\".empresaunidadegestao epu on epu.idempresaunidadegestao = emp.idempresa "
    			+ " inner join \"MED\".empresagestao ges 	on ges.idempresagestao = epu.idempresagestao " 
    			+ " inner join \"MED\".empresa emp1 		on emp1.idempresa = ges.idempresagestao "
    			+ " inner join \"MED\".pessoajuridica pju1 	on pju1.idpessoajuridica = emp1.idempresa "	
    			+ "	inner join \"MED\".cidade cid 			on cid.idcidade = pes.idcidade "
    			+ "	inner join \"MED\".unidadefederacao ufu on ufu.idunidadefederacao = cid.idunidadefederacao ";
    	return sql;
    }
    
	private EmpresaUnidadeGestao recuperaEmpresaGestao(ResultSet rs) throws SQLException {
		
		Pessoa pessoa = new Pessoa();
		pessoa.setId(rs.getInt("idpessoa"));
		pessoa.setNomePessoa(rs.getString("nomepessoa"));
		pessoa.setCodTipoPessoa(TipoPessoa.fromCodigo(rs.getInt("codtipopessoa")));
		pessoa.setDescricaoEndereco(rs.getString("descricaoendereco"));
		pessoa.setDescricaoComplemento(rs.getString("descricaocomplemento"));
		pessoa.setDescricaoBairro(rs.getString("descricaobairro"));
		pessoa.setNumeroCep(rs.getString("numerocep"));
		pessoa.setNumeroCelular(rs.getString("numerocelular"));
		pessoa.setDescricaoEmail(rs.getString("descricaoemail"));
		
		Cidade cidade = new Cidade();
		cidade.setId(rs.getInt("idcidade"));
		cidade.setNomeCidade(rs.getString("nomecidade"));
		
		UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
		unidadeFederacao.setIdUnidadeFederacao(rs.getInt("idunidadefederacao"));
		unidadeFederacao.setNomeUnidadeFederacao(rs.getString("nomeunidadefederacao"));
		unidadeFederacao.setSiglaUnidadeFederacao(rs.getString("siglaunidadefederacao"));
		cidade.setUnidadeFederacao(unidadeFederacao);
		
		pessoa.setCidade(cidade);
		
		PessoaJuridica pessoaJuridica = new PessoaJuridica();
		pessoaJuridica.setId(rs.getInt("idpessoajuridica"));
		pessoaJuridica.setCnpj(rs.getString("numerocnpj"));
		pessoaJuridica.setRazaoSocial(rs.getString("nomerazaosocial"));             	
		pessoaJuridica.setPessoa(pessoa);
		
		Empresa empresa = new Empresa();
		empresa.setId(rs.getInt("idempresa"));
		empresa.setNomeFantasia(rs.getString("nomefantasia"));	
		empresa.setNomeResponsavel(rs.getString("nomeresponsavel"));
		empresa.setPessoaJuridica(pessoaJuridica);	
		
		EmpresaUnidadeGestao empresaUnidadeGestao = new EmpresaUnidadeGestao();
		empresaUnidadeGestao.setId(rs.getInt("idempresaunidadegestao"));
		empresaUnidadeGestao.setNumeroPlantonistaDia(rs.getInt("numeroplantonistadia"));
		empresaUnidadeGestao.setNumeroPlantonistaPeriodo(rs.getInt("numeroplantonistaperiodo"));
		empresaUnidadeGestao.setEmpresa(empresa);
		
		
		/** Empresa gestora da unidade gestao **/
		EmpresaGestao empresaGestao = new EmpresaGestao();
		empresaGestao.setId(rs.getInt("idempreagestora"));
		
		PessoaJuridica pessoaJuridicaGestao = new PessoaJuridica();
		pessoaJuridicaGestao.setRazaoSocial(rs.getString("nomerazaosocialgestora"));  
		
		Empresa empresaGestora = new Empresa();
		empresaGestora.setId(rs.getInt("idempresagestao"));
		empresaGestora.setNomeFantasia(rs.getString("nomefantasiagestora"));
		empresaGestora.setNomeResponsavel(rs.getString("nomeresponsavelgestora"));
		empresaGestora.setPessoaJuridica(pessoaJuridica);		
		empresaGestao.setEmpresa(empresaGestora);
		
		empresaUnidadeGestao.setEmpresaGestora(empresaGestao);
		
		return empresaUnidadeGestao;
	}	
}