package br.com.medmentor.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.medmentor.dao.SolicitacaoAcessoDAO;
import br.com.medmentor.enums.Canal;
import br.com.medmentor.enums.TipoPessoa;
import br.com.medmentor.enums.TipoSolicitacaoAcesso;
import br.com.medmentor.model.Cidade;
import br.com.medmentor.model.Pessoa;
import br.com.medmentor.model.PessoaFisica;
import br.com.medmentor.model.SolicitacaoAcesso;
import br.com.medmentor.model.UnidadeFederacao;
import br.com.medmentor.model.Usuario;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class SolicitacaoAcessoDAOImpl implements SolicitacaoAcessoDAO {

    @Resource(lookup = "java:/jdbc/medmentorDS")
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }  

    @Override
    public SolicitacaoAcesso create(SolicitacaoAcesso solicitacaoAcesso) throws SQLException {
        String sql = "INSERT INTO \"MED\".solicitacaoacesso (idusuario, datahorasolicitacao, senhaanterior, codcanal, idsolicitacaoacesso, emailUtilizado) VALUES (?, ?, ?, ?, ?, ?) RETURNING idsolicitacaoacesso";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, solicitacaoAcesso.getUsuario().getId());
            stmt.setObject(2, solicitacaoAcesso.getDataHoraSolicitacao());
            stmt.setString(3, solicitacaoAcesso.getSenhaAnterior());
            stmt.setInt(4, solicitacaoAcesso.getCanal().getCodigo());
            stmt.setInt(5, solicitacaoAcesso.getTipoSolicitacaoAcesso().getCodigo());
            stmt.setString(6, solicitacaoAcesso.getEmailUtilizado());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    solicitacaoAcesso.setId(rs.getInt(1));
                } else {
                    throw new SQLException("Falha ao obter o ID gerado para SolicitacaoAcesso.");
                }
            }
            return solicitacaoAcesso;
        }
    }

    @Override
    public SolicitacaoAcesso findById(Integer id) throws SQLException {
        String sql = this.recuperaSolicitacaoAcessoSQL() + " WHERE idsolicitacaoacesso = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    SolicitacaoAcesso solicitacaoAcesso = this.recuperaSolicitacaoaCesso(rs);
                    return solicitacaoAcesso;
                }
            }
        }
        return null;
    }

    @Override
    public SolicitacaoAcesso update(SolicitacaoAcesso solicitacaoAcesso) throws SQLException {
        String sql = "UPDATE \"MED\".solicitacaoacesso SET idusuario = ?, datahorasolicitacao = ?, senhaanterior = ?, codcanal = ?, codtiposolicitacaoacesso = ?, emailutilizado = ? WHERE idsolicitacaoacesso = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
        	stmt.setInt(1, solicitacaoAcesso.getUsuario().getId());
            stmt.setObject(2, solicitacaoAcesso.getDataHoraSolicitacao());
            stmt.setString(3, solicitacaoAcesso.getSenhaAnterior());
            stmt.setInt(4, solicitacaoAcesso.getCanal().getCodigo());            
            stmt.setInt(5, solicitacaoAcesso.getTipoSolicitacaoAcesso().getCodigo());
            stmt.setString(6, solicitacaoAcesso.getEmailUtilizado());
            stmt.setInt(7, solicitacaoAcesso.getId());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Atualiza��o da SolicitacaoAcesso falhou, nenhuma linha afetada para o ID: " + solicitacaoAcesso.getId());
            }
            return solicitacaoAcesso;
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM \"MED\".solicitacaoacesso WHERE idsolicitacaoacesso = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    @Override
    public List<SolicitacaoAcesso> findAll() throws SQLException {
        List<SolicitacaoAcesso> listaAcao = new ArrayList<>();
        String sql = this.recuperaSolicitacaoAcessoSQL();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                SolicitacaoAcesso solicitacaoAcesso = this.recuperaSolicitacaoaCesso(rs);
                listaAcao.add(solicitacaoAcesso);
            }
        }
        return listaAcao;
    }
    
	private String recuperaSolicitacaoAcessoSQL() {
    	String sql = "select "
    			+ " sol.idsolicitacaoacesso, sol.idusuario, sol.datahorasolicitacao, sol.senhaanterior, sol.codcanal, "
    			+ " sol.codtiposolicitacaoacesso, sol.emailutilizado, "
    			+ "	usu.idusuario, usu.idpessoafisica, usu.nomeusuario, usu.senhausuario, usu.bolativo, "
    			+ "	pfi.idpessoafisica, pfi.numerocpf, pfi.datanascimento, "
    			+ "	pes.idpessoa, pes.nomepessoa, pes.codtipopessoa, pes.descricaoendereco, pes.descricaocomplemento, "
    			+ "	pes.descricaobairro, pes.numerocep, pes.idcidade, "
    			+ "	pes.numerocelular, pes.descricaoemail, "
    			+ "	cid.nomecidade, ufu.idunidadefederacao, ufu.nomeunidadefederacao, ufu.siglaunidadefederacao "
    			+ "from "
    			+ "	\"MED\".usuario usu "
    			+ "	inner join \"MED\".solicitacaoacesso sol	on sol.idusuario = usu.idusuario "
    			+ "	inner join \"MED\".pessoafisica pfi 		on usu.idpessoafisica = pfi.idpessoafisica "
    			+ "	inner join \"MED\".pessoa pes 				on pes.idpessoa = pfi.idpessoafisica "
    			+ "	inner join \"MED\".cidade cid 				on cid.idcidade = pes.idcidade "
    			+ "	inner join \"MED\".unidadefederacao ufu 	on ufu.idunidadefederacao = cid.idunidadefederacao ";
    	return sql;
    }
    
	private SolicitacaoAcesso recuperaSolicitacaoaCesso(ResultSet rs) throws SQLException {
		
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
		
		SolicitacaoAcesso solicitacaoAcesso = new SolicitacaoAcesso();
		solicitacaoAcesso.setId(rs.getInt("idsolicitacaoacesso"));
		solicitacaoAcesso.setCanal(Canal.fromCodigo(rs.getInt("codcanal")));
		solicitacaoAcesso.setDataHoraSolicitacao(rs.getObject("datahorasolicitacao", LocalDateTime.class));
		solicitacaoAcesso.setSenhaAnterior(rs.getString("senhaanterior"));
		solicitacaoAcesso.setTipoSolicitacaoAcesso(TipoSolicitacaoAcesso.fromCodigo(rs.getInt("codtiposolicitacaoacesso")));
		solicitacaoAcesso.setEmailUtilizado(rs.getString("emailutilizado"));
		solicitacaoAcesso.setUsuario(usuario);
		
		return solicitacaoAcesso;
	}	    
}