package br.com.medmentor.service.impl;

import java.util.List;

import br.com.medmentor.dto.AcessoMenuDTO;
import br.com.medmentor.dto.UsuarioDTO;
import br.com.medmentor.service.MenuService;
import br.com.medmentor.service.PerfilService;
import br.com.medmentor.service.UserAuthenticatorService;
import br.com.medmentor.service.UsuarioService;
import br.com.medmentor.util.PasswordGenerator;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;


@Stateless
public class UserAuthenticatorServiceImpl implements UserAuthenticatorService {
	
	@Inject
    private MenuService menuService;
	
	@Inject
    private PerfilService perfilService;
	
	@Inject
    private UsuarioService usuarioService;	

	@Override
	public boolean authenticate(String username, String password) {
		
		UsuarioDTO usuarioDTO = usuarioService.autenticaUsuario(username);
		if (usuarioDTO.getId()==null) {
			return false;
		}
		
		PasswordGenerator passwordGenerator = new PasswordGenerator();
		
		String hashedPassword = passwordGenerator.hashPassword(password, 12); 
		System.out.println("Password hash gerado = " + hashedPassword);
		
		String storedHashedPassword = usuarioDTO.getSenhaUsuario();
		System.out.println("Password hash recuperado = " + storedHashedPassword);
		
		boolean passwordMatchesGen = passwordGenerator.checkPassword(password, hashedPassword);
        System.out.println("Password matches recebido: " + passwordMatchesGen);
        
        boolean passwordMatchesRec = passwordGenerator.checkPassword(password, storedHashedPassword);
        System.out.println("Password matches recebido: " + passwordMatchesRec);
		
		return passwordMatchesRec;
	}

	@Override
	public List<AcessoMenuDTO> getMenusForUser(String username) {
		return menuService.recuperaMenuPorUsuario(username);
	}
	
	@Override
	public String getProfileForUser(String username) {
		return perfilService.recuperaPerfilPorUsuario(username);
	}

	@Override
	public Integer getProfissionalId(String username) {
		UsuarioDTO usuarioDTO = usuarioService.autenticaUsuario(username);
		if (usuarioDTO.getId()==null) {
			return 0;
		}
		return usuarioDTO.getPessoaFisicaDTO().getId();
	}	
}