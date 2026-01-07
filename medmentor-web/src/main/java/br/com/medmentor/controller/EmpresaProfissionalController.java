package br.com.medmentor.controller;

import java.net.URI;
import java.util.List;

import br.com.medmentor.dto.EmpresaProfissionalDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.service.EmpresaProfissionalService;
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

@Path("/empresa-profissional") 
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmpresaProfissionalController { 

    @Inject
    private EmpresaProfissionalService empresaProfissionalService;

    @POST
    public Response incluirEmpresaProfissional(EmpresaProfissionalDTO empresaProfissionalDTO) {
        try {
            if (empresaProfissionalDTO == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o n�o pode ser vazio.")
                               .build();
            }
            EmpresaProfissionalDTO novaAssociacao = empresaProfissionalService.incluirEmpresaProfissional(empresaProfissionalDTO);
            return Response.created(URI.create("/api/empresa-profissional/"
                                     + (novaAssociacao.getId() != null ? novaAssociacao.getId() : "null")
                                     + "/"
                                     + (novaAssociacao.getId() != null ? novaAssociacao.getId() : "null")))
                           .entity(novaAssociacao)
                           .build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao incluir associa��o Empresa-Profissional: " + e.getMessage());
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

    @DELETE
    @Path("/{idEmpresa}/{idProfissional}")
    public Response excluirEmpresaProfissional(
            @PathParam("idEmpresaProfissional") Integer idEmpresaProfissional) {
        try {
            if (idEmpresaProfissional == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("Os IDs da empresa e do profissional n�o podem ser nulos para exclus�o.")
                               .build();
            }

            empresaProfissionalService.excluirEmpresaProfissional(idEmpresaProfissional);
            return Response.noContent().build(); 
        } catch (MedmentorException e) {
            System.err.println("Erro ao excluir associa��o Empresa-Profissional (EmpresaProfissional ID: " + idEmpresaProfissional + "): " + e.getMessage());
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

    @PUT
    public Response alterarEmpresaProfissional(EmpresaProfissionalDTO empresaProfissionalDTO) {
        try {
            if (empresaProfissionalDTO == null || empresaProfissionalDTO.getId() == null || empresaProfissionalDTO.getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o e os IDs de empresa/profissional n�o podem ser vazios.")
                               .build();
            }
            empresaProfissionalService.alterarEmpresaProfissional(empresaProfissionalDTO);
            return Response.ok().entity(empresaProfissionalDTO).build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao alterar associa��o Empresa-Profissional (Empresa ID: " + empresaProfissionalDTO.getId() + ", Profissional ID: " + empresaProfissionalDTO.getId() + "): " + e.getMessage());
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
    
    @GET
    @Path("/{id}")
    public Response recuperarEmpresaProfissionalPorId(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID da associa��o n�o pode ser nulo.")
                               .build();
            }
            EmpresaProfissionalDTO associacao = empresaProfissionalService.recuperarEmpresaProfissionalPorId(id);
            if (associacao != null) {
                return Response.ok(associacao).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Associa��o Empresa-Unidade de Gest�o com ID " + id + " n�o encontrada.")
                               .build();
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao recuperar associa��o Empresa Profissional por ID (" + id + "): " + e.getMessage());
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

    @GET
    @Path("/por-profissional/{idProfissional}")
    public Response recuperarEmpresaProfissionalPorProfissional(@PathParam("idProfissional") Integer idProfissional) {
        try {
            if (idProfissional == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID do profissional n�o pode ser nulo.")
                               .build();
            }
            EmpresaProfissionalDTO EmpresaProfissional = empresaProfissionalService.recuperarEmpresaProfissionalPorProfissional(idProfissional);
            if (EmpresaProfissional != null) {
                return Response.ok(EmpresaProfissional).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Nenhuma associa��o encontrada para o Profissional com ID " + idProfissional + ".")
                               .build();
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao recuperar associa��o Empresa-Profissional por Profissional ID (" + idProfissional + "): " + e.getMessage());
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
    
    @GET
    @Path("todas")
    public Response recuperarListaEmpresaProfissional() {
        try {
            List<EmpresaProfissionalDTO> empresasProfissionais = empresaProfissionalService.recuperarListaEmpresaProfissional();
            if (empresasProfissionais.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(empresasProfissionais).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todos os EmpresasProfissionais: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar EmpresasProfissionais: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de listagem: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }    
}