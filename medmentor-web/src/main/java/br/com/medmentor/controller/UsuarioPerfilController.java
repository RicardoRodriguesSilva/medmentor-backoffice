package br.com.medmentor.controller;

import java.net.URI;
import java.util.List;

import br.com.medmentor.dto.UsuarioPerfilDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.filtro.dto.FiltroUsuarioPerfilDTO;
import br.com.medmentor.service.UsuarioPerfilService;
import jakarta.inject.Inject;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuario-perfil") 
@Produces(MediaType.APPLICATION_JSON) 
@Consumes(MediaType.APPLICATION_JSON) 
public class UsuarioPerfilController {

    @Inject
    private UsuarioPerfilService usuarioPerfilService;

    @POST
    public Response incluirUsuarioPerfil(UsuarioPerfilDTO usuarioPerfilDTO) {
        try {
            if (usuarioPerfilDTO == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o n�o pode ser vazio.")
                               .build();
            }
            UsuarioPerfilDTO novoUsuarioPerfil = usuarioPerfilService.incluiUsuarioPerfil(usuarioPerfilDTO);
            return Response.created(URI.create("/api/usuario-perfis/" + novoUsuarioPerfil.getId()))
                           .entity(novoUsuarioPerfil)
                           .build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao incluir Usu�rio Perfil: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao incluir usu�rio perfil: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de inclus�o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response recuperarUsuarioPerfilPorId(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID do usu�rio perfil n�o pode ser nulo.")
                               .build();
            }
            UsuarioPerfilDTO usuarioPerfil = usuarioPerfilService.recuperaUsuarioPerfilPorId(id);
            if (usuarioPerfil != null) {
                return Response.ok(usuarioPerfil).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Usu�rio Perfil com ID " + id + " n�o encontrado.")
                               .build();
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao recuperar Usu�rio Perfil por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao buscar usu�rio perfil: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de recupera��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }

    @GET
    @Path("/por-filtros")
    public Response recuperarListaUsuarioPerfilPorFiltro(@BeanParam FiltroUsuarioPerfilDTO filtroUsuarioPerfilDTO) {
        try {
            List<UsuarioPerfilDTO> usuarioPerfis = usuarioPerfilService.recuperaListaUsuarioPerfilPorFiltro(filtroUsuarioPerfilDTO);
            if (usuarioPerfis.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(usuarioPerfis).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todos os Usu�rios Perfis: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar usu�rios perfis: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de listagem: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }
    
    @GET
    public Response recuperarListaUsuarioPerfil() {
        try {
            List<UsuarioPerfilDTO> usuarioPerfis = usuarioPerfilService.recuperaListaUsuarioPerfil();
            if (usuarioPerfis.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(usuarioPerfis).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todos os Usu�rios Perfis: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar usu�rios perfis: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de listagem: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }    

    @PUT
    public Response alterarUsuarioPerfil(UsuarioPerfilDTO usuarioPerfilDTO) {
        try {
            if (usuarioPerfilDTO == null || usuarioPerfilDTO.getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o e o ID do usu�rio perfil n�o podem ser vazios.")
                               .build();
            }
            usuarioPerfilService.alteraUsuarioPerfil(usuarioPerfilDTO);
            return Response.ok().entity("Usu�rio Perfil atualizado com sucesso.").build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao alterar Usu�rio Perfil com ID " + usuarioPerfilDTO.getId() + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao atualizar usu�rio perfil: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de altera��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response excluirUsuarioPerfil(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID do usu�rio perfil n�o pode ser nulo para exclus�o.")
                               .build();
            }

            UsuarioPerfilDTO usuarioPerfilExistente = usuarioPerfilService.recuperaUsuarioPerfilPorId(id);
            if (usuarioPerfilExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Usu�rio Perfil com ID " + id + " n�o encontrado para exclus�o.")
                               .build();
            } 

            usuarioPerfilService.excluiUsuarioPerfil(id);
            return Response.noContent().build(); 
        } catch (MedmentorException e) {
            System.err.println("Erro ao excluir Usu�rio Perfil por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao excluir usu�rio perfil: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de exclus�o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }
}