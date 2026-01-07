package br.com.medmentor.dao.impl;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import javax.sql.DataSource;

import br.com.medmentor.dao.ProfissionalRegistroDAO;
import br.com.medmentor.enums.TipoPessoa;
import br.com.medmentor.model.Cidade;
import br.com.medmentor.model.Pessoa;
import br.com.medmentor.model.PessoaFisica;
import br.com.medmentor.model.Profissional;
import br.com.medmentor.model.ProfissionalRegistro;
import br.com.medmentor.model.UnidadeFederacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class ProfissionalRegistroDAOImpl implements ProfissionalRegistroDAO {

	@Resource(lookup = "java:/jdbc/medmentorDS")
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public ProfissionalRegistro create(ProfissionalRegistro profissionalRegistro) throws SQLException {
		String sql = "insert into \"MED\".profissionalregistro (idprofissional, nomeinstituicao, numeranoformacao, numeroregistro) values (?, ?, ?, ?)";
		try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
			stmt.setInt(1, profissionalRegistro.getProfissional().getId());
			stmt.setString(2, profissionalRegistro.getNomeInstituicao());
			
			if (profissionalRegistro.getNumeroAnoFormacao() != null) {
				stmt.setInt(3, profissionalRegistro.getNumeroAnoFormacao());
			} else {
				stmt.setNull(3, Types.SMALLINT);
			}
			stmt.setString(4, profissionalRegistro.getNumeroRegistro());

			int affectedRows = stmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Falha ao criar ProfissionalRegistro, nenhum profissionalRegistro afetado.");
			}
			return profissionalRegistro; 
		}
	}

	@Override
	public ProfissionalRegistro findById(Integer idProfissional) throws SQLException {
		String sql = this.recuperaProfissionalRegistroSQL() + " where pre.idprofissional = ?";
		try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
			stmt.setInt(1, idProfissional);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					ProfissionalRegistro profissionalRegistro = this.recuperaProfissionalRegistro(rs);
					return profissionalRegistro;
				}
			}
		}
		return null;
	}

	@Override
	public List<ProfissionalRegistro> findAll() throws SQLException {
		String sql = this.recuperaProfissionalRegistroSQL();
		List<ProfissionalRegistro> registros = new ArrayList<>();
		try (Connection conn = dataSource.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				ProfissionalRegistro profissionalRegistro = this.recuperaProfissionalRegistro(rs);
				registros.add(profissionalRegistro);
			}
		}
		return registros;
	}

	@Override
	public ProfissionalRegistro update(ProfissionalRegistro profissionalRegistro) throws SQLException {
		String sql = "update \"MED\".profissionalregistro set nomeinstituicao = ?, numeranoformacao = ?, numeroregistro = ? where idprofissional = ?";
		try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
			stmt.setString(1, profissionalRegistro.getNomeInstituicao());
			if (profissionalRegistro.getNumeroAnoFormacao() != null) {
				stmt.setInt(2, profissionalRegistro.getNumeroAnoFormacao());
			} else {
				stmt.setNull(2, Types.SMALLINT);
			}
			stmt.setString(3, profissionalRegistro.getNumeroRegistro());
			stmt.setInt(4, profissionalRegistro.getProfissional().getId()); 

			int affectedRows = stmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Atualização do ProfissionalRegistro falhou, nenhuma linha afetada para o Profissional ID: "
						+ profissionalRegistro.getProfissional().getId());
			}
			return profissionalRegistro;
		}
	}

	@Override
	public void delete(Integer idProfissional) throws SQLException {
		String sql = "delete from \"MED\".profissionalregistro where idprofissional = ?";
		try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
			stmt.setInt(1, idProfissional);
			stmt.executeUpdate();
		}
	}
	
	private String recuperaProfissionalRegistroSQL() {
    	String sql = "select "
    			+ "	pes.idpessoa, pes.nomepessoa, pes.codtipopessoa, pes.descricaoendereco, "
    			+ "	pes.descricaocomplemento, pes.descricaobairro, pes.numerocep, pes.idcidade, "
    			+ "	pes.numerocelular, pes.descricaoemail, "
    			+ "	pfi.idpessoafisica, pfi.datanascimento, pfi.idade, pfi.numerocpf, "
    			+ "	cid.nomecidade, ufu.idunidadefederacao, ufu.nomeunidadefederacao, ufu.siglaunidadefederacao, "
    			+ " pro.idprofissional, "
    			+ " pre.nomeinstituicao, pre.numeranoformacao, pre.numeroregistro "
    			+ "from "
    			+ "	\"MED\".pessoa pes "
    			+ "	inner join \"MED\".pessoafisica pfi 		on pfi.idpessoafisica = pes.idpessoa "
    			+ " inner join \"MED\".profissional pro 		on pro.idprofissional = pfi.idpessoafisica "
    			+ " inner join \"MED\".profissionalregistro pre on pre.idprofissional = pfi.idpessoafisica "
    			+ "	inner join \"MED\".cidade cid 				on cid.idcidade = pes.idcidade "
    			+ "	inner join \"MED\".unidadefederacao ufu 	on ufu.idunidadefederacao = cid.idunidadefederacao ";
    	return sql;
    }
    
	private ProfissionalRegistro recuperaProfissionalRegistro(ResultSet rs) throws SQLException {
		
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
		
		ProfissionalRegistro profissionalRegistro = new ProfissionalRegistro();
		profissionalRegistro.setNomeInstituicao(rs.getString("nomeinstituicao"));
		profissionalRegistro.setNumeroAnoFormacao(rs.getInt("numeranoformacao"));
		profissionalRegistro.setNumeroRegistro(rs.getString("numeroregistro"));
		profissionalRegistro.setProfissional(profissional);
		
		return profissionalRegistro;
	}		
}