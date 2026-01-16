package br.com.medmentor.mobile.controller;

import java.util.List;

import br.com.medmentor.dto.CidadeDTO;
import br.com.medmentor.dto.UnidadeFederacaoDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.service.CidadeService;
import br.com.medmentor.service.UnidadeFederacaoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/mobile/apoio") 
@Produces(MediaType.APPLICATION_JSON) 
public class ApoioController {

    @Inject
    private CidadeService cidadeService;
    
    @Inject
    private UnidadeFederacaoService unidadeFederacaoService;    

    @GET
    @Path("/cidade/{id}")
    public Response recuperarCidadePorId(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID do cidade n�o pode ser nulo.")
                               .build();
            }
            CidadeDTO cidade = cidadeService.recuperarCidadePorId(id);
            if (cidade != null) {
                return Response.ok(cidade).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Cidade com ID " + id + " n�o encontrado.")
                               .build();
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao recuperar Cidade por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao buscar cidade: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de recupera��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }

    @GET
    @Path("/cidade/todas")
    public Response recuperarListaCidade() {
        try {
            List<CidadeDTO> cidades = cidadeService.recuperarListaCidade();
            if (cidades.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok(cidades).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todos os Cidades: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar cidades: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de listagem: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }
      

    @GET
    @Path("/cidade/por-unidadefederacao") 
    public Response listarCidadesPorUnidadeFederacao(@QueryParam("idUnidadeFederacao") Integer idUnidadeFederacao) {
        try {
            System.out.println("Recebida requisição para listar cidades da UF com ID: " + idUnidadeFederacao);

            List<CidadeDTO> cidades = cidadeService.listarTodasPorUnidadeFederacao(idUnidadeFederacao);

            if (cidades.isEmpty()) {
                return Response.noContent().build();
            } else {
                return Response.ok(cidades).build();
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar cidades por Unidade da Federa��o (" + idUnidadeFederacao + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisição: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }
    
    @GET
    @Path("/unidade-federacao/todas")
    public Response listarTodasUnidadesFederacao() {
        try {
            List<UnidadeFederacaoDTO> unidadesFederacao = unidadeFederacaoService.listarTodos();

            if (unidadesFederacao.isEmpty()) {
                return Response.noContent().build();
            } else {
                return Response.ok(unidadesFederacao).build();
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todas as Unidades da Federa��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar unidades de federa��o: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }  
    
    @GET
    @Path("/unidade-federacao/{id}")
    public Response recuperarUnidadeFederacaoPorId(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID do unidadeFederacao n�o pode ser nulo.")
                               .build();
            }
            UnidadeFederacaoDTO unidadeFederacao = unidadeFederacaoService.recuperarUnidadeFederacaoPorId(id);
            if (unidadeFederacao != null) {
                return Response.ok(unidadeFederacao).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("UnidadeFederacao com ID " + id + " n�o encontrado.")
                               .build();
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao recuperar UnidadeFederacao por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao buscar unidadeFederacao: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de recupera��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }    
}
