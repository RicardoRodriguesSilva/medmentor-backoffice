package br.com.medmentor.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.medmentor.dao.PessoaDAO;
import br.com.medmentor.dao.PessoaJuridicaDAO;
import br.com.medmentor.enums.TipoPessoa;
import br.com.medmentor.model.Cidade;
import br.com.medmentor.model.Pessoa;
import br.com.medmentor.model.PessoaJuridica;
import br.com.medmentor.model.UnidadeFederacao;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class PessoaJuridicaDAOImpl implements PessoaJuridicaDAO {

	@Resource(lookup = "java:/jdbc/medmentorDS")
	private DataSource dataSource;

	@Inject
	private PessoaDAO pessoaDAO;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public PessoaJuridica create(PessoaJuridica pessoaJuridica) throws SQLException {

		if (pessoaJuridica.getPessoa().getCodTipoPessoa() != TipoPessoa.JURIDICA) {
			throw new IllegalArgumentException("Pessoa associada a PessoaJuridica deve ser do tipo JURIDICA.");
		}
		Pessoa pessoaCriada = pessoaDAO.create(pessoaJuridica.getPessoa());
		pessoaJuridica.setPessoa(pessoaCriada);

		String sql = "insert into \"MED\".pessoajuridica (idpessoajuridica, numerocnpj, nomerazaosocial) values (?, ?, ?) returning idpessoajuridica";
		try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
			stmt.setInt(1, pessoaJuridica.getPessoa().getId());
			stmt.setString(2, pessoaJuridica.getCnpj());
			stmt.setString(3, pessoaJuridica.getRazaoSocial());

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					pessoaJuridica.setId(rs.getInt(1));
				} else {
					throw new SQLException("Falha ao obter o ID gerado para PessoaJuridica.");
				}
			}
			return pessoaJuridica;
		}
	}

	@Override
	public PessoaJuridica findById(Integer id) throws SQLException {
		String sql = this.recuperaPessoaJuridicaSQL() + " where pju.idpessoajuridica = ?";
		try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					PessoaJuridica pessoaJuridica = this.recuperaPessoaJuridica(rs);
					return pessoaJuridica;
				}
			}
		}
		return null;
	}

	@Override
	public PessoaJuridica update(PessoaJuridica pessoaJuridica) throws SQLException {

		pessoaDAO.update(pessoaJuridica.getPessoa());

		String sql = "update \"MED\".pessoajuridica set numerocnpj = ?, nomerazaosocial = ? where idpessoajuridica = ?";
		try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
			stmt.setString(1, pessoaJuridica.getCnpj());
			stmt.setString(2, pessoaJuridica.getRazaoSocial());
			stmt.setInt(3, pessoaJuridica.getId());
			int affectedRows = stmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Atualização da PessoaJuridica falhou, nenhuma linha afetada para o ID: "
						+ pessoaJuridica.getId());
			}
			return pessoaJuridica;
		}
	}

	@Override
	public void delete(Integer id) throws SQLException {
		PessoaJuridica pessoaJuridica = findById(id);
		if (pessoaJuridica == null) {
			return;
		}

		String sql = "delete from \"MED\".pessoajuridica where idpessoajuridica = ?";
		try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
		}

		pessoaDAO.delete(pessoaJuridica.getPessoa().getId());
	}
	
	@Override
    public List<PessoaJuridica> findAll() throws SQLException {
        List<PessoaJuridica> pessoasJuridicas = new ArrayList<PessoaJuridica>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            String sql = this.recuperaPessoaJuridicaSQL();

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
					PessoaJuridica pessoaJuridica = this.recuperaPessoaJuridica(rs);             	
                    pessoasJuridicas.add(pessoaJuridica);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar todas as Pessoas Jurídicas: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return pessoasJuridicas;
    }
	
	private String recuperaPessoaJuridicaSQL() {
    	String sql = "select "
    			+ "	pes.idpessoa, pes.nomepessoa, pes.codtipopessoa, pes.descricaoendereco, "
    			+ "	pes.descricaocomplemento, pes.descricaobairro, pes.numerocep, pes.idcidade, "
    			+ "	pes.numerocelular, pes.descricaoemail, "
    			+ "	pju.idpessoajuridica, pju.nomerazacaosocial, pju.numerocnpj, "
    			+ "	cid.nomecidade, ufu.idunidadefederacao, ufu.nomeunidadefederacao, ufu.siglaunidadefederacao "
    			+ "from "
    			+ "	\"MED\".pessoa pes, "
    			+ "	inner join \"MED\".pessoajuridica pju 	on pju.idpessoajuridica = pes.idpessoa "
    			+ "	inner join \"MED\".cidade cid 			on cid.idcidade = pes.idcidade "
    			+ "	inner join \"MED\".unidadefederacao ufu on ufu.idunidadefederacao = cid.idunidadefederacao ";
    	return sql;
    }
    
	private PessoaJuridica recuperaPessoaJuridica(ResultSet rs) throws SQLException {
		
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
		
		return pessoaJuridica;
	}   	
}