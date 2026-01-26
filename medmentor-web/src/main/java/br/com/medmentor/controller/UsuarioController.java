package br.com.medmentor.controller;

import br.com.medmentor.dto.UsuarioDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.service.UsuarioService;
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
import java.util.stream.Collectors;

@Path("/usuario") 
@Produces(MediaType.APPLICATION_JSON) 
@Consumes(MediaType.APPLICATION_JSON) 
public class UsuarioController {

    @Inject
    private UsuarioService usuarioService;

    @POST
    public Response incluirUsuario(UsuarioDTO usuarioDTO) {
        try {
            if (usuarioDTO == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o n�o pode ser vazio.")
                               .build();
            }
            UsuarioDTO novoUsuario = usuarioService.incluiUsuario(usuarioDTO);
            return Response.created(URI.create("/api/usuario/" + novoUsuario.getId()))
                           .entity(novoUsuario)
                           .build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao incluir Usu�rio: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao incluir usu�rio: " + e.getMessage())
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
    public Response recuperarUsuarioPorId(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID do usu�rio n�o pode ser nulo.")
                               .build();
            }
            UsuarioDTO usuario = usuarioService.recuperaUsuarioPorId(id);
            if (usuario != null) {
            	usuario.setSenhaUsuario("**********");
                return Response.ok(usuario).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Usu�rio com ID " + id + " n�o encontrado.")
                               .build();
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao recuperar Usu�rio por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao buscar usu�rio: " + e.getMessage())
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
    public Response recuperarUsuarioPorNome(@QueryParam("nome") String nome) {
        try {
            if (nome == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O nome do usu�rio n�o pode ser nulo.")
                               .build();
            }
            UsuarioDTO usuario = usuarioService.recuperaUsuarioPorNome(nome);
            if (usuario != null) {
            	usuario.setSenhaUsuario("**********");
                return Response.ok(usuario).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Usu�rio com nome " + nome + " n�o encontrado.")
                               .build();
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao recuperar Usu�rio por nome (" + nome + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao buscar usu�rio: " + e.getMessage())
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
    public Response recuperarListaUsuario() {
        try {
            List<UsuarioDTO> usuarios = usuarioService.recuperaListaUsuario();
            if (usuarios.isEmpty()) {
                return Response.noContent().build(); 
            } else {
            	
            	usuarios = usuarios.stream()
                .map(usuario -> {
                    usuario.setSenhaUsuario("**********");
                    return usuario;
                })
                .collect(Collectors.toList());
            	
                return Response.ok(usuarios).build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao listar todos os Usu�rios: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao listar usu�rios: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de listagem: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }

    @PUT
    public Response alterarUsuario(UsuarioDTO usuarioDTO) {
        try {
            if (usuarioDTO == null || usuarioDTO.getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o e o ID do usu�rio n�o podem ser vazios.")
                               .build();
            }
            usuarioService.alteraUsuario(usuarioDTO);
            return Response.ok().entity("Usu�rio atualizado com sucesso.").build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao alterar Usu�rio com ID " + usuarioDTO.getId() + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao atualizar usu�rio: " + e.getMessage())
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
    public Response excluirUsuario(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID do usu�rio n�o pode ser nulo para exclus�o.")
                               .build();
            }

            UsuarioDTO usuarioExistente = usuarioService.recuperaUsuarioPorId(id);
            if (usuarioExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Usu�rio com ID " + id + " n�o encontrado para exclus�o.")
                               .build();
            }

            usuarioService.excluiUsuario(id);
            return Response.noContent().build(); 
        } catch (MedmentorException e) {
            System.err.println("Erro ao excluir Usu�rio por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao excluir usu�rio: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de exclus�o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        } 
    }
    
    @GET
    @Path("/gerar-login")
    public Response gerarLoginUsuario(@QueryParam("nome") String nome) {
        try {        	
        	String nomeUsuario = usuarioService.geraLoginUsuario(nome);
            if (nomeUsuario.isEmpty()) {
                return Response.noContent().build(); 
            } else {
                return Response.ok("{\"nomeUsuario\": \"" + nomeUsuario + "\"}").build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao gerar nome do  Usu�rios: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao gerar nome usu�rio: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }  
    
    @GET
    @Path("/gerar-senha")
    public Response gerarSenhaUsuario(@QueryParam("nome") String nome) {
        try {        	
        	String senhaUsuario = usuarioService.geraSenha(nome);     
            if (senhaUsuario.isEmpty()) {
                return Response.noContent().build(); 
            } else {
            	return Response.ok("{\"senhaUsuario\": \"" + senhaUsuario + "\"}").build(); 
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao gerar senha do  Usu�rio: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao gerar nome usu�rio: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }     
}