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
import br.com.medmentor.enums.TipoPessoa;
import br.com.medmentor.model.Cidade;
import br.com.medmentor.model.Empresa;
import br.com.medmentor.model.EmpresaGestao;
import br.com.medmentor.model.Pessoa;
import br.com.medmentor.model.PessoaJuridica;
import br.com.medmentor.model.UnidadeFederacao;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class EmpresaGestaoDAOImpl implements EmpresaGestaoDAO {

	@Resource(lookup = "java:/jdbc/medmentorDS")
	private DataSource dataSource;

	@Inject
	private EmpresaDAO empresaDAO;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public EmpresaGestao create(EmpresaGestao empresaGestao) throws SQLException {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			Empresa empresaCriada = empresaDAO.create(empresaGestao.getEmpresa());
			empresaGestao.setEmpresa(empresaCriada);

			String sql = "insert into \"MED\".empresagestao (idempresagestao) values (?) returning idempresagestao";
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, empresaGestao.getEmpresa().getId());

				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next()) {
						empresaGestao.setId(rs.getInt(1));
					} else {
						throw new SQLException("Falha ao obter o ID gerado para EmpresaGestao.");
					}
				}
			}
			return empresaGestao;
		} catch (SQLException e) {
			throw new SQLException("Erro ao criar EmpresaGestao e sua hierarquia: " + e.getMessage(), e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public EmpresaGestao findById(Integer id) throws SQLException {
		Connection conn = null;
		EmpresaGestao empresaGestao = null;
		try {
			conn = dataSource.getConnection();
			String sql = this.recuperaEmpresaGestaoSQL() + " where ges.idempresagestao = ?";
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, id);
				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next()) {
						empresaGestao = this.recuperaEmpresaGestao(rs);
						return empresaGestao;
					}
				}
			}
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return empresaGestao;
	}

	@Override
	public EmpresaGestao update(EmpresaGestao empresaGestao) throws SQLException {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			empresaDAO.update(empresaGestao.getEmpresa());

			String sql = "update \"MED\".empresagestao set idempresagestao = ? where idempresagestao = ?";
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, empresaGestao.getId()); 
				stmt.setInt(2, empresaGestao.getId()); 
				stmt.executeUpdate();
			}

			return empresaGestao;
		} catch (SQLException e) {
			throw new SQLException("Erro ao atualizar EmpresaGestao e sua hierarquia: " + e.getMessage(), e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public void delete(Integer id) throws SQLException {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			EmpresaGestao empresaGestao = findById(id);
			if (empresaGestao == null) {
				conn.rollback();
				return;
			}

			String sql = "delete from \"MED\".empresagestao where idempresagestao = ?";
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, id);
				stmt.executeUpdate();
			}

			empresaDAO.delete(empresaGestao.getEmpresa().getId());
		} catch (SQLException e) {
			throw new SQLException("Erro ao deletar EmpresaGestao e sua hierarquia: " + e.getMessage(), e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	@Override
	public List<EmpresaGestao> findAll() throws SQLException {
		List<EmpresaGestao> lista = new ArrayList<EmpresaGestao>();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			String sql = this.recuperaEmpresaGestaoSQL();

			try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {					
					EmpresaGestao empresaGestao = this.recuperaEmpresaGestao(rs);
					lista.add(empresaGestao);
				}
			}
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return lista;
	}
	
	private String recuperaEmpresaGestaoSQL() {
    	String sql = "select "
    			+ "	pes.idpessoa, pes.nomepessoa, pes.codtipopessoa, pes.descricaoendereco, "
    			+ "	pes.descricaocomplemento, pes.descricaobairro, pes.numerocep, pes.idcidade, "
    			+ "	pes.numerocelular, pes.descricaoemail, "
    			+ "	pju.idpessoajuridica, pju.nomerazaosocial, pju.numerocnpj, "
    			+ "	emp.idempresa, ges.idempresagestao, emp.nomefantasia, "
    			+ "	cid.nomecidade, ufu.idunidadefederacao, ufu.nomeunidadefederacao, ufu.siglaunidadefederacao "
    			+ "from "
    			+ "	\"MED\".pessoa pes "
    			+ "	inner join \"MED\".pessoajuridica pju 	on pju.idpessoajuridica = pes.idpessoa "
    			+ " inner join \"MED\".empresa emp 			on emp.idempresa = pju.idpessoajuridica "
    			+ " inner join \"MED\".empresagestao ges 	on ges.idempresagestao = emp.idempresa " 
    			+ "	inner join \"MED\".cidade cid 			on cid.idcidade = pes.idcidade "
    			+ "	inner join \"MED\".unidadefederacao ufu on ufu.idunidadefederacao = cid.idunidadefederacao ";
    	return sql;
    }
    
	private EmpresaGestao recuperaEmpresaGestao(ResultSet rs) throws SQLException {
		
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
		
		EmpresaGestao empresaGestao = new EmpresaGestao();
		empresaGestao.setId(rs.getInt("idempresagestao"));
		empresaGestao.setEmpresa(empresa);
		
		return empresaGestao;
	}	
}