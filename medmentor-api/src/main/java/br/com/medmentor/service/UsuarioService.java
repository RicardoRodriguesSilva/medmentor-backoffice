package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.UsuarioDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface UsuarioService extends GenericService {
    
    UsuarioDTO incluiUsuario(UsuarioDTO usuarioDTO) throws MedmentorException; 
    
    void excluiUsuario(Integer id) throws MedmentorException;
    
    void alteraUsuario(UsuarioDTO usuarioDTO) throws MedmentorException;
    
    UsuarioDTO recuperaUsuarioPorId(Integer id) throws MedmentorException;
    
    UsuarioDTO recuperaUsuarioPorNome(String nome) throws MedmentorException;
    
    List<UsuarioDTO> recuperaListaUsuario() throws MedmentorException;
    
    UsuarioDTO autenticaUsuario(String nome) throws MedmentorException;
    
    String geraLoginUsuario(String nome) throws MedmentorException;
    
    String geraSenha(String nome) throws MedmentorException;
    
    void recuperaSenha(String nomeUsuario) throws MedmentorException;
    
}