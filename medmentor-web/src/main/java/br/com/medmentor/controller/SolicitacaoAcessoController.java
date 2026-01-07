package br.com.medmentor.controller;

import java.net.URI;
import java.util.List;

import br.com.medmentor.dto.SolicitacaoAcessoDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.service.SolicitacaoAcessoService;
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

@Path("/solicitacao-acesso") 
@Produces(MediaType.APPLICATION_JSON) 
@Consumes(MediaType.APPLICATION_JSON) 
public class SolicitacaoAcessoController {

	@Inject
    private SolicitacaoAcessoService acaoService;

    @POST
    public Response incluirSolicitacaoAcesso(SolicitacaoAcessoDTO solicitacaoAcessoDTO) {
        try {
            if (solicitacaoAcessoDTO == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o n�o pode ser vazio.")
                               .build();
            }
            SolicitacaoAcessoDTO novaSolicitacaoAcesso = acaoService.incluirSolicitacaoAcesso(solicitacaoAcessoDTO);
            return Response.created(URI.create("/api/acao/" + novaSolicitacaoAcesso.getId()))
                           .entity(novaSolicitacaoAcesso)
                           .build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao incluir A��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao incluir solicita��o de acesso: " + e.getMessage())
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
    public Response recuperarSolicitacaoAcessoPorId(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID da solicita��o de acesso n�o pode ser nulo.")
                               .build();
            }
            SolicitacaoAcessoDTO acao = acaoService.recuperarSolicitacaoAcessoPorId(id);
            if (acao != null) {
                return Response.ok(acao).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("A��o com ID " + id + " n�o encontrada.")
                               .build();
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao recuperar A��o por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao buscar solicita��o de acesso: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de recupera��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }

    @GET
    public Response recuperarListaSolicitacaoAcesso() {
        try {
            List<SolicitacaoAcessoDTO> acoes = acaoService.recuperarListaSolicitacaoAcesso();
            if (acoes.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(acoes).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todas as Solicita��es de Acesso: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar a��es: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de listagem: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }

    @PUT
    public Response alterarSolicitacaoAcesso(SolicitacaoAcessoDTO solicitacaoAcessoDTO) {
        try {
            if (solicitacaoAcessoDTO == null || solicitacaoAcessoDTO.getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o e o ID da solicita��o de acesso n�o podem ser vazios.")
                               .build();
            }
            acaoService.alterarSolicitacaoAcesso(solicitacaoAcessoDTO);
            return Response.ok().entity("A��o atualizada com sucesso.").build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao alterar A��o com ID " + solicitacaoAcessoDTO.getId() + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao atualizar solicita��o de acesso: " + e.getMessage())
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
    public Response excluirSolicitacaoAcesso(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID da solicita��o de acesso n�o pode ser nulo para exclus�o.")
                               .build();
            }

            SolicitacaoAcessoDTO acaoExistente = acaoService.recuperarSolicitacaoAcessoPorId(id);
            if (acaoExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("A��o com ID " + id + " n�o encontrada para exclus�o.")
                               .build();
            }

            acaoService.excluirSolicitacaoAcesso(id);
            return Response.noContent().build();  
        } catch (MedmentorException e) {
            System.err.println("Erro ao excluir A��o por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao excluir solicita��o de acesso: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de exclus�o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }
}