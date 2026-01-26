package br.com.medmentor.controller;

import br.com.medmentor.dto.PerfilMenuAcaoDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.service.PerfilMenuAcaoService;
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

@Path("/perfil-menu-acao") 
@Produces(MediaType.APPLICATION_JSON) 
@Consumes(MediaType.APPLICATION_JSON) 
public class PerfilMenuAcaoController {

    @Inject
    private PerfilMenuAcaoService perfilMenuAcaoService;

    @POST
    public Response incluirPerfilMenuAcao(PerfilMenuAcaoDTO perfilMenuAcaoDTO) {
        try {
            if (perfilMenuAcaoDTO == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o n�o pode ser vazio.")
                               .build();
            }
            PerfilMenuAcaoDTO novoPerfilMenuAcao = perfilMenuAcaoService.incluiPerfilMenuAcao(perfilMenuAcaoDTO);
            return Response.created(URI.create("/api/perfil-menu-acao/" + novoPerfilMenuAcao.getId()))
                           .entity(novoPerfilMenuAcao)
                           .build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao incluir PerfilMenuAcao: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao incluir perfil menu acao: " + e.getMessage())
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
    public Response recuperarPerfilMenuAcaoPorId(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID do perfil menu acao n�o pode ser nulo.")
                               .build();
            }
            PerfilMenuAcaoDTO perfilMenuAcao = perfilMenuAcaoService.recuperaPerfilMenuAcaoPorId(id);
            if (perfilMenuAcao != null) {
                return Response.ok(perfilMenuAcao).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("PerfilMenuAcao com ID " + id + " n�o encontrado.")
                               .build();
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao recuperar PerfilMenuAcao por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao buscar perfil menu acao: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de recupera��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }

    @GET
    public Response recuperarListaPerfilMenuAcao() {
        try {
            List<PerfilMenuAcaoDTO> perfilMenuAcoes = perfilMenuAcaoService.recuperaListaPerfilMenuAcao();
            if (perfilMenuAcoes.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(perfilMenuAcoes).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todas as PerfilMenuAcoes: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar perfil menu acoes: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de listagem: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }

    @PUT
    public Response alterarPerfilMenuAcao(PerfilMenuAcaoDTO perfilMenuAcaoDTO) {
        try {
            if (perfilMenuAcaoDTO == null || perfilMenuAcaoDTO.getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o e o ID do perfil menu acao n�o podem ser vazios.")
                               .build();
            }
            perfilMenuAcaoService.alteraPerfilMenuAcao(perfilMenuAcaoDTO);
            return Response.ok().entity("PerfilMenuAcao atualizada com sucesso.").build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao alterar PerfilMenuAcao com ID " + perfilMenuAcaoDTO.getId() + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao atualizar perfil menu acao: " + e.getMessage())
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
    public Response excluirPerfilMenuAcao(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID do perfil menu acao n�o pode ser nulo para exclus�o.")
                               .build();
            }

            PerfilMenuAcaoDTO perfilMenuAcaoExistente = perfilMenuAcaoService.recuperaPerfilMenuAcaoPorId(id);
            if (perfilMenuAcaoExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("PerfilMenuAcao com ID " + id + " n�o encontrada para exclus�o.")
                               .build();
            } 

            perfilMenuAcaoService.excluiPerfilMenuAcao(id);
            return Response.noContent().build(); 
        } catch (MedmentorException e) {
            System.err.println("Erro ao excluir PerfilMenuAcao por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao excluir perfil menu acao: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de exclus�o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }
}