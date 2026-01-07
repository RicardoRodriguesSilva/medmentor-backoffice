package br.com.medmentor.controller;

import java.net.URI;
import java.util.List;

import br.com.medmentor.dto.EmpresaGestaoDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.service.EmpresaGestaoService;
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

@Path("/empresa-gestao") 
@Produces(MediaType.APPLICATION_JSON) 
@Consumes(MediaType.APPLICATION_JSON) 
public class EmpresaGestaoController { 

    @Inject
    private EmpresaGestaoService empresaGestaoService;

    @POST
    public Response incluirEmpresaGestao(EmpresaGestaoDTO empresaGestaoDTO) {
        try {
            if (empresaGestaoDTO == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o n�o pode ser vazio.")
                               .build();
            }
            EmpresaGestaoDTO novaEmpresa = empresaGestaoService.incluirEmpresaGestao(empresaGestaoDTO);
            return Response.created(URI.create("/api/empresa-gestao/" + novaEmpresa.getId()))
                           .entity(novaEmpresa)
                           .build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao incluir EmpresaGestao: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao incluir empresa: " + e.getMessage())
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
    public Response recuperarEmpresaGestaoPorId(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID da empresa n�o pode ser nulo.")
                               .build();
            }
            EmpresaGestaoDTO empresaGestao = empresaGestaoService.recuperarEmpresaGestaoPorId(id);
            if (empresaGestao != null) {
                return Response.ok(empresaGestao).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("EmpresaGestao com ID " + id + " n�o encontrada.")
                               .build();
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao recuperar EmpresaGestao por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao buscar empresa: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de recupera��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }

    @PUT
    public Response alterarEmpresaGestao(EmpresaGestaoDTO empresaGestaoDTO) {
        try {
            if (empresaGestaoDTO == null || empresaGestaoDTO.getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o e o ID da empresa n�o podem ser vazios.")
                               .build();
            }
            empresaGestaoService.alterarEmpresaGestao(empresaGestaoDTO);
            return Response.ok().entity(empresaGestaoDTO).build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao alterar EmpresaGestao com ID " + empresaGestaoDTO.getId() + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao atualizar empresa: " + e.getMessage())
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
    public Response excluirEmpresaGestao(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID da empresa n�o pode ser nulo para exclus�o.")
                               .build();
            }

            EmpresaGestaoDTO empresaExistente = empresaGestaoService.recuperarEmpresaGestaoPorId(id);
            if (empresaExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("EmpresaGestao com ID " + id + " n�o encontrada para exclus�o.")
                               .build();
            }

            empresaGestaoService.excluirEmpresaGestao(id);
            return Response.noContent().build(); 
        } catch (MedmentorException e) {
            System.err.println("Erro ao excluir EmpresaGestao por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao excluir empresa: " + e.getMessage())
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
    public Response recuperarListaEmpresaGestao() {
        try {
            List<EmpresaGestaoDTO> empresaGestaos = empresaGestaoService.recuperarListaEmpresaGestao();
            if (empresaGestaos.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(empresaGestaos).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todos os EmpresaGestaos: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar empresaGestaos: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de listagem: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }
    
}