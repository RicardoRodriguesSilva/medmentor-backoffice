package br.com.medmentor.controller;

import java.net.URI;
import java.util.List;

import br.com.medmentor.dto.NotificacaoDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.filtro.dto.FiltroNotificacaoDTO;
import br.com.medmentor.service.NotificacaoService;
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

@Path("/notificacao") 
@Produces(MediaType.APPLICATION_JSON) 
@Consumes(MediaType.APPLICATION_JSON) 
public class NotificacaoController {

    @Inject
    private NotificacaoService notificacaoService;

    @POST
    public Response incluiNotificacao(NotificacaoDTO NotificacaoDTO) {
        try {
            if (NotificacaoDTO == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o n�o pode ser vazio.")
                               .build();
            }
            NotificacaoDTO novaNotificacao = notificacaoService.incluiNotificacao(NotificacaoDTO);
            return Response.created(URI.create("/api/notificacao/" + novaNotificacao.getId()))
                           .entity(novaNotificacao)
                           .build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao incluir Notificacao: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao incluir notificacao: " + e.getMessage())
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
    public Response recuperaNotificacaoPorId(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID da notificacao n�o pode ser nulo.")
                               .build();
            }
            NotificacaoDTO Notificacao = notificacaoService.recuperaNotificacaoPorId(id);
            if (Notificacao != null) {
                return Response.ok(Notificacao).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Notificacao com ID " + id + " n�o encontrada.")
                               .build();
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao recuperar Notificacao por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao buscar notificacao: " + e.getMessage())
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
    public Response recuperaListaNotificacao() {
        try {
            List<NotificacaoDTO> notificacaos = notificacaoService.recuperaListaNotificacao();
            if (notificacaos.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(notificacaos).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todas as Notificacaos de Trabalho: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar notificacaos: " + e.getMessage())
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
    public Response recuperaListaNotificacaoPorFiltro(@BeanParam FiltroNotificacaoDTO filtroNotificacaoDTO) {
        try {
            List<NotificacaoDTO> notificacaos = notificacaoService.
            		recuperaListaNotificacaoPorFiltro(filtroNotificacaoDTO);
            if (notificacaos.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(notificacaos).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todas as Notificacaos de Trabalho: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar notificacaos: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de listagem: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }    

    @PUT
    public Response alteraNotificacao(NotificacaoDTO NotificacaoDTO) {
        try {
            if (NotificacaoDTO == null || NotificacaoDTO.getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o e o ID da notificacao n�o podem ser vazios.")
                               .build();
            }
            notificacaoService.alteraNotificacao(NotificacaoDTO);
            return Response.ok().entity(NotificacaoDTO).build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao alterar Notificacao com ID " + NotificacaoDTO.getId() + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao atualizar notificacao: " + e.getMessage())
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
    public Response excluiNotificacao(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID da notificacao n�o pode ser nulo para exclus�o.")
                               .build();
            }

            NotificacaoDTO notificacaoExistente = notificacaoService.recuperaNotificacaoPorId(id);
            if (notificacaoExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Notificacao com ID " + id + " n�o encontrada para exclus�o.")
                               .build();
            }

            notificacaoService.excluiNotificacao(id);
            return Response.noContent().build(); 
        } catch (MedmentorException e) {
            System.err.println("Erro ao excluir Notificacao por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao excluir notificacao: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de exclus�o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }
}