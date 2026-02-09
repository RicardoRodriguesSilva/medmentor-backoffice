package br.com.medmentor.mobile.controller;

import java.util.List;

import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.mobile.dto.MensagemDTO;
import br.com.medmentor.mobile.filtro.dto.FiltroMensagemDTO;
import br.com.medmentor.mobile.service.MensagemService;
import jakarta.inject.Inject;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/mobile/mensagem") 
@Produces(MediaType.APPLICATION_JSON) 
public class MensagemController {
	
	@Inject 
	private MensagemService mensagemService;

	@GET
    @Path("/por-filtros")
    public Response recuperarListaMensagemPorFiltros(@BeanParam FiltroMensagemDTO filtroSolicitacaoMudancaDTO) {
        try {
            List<MensagemDTO> mensagens = mensagemService.recuperaListaMensagemPorFiltro(filtroSolicitacaoMudancaDTO);
            if (mensagens.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(mensagens).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todas as mensagens : " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar as mensagens: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de listagem: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    } 
	
	@PUT
    @Path("/marcar-lida/{id}")
    public Response marcarMensagemLida(@PathParam("id") Integer id) {
        try {
        	mensagemService.marcarMensagemLida(id);
            return Response.noContent().build(); 
        } catch (MedmentorException e) {
            System.err.println("Erro ao marcar a Mensagem como lida por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao marcar a mensagem como lida: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de confirma��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    } 	
}
