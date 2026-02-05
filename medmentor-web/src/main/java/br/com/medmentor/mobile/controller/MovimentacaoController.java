package br.com.medmentor.mobile.controller;

import java.util.List;

import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.filtro.dto.FiltroHorasEscalaTrabalhoDTO;
import br.com.medmentor.mobile.dto.EscalaDTO;
import br.com.medmentor.mobile.dto.GestoraSaudeDTO;
import br.com.medmentor.mobile.dto.HorasEscalaDTO;
import br.com.medmentor.mobile.dto.SolicitacaoMudancaDTO;
import br.com.medmentor.mobile.filtro.dto.FiltroEscalaDTO;
import br.com.medmentor.mobile.filtro.dto.FiltroSolicitacaoMudancaDTO;
import br.com.medmentor.mobile.service.MovimentacaoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
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
    
    @GET
    @Path("/escala/disponivel/por-filtros")
    public Response recuperarListaEscalaDisponivelPorFiltros(@BeanParam FiltroEscalaDTO filtroEscalaDTO) {
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
    @Path("/escala/indisponibilza/{id}")
    public Response indisponibilizarEscalaTrabalho(@PathParam("id") Integer id) {
        try {
        	movimentoService.indisponibilzaEscalaTrabalho(id);
            return Response.noContent().build(); 
        } catch (MedmentorException e) {
            System.err.println("Erro ao indisponibilizar Escala de Trabalho por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao indisponibilizar escala: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de indisponibiliza��o: " + e.getMessage());
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
    
    @GET
    @Path("/solicitacao-mudanca/por-filtros")
    public Response recuperarListaSolicitacaoMudamcaPorFiltros(@BeanParam FiltroSolicitacaoMudancaDTO filtroSolicitacaoMudancaDTO) {
        try {
            List<SolicitacaoMudancaDTO> solicitacoes = movimentoService.recuperaListaSolicitacaoMudancaPorFiltro(filtroSolicitacaoMudancaDTO);
            if (solicitacoes.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(solicitacoes).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todas as solicitacoes de mudancas: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar solicitacoes de mudancas: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de listagem: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }  
    
    @POST
    @Path("/solicitacao-mudanca/inclui/{idProfissional}/{idEscala}")
    public Response incluiSolicitacaoMudanca(@PathParam("idProfissional") Integer idProfissional, @PathParam("idEscala") Integer idEscala) {
        try {
        	movimentoService.incluiSolicitacaoMudanca(idProfissional, idEscala);
            return Response.noContent().build(); 
        } catch (MedmentorException e) {
            System.err.println("Erro ao incluir solicitacao de mudanca: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao incluir solicitacao de mudanca: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de confirma��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }  
    
    @PUT
    @Path("/solicitacao-mudanca/aceita/{id}")
    public Response aceitaSolicitacaoMudanca(@PathParam("id") Integer id) {
        try {
        	if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID da solicitacaoMudanca n�o pode ser nulo.")
                               .build();
            }
        	movimentoService.aceitaSolicitacaoMudanca(id); 
            return Response.ok().build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao aceitar SolicitacaoMudanca com ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao atualizar solicitacaoMudanca: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de aceite: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }
    
    @PUT
    @Path("/solicitacao-mudanca/recusa/{id}")
    public Response recusaSolicitacaoMudanca(@PathParam("id") Integer id) {
        try {
        	if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID da solicitacaoMudanca n�o pode ser nulo.")
                               .build();
            }
        	movimentoService.recusaSolicitacaoMudanca(id); 
            return Response.ok().build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao recusar SolicitacaoMudanca com ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao atualizar solicitacaoMudanca: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de recusa: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }    

    @DELETE
    @Path("/solicitacao-mudanca/exclui/{id}")
    public Response excluiSolicitacaoMudanca(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID da solicitacaoMudanca n�o pode ser nulo para exclus�o.")
                               .build();
            }

            movimentoService.excluiSolicitacaoMudanca(id);
            return Response.noContent().build(); 
        } catch (MedmentorException e) {
            System.err.println("Erro ao excluir SolicitacaoMudanca por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao excluir solicitacaoMudanca: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de exclus�o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }    
}
