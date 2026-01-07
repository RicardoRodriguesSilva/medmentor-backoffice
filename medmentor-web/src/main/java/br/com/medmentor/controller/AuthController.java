package br.com.medmentor.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.medmentor.model.AuthRequest;
import br.com.medmentor.model.AuthResponse;
import br.com.medmentor.service.UserAuthenticatorService;
import br.com.medmentor.util.JwtUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {

	private static final Logger LOGGER = Logger.getLogger(AuthController.class.getName());

	@Inject
	private UserAuthenticatorService userAuthenticator;
	
	private final JwtUtil jwtUtil = new JwtUtil();
	private final ObjectMapper objectMapper = new ObjectMapper();

	@POST
	@Path("/login")
	public Response authenticateUser(AuthRequest authRequest) {
		
		System.out.println("AuthController: Método login() chamado para usuário: " + authRequest.getUsername());
		LOGGER.info("Attempting to authenticate user: " + authRequest.getUsername());
		if (userAuthenticator.authenticate(authRequest.getUsername(), authRequest.getPassword())) {
			try {
				Map<String, Object> claims = new HashMap<>();
				claims.put("user_menus",
						objectMapper.writeValueAsString(userAuthenticator.getMenusForUser(authRequest.getUsername())));
				
				claims.put("user_profile",
						objectMapper.writeValueAsString(userAuthenticator.getProfileForUser(authRequest.getUsername())));

				String jwt = jwtUtil.generateToken(authRequest.getUsername(), claims);
				LOGGER.info("User authenticated successfully: " + authRequest.getUsername());
				return Response.ok(new AuthResponse(jwt)).build();
			} catch (JsonProcessingException e) {
				LOGGER.severe("Error processing JSON for menus: " + e.getMessage());
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao gerar token").build();
			}
		} else {
			LOGGER.warning("Authentication failed for user: " + authRequest.getUsername());
			return Response.status(Response.Status.UNAUTHORIZED).entity("Credenciais inválidas").build();
		}
	}	
}