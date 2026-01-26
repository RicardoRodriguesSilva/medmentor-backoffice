package br.com.medmentor.controller;

import java.net.URI;
import java.util.List;

import br.com.medmentor.dto.EmpresaUnidadeGestaoDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.service.EmpresaUnidadeGestaoService;
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

@Path("/empresa-unidade-gestao") 
@Produces(MediaType.APPLICATION_JSON) 
@Consumes(MediaType.APPLICATION_JSON) 
public class EmpresaUnidadeGestaoController {

    @Inject
    private EmpresaUnidadeGestaoService empresaUnidadeGestaoService;

    @POST
    public Response incluirEmpresaUnidadeGestao(EmpresaUnidadeGestaoDTO empresaUnidadeGestaoDTO) {
        try {
            if (empresaUnidadeGestaoDTO == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o n�o pode ser vazio.")
                               .build();
            }
            EmpresaUnidadeGestaoDTO novaAssociacao = empresaUnidadeGestaoService.incluiEmpresaUnidadeGestao(empresaUnidadeGestaoDTO);
            return Response.created(URI.create("/api/empresa-unidade-gestao/" + novaAssociacao.getId()))
                           .entity(novaAssociacao)
                           .build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao incluir associa��o Empresa-Unidade de Gest�o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao incluir associa��o: " + e.getMessage())
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
    public Response recuperarEmpresaUnidadeGestaoPorId(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID da associa��o n�o pode ser nulo.")
                               .build();
            }
            EmpresaUnidadeGestaoDTO associacao = empresaUnidadeGestaoService.recuperaEmpresaUnidadeGestaoPorId(id);
            if (associacao != null) {
                return Response.ok(associacao).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Associa��o Empresa-Unidade de Gest�o com ID " + id + " n�o encontrada.")
                               .build();
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao recuperar associa��o Empresa-Unidade de Gest�o por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao buscar associa��o: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de recupera��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }

    @PUT
    public Response alterarEmpresaUnidadeGestao(EmpresaUnidadeGestaoDTO empresaUnidadeGestaoDTO) {
        try {
            if (empresaUnidadeGestaoDTO == null || empresaUnidadeGestaoDTO.getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o e o ID da associa��o n�o podem ser vazios.")
                               .build();
            }
            empresaUnidadeGestaoService.alteraEmpresaUnidadeGestao(empresaUnidadeGestaoDTO);
            return Response.ok().entity(empresaUnidadeGestaoDTO).build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao alterar associa��o Empresa-Unidade de Gest�o com ID " + empresaUnidadeGestaoDTO.getId() + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao atualizar associa��o: " + e.getMessage())
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
    public Response excluirEmpresaUnidadeGestao(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID da associa��o n�o pode ser nulo para exclus�o.")
                               .build();
            }
            EmpresaUnidadeGestaoDTO associacaoExistente = empresaUnidadeGestaoService.recuperaEmpresaUnidadeGestaoPorId(id);
            if (associacaoExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Associa��o Empresa-Unidade de Gest�o com ID " + id + " n�o encontrada para exclus�o.")
                               .build();
            }

            empresaUnidadeGestaoService.excluiEmpresaUnidadeGestao(id);
            return Response.noContent().build(); 
        } catch (MedmentorException e) {
            System.err.println("Erro ao excluir associa��o Empresa-Unidade de Gest�o por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao excluir associa��o: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de exclus�o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }
    
    @GET
    @Path("todas")
    public Response recuperarListaEmpresaUnidadeGestao() {
        try {
            List<EmpresaUnidadeGestaoDTO> empresaGestaos = empresaUnidadeGestaoService.recuperaListaEmpresaUnidadeGestao();
            if (empresaGestaos.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(empresaGestaos).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todas as Unidades de Gestao: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar Unidades de gestao: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de listagem: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }    
}