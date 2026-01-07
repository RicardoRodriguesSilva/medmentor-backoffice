package br.com.medmentor.controller;

import br.com.medmentor.dto.TipoMenuDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.service.TipoMenuService;
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

@Path("/tipo-menu") 
@Produces(MediaType.APPLICATION_JSON) 
@Consumes(MediaType.APPLICATION_JSON) 
public class TipoMenuController {

    @Inject
    private TipoMenuService tipoTipoMenuService;

    @POST
    public Response incluirTipoMenu(TipoMenuDTO tipoTipoMenuDTO) {
        try {
            if (tipoTipoMenuDTO == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o n�o pode ser vazio.")
                               .build();
            }
            TipoMenuDTO novoTipoMenu = tipoTipoMenuService.incluirTipoMenu(tipoTipoMenuDTO);
            return Response.created(URI.create("/api/tipo-menu/" + novoTipoMenu.getId()))
                           .entity(novoTipoMenu)
                           .build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao incluir TipoMenu: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao incluir tipoTipoMenu: " + e.getMessage())
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
    public Response recuperarTipoMenuPorId(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID do tipoTipoMenu n�o pode ser nulo.")
                               .build();
            }
            TipoMenuDTO tipoTipoMenu = tipoTipoMenuService.recuperarTipoMenuPorId(id);
            if (tipoTipoMenu != null) {
                return Response.ok(tipoTipoMenu).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("TipoMenu com ID " + id + " n�o encontrado.")
                               .build();
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao recuperar TipoMenu por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao buscar tipoTipoMenu: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de recupera��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }

    @GET
    public Response recuperarListaTipoMenu() {
        try {
            List<TipoMenuDTO> tipoTipoMenus = tipoTipoMenuService.recuperarListaTipoMenu();
            if (tipoTipoMenus.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(tipoTipoMenus).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todos os TipoMenus: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar tipoTipoMenus: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de listagem: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }

    @PUT
    public Response alterarTipoMenu(TipoMenuDTO tipoTipoMenuDTO) {
        try {
            if (tipoTipoMenuDTO == null || tipoTipoMenuDTO.getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o e o ID do tipoTipoMenu n�o podem ser vazios.")
                               .build();
            }
            tipoTipoMenuService.alterarTipoMenu(tipoTipoMenuDTO);
            return Response.ok().entity("TipoMenu atualizado com sucesso.").build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao alterar TipoMenu com ID " + tipoTipoMenuDTO.getId() + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao atualizar tipoTipoMenu: " + e.getMessage())
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
    public Response excluirTipoMenu(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID do tipoTipoMenu n�o pode ser nulo para exclus�o.")
                               .build();
            }

            TipoMenuDTO tipoTipoMenuExistente = tipoTipoMenuService.recuperarTipoMenuPorId(id);
            if (tipoTipoMenuExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("TipoMenu com ID " + id + " n�o encontrado para exclus�o.")
                               .build();
            }

            tipoTipoMenuService.excluirTipoMenu(id);
            return Response.noContent().build(); 
        } catch (MedmentorException e) { 
            System.err.println("Erro ao excluir TipoMenu por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao excluir tipoTipoMenu: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de exclus�o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }
}