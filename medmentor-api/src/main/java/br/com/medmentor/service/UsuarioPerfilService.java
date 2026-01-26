package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.UsuarioPerfilDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.filtro.dto.FiltroUsuarioPerfilDTO;
import jakarta.ejb.Local;

@Local
public interface UsuarioPerfilService extends GenericService {
    
    UsuarioPerfilDTO incluiUsuarioPerfil(UsuarioPerfilDTO usuarioPerfilDTO) throws MedmentorException;
    
    void excluiUsuarioPerfil(Integer id) throws MedmentorException;
    
    void alteraUsuarioPerfil(UsuarioPerfilDTO usuarioPerfilDTO) throws MedmentorException;
    
    UsuarioPerfilDTO recuperaUsuarioPerfilPorId(Integer id) throws MedmentorException;
    
    List<UsuarioPerfilDTO> recuperaListaUsuarioPerfil() throws MedmentorException;
    
    List<UsuarioPerfilDTO> recuperaListaUsuarioPerfilPorFiltro(FiltroUsuarioPerfilDTO filtroUsuarioPerfilDTO) throws MedmentorException;
    
}