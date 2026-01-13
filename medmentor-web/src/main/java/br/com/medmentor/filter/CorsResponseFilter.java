package br.com.medmentor.filter;

import java.io.IOException;

import br.com.medmentor.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

/**
 * Filtro para adicionar headers CORS às respostas de requisições "reais"
 * (GET, POST, PUT, DELETE) após o recurso ter sido processado.
 *
 * Ele IGNORA requisições OPTIONS, pois estas são tratadas pelo CorsPreflightFilter.
 */
@Provider
public class CorsResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        // Log para depuração
        System.out.println("CorsResponseFilter EXECUTADO. Método: " + requestContext.getMethod());

        // --- CRÍTICO: SOMENTE APLICAR HEADERS SE A REQUISIÇÃO ORIGINAL NÃO FOI OPTIONS ---
        // Se a requisição foi OPTIONS, o CorsPreflightFilter já a tratou e abortou.
        // A resposta gerada pelo CorsPreflightFilter ainda passa por este filtro,
        // mas não queremos adicionar os headers CORS novamente.
        if (!requestContext.getMethod().equalsIgnoreCase("OPTIONS")) {
            System.out.println("CorsResponseFilter: Adicionando headers CORS para requisição real.");

            responseContext.getHeaders().add("Access-Control-Allow-Origin", "http://localhost:4200");
            responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");

            // Para requisições POST/PUT/DELETE, o navegador pode enviar Content-Type
            // e Authorization, então eles precisam ser permitidos.
            // Para requisições reais, não precisamos do "Access-Control-Request-Headers"
            // pois este é um header da requisição preflight OPTIONS.
            responseContext.getHeaders().add("Access-Control-Allow-Headers", "Content-Type, Accept, Authorization");
            
            responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
            
            // Access-Control-Max-Age não é estritamente necessário na resposta de requisições reais,
            // mas não causa problemas se for adicionado. No entanto, sua principal função é para o cache
            // das respostas de OPTIONS.
            responseContext.getHeaders().add("Access-Control-Max-Age", "600");
            
            String loginPath = "/mobile/auth/login";
            String requestPath = requestContext.getUriInfo().getPath().trim();
            if (!requestPath.equals(loginPath)) {            
	            JwtUtil jwtUtil = new JwtUtil();
	            String authorizationHeader  = requestContext.getHeaderString("Authorization");
	            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
	            	responseContext.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 
	            	responseContext.setEntity("Nao Autorizado");
	            } else {
	            	String authorizationClean = authorizationHeader.replace("Bearer ", "");
	            	String username = jwtUtil.extractUsername(authorizationClean);
	            	Boolean isValido = jwtUtil.validateToken(authorizationClean, username);
	            	if (!isValido) {
	            		responseContext.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 
	                	responseContext.setEntity("Nao Autorizado");            		
	            	}
	            }
            }
        } else {
            System.out.println("CorsResponseFilter: Ignorando adição de headers CORS para requisição OPTIONS (tratada por CorsPreflightFilter).");
        }
    }
}