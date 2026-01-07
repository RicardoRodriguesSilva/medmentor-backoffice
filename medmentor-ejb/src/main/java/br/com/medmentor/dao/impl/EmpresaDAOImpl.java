package br.com.medmentor.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.medmentor.dao.EmpresaDAO;
import br.com.medmentor.dao.PessoaJuridicaDAO;
import br.com.medmentor.enums.TipoPessoa;
import br.com.medmentor.model.Cidade;
import br.com.medmentor.model.Empresa;
import br.com.medmentor.model.Pessoa;
import br.com.medmentor.model.PessoaJuridica;
import br.com.medmentor.model.UnidadeFederacao;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class EmpresaDAOImpl implements EmpresaDAO {

	@Resource(lookup = "java:/jdbc/medmentorDS")
	private DataSource dataSource;

	@Inject
	private PessoaJuridicaDAO pessoaJuridicaDAO;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Empresa create(Empresa empresa) throws SQLException {
		PessoaJuridica pjCriada = pessoaJuridicaDAO.create(empresa.getPessoaJuridica());
		empresa.setPessoaJuridica(pjCriada);

		String sql = "insert into \"MED\".empresa (idempresa, nomefantasia) values (?, ?) returning idempresa";
		try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
			stmt.setInt(1, empresa.getPessoaJuridica().getId());
			stmt.setString(2, empresa.getNomeFantasia());

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					empresa.setId(rs.getInt(1));
				} else {
					throw new SQLException("Falha ao obter o ID gerado para Empresa.");
				}
			}
			return empresa;
		}
	}

	@Override
	public Empresa findById(Integer id) throws SQLException {
		String sql = this.recuperaEmpresaSQL() + " where emp.idempresa = ?";
		try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Empresa empresa = this.recuperaEmpresa(rs);
					return empresa;
				}
			}
		}
		return null;
	}

	@Override
	public Empresa update(Empresa empresa) throws SQLException {
		pessoaJuridicaDAO.update(empresa.getPessoaJuridica());

		String sql = "update \"MED\".empresa set nomefantasia = ? where idempresa = ?";
		try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
			stmt.setString(1, empresa.getNomeFantasia());
			stmt.setInt(2, empresa.getId());
			int affectedRows = stmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException(
						"Atualiza��o da Empresa falhou, nenhuma linha afetada para o ID: " + empresa.getId());
			}
			return empresa;
		}
	}

	@Override
	public void delete(Integer id) throws SQLException {
		Empresa empresa = findById(id);
		if (empresa == null) {
			return;
		}

		String sql = "delete from \"MED\".empresa where idempresa = ?";
		try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
		}

		pessoaJuridicaDAO.delete(empresa.getPessoaJuridica().getId());
	}
	
	@Override
    public List<Empresa> findAll() throws SQLException {
        List<Empresa> listaEmpresa = new ArrayList<Empresa>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            String sql = this.recuperaEmpresaSQL();

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                	Empresa empresa = this.recuperaEmpresa(rs);	
					listaEmpresa.add(empresa);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar todas as Empresas: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return listaEmpresa;
    }	
	
	private String recuperaEmpresaSQL() {
    	String sql = "select "
    			+ "	pes.idpessoa, pes.nomepessoa, pes.codtipopessoa, pes.descricaoendereco, "
    			+ "	pes.descricaocomplemento, pes.descricaobairro, pes.numerocep, pes.idcidade, "
    			+ "	pes.numerocelular, pes.descricaoemail, "
    			+ "	pju.idpessoajuridica, pju.nomerazaosocial, pju.numerocnpj, "
    			+ "	emp.idempresa, "
    			+ "	cid.nomecidade, ufu.idunidadefederacao, ufu.nomeunidadefederacao, ufu.siglaunidadefederacao "
    			+ "from "
    			+ "	\"MED\".pessoa pes "
    			+ "	inner join \"MED\".pessoajuridica pju 	on pju.idpessoajuridica = pes.idpessoa "
    			+ " inner join \"MED\".empresa emp 			on emp.idempresa = pju.idpessoajuridica "
    			+ "	inner join \"MED\".cidade cid 			on cid.idcidade = pes.idcidade "
    			+ "	inner join \"MED\".unidadefederacao ufu on ufu.idunidadefederacao = cid.idunidadefederacao ";
    	return sql;
    }
    
	private Empresa recuperaEmpresa(ResultSet rs) throws SQLException {
		
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
		
		return empresa;
	}	
}