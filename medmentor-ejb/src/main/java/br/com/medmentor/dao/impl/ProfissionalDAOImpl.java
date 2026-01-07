package br.com.medmentor.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.medmentor.dao.PessoaFisicaDAO;
import br.com.medmentor.dao.ProfissionalDAO;
import br.com.medmentor.enums.TipoPessoa;
import br.com.medmentor.model.Cidade;
import br.com.medmentor.model.Pessoa;
import br.com.medmentor.model.PessoaFisica;
import br.com.medmentor.model.Profissional;
import br.com.medmentor.model.UnidadeFederacao;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class ProfissionalDAOImpl implements ProfissionalDAO {

	@Resource(lookup = "java:/jdbc/medmentorDS")
	private DataSource dataSource;

	@Inject
	private PessoaFisicaDAO pessoaFisicaDAO;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Profissional create(Profissional profissional) throws SQLException {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			PessoaFisica pfCriada = pessoaFisicaDAO.create(profissional.getPessoaFisica());
			profissional.setPessoaFisica(pfCriada);

			String sql = "insert into \"MED\".profissional (idprofissional) values (?) returning idprofissional"; 
			
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, profissional.getPessoaFisica().getId());

				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next()) {
						profissional.setId(rs.getInt(1));
					} else {
						throw new SQLException("Falha ao obter o ID gerado para Profissional.");
					}
				}
			}
			return profissional;
		} catch (SQLException e) {
			throw new SQLException("Erro ao criar Profissional e sua hierarquia: " + e.getMessage(), e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public Profissional findById(Integer id) throws SQLException {
		Connection conn = null;
		Profissional profissional = null;
		try {
			conn = dataSource.getConnection();
			String sql = this.recuperaProfissionalSQL() + " where idprofissional = ?";
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, id);
				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next()) {
						profissional = this.recuperaProfissional(rs);
						return profissional;
					}
				}
			}
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return profissional;
	}

	@Override
	public Profissional update(Profissional profissional) throws SQLException {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			pessoaFisicaDAO.update(profissional.getPessoaFisica());

			String sql = "update \"MED\".profissional set idprofissional = ? where idprofissional = ?";
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, profissional.getPessoaFisica().getId());
				stmt.setInt(2, profissional.getId());
				int affectedRows = stmt.executeUpdate();
				if (affectedRows == 0) {
					throw new SQLException("Atualiza��o do Profissional falhou, nenhuma linha afetada para o ID: "
							+ profissional.getId());
				}
			}
			return profissional;
		} catch (SQLException e) {
			throw new SQLException("Erro ao atualizar Profissional e sua hierarquia: " + e.getMessage(), e);
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

			Profissional profissional = findById(id);
			if (profissional == null) {
				conn.rollback();
				return; 
			}

			String sql = "delete from \\\"med\\\".profissional where idprofissional = ?";
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, id);
				stmt.executeUpdate();
			}

			pessoaFisicaDAO.delete(profissional.getPessoaFisica().getId());
		} catch (SQLException e) {
			throw new SQLException("Erro ao deletar Profissional e sua hierarquia: " + e.getMessage(), e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	public List<Profissional> findAll() throws SQLException {
		List<Profissional> profissionais = new ArrayList<>();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			String sql = this.recuperaProfissionalSQL();
			try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
				while (rs.next()) {
					Profissional profissional = this.recuperaProfissional(rs);

					profissionais.add(profissional);
				}
			}
		} catch (SQLException e) {
			throw new SQLException("Erro ao listar todos os profissionais: " + e.getMessage(), e);
		} finally {
			if (conn != null) {
				conn.close(); 
			}
		}
		return profissionais;
	}

	public List<Profissional> findByCidade(Integer idCidade) throws SQLException {
		List<Profissional> profissionais = new ArrayList<>();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			String sql = this.recuperaProfissionalSQL() + " where cid.idcidade = ?";

			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, idCidade);
				try (ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						Profissional profissional = this.recuperaProfissional(rs);
						profissionais.add(profissional);
					}
				}
			}
		} catch (SQLException e) {
			throw new SQLException("Erro ao buscar profissionais por cidade (ID: " + idCidade + "): " + e.getMessage(),
					e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return profissionais;
	}
	
	@Override
	public List<Profissional> findByNome(String nome) throws SQLException {
		List<Profissional> profissionais = new ArrayList<>();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			String sql = this.recuperaProfissionalSQL() + " where 1 = 1 ";

			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				if ((nome != null)&&(!nome.isEmpty())) {
					sql = sql + " AND pes.nomepessoa like ?";
					stmt.setString(1, nome);
				}
				try (ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						Profissional profissional = this.recuperaProfissional(rs);
						profissionais.add(profissional);
					}
				}
			}
		} catch (SQLException e) {
			throw new SQLException("Erro ao buscar profissionais por nome (Nome: " + nome + "): " + e.getMessage(),
					e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return profissionais;
	}	
	
	private String recuperaProfissionalSQL() {
    	String sql = "select "
    			+ "	pes.idpessoa, pes.nomepessoa, pes.codtipopessoa, pes.descricaoendereco, "
    			+ "	pes.descricaocomplemento, pes.descricaobairro, pes.numerocep, pes.idcidade, "
    			+ "	pes.numerocelular, pes.descricaoemail, "
    			+ "	pfi.idpessoafisica, pfi.datanascimento, pfi.idade, pfi.numerocpf, "
    			+ "	cid.nomecidade, ufu.idunidadefederacao, ufu.nomeunidadefederacao, ufu.siglaunidadefederacao, "
    			+ " pro.idprofissional "
    			+ "from "
    			+ "	\"MED\".pessoa pes "
    			+ "	inner join \"MED\".pessoafisica pfi 	on pfi.idpessoafisica = pes.idpessoa "
    			+ " inner join \"MED\".profissional pro 		on pro.idprofissional = pfi.idpessoafisica "
    			+ "	inner join \"MED\".cidade cid 			on cid.idcidade = pes.idcidade "
    			+ "	inner join \"MED\".unidadefederacao ufu on ufu.idunidadefederacao = cid.idunidadefederacao ";
    	return sql;
    }
    
	private Profissional recuperaProfissional(ResultSet rs) throws SQLException {
		
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
		
		PessoaFisica pessoaFisica = new PessoaFisica();
		pessoaFisica.setDataNascimento(rs.getDate("datanascimento").toLocalDate());
		pessoaFisica.setIdade(rs.getObject("idade", Integer.class));
		pessoaFisica.setCpf(rs.getString("numerocpf"));  
		pessoaFisica.setId(rs.getInt("idpessoafisica"));
        pessoaFisica.setPessoa(pessoa);
        
        Profissional profissional = new Profissional();
		profissional.setId(rs.getInt("idprofissional"));
		profissional.setPessoaFisica(pessoaFisica);
		
		return profissional;
	}	
}