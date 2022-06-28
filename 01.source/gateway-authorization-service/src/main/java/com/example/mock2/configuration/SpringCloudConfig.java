package com.example.mock2.configuration;


import com.example.mock2.filter.AuthorizationWebFluxFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfig {
    @Autowired
    AuthorizationWebFluxFilter filter;
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/auth/**").and()
//                        .filters(f -> f.addRequestHeader("jwt_token", "code"))

                        .uri("lb://authentication-service"))

                .route(r -> r.path("/Product/**","/Category/**","/ProductRating/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://product-service"))
                .route(r -> r.path("/User/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://user-server"))
//                .route(r -> r.path("/cart/**")
//                        .filters(f -> f.filter(filter))
//                        .uri("lb://cart-server"))
//                .route(r -> r.path("/cart/**")
//                        .filters(f -> f.filter(filter))
//                        .uri("lb://cart-server"))
                .build();
    }

}