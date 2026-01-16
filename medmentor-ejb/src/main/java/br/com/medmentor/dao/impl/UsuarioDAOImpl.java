package br.com.medmentor.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.medmentor.dao.UsuarioDAO;
import br.com.medmentor.enums.TipoPessoa; // Assumido para PessoaFisica mapear TipoPessoa
import br.com.medmentor.model.Cidade;
import br.com.medmentor.model.Pessoa; // Assumido para PessoaFisica ter um objeto Pessoa
import br.com.medmentor.model.PessoaFisica; // Assumido
import br.com.medmentor.model.UnidadeFederacao;
import br.com.medmentor.model.Usuario;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class UsuarioDAOImpl implements UsuarioDAO {

	@Resource(lookup = "java:/jdbc/medmentorDS")
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Usuario create(Usuario usuario) throws SQLException {
		
		String sql = "insert into \"MED\".usuario (idpessoa, nomeusuario, senhausuario, bolativo) values (?, ?, ?, ?) returning idusuario";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, usuario.getPessoaFisica().getId());
			stmt.setString(2, usuario.getNomeUsuario());
			stmt.setString(3, usuario.getSenhaUsuario());
			stmt.setBoolean(4, usuario.getBolAtivo());

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					usuario.setId(rs.getInt(1));
				} else {
					throw new SQLException("Falha ao obter o ID gerado para Usuario.");
				}
			}
			return usuario;
		}
	}

	@Override
	public Usuario findById(Integer id) throws SQLException {
		String sql = this.recuperaUsuarioSQL() + "where usu.idusuario = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Usuario usuario = this.recuperaUsuario(rs);
					return usuario;
				}
			}
		}
		return null;
	}
	
	@Override
	public Usuario findByNome(String nome) throws SQLException {
		String sql = this.recuperaUsuarioSQL() + "where usu.nomeusuario = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, nome);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Usuario usuario = this.recuperaUsuario(rs);
					return usuario;
				}
			}
		}
		return null;
	}	

	@Override
	public Usuario update(Usuario usuario) throws SQLException {
		String sql = "update \"MED\".usuario set nomeusuario = ?, senhausuario = ?, bolativo = ?, idpessoa = ? where idusuario = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, usuario.getNomeUsuario());
			stmt.setString(2, usuario.getSenhaUsuario());
			stmt.setBoolean(3, usuario.getBolAtivo());
			stmt.setInt(4, usuario.getPessoaFisica().getId());
			stmt.setInt(5, usuario.getId());
			int affectedRows = stmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException(
						"Atualiza��o do Usuario falhou, nenhuma linha afetada para o ID: " + usuario.getId());
			}
			return usuario;
		}
	}

	@Override
	public void delete(Integer id) throws SQLException {
		Usuario usuario = findById(id);
		if (usuario == null) {
			return;
		}

		String sql = "delete from \"MED\".usuario where idusuario = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
		}
	}

	@Override
	public List<Usuario> findAll() throws SQLException {
		List<Usuario> listaUsuario = new ArrayList<>();
		String sql = this.recuperaUsuarioSQL();
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				Usuario usuario = this.recuperaUsuario(rs);
				listaUsuario.add(usuario);
			}
		}
		return listaUsuario;
	}

	@Override
	public Usuario autenticaUsuario(String nome) throws SQLException {
		String sql = this.recuperaUsuarioSQL() + "where usu.nomeusuario = ? ";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, nome);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Usuario usuario = this.recuperaUsuario(rs);
					return usuario;
				}
			}
		}
		return null;
	}
	
	private String recuperaUsuarioSQL() {
    	String sql = "select "
    			+ "	usu.idusuario, usu.idpessoa, usu.nomeusuario, usu.senhausuario, usu.bolativo, "
    			+ "	pfi.idpessoafisica, pfi.numerocpf, pfi.datanascimento, "
    			+ "	pes.idpessoa, pes.nomepessoa, pes.codtipopessoa, pes.descricaoendereco, pes.descricaocomplemento, "
    			+ "	pes.descricaobairro, pes.numerocep, pes.idcidade, "
    			+ "	pes.numerocelular, pes.descricaoemail, "
    			+ "	cid.nomecidade, ufu.idunidadefederacao, ufu.nomeunidadefederacao, ufu.siglaunidadefederacao "
    			+ "from "
    			+ "	\"MED\".usuario usu "
    			+ "	left join \"MED\".pessoafisica pfi 		on usu.idpessoa = pfi.idpessoafisica "
    			+ "	left join \"MED\".pessoajuridica pju 	on usu.idpessoa = pju.idpessoajuridica "
    			+ "	inner join \"MED\".pessoa pes 			on pes.idpessoa = pfi.idpessoafisica "
    			+ "	inner join \"MED\".cidade cid 			on cid.idcidade = pes.idcidade "
    			+ "	inner join \"MED\".unidadefederacao ufu 	on ufu.idunidadefederacao = cid.idunidadefederacao ";
    	return sql;
    }
    
	private Usuario recuperaUsuario(ResultSet rs) throws SQLException {
		
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
		pessoaFisica.setId(rs.getInt("idpessoafisica"));
		pessoaFisica.setDataNascimento(rs.getDate("datanascimento").toLocalDate());
		
		LocalDate dataAtual = LocalDate.now();
		Period periodo = Period.between(pessoaFisica.getDataNascimento(), dataAtual);
		pessoaFisica.setIdade(periodo.getYears());
		pessoaFisica.setCpf(rs.getString("numerocpf"));                	
        pessoaFisica.setPessoa(pessoa);
        
        Usuario usuario = new Usuario();
		usuario.setId(rs.getInt("idusuario"));
		usuario.setNomeUsuario(rs.getString("nomeusuario"));
		usuario.setSenhaUsuario(rs.getString("senhausuario"));
		usuario.setBolAtivo(rs.getBoolean("bolativo"));
		usuario.setPessoaFisica(pessoaFisica);
		
		return usuario;
	}	
}