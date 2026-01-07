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

import br.com.medmentor.dao.UsuarioPerfilDAO;
import br.com.medmentor.enums.TipoPessoa;
import br.com.medmentor.model.Cidade;
import br.com.medmentor.model.Perfil;
import br.com.medmentor.model.Pessoa;
import br.com.medmentor.model.PessoaFisica;
import br.com.medmentor.model.UnidadeFederacao;
import br.com.medmentor.model.Usuario;
import br.com.medmentor.model.UsuarioPerfil;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class UsuarioPerfilDAOImpl implements UsuarioPerfilDAO {

    @Resource(lookup = "java:/jdbc/medmentorDS")
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    } 

    @Override
    public UsuarioPerfil create(UsuarioPerfil usuarioPerfil) throws SQLException {
        String sql = "INSERT INTO \"MED\".USUARIOPERFIL (IDUSUARIO, IDPERFIL) VALUES (?, ?) RETURNING IDUSUARIOPERFIL";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioPerfil.getUsuario().getId());
            stmt.setInt(2, usuarioPerfil.getPerfil().getId());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuarioPerfil.setId(rs.getInt(1));
                } else {
                    throw new SQLException("Falha ao obter o ID gerado para UsuarioPerfil.");
                }
            }
            return usuarioPerfil;
        }
    }

    @Override
    public UsuarioPerfil findById(Integer id) throws SQLException {
        String sql = this.recuperaUsuarioPerfilSQL() + " where idusuarioperfil = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UsuarioPerfil usuarioPerfil = this.recuperaUsuarioPerfil(rs);
                    return usuarioPerfil;
                }
            }
        }
        return null;
    }

    @Override
    public UsuarioPerfil update(UsuarioPerfil usuarioPerfil) throws SQLException {
        String sql = "UPDATE \"MED\".USUARIOPERFIL SET IDUSUARIO = ?, IDPERFIL = ? WHERE IDUSUARIOPERFIL = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioPerfil.getUsuario().getId());
            stmt.setInt(2, usuarioPerfil.getPerfil().getId());
            stmt.setInt(3, usuarioPerfil.getId());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Atualiza��o de UsuarioPerfil falhou, nenhuma linha afetada para o ID: " + usuarioPerfil.getId());
            }
            return usuarioPerfil;
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM \"MED\".USUARIOPERFIL WHERE IDUSUARIOPERFIL = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    @Override
    public List<UsuarioPerfil> findAll() throws SQLException {
        List<UsuarioPerfil> listaUsuarioPerfil = new ArrayList<>();
        String sql = this.recuperaUsuarioPerfilSQL();
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				UsuarioPerfil usuarioPerfil = this.recuperaUsuarioPerfil(rs);
				listaUsuarioPerfil.add(usuarioPerfil);
			}
		}
        return listaUsuarioPerfil;
    }
    
    private String recuperaUsuarioPerfilSQL() {
    	String sql = "select "
    			+ "	usu.idusuario, usu.idpessoafisica, usu.nomeusuario, usu.senhausuario, usu.bolativo, "
    			+ "	pfi.idpessoafisica, pfi.numerocpf, pfi.datanascimento, "
    			+ "	pes.idpessoa, pes.nomepessoa, pes.codtipopessoa, pes.descricaoendereco, pes.descricaocomplemento, "
    			+ "	pes.descricaobairro, pes.numerocep, pes.idcidade, "
    			+ "	cid.nomecidade, ufu.idunidadefederacao, ufu.nomeunidadefederacao, ufu.siglaunidadefederacao, "
    			+ " usp.idusuarioperfil, "  
    			+ " per.idperfil, per.nomeperfil "
    			+ "from "
    			+ "	\"MED\".usuario usu "
    			+ "	inner join \"MED\".pessoafisica pfi 	on usu.idpessoafisica = pfi.idpessoafisica "
    			+ "	inner join \"MED\".pessoa pes 			on pes.idpessoa = pfi.idpessoafisica "
    			+ "	inner join \"MED\".cidade cid 			on cid.idcidade = pes.idcidade "
    			+ "	inner join \"MED\".unidadefederacao ufu on ufu.idunidadefederacao = cid.idunidadefederacao "
    			+ " inner join \"MED\".usuarioperfil usp    on usp.idusuario = usu.idusuario " 
    			+ " inner join \"MED\".perfil per           on per.idperfil = usp.idperfil ";
    	return sql;
    }
    
	private UsuarioPerfil recuperaUsuarioPerfil(ResultSet rs) throws SQLException {
		
		Pessoa pessoa = new Pessoa();
		pessoa.setId(rs.getInt("idpessoa"));
		pessoa.setNomePessoa(rs.getString("nomepessoa"));
		pessoa.setCodTipoPessoa(TipoPessoa.fromCodigo(rs.getInt("codtipopessoa")));
		pessoa.setDescricaoEndereco(rs.getString("descricaoendereco"));
		pessoa.setDescricaoComplemento(rs.getString("descricaocomplemento"));
		pessoa.setDescricaoBairro(rs.getString("descricaobairro"));
		pessoa.setNumeroCep(rs.getString("numerocep"));
		
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
		
		Perfil perfil = new Perfil();
		perfil.setId(rs.getInt("idperfil"));
		perfil.setNomePerfil(rs.getString("nomeperfil"));
		
		UsuarioPerfil usuarioPerfil = new UsuarioPerfil();
		usuarioPerfil.setId(rs.getInt("idusuarioPerfil"));
		usuarioPerfil.setPerfil(perfil);
		usuarioPerfil.setUsuario(usuario);
		
		return usuarioPerfil;
	}    
}