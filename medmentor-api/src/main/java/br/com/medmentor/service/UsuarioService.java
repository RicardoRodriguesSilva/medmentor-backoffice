package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.UsuarioDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface UsuarioService extends GenericService {
    
    UsuarioDTO incluirUsuario(UsuarioDTO usuarioDTO) throws MedmentorException; 
    
    void excluirUsuario(Integer id) throws MedmentorException;
    
    void alterarUsuario(UsuarioDTO usuarioDTO) throws MedmentorException;
    
    UsuarioDTO recuperarUsuarioPorId(Integer id) throws MedmentorException;
    
    UsuarioDTO recuperarUsuarioPorNome(String nome) throws MedmentorException;
    
    List<UsuarioDTO> recuperarListaUsuario() throws MedmentorException;
    
    UsuarioDTO autenticaUsuario(String nome) throws MedmentorException;
    
    String gerarLoginUsuario(String nome) throws MedmentorException;
    
    String gerarSenha(String nome) throws MedmentorException;
    
    void recuperarSenha(String nomeUsuario) throws MedmentorException;
    
}