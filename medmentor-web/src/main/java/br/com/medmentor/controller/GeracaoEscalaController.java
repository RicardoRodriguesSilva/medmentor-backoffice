package br.com.medmentor.controller;

import java.net.URI;
import java.util.List;

import br.com.medmentor.dto.GeracaoEscalaDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.filtro.dto.FiltroGeracaoEscalaDTO;
import br.com.medmentor.service.GeracaoEscalaService;
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

@Path("/geracao-escala") 
@Produces(MediaType.APPLICATION_JSON) 
@Consumes(MediaType.APPLICATION_JSON) 
public class GeracaoEscalaController {

    @Inject
    private GeracaoEscalaService geracaoEscalaService;

    @POST
    public Response incluirGeracaoEscala(GeracaoEscalaDTO geracaoEscalaDTO) {
        try {
            if (geracaoEscalaDTO == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o n�o pode ser vazio.")
                               .build();
            }
            GeracaoEscalaDTO novaEscala = geracaoEscalaService.incluirGeracaoEscala(geracaoEscalaDTO);
            return Response.created(URI.create("/api/escala-trabalho/" + novaEscala.getId()))
                           .entity(novaEscala)
                           .build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao incluir Escala de Trabalho: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao incluir escala: " + e.getMessage())
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
    public Response recuperarGeracaoEscalaPorId(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID da escala n�o pode ser nulo.")
                               .build();
            }
            GeracaoEscalaDTO geracaoEscala = geracaoEscalaService.recuperarGeracaoEscalaPorId(id);
            if (geracaoEscala != null) {
                return Response.ok(geracaoEscala).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Escala de Trabalho com ID " + id + " n�o encontrada.")
                               .build();
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao recuperar Escala de Trabalho por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao buscar escala: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de recupera��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }

    @GET
    @Path("todas")
    public Response recuperarListaGeracaoEscala() {
        try {
            List<GeracaoEscalaDTO> escalas = geracaoEscalaService.recuperarListaGeracaoEscala();
            if (escalas.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(escalas).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todas as Escalas de Trabalho: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar escalas: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de listagem: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }
    
    @GET
    @Path("/por-filtros")
    public Response recuperarListaGeracaoEscalaPorFiltro(@BeanParam FiltroGeracaoEscalaDTO filtroGeracaoEscalaDTO) {
        try {
            List<GeracaoEscalaDTO> escalas = geracaoEscalaService.
            		recuperarListaGeracaoEscalaPorFiltro(filtroGeracaoEscalaDTO);
            if (escalas.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(escalas).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todas as Escalas de Trabalho: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar escalas: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de listagem: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }    

    @PUT
    public Response alterarGeracaoEscala(GeracaoEscalaDTO geracaoEscalaDTO) {
        try {
            if (geracaoEscalaDTO == null || geracaoEscalaDTO.getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o e o ID da escala n�o podem ser vazios.")
                               .build();
            }
            geracaoEscalaService.alterarGeracaoEscala(geracaoEscalaDTO);
            return Response.ok().entity(geracaoEscalaDTO).build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao alterar Escala de Trabalho com ID " + geracaoEscalaDTO.getId() + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao atualizar escala: " + e.getMessage())
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
    public Response excluirGeracaoEscala(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID da escala n�o pode ser nulo para exclus�o.")
                               .build();
            }

            GeracaoEscalaDTO escalaExistente = geracaoEscalaService.recuperarGeracaoEscalaPorId(id);
            if (escalaExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Escala de Trabalho com ID " + id + " n�o encontrada para exclus�o.")
                               .build();
            }

            geracaoEscalaService.excluirGeracaoEscala(id);
            return Response.noContent().build(); 
        } catch (MedmentorException e) {
            System.err.println("Erro ao excluir Escala de Trabalho por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao excluir escala: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de exclus�o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }
}