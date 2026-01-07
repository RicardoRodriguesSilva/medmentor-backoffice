package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.PerfilDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface PerfilService extends GenericService {
    
    PerfilDTO incluirPerfil(PerfilDTO perfilDTO) throws MedmentorException;
    
    void excluirPerfil(Integer id) throws MedmentorException;
    
    void alterarPerfil(PerfilDTO perfilDTO) throws MedmentorException;
    
    PerfilDTO recuperarPerfilPorId(Integer id) throws MedmentorException;
    
    List<PerfilDTO> recuperarListaPerfil() throws MedmentorException;
    
    String recuperaPerfilPorUsuario(String nome) throws MedmentorException;
    
}