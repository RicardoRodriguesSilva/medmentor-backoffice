package br.com.medmentor.filter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Filtro @PreMatching para lidar com requisições HTTP OPTIONS (preflight CORS).
 * Este filtro intercepta requisições OPTIONS, adiciona os headers CORS necessários
 * à resposta e aborta o processamento da requisição para que ela não chegue
 * aos recursos ou a outros filtros de resposta.
 */
@Provider
@PreMatching // Garante que este filtro seja executado ANTES do mapeamento do recurso
public class CorsPreflightFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Log para depuração
        System.out.println("CorsPreflightFilter EXECUTADO. Método: " + requestContext.getMethod());        

        if (requestContext.getMethod().equalsIgnoreCase("OPTIONS")) {
            System.out.println("Requisição OPTIONS interceptada. Adicionando headers CORS.");
            
            // Constrói a resposta para a requisição OPTIONS.
            // Todos os headers CORS para a preflight devem ser adicionados AQUI.
            Response.ResponseBuilder responseBuilder = Response.ok();

            // --- 1. Access-Control-Allow-Origin ---
            // IMPORTANTE: Adicionar APENAS UMA VEZ com o valor correto.
            //responseBuilder.header("Access-Control-Allow-Origin", "http://localhost:4200");
            //nao aceitou multiplus
            //responseBuilder.header("Access-Control-Allow-Origin", "http://localhost:4200, http://localhost:8100");
            responseBuilder.header("Access-Control-Allow-Origin", "*");

            // --- 2. Access-Control-Allow-Credentials ---
            responseBuilder.header("Access-Control-Allow-Credentials", "true");

            // --- 3. Access-Control-Allow-Methods ---
            // Métodos que seu backend suporta para requisições cross-origin.
            responseBuilder.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
            
            // --- 4. Access-Control-Allow-Headers ---
            // Headers que seu frontend enviará nas requisições reais.
            // Inclui os headers que o navegador pediu via Access-Control-Request-Headers.
            String requestedHeaders = requestContext.getHeaderString("Access-Control-Request-Headers");
            if (requestedHeaders != null && !requestedHeaders.isEmpty()) {
                 responseBuilder.header("Access-Control-Allow-Headers", requestedHeaders + ", Content-Type, Accept, Authorization");
            } else {
                 responseBuilder.header("Access-Control-Allow-Headers", "Content-Type, Accept, Authorization");
            }

            // --- 5. Access-Control-Max-Age ---
            // Cache para o resultado da preflight request.
            responseBuilder.header("Access-Control-Max-Age", "600"); // 10 minutos

            // Aborta a requisição, enviando a resposta que acabamos de construir.
            // Nenhum outro filtro (ContainerResponseFilter) ou método de recurso será executado para esta OPTIONS.
            requestContext.abortWith(responseBuilder.build());
        }
        // Se não for OPTIONS, a requisição continua normalmente.
        // O CorsResponseFilter tratará a resposta para essas requisições.
    }
}