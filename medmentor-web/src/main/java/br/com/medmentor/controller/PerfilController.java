package br.com.medmentor.controller;

import br.com.medmentor.dto.PerfilDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.service.PerfilService;
import jakarta.inject.Inject;
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
import java.net.URI;
import java.util.List;

@Path("/perfil") 
@Produces(MediaType.APPLICATION_JSON) 
@Consumes(MediaType.APPLICATION_JSON) 
public class PerfilController {

    @Inject
    private PerfilService perfilService;

    @POST
    public Response incluirPerfil(PerfilDTO perfilDTO) {
        try {
            if (perfilDTO == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o n�o pode ser vazio.")
                               .build();
            }
            PerfilDTO novoPerfil = perfilService.incluiPerfil(perfilDTO);
            return Response.created(URI.create("/api/perfil/" + novoPerfil.getId()))
                           .entity(novoPerfil)
                           .build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao incluir Perfil: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao incluir perfil: " + e.getMessage())
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
    public Response recuperarPerfilPorId(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID do perfil n�o pode ser nulo.")
                               .build();
            }
            PerfilDTO perfil = perfilService.recuperaPerfilPorId(id);
            if (perfil != null) {
                return Response.ok(perfil).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Perfil com ID " + id + " n�o encontrado.")
                               .build();
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao recuperar Perfil por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao buscar perfil: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de recupera��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }

    @GET
    public Response recuperarListaPerfil() {
        try {
            List<PerfilDTO> perfis = perfilService.recuperaListaPerfil();
            if (perfis.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(perfis).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todos os Perfis: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar perfis: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de listagem: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }

    @PUT
    public Response alterarPerfil(PerfilDTO perfilDTO) {
        try {
            if (perfilDTO == null || perfilDTO.getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o e o ID do perfil n�o podem ser vazios.")
                               .build();
            }
            perfilService.alteraPerfil(perfilDTO);
            return Response.ok().entity("Perfil atualizado com sucesso.").build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao alterar Perfil com ID " + perfilDTO.getId() + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao atualizar perfil: " + e.getMessage())
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
    public Response excluirPerfil(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID do perfil n�o pode ser nulo para exclus�o.")
                               .build();
            }

            PerfilDTO perfilExistente = perfilService.recuperaPerfilPorId(id);
            if (perfilExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Perfil com ID " + id + " n�o encontrado para exclus�o.")
                               .build(); 
            }

            perfilService.excluiPerfil(id);
            return Response.noContent().build(); 
        } catch (MedmentorException e) {
            System.err.println("Erro ao excluir Perfil por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao excluir perfil: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de exclus�o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }
}