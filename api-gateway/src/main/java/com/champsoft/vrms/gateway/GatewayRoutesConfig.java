package com.champsoft.vrms.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("cars-service", route -> route
                        .path("/api/cars", "/api/cars/**")
                        .uri("http://localhost:8081"))
                .route("owners-service", route -> route
                        .path("/api/owners", "/api/owners/**")
                        .uri("http://localhost:8082"))
                .route("agents-service", route -> route
                        .path("/api/agents", "/api/agents/**")
                        .uri("http://localhost:8083"))
                .route("registration-service", route -> route
                        .path("/api/registrations", "/api/registrations/**")
                        .uri("http://localhost:8084"))
                .build();
    }
}
