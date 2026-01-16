package br.com.medmentor.mobile.controller;

import java.util.List;

import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.mobile.dto.EscalaDTO;
import br.com.medmentor.mobile.dto.GestoraSaudeDTO;
import br.com.medmentor.mobile.filtro.dto.FiltroEscalaDTO;
import br.com.medmentor.mobile.service.MovimentacaoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/mobile/movimentacao") 
@Produces(MediaType.APPLICATION_JSON) 
public class MovimentacaoController {
	
	@Inject 
	private MovimentacaoService escalaService;
	
	@GET
    @Path("/gestora-saude/por-profissional/{id}")
    public Response recuperarListaGestorasSaudePorProfissional(@PathParam("id") Integer id) {
        try {
            List<GestoraSaudeDTO> gestoras = escalaService.recuperaListaGestoraSaudePorProfissional(id);
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
            List<EscalaDTO> escalas = escalaService.recuperaListaEscalaPorFiltro(filtroEscalaDTO);
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
}
