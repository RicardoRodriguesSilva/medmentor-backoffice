package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.UsuarioPerfilDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface UsuarioPerfilService extends GenericService {
    
    UsuarioPerfilDTO incluirUsuarioPerfil(UsuarioPerfilDTO usuarioPerfilDTO) throws MedmentorException;
    
    void excluirUsuarioPerfil(Integer id) throws MedmentorException;
    
    void alterarUsuarioPerfil(UsuarioPerfilDTO usuarioPerfilDTO) throws MedmentorException;
    
    UsuarioPerfilDTO recuperarUsuarioPerfilPorId(Integer id) throws MedmentorException;
    
    List<UsuarioPerfilDTO> recuperarListaUsuarioPerfil() throws MedmentorException;
    
}