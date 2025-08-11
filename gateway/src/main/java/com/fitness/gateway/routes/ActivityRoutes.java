package com.fitness.gateway.routes;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;

@Configuration
public class ActivityRoutes {

    @Bean
    public RouterFunction<ServerResponse> activityServiceRoutes() {
        return GatewayRouterFunctions.route("activity-service")
                .route(RequestPredicates.path("/api/activities/**"),
                        HandlerFunctions.http("http://localhost:8081"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> activityServiceOpenApiDocs() {
        return GatewayRouterFunctions.route("activity-service-api-doc")
                .route(RequestPredicates.path("/docs/activityservice/v3/api-docs"),
                        HandlerFunctions.http("http://localhost:8081"))
                .filter(setPath("/v3/api-docs"))
                .build();
    }
}
