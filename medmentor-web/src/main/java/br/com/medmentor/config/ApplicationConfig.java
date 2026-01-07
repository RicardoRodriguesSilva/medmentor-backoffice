package br.com.medmentor.config;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api") // Define o prefixo da URL para todos os recursos JAX-RS
public class ApplicationConfig extends Application {

	
}