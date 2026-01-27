package br.com.medmentor.mobile.controller;

import java.util.List;

import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.filtro.dto.FiltroHorasEscalaTrabalhoDTO;
import br.com.medmentor.mobile.dto.EscalaDTO;
import br.com.medmentor.mobile.dto.GestoraSaudeDTO;
import br.com.medmentor.mobile.dto.HorasEscalaDTO;
import br.com.medmentor.mobile.filtro.dto.FiltroEscalaDTO;
import br.com.medmentor.mobile.service.MovimentacaoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/mobile/movimentacao") 
@Produces(MediaType.APPLICATION_JSON) 
public class MovimentacaoController {
	
	@Inject 
	private MovimentacaoService movimentoService;
	
	@GET
    @Path("/gestora-saude/por-profissional/{id}")
    public Response recuperarListaGestorasSaudePorProfissional(@PathParam("id") Integer id) {
        try {
            List<GestoraSaudeDTO> gestoras = movimentoService.recuperaListaGestoraSaudePorProfissional(id);
            if (gestoras.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(gestoras).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todas as Gestoras de Saude: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar Gestoras de Saude: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de listagem: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    } 
	
    @GET
    @Path("/escala/por-filtros")
    public Response recuperarListaEscalaPorFiltros(@BeanParam FiltroEscalaDTO filtroEscalaDTO) {
        try {
            List<EscalaDTO> escalas = movimentoService.recuperaListaEscalaPorFiltro(filtroEscalaDTO);
            if (escalas.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(escalas).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todas as Escalas: " + e.getMessage());
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
    @Path("/escala/disponibilza/{id}")
    public Response disponibilizarEscalaTrabalho(@PathParam("id") Integer id) {
        try {
        	movimentoService.disponibilzaEscalaTrabalho(id);
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
    @Path("/escala/confirma/{id}")
    public Response confirmaEscalaTrabalho(@PathParam("id") Integer id) {
        try {
        	movimentoService.confirmaEscalaTrabalho(id);
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
    
    @PUT
    @Path("/escala/cancela/{id}")
    public Response cancelaEscalaTrabalho(@PathParam("id") Integer id) {
        try {
        	movimentoService.cancelaEscalaTrabalho(id);
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
    
    @GET
    @Path("/horas/por-filtros")
    public Response recuperarListaHorasEscalaPorFiltros(@BeanParam FiltroHorasEscalaTrabalhoDTO filtroHorasEscalaTrabalhoDTO) {
        try {
            List<HorasEscalaDTO> horas = movimentoService.recuperaListaHorasTrabalhadasPorFiltro(filtroHorasEscalaTrabalhoDTO);
            if (horas.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(horas).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todas as horas: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar horas: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de listagem: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }     
}
