package br.com.medmentor.controller;

import java.net.URI;
import java.util.List;

import br.com.medmentor.dto.ProfissionalDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.service.ProfissionalService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/profissional")
@Produces(MediaType.APPLICATION_JSON) 
@Consumes(MediaType.APPLICATION_JSON)
public class ProfissionalController {

    @Inject
    private ProfissionalService profissionalService;

    @POST
    public Response incluirProfissional(ProfissionalDTO profissionalDTO) {
        try {
            if (profissionalDTO == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o n�o pode ser vazio.")
                               .build();
            }
            ProfissionalDTO novoProfissional = profissionalService.incluiProfissional(profissionalDTO);
            return Response.created(URI.create("/api/profissional/" + novoProfissional.getId()))
                           .entity(novoProfissional)
                           .build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao incluir Profissional: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao incluir profissional: " + e.getMessage())
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
    public Response recuperarProfissionalPorId(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID do profissional n�o pode ser nulo.")
                               .build();
            }
            ProfissionalDTO profissional = profissionalService.recuperaProfissionalPorId(id);
            if (profissional != null) {
                return Response.ok(profissional).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Profissional com ID " + id + " n�o encontrado.")
                               .build();
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao recuperar Profissional por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao buscar profissional: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de recupera��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }
    
    @GET
    @Path("/por-nome") 
    public Response listarTodosPorNome(@QueryParam("nome") String nome) {
        try {
            List<ProfissionalDTO> profissionais = profissionalService.recuperaListaProfissionalPorNome(nome);
            if (profissionais.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(profissionais).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todas as Unidades de Gestao: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar Profissionais : " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de listagem: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }   
    
    @GET
    @Path("todos")
    public Response recuperarListaProfissional() {
        try {
            List<ProfissionalDTO> profissionais = profissionalService.recuperaListaProfissional();
            if (profissionais.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(profissionais).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todas as Unidades de Gestao: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar Profissionais : " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de listagem: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }      

    @GET
    @Path("/por-cidade/{idCidade}")
    public Response recuperarProfissionalPorCidade(@PathParam("idCidade") Integer idCidade) {
        try {
            if (idCidade == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID da cidade n�o pode ser nulo.")
                               .build();
            }
            List<ProfissionalDTO> profissionais = profissionalService.recuperaProfissionalPorCidade(idCidade);
            if (profissionais.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(profissionais).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar Profissionais por Cidade (" + idCidade + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar profissionais: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de listagem por cidade: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }

    @PUT
    public Response alterarProfissional(ProfissionalDTO profissionalDTO) {
        try {
            if (profissionalDTO == null || profissionalDTO.getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o e o ID do profissional n�o podem ser vazios.")
                               .build();
            }
            profissionalService.alteraProfissional(profissionalDTO);
            return Response.ok(profissionalDTO).build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao alterar Profissional com ID " + profissionalDTO.getId() + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao atualizar profissional: " + e.getMessage())
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
    public Response excluirProfissional(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID do profissional n�o pode ser nulo para exclus�o.")
                               .build();
            }

            ProfissionalDTO profissionalExistente = profissionalService.recuperaProfissionalPorId(id);
            if (profissionalExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Profissional com ID " + id + " n�o encontrado para exclus�o.")
                               .build();
            }

            profissionalService.excluiProfissional(id);
            return Response.noContent().build(); 
        } catch (MedmentorException e) {
            System.err.println("Erro ao excluir Profissional por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao excluir profissional: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de exclus�o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }
}