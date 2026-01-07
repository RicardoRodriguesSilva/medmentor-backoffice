package br.com.medmentor.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.medmentor.dao.PessoaDAO;
import br.com.medmentor.enums.TipoPessoa;
import br.com.medmentor.model.Cidade;
import br.com.medmentor.model.Pessoa;
import br.com.medmentor.model.UnidadeFederacao;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class PessoaDAOImpl implements PessoaDAO {

	@Resource(lookup = "java:/jdbc/medmentorDS")
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Pessoa create(Pessoa pessoa) throws SQLException { 
		String sql = "insert into \"MED\".pessoa (nomepessoa, codtipopessoa, descricaoendereco, descricaocomplemento, descricaobairro, numerocep, idcidade, numerocelular, descricaoemail) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING IDpessoa";
		try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
			stmt.setString(1, pessoa.getNomePessoa());
			stmt.setInt(2, pessoa.getCodTipoPessoa().getCodigo());
			stmt.setString(3, pessoa.getDescricaoEndereco());
			stmt.setString(4, pessoa.getDescricaoComplemento());
			stmt.setString(5, pessoa.getDescricaoBairro());
			stmt.setString(6, pessoa.getNumeroCep());
			stmt.setInt(7, pessoa.getCidade().getId());
			stmt.setString(8, pessoa.getNumeroCelular());
			stmt.setString(9, pessoa.getDescricaoEmail());

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					pessoa.setId(rs.getInt(1));
				} else {
					throw new SQLException("Falha ao obter o ID gerado para Pessoa.");
				}
			}
			return pessoa;
		}
	}

	@Override
	public Pessoa findById(Integer id) throws SQLException {
		String sql = this.recuperaPessoaSQL() + " where pes.idpessoa = ?";
		try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Pessoa pessoa = this.recuperaPessoa(rs);				
					return pessoa;
				}
			}
		}
		return null;
	}

	@Override
	public Pessoa update(Pessoa pessoa) throws SQLException {
		String sql = "update \"MED\".pessoa set nomepessoa = ?, codtipopessoa = ?, descricaoendereco = ?, descricaocomplemento = ?, descricaobairro = ?, numerocep = ?, idcidade = ?, numerocelular = ?, descricaoemail = ? where IDpessoa = ?";
		try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
			stmt.setString(1, pessoa.getNomePessoa());
			stmt.setInt(2, pessoa.getCodTipoPessoa().getCodigo());
			stmt.setString(3, pessoa.getDescricaoEndereco());
			stmt.setString(4, pessoa.getDescricaoComplemento());
			stmt.setString(5, pessoa.getDescricaoBairro());
			stmt.setString(6, pessoa.getNumeroCep());
			stmt.setInt(7, pessoa.getCidade().getId());
			stmt.setString(8, pessoa.getNumeroCelular());
			stmt.setString(9, pessoa.getDescricaoEmail());
			stmt.setInt(10, pessoa.getId());
			int affectedRows = stmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException(
						"Atualização da Pessoa falhou, nenhuma linha afetada para o ID: " + pessoa.getId());
			}
			return pessoa;
		}
	}

	@Override
	public void delete(Integer id) throws SQLException {
		String sql = "delete from \"MED\".pessoa where IDpessoa = ?";
		try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
		}
	}

	@Override
	public List<Pessoa> findAll() throws SQLException {
		String sql = this.recuperaPessoaSQL();
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		try (Connection conn = dataSource.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				Pessoa pessoa = this.recuperaPessoa(rs);			
				pessoas.add(pessoa);
			}
		}
		return pessoas;
	}
	
	private String recuperaPessoaSQL() {
		String sql = "select "
				+ "	pes.idpessoa, pes.nomepessoa, pes.codtipopessoa, pes.descricaoendereco, "
				+ "	pes.descricaocomplemento, pes.descricaobairro, pes.numerocep, pes.idcidade, "
				+ "	pes.numerocelular, pes.descricaoemail, "
				+ "	cid.nomecidade, ufu.idunidadefederacao, ufu.nomeunidadefederacao, ufu.siglaunidadefederacao "
				+ "from "
				+ "	\"MED\".pessoa pes "
				+ "	inner join \"MED\".cidade cid 			on cid.idcidade = pes.idcidade "
				+ "	inner join \"MED\".unidadefederacao ufu on ufu.idunidadefederacao = cid.idunidadefederacao ";
		return sql;
	}
	
	private Pessoa recuperaPessoa(ResultSet rs) throws SQLException {
		
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
		
		return pessoa;
	}
}