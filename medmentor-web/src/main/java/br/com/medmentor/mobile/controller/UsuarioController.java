package br.com.medmentor.mobile.controller;

import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/mobile/usuario") 
@Produces(MediaType.APPLICATION_JSON) 
@Consumes(MediaType.APPLICATION_JSON) 
public class UsuarioController {

    @Inject
    private UsuarioService usuarioService;

    @POST
    @Path("/recuperar-senha")
    public Response recuperarSenha(@QueryParam("nomeUsuario") String nomeUsuario) {
        try {
            if (nomeUsuario == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o n�o pode ser vazio.")
                               .build();
            }
            
            usuarioService.recuperaSenha(nomeUsuario);            
            return Response.ok("{\"mensagem\": \"Foi enviada para o seu e-mail uma nova senha.\"}").build(); 
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
}