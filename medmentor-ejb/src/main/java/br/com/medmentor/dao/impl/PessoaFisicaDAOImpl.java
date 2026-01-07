package br.com.medmentor.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.medmentor.dao.PessoaDAO;
import br.com.medmentor.dao.PessoaFisicaDAO;
import br.com.medmentor.enums.TipoPessoa;
import br.com.medmentor.model.Cidade;
import br.com.medmentor.model.Pessoa;
import br.com.medmentor.model.PessoaFisica;
import br.com.medmentor.model.UnidadeFederacao;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class PessoaFisicaDAOImpl implements PessoaFisicaDAO {

	@Resource(lookup = "java:/jdbc/medmentorDS")
	private DataSource dataSource;

	@Inject 
	private PessoaDAO pessoaDAO;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public PessoaFisica create(PessoaFisica pessoaFisica) throws SQLException {

		Pessoa pessoaCriada = pessoaDAO.create(pessoaFisica.getPessoa());
		pessoaFisica.setPessoa(pessoaCriada);

		String sql = "insert into \"MED\".pessoafisica (idpessoafisica, numerocpf, datanascimento, idade) values (?, ?, ?, ?) returning idpessoafisica";
		try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
			stmt.setInt(1, pessoaFisica.getPessoa().getId());
			stmt.setString(2, pessoaFisica.getCpf());
			stmt.setDate(3, Date.valueOf(pessoaFisica.getDataNascimento()));
			stmt.setInt(4, pessoaFisica.getIdade());

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					pessoaFisica.setId(rs.getInt(1));
				} else {
					throw new SQLException("Falha ao obter o ID gerado para PessoaFisica.");
				}
			}
			return pessoaFisica;
		}
	}

	@Override
	public PessoaFisica findById(Integer id) throws SQLException {
		String sql = this.recuperaPessoaFisicaSQL() + " where pfi.idpessoafisica = ?";
		try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					PessoaFisica pessoaFisica = this.recuperaPessoaFisica(rs);
					return pessoaFisica;
				}
			}
		}
		return null;
	}

	@Override
	public PessoaFisica update(PessoaFisica pessoaFisica) throws SQLException {

		pessoaDAO.update(pessoaFisica.getPessoa());

		String sql = "update \"MED\".pessoafisica set numerocpf = ?, datanascimento = ?, idade = ? where idpessoafisica = ?";
		try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
			stmt.setString(1, pessoaFisica.getCpf());
			stmt.setDate(2, Date.valueOf(pessoaFisica.getDataNascimento()));
			stmt.setInt(3, pessoaFisica.getIdade());
			stmt.setInt(4, pessoaFisica.getId());
			int affectedRows = stmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Atualização da PessoaFisica falhou, nenhuma linha afetada para o ID: "
						+ pessoaFisica.getId());
			}
			return pessoaFisica;
		}
	}

	@Override
	public void delete(Integer id) throws SQLException {
		PessoaFisica pessoaFisica = findById(id);
		if (pessoaFisica == null) {
			return; 
		}

		String sql = "delete from \"MED\".pessoafisica where idpessoafisica = ?";
		try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
		}

		pessoaDAO.delete(pessoaFisica.getPessoa().getId());
	}

	@Override
	public List<PessoaFisica> findByCidade(Integer idCidade) throws SQLException {
		List<PessoaFisica> pFisicas = new ArrayList<>();
		String sql = this.recuperaPessoaFisicaSQL() + "where pes.codtipopessoa = ? and cid.idcidade = ? ";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
			stmt.setInt(1, TipoPessoa.FISICA.getCodigo());
			stmt.setInt(2, idCidade);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					PessoaFisica pessoaFisica = this.recuperaPessoaFisica(rs);
					pFisicas.add(pessoaFisica);
				}
			}
		}
		return pFisicas;
	}
	
	@Override
    public List<PessoaFisica> findAll() throws SQLException {
        List<PessoaFisica> pessoasFisicas = new ArrayList<>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            String sql = this.recuperaPessoaFisicaSQL();

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
					PessoaFisica pessoaFisica = this.recuperaPessoaFisica(rs);					               	
                    pessoasFisicas.add(pessoaFisica);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar todas as Pessoas Físicas: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return pessoasFisicas;
    }
    
    private String recuperaPessoaFisicaSQL() {
    	String sql = "select "
    			+ "	pes.idpessoa, pes.nomepessoa, pes.codtipopessoa, pes.descricaoendereco, "
    			+ "	pes.descricaocomplemento, pes.descricaobairro, pes.numerocep, pes.idcidade, "
    			+ "	pes.numerocelular, pes.descricaoemail, "
    			+ "	pfi.idpessoafisica, pfi.datanascimento, pfi.idade, pfi.numerocpf, "
    			+ "	cid.nomecidade, ufu.idunidadefederacao, ufu.nomeunidadefederacao, ufu.siglaunidadefederacao "
    			+ "from "
    			+ "	\"MED\".pessoa pes, "
    			+ "	inner join \"MED\".pessoafisica pfi 	on pfi.idpessoafisica = pes.idpessoa "
    			+ "	inner join \"MED\".cidade cid 			on cid.idcidade = pes.idcidade "
    			+ "	inner join \"MED\".unidadefederacao ufu on ufu.idunidadefederacao = cid.idunidadefederacao ";
    	return sql;
    }
    
	private PessoaFisica recuperaPessoaFisica(ResultSet rs) throws SQLException {
		
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
        pessoaFisica.setPessoa(pessoa);
		
		return pessoaFisica;
	}    
}