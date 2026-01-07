package br.com.medmentor.controller;

import br.com.medmentor.dto.MudancaEscalaTrabalhoDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.service.MudancaEscalaTrabalhoService;
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

@Path("/mudanca-escala-trabalho") 
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MudancaEscalaTrabalhoController {

    @Inject
    private MudancaEscalaTrabalhoService mudancaEscalaTrabalhoService;

    @POST
    public Response incluirMudancaEscalaTrabalho(MudancaEscalaTrabalhoDTO mudancaEscalaTrabalhoDTO) {
        try {
            if (mudancaEscalaTrabalhoDTO == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o n�o pode ser vazio.")
                               .build();
            }
            MudancaEscalaTrabalhoDTO novaMudanca = mudancaEscalaTrabalhoService.incluirMudancaEscalaTrabalho(mudancaEscalaTrabalhoDTO);
            return Response.created(URI.create("/api/mudanca-escala-trabalho/" + novaMudanca.getIdMudancaEscalaTrabalho()))
                           .entity(novaMudanca)
                           .build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao incluir Mudan�a de Escala de Trabalho: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao incluir mudan�a de escala: " + e.getMessage())
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
    public Response recuperarMudancaEscalaTrabalhoPorId(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID da mudan�a de escala n�o pode ser nulo.")
                               .build();
            }
            MudancaEscalaTrabalhoDTO mudancaEscala = mudancaEscalaTrabalhoService.recuperarMudancaEscalaTrabalhoPorId(id);
            if (mudancaEscala != null) {
                return Response.ok(mudancaEscala).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Mudan�a de Escala de Trabalho com ID " + id + " n�o encontrada.")
                               .build();
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao recuperar Mudan�a de Escala de Trabalho por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao buscar mudan�a de escala: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de recupera��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }

    @GET
    public Response recuperarListaMudancaEscalaTrabalho() {
        try {
            List<MudancaEscalaTrabalhoDTO> mudancas = mudancaEscalaTrabalhoService.recuperarListaMudancaEscalaTrabalho();
            if (mudancas.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(mudancas).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todas as Mudan�as de Escalas de Trabalho: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar mudan�as de escalas: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de listagem: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }

    @PUT
    public Response alterarMudancaEscalaTrabalho(MudancaEscalaTrabalhoDTO mudancaEscalaTrabalhoDTO) {
        try {
            if (mudancaEscalaTrabalhoDTO == null || mudancaEscalaTrabalhoDTO.getIdMudancaEscalaTrabalho() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o e o ID da mudan�a de escala n�o podem ser vazios.")
                               .build();
            }
            mudancaEscalaTrabalhoService.alterarMudancaEscalaTrabalho(mudancaEscalaTrabalhoDTO);
            return Response.ok().entity("Mudan�a de Escala de Trabalho atualizada com sucesso.").build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao alterar Mudan�a de Escala de Trabalho com ID " + mudancaEscalaTrabalhoDTO.getIdMudancaEscalaTrabalho() + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao atualizar mudan�a de escala: " + e.getMessage())
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
    public Response excluirMudancaEscalaTrabalho(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID da mudan�a de escala n�o pode ser nulo para exclus�o.")
                               .build();
            }
            MudancaEscalaTrabalhoDTO mudancaExistente = mudancaEscalaTrabalhoService.recuperarMudancaEscalaTrabalhoPorId(id);
            if (mudancaExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Mudan�a de Escala de Trabalho com ID " + id + " n�o encontrada para exclus�o.")
                               .build();
            }

            mudancaEscalaTrabalhoService.excluirMudancaEscalaTrabalho(id);
            return Response.noContent().build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao excluir Mudan�a de Escala de Trabalho por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao excluir mudan�a de escala: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de exclus�o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }
}