package br.com.medmentor.controller;

import java.net.URI;
import java.util.List;

import br.com.medmentor.dto.EscalaTrabalhoDTO;
import br.com.medmentor.dto.HorasEscalaTrabalhoDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.filtro.dto.FiltroEscalaTrabalhoDTO;
import br.com.medmentor.filtro.dto.FiltroHorasEscalaTrabalhoDTO;
import br.com.medmentor.service.EscalaTrabalhoService;
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

@Path("/escala-trabalho") 
@Produces(MediaType.APPLICATION_JSON) 
@Consumes(MediaType.APPLICATION_JSON) 
public class EscalaTrabalhoController {

    @Inject
    private EscalaTrabalhoService escalaTrabalhoService;

    @POST
    public Response incluiEscalaTrabalho(EscalaTrabalhoDTO escalaTrabalhoDTO) {
        try {
            if (escalaTrabalhoDTO == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o n�o pode ser vazio.")
                               .build();
            }
            EscalaTrabalhoDTO novaEscala = escalaTrabalhoService.incluiEscalaTrabalho(escalaTrabalhoDTO);
            return Response.created(URI.create("/api/escala-trabalho/" + novaEscala.getId()))
                           .entity(novaEscala)
                           .build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao incluir Escala de Trabalho: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao incluir escala: " + e.getMessage())
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
    public Response recuperaEscalaTrabalhoPorId(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID da escala n�o pode ser nulo.")
                               .build();
            }
            EscalaTrabalhoDTO escalaTrabalho = escalaTrabalhoService.recuperaEscalaTrabalhoPorId(id);
            if (escalaTrabalho != null) {
                return Response.ok(escalaTrabalho).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Escala de Trabalho com ID " + id + " n�o encontrada.")
                               .build();
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao recuperar Escala de Trabalho por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao buscar escala: " + e.getMessage())
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
    public Response recuperaListaEscalaTrabalho() {
        try {
            List<EscalaTrabalhoDTO> escalas = escalaTrabalhoService.recuperaListaEscalaTrabalho();
            if (escalas.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(escalas).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todas as Escalas de Trabalho: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar escalas: " + e.getMessage())
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
    public Response recuperaListaEscalaTrabalhoFiltros(@BeanParam FiltroEscalaTrabalhoDTO filtroEscalaTrabalhoDTO) {
        try {
            List<EscalaTrabalhoDTO> escalas = escalaTrabalhoService.recuperaListaEscalaTrabalhoPorFiltro(filtroEscalaTrabalhoDTO);
            if (escalas.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(escalas).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todas as Escalas de Trabalho: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar escalas: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de listagem: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }
    
    @GET
    @Path("/horas/por-filtros")
    public Response recuperaListaHorasEscalaTrabalhoPorFiltro(@BeanParam FiltroHorasEscalaTrabalhoDTO filtroHorasEscalaTrabalhoDTO) {
        try {
        	
            List<HorasEscalaTrabalhoDTO> escalas = escalaTrabalhoService.recuperaListaHorasEscalaTrabalhoPorFiltro(filtroHorasEscalaTrabalhoDTO);
            if (escalas.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(escalas).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todas as Escalas de Trabalho: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar escalas: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de listagem: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }    

    @PUT
    public Response alteraEscalaTrabalho(EscalaTrabalhoDTO escalaTrabalhoDTO) {
        try {
            if (escalaTrabalhoDTO == null || escalaTrabalhoDTO.getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o e o ID da escala n�o podem ser vazios.")
                               .build();
            }
            escalaTrabalhoService.alteraEscalaTrabalho(escalaTrabalhoDTO);
            return Response.ok().entity(escalaTrabalhoDTO).build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao alterar Escala de Trabalho com ID " + escalaTrabalhoDTO.getId() + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao atualizar escala: " + e.getMessage())
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
    public Response excluiEscalaTrabalho(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID da escala n�o pode ser nulo para exclus�o.")
                               .build();
            }

            EscalaTrabalhoDTO escalaExistente = escalaTrabalhoService.recuperaEscalaTrabalhoPorId(id);
            if (escalaExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Escala de Trabalho com ID " + id + " n�o encontrada para exclus�o.")
                               .build();
            }

            escalaTrabalhoService.excluiEscalaTrabalho(id);
            return Response.noContent().build(); 
        } catch (MedmentorException e) {
            System.err.println("Erro ao excluir Escala de Trabalho por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao excluir escala: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de exclus�o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }
    
    @PUT
    @Path("/disponibilza/{id}")
    public Response disponibilizaEscalaTrabalho(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID da escala n�o pode ser nulo para disponibiliza��o.")
                               .build();
            }

            EscalaTrabalhoDTO escalaExistente = escalaTrabalhoService.recuperaEscalaTrabalhoPorId(id);
            if (escalaExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Escala de Trabalho com ID " + id + " n�o encontrada para disponibiliza��o.")
                               .build();
            }

            escalaTrabalhoService.disponibilzaEscalaTrabalho(id);
            return Response.noContent().build(); 
        } catch (MedmentorException e) {
            System.err.println("Erro ao excluir Escala de Trabalho por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao disponibilizar escala: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de disponibiliza��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }  
    
    @PUT
    @Path("/confirma/{id}")
    public Response confirmaEscalaTrabalhoEfetuado(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID da escala n�o pode ser nulo para confirma��o.")
                               .build();
            }

            EscalaTrabalhoDTO escalaExistente = escalaTrabalhoService.recuperaEscalaTrabalhoPorId(id);
            if (escalaExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Escala de Trabalho com ID " + id + " n�o encontrada para confirma��o.")
                               .build();
            }

            escalaTrabalhoService.confirmaEscalaTrabalhoEfetuado(id);
            return Response.noContent().build(); 
        } catch (MedmentorException e) {
            System.err.println("Erro ao excluir Escala de Trabalho por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao confirmar escala: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de confirma��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }       
}