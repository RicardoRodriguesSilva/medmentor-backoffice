package br.com.medmentor.service.impl;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.medmentor.dao.ParametroDAO;
import br.com.medmentor.dao.SolicitacaoAcessoDAO;
import br.com.medmentor.dao.UsuarioDAO;
import br.com.medmentor.dto.UsuarioDTO;
import br.com.medmentor.enums.Canal;
import br.com.medmentor.enums.Parametro;
import br.com.medmentor.enums.TipoSolicitacaoAcesso;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.mapper.UsuarioMapper;
import br.com.medmentor.model.SolicitacaoAcesso;
import br.com.medmentor.model.Usuario;
import br.com.medmentor.service.UsuarioService;
import br.com.medmentor.util.EmailGenerator;
import br.com.medmentor.util.LoginGenerator;
import br.com.medmentor.util.PasswordGenerator;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Stateless 
public class UsuarioServiceImpl implements UsuarioService {

	@Inject 
    private ParametroDAO parametroDAO;
	
	@Inject 
    private SolicitacaoAcessoDAO solicitacaoAcessoDAO;
	
	@Inject 
    private UsuarioDAO usuarioDAO;
    
    @Inject
    private UsuarioMapper usuarioMapper;
    
    PasswordGenerator passwordGenerator;

    public UsuarioServiceImpl() {
    	
    	passwordGenerator = new PasswordGenerator();
    }

    @Override
    @Transactional
    public UsuarioDTO incluirUsuario(UsuarioDTO usuarioDTO) throws MedmentorException {
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        
        try {
        	usuario.setSenhaUsuario(passwordGenerator.hashPassword(usuarioDTO.getSenhaUsuario(), 12));
            usuario = usuarioDAO.create(usuario);
            this.enviarNovaSenha(Canal.PORTAL, TipoSolicitacaoAcesso.NOVA_SENHA, usuarioDTO, 
            		usuarioDTO.getSenhaUsuario(), "PRIMEIRA");
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }		

        return usuarioMapper.toDto(usuario);
    }

    @Override
    @Transactional
    public void excluirUsuario(Integer id) throws MedmentorException {
        try {
            usuarioDAO.delete(id);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }				
    }

    @Override
    @Transactional
    public void alterarUsuario(UsuarioDTO usuarioDTO) throws MedmentorException {
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        try {
        	Usuario usuarioAnteriorUsuario = usuarioDAO.findById(usuarioDTO.getId());
        	usuario.setSenhaUsuario(passwordGenerator.hashPassword(usuarioDTO.getSenhaUsuario(), 12));
            usuarioDAO.update(usuario);
            
            if (!usuarioDTO.getSenhaUsuario().equals(usuarioAnteriorUsuario.getSenhaUsuario())) {
            	this.enviarNovaSenha(Canal.PORTAL, TipoSolicitacaoAcesso.NOVA_SENHA, usuarioDTO, 
                		usuarioDTO.getSenhaUsuario(), usuarioAnteriorUsuario.getSenhaUsuario());
            }
            
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }	
    }

    @Override
    public UsuarioDTO recuperarUsuarioPorId(Integer id) throws MedmentorException {
        Usuario usuario;
        try {
            usuario = usuarioDAO.findById(id);
            if (usuario == null) {
                throw new MedmentorException("Usu�rio com ID " + id + " n�o encontrado.");
            }
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }
        
        return usuarioMapper.toDto(usuario); 
    } 
    
    @Override
    public UsuarioDTO recuperarUsuarioPorNome(String nome) throws MedmentorException {
        Usuario usuario;
        try {
            usuario = usuarioDAO.findByNome(nome);
            if (usuario == null) {
                throw new MedmentorException("Usu�rio com Nome " + nome + " n�o encontrado.");
            }
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }
        
        return usuarioMapper.toDto(usuario); 
    }     

    @Override
    public List<UsuarioDTO> recuperarListaUsuario() throws MedmentorException {
        List<UsuarioDTO> listaDto = new ArrayList<>();
        try {
            listaDto = usuarioMapper.toListDto(usuarioDAO.findAll());
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }
        return listaDto;
    }

	@Override
	public UsuarioDTO autenticaUsuario(String nome) throws MedmentorException {
        Usuario usuario;
        try {
            usuario = usuarioDAO.autenticaUsuario(nome);
            if (usuario == null) {
                throw new MedmentorException("Usu�rio com ID " + nome + " n�o encontrado.");
            }
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }
        
        return usuarioMapper.toDto(usuario); 
	}

	@Override
	public String gerarLoginUsuario(String nome) throws MedmentorException {
		LoginGenerator loginGenerator = new LoginGenerator();
		String login = "";
		try {
			List<Usuario> listaUsuarios = usuarioDAO.findAll();
			
			if ((listaUsuarios!=null) &&(listaUsuarios.size()>0)) {							
				List<String> nomesDosUsuarios = listaUsuarios.stream()
		                .map(Usuario::getNomeUsuario) 
		                .collect(Collectors.toList());
				login = loginGenerator.generateLogin(nome, nomesDosUsuarios);
			} else {
				List<String> nomesDosUsuarios = new ArrayList<String>();
				login = loginGenerator.generateLogin(nome, nomesDosUsuarios);
			}
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }		
		return login;
	}

	@Override
	public String gerarSenha(String nome) throws MedmentorException {
		PasswordGenerator passwordGenerator = new PasswordGenerator();
		String password = "";
		try {
			List<Usuario> listaUsuarios = usuarioDAO.findAll();
			
			if ((listaUsuarios!=null) &&(listaUsuarios.size()>0)) {							
				List<String> nomesDosUsuarios = listaUsuarios.stream()
		                .map(Usuario::getNomeUsuario) 
		                .collect(Collectors.toList());
				password = passwordGenerator.generatePassword(nome, nomesDosUsuarios);
			} else {
				List<String> nomesDosUsuarios = new ArrayList<String>();
				password = passwordGenerator.generatePassword(nome, nomesDosUsuarios);
			}
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }		
		return password;
	}
	
	@Override
	@Transactional
	public void recuperarSenha(String nomeUsuario)
			throws MedmentorException {
		try {
			Usuario usuarioAnterior = usuarioDAO.findByNome(nomeUsuario);
			String senhaAntiga = usuarioAnterior.getSenhaUsuario();
			
			UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuarioAnterior);
			Usuario usuarioNovo = usuarioAnterior;
			String novaSenha = this.gerarSenha(nomeUsuario);
			usuarioNovo.setSenhaUsuario(passwordGenerator.hashPassword(novaSenha, 12));
			usuarioDAO.update(usuarioNovo);
			
			this.enviarNovaSenha(Canal.MOBILE, TipoSolicitacaoAcesso.RECUPERACAO_SENHA, usuarioDTO, 
					novaSenha, senhaAntiga);
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }	
	}	

	private void enviarNovaSenha(Canal canal, TipoSolicitacaoAcesso tipoSolicitacaoAcesso, UsuarioDTO usuarioDTO, 
			String novaSenha, String senhaAntiga) throws MedmentorException {
		EmailGenerator emailGenerator = new EmailGenerator();
		try {
			
			String emailOrigem = parametroDAO.findById(Parametro.ADMIN_EMAIL.getCodigo()).getValor();
			String emailHost = parametroDAO.findById(Parametro.HOST_EMAIL.getCodigo()).getValor();
			String emailPorta = parametroDAO.findById(Parametro.PORT_EMAIL.getCodigo()).getValor();
			String emailSenha = parametroDAO.findById(Parametro.PASS_EMAIL.getCodigo()).getValor();
			String emailDestino = usuarioDTO.getPessoaFisicaDTO().getPessoaDTO().getDescricaoEmail(); 
			String nomePessoa = usuarioDTO.getPessoaFisicaDTO().getPessoaDTO().getNomePessoa();
			String nomeUsuario = usuarioDTO.getNomeUsuario();			
			
			Boolean emailEnviado = emailGenerator.generateEmail(emailOrigem, emailHost, emailPorta, emailSenha, emailDestino, nomePessoa, nomeUsuario, novaSenha, tipoSolicitacaoAcesso);
			
			if (emailEnviado) {
				SolicitacaoAcesso solicitacaoAcesso = new SolicitacaoAcesso();
				solicitacaoAcesso.setCanal(canal);
				solicitacaoAcesso.setDataHoraSolicitacao(LocalDateTime.now());
				solicitacaoAcesso.setEmailUtilizado(usuarioDTO.getPessoaFisicaDTO().getPessoaDTO().getDescricaoEmail());
				solicitacaoAcesso.setSenhaAnterior(senhaAntiga);
				solicitacaoAcesso.setTipoSolicitacaoAcesso(tipoSolicitacaoAcesso);
				solicitacaoAcesso.setUsuario(usuarioMapper.toEntity(usuarioDTO));
				solicitacaoAcessoDAO.create(solicitacaoAcesso);
			}
			
        } catch (SQLException e) {
            throw new MedmentorException(e.getMessage(), e.getCause());
        }			
		
	}
}