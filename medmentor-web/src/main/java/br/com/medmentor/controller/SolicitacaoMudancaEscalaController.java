package br.com.medmentor.controller;

import java.net.URI;
import java.util.List;

import br.com.medmentor.dto.SolicitacaoMudancaEscalaDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.filtro.dto.FiltroSolicitacaoMudancaEscalaDTO;
import br.com.medmentor.service.SolicitacaoMudancaEscalaService;
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

@Path("/solicitacaoMudancaEscala") 
@Produces(MediaType.APPLICATION_JSON) 
@Consumes(MediaType.APPLICATION_JSON) 
public class SolicitacaoMudancaEscalaController {

    @Inject
    private SolicitacaoMudancaEscalaService solicitacaoMudancaEscalaService;

    @POST
    public Response incluiSolicitacaoMudancaEscala(SolicitacaoMudancaEscalaDTO SolicitacaoMudancaEscalaDTO) {
        try {
            if (SolicitacaoMudancaEscalaDTO == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o n�o pode ser vazio.")
                               .build();
            }
            SolicitacaoMudancaEscalaDTO novaSolicitacaoMudancaEscala = solicitacaoMudancaEscalaService.incluiSolicitacaoMudancaEscala(SolicitacaoMudancaEscalaDTO);
            return Response.created(URI.create("/api/solicitacaoMudancaEscala/" + novaSolicitacaoMudancaEscala.getId()))
                           .entity(novaSolicitacaoMudancaEscala)
                           .build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao incluir SolicitacaoMudancaEscala: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao incluir solicitacaoMudancaEscala: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de inclus�o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(e.getMessage())
                           .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response recuperaSolicitacaoMudancaEscalaPorId(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID da solicitacaoMudancaEscala n�o pode ser nulo.")
                               .build();
            }
            SolicitacaoMudancaEscalaDTO SolicitacaoMudancaEscala = solicitacaoMudancaEscalaService.recuperaSolicitacaoMudancaEscalaPorId(id);
            if (SolicitacaoMudancaEscala != null) {
                return Response.ok(SolicitacaoMudancaEscala).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("SolicitacaoMudancaEscala com ID " + id + " n�o encontrada.")
                               .build();
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao recuperar SolicitacaoMudancaEscala por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao buscar solicitacaoMudancaEscala: " + e.getMessage())
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
    public Response recuperaListaSolicitacaoMudancaEscala() {
        try {
            List<SolicitacaoMudancaEscalaDTO> solicitacaoMudancaEscalas = solicitacaoMudancaEscalaService.recuperaListaSolicitacaoMudancaEscala();
            if (solicitacaoMudancaEscalas.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(solicitacaoMudancaEscalas).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todas as SolicitacaoMudancaEscalas de Trabalho: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar solicitacaoMudancaEscalas: " + e.getMessage())
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
    public Response recuperaListaSolicitacaoMudancaEscalaPorFiltro(@BeanParam FiltroSolicitacaoMudancaEscalaDTO filtroSolicitacaoMudancaEscalaDTO) {
        try {
            List<SolicitacaoMudancaEscalaDTO> solicitacaoMudancaEscalas = solicitacaoMudancaEscalaService.
            		recuperaListaSolicitacaoMudancaEscalaPorFiltro(filtroSolicitacaoMudancaEscalaDTO);
            if (solicitacaoMudancaEscalas.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(solicitacaoMudancaEscalas).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todas as SolicitacaoMudancaEscalas de Trabalho: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar solicitacaoMudancaEscalas: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de listagem: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }    

    @PUT
    @Path("/aceita/{id}")
    public Response aceitaSolicitacaoMudancaEscala(@PathParam("id") Integer id) {
        try {
        	if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID da solicitacaoMudancaEscala n�o pode ser nulo.")
                               .build();
            }
            solicitacaoMudancaEscalaService.aceitaSolicitacaoMudancaEscala(id); 
            return Response.ok().build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao aceitar SolicitacaoMudancaEscala com ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao atualizar solicitacaoMudancaEscala: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de aceite: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }
    
    @PUT
    @Path("/recusa/{id}")
    public Response recusaSolicitacaoMudancaEscala(@PathParam("id") Integer id) {
        try {
        	if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID da solicitacaoMudancaEscala n�o pode ser nulo.")
                               .build();
            }
            solicitacaoMudancaEscalaService.recusaSolicitacaoMudancaEscala(id); 
            return Response.ok().build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao recusar SolicitacaoMudancaEscala com ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao atualizar solicitacaoMudancaEscala: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de recusa: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }    

    @DELETE
    @Path("/{id}")
    public Response excluiSolicitacaoMudancaEscala(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID da solicitacaoMudancaEscala n�o pode ser nulo para exclus�o.")
                               .build();
            }

            SolicitacaoMudancaEscalaDTO solicitacaoMudancaEscalaExistente = solicitacaoMudancaEscalaService.recuperaSolicitacaoMudancaEscalaPorId(id);
            if (solicitacaoMudancaEscalaExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("SolicitacaoMudancaEscala com ID " + id + " n�o encontrada para exclus�o.")
                               .build();
            }

            solicitacaoMudancaEscalaService.excluiSolicitacaoMudancaEscala(id);
            return Response.noContent().build(); 
        } catch (MedmentorException e) {
            System.err.println("Erro ao excluir SolicitacaoMudancaEscala por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao excluir solicitacaoMudancaEscala: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de exclus�o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }
}