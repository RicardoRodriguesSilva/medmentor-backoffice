package br.com.medmentor.mobile.controller;

import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.mobile.dto.DadosBancariosDTO;
import br.com.medmentor.mobile.dto.EmpresaDTO;
import br.com.medmentor.mobile.dto.MedicoDTO;
import br.com.medmentor.mobile.dto.RegistroDTO;
import br.com.medmentor.mobile.service.CadastroService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/mobile/cadastro") 
@Produces(MediaType.APPLICATION_JSON) 
public class CadastroController {

	@Inject
	private CadastroService cadastroService;
	
	@GET
	@Path("/medico/{id}")
	public Response recuperarMedico(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID do medico n�o pode ser nulo.")
                               .build();
            }
            MedicoDTO medicoDTO = cadastroService.recuperarMedico(id);
            if (medicoDTO != null) {
                return Response.ok(medicoDTO).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Medico com ID " + id + " n�o encontrado.")
                               .build();
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao recuperar Medico por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao buscar Medico: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de recupera��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
	}  
	
	@PUT
	@Path("/medico/{id}")
    public Response alterarMedico(@PathParam("id") Integer id, MedicoDTO medicoDTO) {
        try {
            if (id == null || medicoDTO.getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o e o ID do medico n�o podem ser vazios.")
                               .build();
            }
            cadastroService.alterarMedico(id, medicoDTO);
            return Response.ok().entity(medicoDTO).build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao alterar Medico com ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao atualizar medico: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de altera��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }	
	
	@GET
	@Path("/registro/{id}")
	public Response recuperarRegistro(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID do registro n�o pode ser nulo.")
                               .build();
            }
            RegistroDTO registroDTO = cadastroService.recuperarRegistro(id);
            if (registroDTO != null) {
                return Response.ok(registroDTO).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Registro com ID " + id + " n�o encontrado.")
                               .build();
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao recuperar Registro por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao buscar Registro: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de recupera��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
	}  
	
	@PUT
	@Path("/registro")
    public Response alterarRegistro(Integer id, RegistroDTO registroDTO) {
        try {
            if (id == null || registroDTO.getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o e o ID do registro n�o podem ser vazios.")
                               .build();
            }
            cadastroService.alterarRegistro(id, registroDTO);
            return Response.ok().entity(registroDTO).build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao alterar Registro com ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao atualizar registro: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de altera��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }	
	
	@GET
	@Path("/empresa/{id}")
	public Response recuperarEmpresa(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID do empresa n�o pode ser nulo.")
                               .build();
            }
            EmpresaDTO empresaDTO = cadastroService.recuperarEmpresa(id);
            if (empresaDTO != null) {
                return Response.ok(empresaDTO).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Empresa com ID " + id + " n�o encontrado.")
                               .build();
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao recuperar Empresa por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao buscar Empresa: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de recupera��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
	}  
	
	@PUT
	@Path("/empresa")
    public Response alterarEmpresa(Integer id, EmpresaDTO empresaDTO) {
        try {
            if (id == null || empresaDTO.getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o e o ID do empresa n�o podem ser vazios.")
                               .build();
            }
            cadastroService.alterarEmpresa(id, empresaDTO);
            return Response.ok().entity(empresaDTO).build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao alterar Empresa com ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao atualizar empresa: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de altera��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }
	
	@GET
	@Path("/dadosBancarios/{id}")
	public Response recuperarDadosBancarios(@PathParam("id") Integer id) {
        try {
            if (id == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O ID do dadosBancarios n�o pode ser nulo.")
                               .build();
            }
            DadosBancariosDTO dadosBancariosDTO = cadastroService.recuperarDadosBancarios(id);
            if (dadosBancariosDTO != null) {
                return Response.ok(dadosBancariosDTO).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("DadosBancarios com ID " + id + " n�o encontrado.")
                               .build();
            }
        } catch (MedmentorException e) {
            System.err.println("Erro ao recuperar DadosBancarios por ID (" + id + "): " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao buscar DadosBancarios: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de recupera��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
	}  
	
	@PUT
	@Path("/dadosBancarios")
    public Response alterarDadosBancarios(Integer id, DadosBancariosDTO dadosBancariosDTO) {
        try {
            if (id == null || dadosBancariosDTO.getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("O corpo da requisi��o e o ID do dadosBancarios n�o podem ser vazios.")
                               .build();
            }
            cadastroService.alterarDadosBancarios(id, dadosBancariosDTO);
            return Response.ok().entity(dadosBancariosDTO).build();
        } catch (MedmentorException e) {
            System.err.println("Erro ao alterar DadosBancarios com ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro interno do servidor ao atualizar dadosBancarios: " + e.getMessage())
                           .build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar requisi��o de altera��o: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                           .build();
        }
    }	
	
}
