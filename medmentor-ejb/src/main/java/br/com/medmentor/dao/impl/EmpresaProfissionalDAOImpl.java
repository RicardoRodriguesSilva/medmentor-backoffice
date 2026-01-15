package br.com.medmentor.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.medmentor.dao.EmpresaDAO;
import br.com.medmentor.dao.EmpresaProfissionalDAO;
import br.com.medmentor.enums.TipoPessoa;
import br.com.medmentor.model.Cidade;
import br.com.medmentor.model.Empresa;
import br.com.medmentor.model.EmpresaGestao;
import br.com.medmentor.model.Pessoa;
import br.com.medmentor.model.PessoaFisica;
import br.com.medmentor.model.PessoaJuridica;
import br.com.medmentor.model.Profissional;
import br.com.medmentor.model.EmpresaProfissional;
import br.com.medmentor.model.UnidadeFederacao;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class EmpresaProfissionalDAOImpl implements EmpresaProfissionalDAO {

	@Resource(lookup = "java:/jdbc/medmentorDS")
	private DataSource dataSource;
	
	@Inject
	private EmpresaDAO empresaDAO;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public EmpresaProfissional create(EmpresaProfissional empresaProfissional) throws SQLException {
		
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			Empresa empresaCriada = empresaDAO.create(empresaProfissional.getEmpresa());

			String sql = "insert into \"MED\".empresaprofissional (idempresaprofissional, idprofissional, numerobanco, numeroagencia, numeroconta, descricaochavepix, idempresagestao) values (?, ?, ?, ?, ?, ?, ?)";
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, empresaCriada.getId());
				stmt.setInt(2, empresaProfissional.getProfissional().getId());
				stmt.setString(3, empresaProfissional.getNumeroBanco());
				stmt.setString(4, empresaProfissional.getNumeroAgencia());
				stmt.setString(5, empresaProfissional.getNumeroConta());
				stmt.setString(6, empresaProfissional.getDescricaoChavePix());
				stmt.setInt(7, empresaProfissional.getEmpresaGestao().getId());
				stmt.executeUpdate();
				return empresaProfissional; 
			}
		} catch (SQLException e) {
			if (conn != null) {
			}
			throw new SQLException("Erro ao criar Profssional Empresa e sua hierarquia: " + e.getMessage(), e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}			
	}

	public EmpresaProfissional findById(Integer id) throws SQLException {
		String sql = this.recuperaEmpresaProfissionalSQL() + " where idempresaprofissional = ?";
		try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					EmpresaProfissional empresaProfissional = this.recuperaEmpresaProfissional(rs);
					return empresaProfissional;
				}
			}
		}
		return null;
	}
	
	public EmpresaProfissional update(EmpresaProfissional empresaProfissional) throws SQLException {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			empresaDAO.update(empresaProfissional.getEmpresa());
			
			String sql = "UPDATE \"MED\".empresaprofissional SET idprofissional=?, numerobanco=?, numeroagencia=?, numeroconta=?, descricaochavepix=?, idempresagestao=? "
					+ "WHERE idempresaprofissional=?";
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, empresaProfissional.getProfissional().getId()); 
				stmt.setString(2, empresaProfissional.getNumeroBanco()); 
				stmt.setString(3, empresaProfissional.getNumeroAgencia()); 
				stmt.setString(4, empresaProfissional.getNumeroConta());
				stmt.setString(5, empresaProfissional.getDescricaoChavePix());
				stmt.setInt(6, empresaProfissional.getEmpresaGestao().getId());
				stmt.setInt(7, empresaProfissional.getId());
				int affectedRows = stmt.executeUpdate();
				if (affectedRows == 0) {
					throw new SQLException(
							"Atualização da EmpresaProfissional falhou, registro não encontrado ou ID alterado. IDEmpresaProfissional: "
									+ empresaProfissional.getId());
				}
			}			
			
			return empresaProfissional;
		} catch (SQLException e) {
			if (conn != null) {
			}
			throw new SQLException("Erro ao atualizar EmpresaProfissional e sua hierarquia: " + e.getMessage(), e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}	
	
	public void delete(Integer idEmpresaProfissional) throws SQLException {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			EmpresaProfissional empresaProfissional = findById(idEmpresaProfissional);
			if (empresaProfissional == null) {
				conn.rollback();
				return;
			}

			String sql = "DELETE FROM \"MED\".PROFISSIONALEMPRESA WHERE idempresaprofissional = ?";
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, idEmpresaProfissional);
				stmt.executeUpdate();
			}

			empresaDAO.delete(empresaProfissional.getEmpresa().getId());

		} catch (SQLException e) {
			if (conn != null) {
			}
			throw new SQLException("Erro ao deletar EmpresaGestao e sua hierarquia: " + e.getMessage(), e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}	
	
	public EmpresaProfissional findByProfissional(Integer idProfissional) throws SQLException {
		String sql = this.recuperaEmpresaProfissionalSQL() + " where pro.idprofissional = ?";
		try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
			stmt.setInt(1, idProfissional);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					EmpresaProfissional empresaProfissional = this.recuperaEmpresaProfissional(rs);
					return empresaProfissional;
				}
			}
		}
		return null;
	}

	@Override
	public List<EmpresaProfissional> findAll() throws SQLException {
		String sql = this.recuperaEmpresaProfissionalSQL();
		List<EmpresaProfissional> empresas = new ArrayList<>();
		try (Connection conn = dataSource.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				EmpresaProfissional empresaProfissional = this.recuperaEmpresaProfissional(rs);
				empresas.add(empresaProfissional);
			}
		}
		return empresas;
	}
	
	private String recuperaEmpresaProfissionalSQL() {
    	String sql = "select "
    			+ "	pes.idpessoa, pes.nomepessoa, pes.codtipopessoa, pes.descricaoendereco, "
    			+ "	pes.descricaocomplemento, pes.descricaobairro, pes.numerocep, pes.idcidade, "
    			+ "	pes.numerocelular, pes.descricaoemail, "
    			+ "	pju.idpessoajuridica, pju.nomerazaosocial, pju.numerocnpj, "
    			+ "	emp.idempresa, emp.nomefantasia, "
    			+ " epp.idempresaprofissional, epp.idprofissional, epp.numerobanco, epp.numeroagencia, epp.numeroconta, epp.descricaochavepix, "
    			
    			+ " epg.idempresagestao as idempresagestora, "
    			+ " emp1.idempresa as idempresagestao, emp1.nomefantasia as nomefantasiagestao, "
    			+ " pju1.idpessoajuridica as idpessoajuridicagestao, pju1.nomerazaosocial as nomerazaosocialgestao, "
    			
    			+ " pro.idprofissional, "
    			+ " pfi.idpessoafisica, "
    			+ " pes1.idpessoa as idpessoaprofissional, pes1.nomepessoa as nomepessoaprofissional, "
    			+ "	cid.nomecidade, ufu.idunidadefederacao, ufu.nomeunidadefederacao, ufu.siglaunidadefederacao "
    			+ "from "
    			+ "	\"MED\".pessoa pes "
    			+ "	inner join \"MED\".pessoajuridica pju 		on pju.idpessoajuridica = pes.idpessoa "
    			+ " inner join \"MED\".empresa emp 				on emp.idempresa = pju.idpessoajuridica "
    			+ " inner join \"MED\".empresaprofissional epp  on epp.idempresaprofissional = emp.idempresa "
    			
    			+ " inner join \"MED\".empresagestao epg  		on epg.idempresagestao = epp.idempresagestao "
    			+ " inner join \"MED\".empresa emp1 			on emp1.idempresa = epg.idempresagestao "
    			+ "	inner join \"MED\".pessoajuridica pju1 		on pju1.idpessoajuridica = emp1.idempresa "
    			
    			+ " inner join \"MED\".profissional pro    		on pro.idprofissional = epp.idprofissional " 
    			+ " inner join \"MED\".pessoafisica pfi    		on pfi.idpessoafisica = pro.idprofissional "
    			+ " inner join \"MED\".pessoa pes1    			on pes1.idpessoa = pfi.idpessoafisica "
    			+ "	inner join \"MED\".cidade cid 				on cid.idcidade = pes.idcidade "
    			+ "	inner join \"MED\".unidadefederacao ufu 	on ufu.idunidadefederacao = cid.idunidadefederacao ";
    	return sql;
    }
    
	private EmpresaProfissional recuperaEmpresaProfissional(ResultSet rs) throws SQLException {
		
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
		empresa.setPessoaJuridica(pessoaJuridica);	
		
		EmpresaProfissional empresaProfissional = new EmpresaProfissional();
		empresaProfissional.setDescricaoChavePix(rs.getString("descricaochavepix"));
		empresaProfissional.setEmpresa(empresa);
		empresaProfissional.setId(rs.getInt("idempresaprofissional"));
		empresaProfissional.setNumeroAgencia(rs.getString("numeroagencia"));
		empresaProfissional.setNumeroBanco(rs.getString("numerobanco"));
		empresaProfissional.setNumeroConta(rs.getString("numeroconta"));
		
		Pessoa pessoaProfissional = new Pessoa();
		pessoaProfissional.setId(rs.getInt("idpessoaprofissional"));
		pessoaProfissional.setNomePessoa(rs.getString("nomepessoaprofissional"));
		
		PessoaFisica pessoaFisica = new PessoaFisica();
		pessoaFisica.setId(rs.getInt("idpessoafisica"));
		pessoaFisica.setPessoa(pessoaProfissional);
		
		Profissional profissional = new Profissional();
		profissional.setId(rs.getInt("idprofissional"));
		profissional.setPessoaFisica(pessoaFisica);
		
		empresaProfissional.setProfissional(profissional);
		
		PessoaJuridica pessoaJuridicaGestao = new PessoaJuridica();
		pessoaJuridicaGestao.setId(rs.getInt("idpessoajuridicagestao"));
		pessoaJuridicaGestao.setRazaoSocial(rs.getString("nomerazaosocialgestao"));   
		
		Empresa empresaGestao = new Empresa();
		empresaGestao.setId(rs.getInt("idempresagestao"));
		empresaGestao.setNomeFantasia(rs.getString("nomefantasiagestao"));	
		empresaGestao.setPessoaJuridica(pessoaJuridicaGestao);
		
		EmpresaGestao empresaGestora = new EmpresaGestao();
		empresaGestao.setId(rs.getInt("idempresagestora"));
		empresaGestora.setEmpresa(empresaGestao);
		
		empresaProfissional.setEmpresaGestao(empresaGestora);
		return empresaProfissional;
	}		
}