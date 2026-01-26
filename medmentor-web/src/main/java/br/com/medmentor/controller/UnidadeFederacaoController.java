package br.com.medmentor.controller;

import java.net.URI;
import java.util.List;

import br.com.medmentor.dto.UnidadeFederacaoDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.service.UnidadeFederacaoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/unidade-federacao") 
@Produces(MediaType.APPLICATION_JSON) 
public class UnidadeFederacaoController {

    @Inject
    private UnidadeFederacaoService unidadeFederacaoService;
    
    @POST
    public Response incluirUnidadeFederacao(UnidadeFederacaoDTO unidadeFederacaoDTO) {
        try {
            if (unidadeFederacaoDTO == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o n�o pode ser vazio.")
                               .build();
            }
            UnidadeFederacaoDTO novoUnidadeFederacao = unidadeFederacaoService.incluiUnidadeFederacao(unidadeFederacaoDTO);
            return Response.created(URI.create("/api/unidade-federacao/" + novoUnidadeFederacao.getId()))
                           .entity(novoUnidadeFederacao)
                           .build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao incluir UnidadeFederacao: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao incluir unidadeFederacao: " + e.getMessage())
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
    public Response recuperarUnidadeFederacaoPorId(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID do unidadeFederacao n�o pode ser nulo.")
                               .build();
            }
            UnidadeFederacaoDTO unidadeFederacao = unidadeFederacaoService.recuperaUnidadeFederacaoPorId(id);
            if (unidadeFederacao != null) {
                return Response.ok(unidadeFederacao).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("UnidadeFederacao com ID " + id + " n�o encontrado.")
                               .build();
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao recuperar UnidadeFederacao por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao buscar unidadeFederacao: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de recupera��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }

    @PUT
    public Response alterarUnidadeFederacao(UnidadeFederacaoDTO unidadeFederacaoDTO) {
        try {
            if (unidadeFederacaoDTO == null || unidadeFederacaoDTO.getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o e o ID do unidadeFederacao n�o podem ser vazios.")
                               .build();
            }
            unidadeFederacaoService.alteraUnidadeFederacao(unidadeFederacaoDTO);
            return Response.ok().entity("UnidadeFederacao atualizado com sucesso.").build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao alterar UnidadeFederacao com ID " + unidadeFederacaoDTO.getId() + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao atualizar unidadeFederacao: " + e.getMessage())
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
    public Response excluirUnidadeFederacao(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID do unidadeFederacao n�o pode ser nulo para exclus�o.")
                               .build();
            }

            UnidadeFederacaoDTO unidadeFederacaoExistente = unidadeFederacaoService.recuperaUnidadeFederacaoPorId(id);
            if (unidadeFederacaoExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("UnidadeFederacao com ID " + id + " n�o encontrado para exclus�o.")
                               .build();
            }

            unidadeFederacaoService.excluiUnidadeFederacao(id);
            return Response.noContent().build(); 
        } catch (MedmentorException e) { 
            System.err.println("Erro ao excluir UnidadeFederacao por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao excluir unidadeFederacao: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de exclus�o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }    

    @GET
    public Response listarTodasUnidadesFederacao() {
        try {
            List<UnidadeFederacaoDTO> unidadesFederacao = unidadeFederacaoService.listaTodos();

            if (unidadesFederacao.isEmpty()) {
                return Response.noContent().build();
            } else {
                return Response.ok(unidadesFederacao).build();
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todas as Unidades da Federa��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar unidades de federa��o: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }
}