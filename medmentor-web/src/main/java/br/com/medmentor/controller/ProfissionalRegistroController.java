package br.com.medmentor.controller;

import br.com.medmentor.dto.ProfissionalRegistroDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.service.ProfissionalRegistroService;
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
import java.net.URI;
import java.util.List;

@Path("/profissional-registro")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfissionalRegistroController {

    @Inject
    private ProfissionalRegistroService profissionalRegistroService;

    @POST
    public Response incluirProfissionalRegistro(ProfissionalRegistroDTO profissionalRegistroDTO) {
        try {
            if (profissionalRegistroDTO == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o n�o pode ser vazio.")
                               .build();
            }
            ProfissionalRegistroDTO novoRegistro = profissionalRegistroService.incluirProfissionalRegistro(profissionalRegistroDTO);
            return Response.created(URI.create("/api/profissional-registro/" + novoRegistro.getProfissionalDTO().getId()))
                           .entity(novoRegistro)
                           .build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao incluir Registro Profissional: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao incluir registro: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de inclus�o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }

    @GET
    @Path("/{idProfissional}")
    public Response recuperarProfissionalRegistroPorId(@PathParam("idProfissional") Integer idProfissional) {
        try {
            if (idProfissional == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID do profissional n�o pode ser nulo.")
                               .build();
            }
            ProfissionalRegistroDTO registro = profissionalRegistroService.recuperarProfissionalRegistroPorId(idProfissional);
            if (registro != null) {
                return Response.ok(registro).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Registro Profissional com ID " + idProfissional + " n�o encontrado.")
                               .build();
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao recuperar Registro Profissional por ID (" + idProfissional + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao buscar registro: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de recupera��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }

    @GET
    @Path("todos")
    public Response recuperarListaProfissionalRegistro() {
        try {
            List<ProfissionalRegistroDTO> registros = profissionalRegistroService.recuperarListaProfissionalRegistro();
            if (registros.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(registros).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todos os Registros Profissionais: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar registros: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de listagem: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }
    
    @GET
    @Path("/por-nome")
    public Response recuperarListaProfissionalRegistroPorNome(@QueryParam("nome") String nome) {
        try {
            List<ProfissionalRegistroDTO> registros = profissionalRegistroService.recuperarListaProfissionalRegistro();
            if (registros.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(registros).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todos os Registros Profissionais: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar registros: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de listagem: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }    

    @PUT
    public Response alterarProfissionalRegistro(ProfissionalRegistroDTO profissionalRegistroDTO) {
        try {
            if (profissionalRegistroDTO == null || profissionalRegistroDTO.getProfissionalDTO().getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o e o ID do profissional n�o podem ser vazios.")
                               .build();
            }
            profissionalRegistroService.alterarProfissionalRegistro(profissionalRegistroDTO);
            return Response.ok().build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao alterar Registro Profissional com ID " + profissionalRegistroDTO.getProfissionalDTO().getId() + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao atualizar registro: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de altera��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }

    @DELETE
    @Path("/{idProfissional}")
    public Response excluirProfissionalRegistro(@PathParam("idProfissional") Integer idProfissional) {
        try {
            if (idProfissional == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID do profissional n�o pode ser nulo para exclus�o.")
                               .build();
            }
            ProfissionalRegistroDTO registroExistente = profissionalRegistroService.recuperarProfissionalRegistroPorId(idProfissional);
            if (registroExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Registro Profissional com ID " + idProfissional + " n�o encontrado para exclus�o.")
                               .build();
            }

            profissionalRegistroService.excluirProfissionalRegistro(idProfissional);
            return Response.noContent().build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao excluir Registro Profissional por ID (" + idProfissional + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao excluir registro: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de exclus�o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }
}