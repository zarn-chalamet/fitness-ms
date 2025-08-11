package com.fitness.gateway.routes;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class AiServiceRoutes {

    @Bean
    public RouterFunction<ServerResponse> aIServiceRoutes() {
        return GatewayRouterFunctions.route("ai-service")
                .route(RequestPredicates.path("/api/recommendations/**"),
                        HandlerFunctions.http("http://localhost:8083"))
                .build();
    }

}
