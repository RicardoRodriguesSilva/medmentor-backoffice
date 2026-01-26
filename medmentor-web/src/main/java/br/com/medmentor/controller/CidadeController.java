package br.com.medmentor.controller;

import java.net.URI;
import java.util.List;

import br.com.medmentor.dto.CidadeDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.service.CidadeService;
import jakarta.inject.Inject;
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

@Path("/cidade") 
@Produces(MediaType.APPLICATION_JSON) 
public class CidadeController {

    @Inject
    private CidadeService cidadeService;
    
    @POST
    public Response incluirCidade(CidadeDTO cidadeDTO) {
        try {
            if (cidadeDTO == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o n�o pode ser vazio.")
                               .build();
            }
            CidadeDTO novoCidade = cidadeService.incluiCidade(cidadeDTO);
            return Response.created(URI.create("/api/cidade/" + novoCidade.getId()))
                           .entity(novoCidade)
                           .build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao incluir Cidade: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao incluir cidade: " + e.getMessage())
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
    public Response recuperarCidadePorId(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID do cidade n�o pode ser nulo.")
                               .build();
            }
            CidadeDTO cidade = cidadeService.recuperaCidadePorId(id);
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
    @Path("todas")
    public Response recuperarListaCidade() {
        try {
            List<CidadeDTO> cidades = cidadeService.recuperaListaCidade();
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

    @PUT
    public Response alterarCidade(CidadeDTO cidadeDTO) {
        try {
            if (cidadeDTO == null || cidadeDTO.getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o e o ID do cidade n�o podem ser vazios.")
                               .build();
            }
            cidadeService.alteraCidade(cidadeDTO);
            return Response.ok().entity("Cidade atualizado com sucesso.").build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao alterar Cidade com ID " + cidadeDTO.getId() + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao atualizar cidade: " + e.getMessage())
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
    public Response excluirCidade(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID do cidade n�o pode ser nulo para exclus�o.")
                               .build();
            }

            CidadeDTO cidadeExistente = cidadeService.recuperaCidadePorId(id);
            if (cidadeExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Cidade com ID " + id + " n�o encontrado para exclus�o.")
                               .build();
            }

            cidadeService.excluiCidade(id);
            return Response.noContent().build(); 
        } catch (MedmentorException e) { 
            System.err.println("Erro ao excluir Cidade por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao excluir cidade: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de exclus�o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }    

    @GET
    @Path("/por-unidadefederacao") 
    public Response listarCidadesPorUnidadeFederacao(@QueryParam("idUnidadeFederacao") Integer idUnidadeFederacao) {
        try {
            System.out.println("Recebida requisição para listar cidades da UF com ID: " + idUnidadeFederacao);

            List<CidadeDTO> cidades = cidadeService.listaTodasPorUnidadeFederacao(idUnidadeFederacao);

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
    @Path("/por-undidadefederacao-nome") 
    public Response listarTodasPorUnidadeFederacaoENome(@QueryParam("idUnidadeFederacao") Integer idUnidadeFederacao,
    		@QueryParam("nome") String nome) {
        try {
            System.out.println("Recebida requisição para listar cidades da UF com ID: " + idUnidadeFederacao);

            List<CidadeDTO> cidades = cidadeService.listaTodasPorUnidadeFederacaoENome(idUnidadeFederacao, nome);

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
}
