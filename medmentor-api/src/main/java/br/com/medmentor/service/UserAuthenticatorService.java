package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.AcessoMenuDTO;
import jakarta.ejb.Local;

@Local
public interface UserAuthenticatorService {
	
	boolean authenticate(String username, String password);

	List<AcessoMenuDTO> getMenusForUser(String username);
	
	String getProfileForUser(String username);
	
	Integer getProfissionalId(String username);
}