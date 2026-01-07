package br.com.medmentor.controller;

import br.com.medmentor.dto.MenuDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.service.MenuService;
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

@Path("/menu") 
@Produces(MediaType.APPLICATION_JSON) 
@Consumes(MediaType.APPLICATION_JSON) 
public class MenuController {

    @Inject
    private MenuService menuService;

    @POST
    public Response incluirMenu(MenuDTO menuDTO) {
        try {
            if (menuDTO == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o n�o pode ser vazio.")
                               .build();
            }
            MenuDTO novoMenu = menuService.incluirMenu(menuDTO);
            return Response.created(URI.create("/api/menu/" + novoMenu.getId()))
                           .entity(novoMenu)
                           .build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao incluir Menu: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao incluir menu: " + e.getMessage())
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
    public Response recuperarMenuPorId(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID do menu n�o pode ser nulo.")
                               .build();
            }
            MenuDTO menu = menuService.recuperarMenuPorId(id);
            if (menu != null) {
                return Response.ok(menu).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Menu com ID " + id + " n�o encontrado.")
                               .build();
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao recuperar Menu por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao buscar menu: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de recupera��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }

    @GET
    public Response recuperarListaMenu() {
        try {
            List<MenuDTO> menus = menuService.recuperarListaMenu();
            if (menus.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(menus).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todos os Menus: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar menus: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de listagem: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }

    @PUT
    public Response alterarMenu(MenuDTO menuDTO) {
        try {
            if (menuDTO == null || menuDTO.getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o e o ID do menu n�o podem ser vazios.")
                               .build();
            }
            menuService.alterarMenu(menuDTO);
            return Response.ok().entity(menuDTO).build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao alterar Menu com ID " + menuDTO.getId() + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao atualizar menu: " + e.getMessage())
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
    public Response excluirMenu(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID do menu n�o pode ser nulo para exclus�o.")
                               .build();
            }

            MenuDTO menuExistente = menuService.recuperarMenuPorId(id);
            if (menuExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Menu com ID " + id + " n�o encontrado para exclus�o.")
                               .build();
            }

            menuService.excluirMenu(id);
            return Response.noContent().build(); 
        } catch (MedmentorException e) { 
            System.err.println("Erro ao excluir Menu por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao excluir menu: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de exclus�o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }
}