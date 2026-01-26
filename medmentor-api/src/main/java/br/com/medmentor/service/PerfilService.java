package br.com.medmentor.service;

import java.util.List;

import br.com.medmentor.dto.PerfilDTO;
import br.com.medmentor.exception.MedmentorException;
import jakarta.ejb.Local;

@Local
public interface PerfilService extends GenericService {
    
    PerfilDTO incluiPerfil(PerfilDTO perfilDTO) throws MedmentorException;
    
    void excluiPerfil(Integer id) throws MedmentorException;
    
    void alteraPerfil(PerfilDTO perfilDTO) throws MedmentorException;
    
    PerfilDTO recuperaPerfilPorId(Integer id) throws MedmentorException;
    
    List<PerfilDTO> recuperaListaPerfil() throws MedmentorException;
    
    String recuperaPerfilPorUsuario(String nome) throws MedmentorException;
    
}