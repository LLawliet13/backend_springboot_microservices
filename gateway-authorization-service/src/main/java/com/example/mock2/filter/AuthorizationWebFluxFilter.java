package com.example.mock2.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.*;

@RefreshScope
@Component
public class AuthorizationWebFluxFilter implements GatewayFilter {

    public static final List<String> openApiEndpoints = Arrays.asList(
            "/auth/register",
            "/auth/login",
            "/auth/refreshToken"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (openApiEndpoints.stream().noneMatch(uri -> request.getURI().getPath().contains(uri))) {

            String authorizationHeader;
            HttpCookie cookie = getCookie("jwt_token", request);
            if (cookie != null) {
                authorizationHeader = cookie.getValue();
                System.out.println("authorizationHeader in cookie: " + authorizationHeader);
            } else {
                if (!isAuthMissing(request))
                    authorizationHeader = this.getAuthHeader(request);
                else authorizationHeader = null;
            }
            if (authorizationHeader == null)
                return this.onError(exchange, "Authorization header or cookie is missing in request", HttpStatus.UNAUTHORIZED);

            if (!this.isValid(authorizationHeader))
                return this.onError(exchange, "Authorization header or cookie is invalid", HttpStatus.UNAUTHORIZED);

            this.populateRequestWithHeaders(exchange, authorizationHeader);
        }
        return chain.filter(exchange);
    }


    /*PRIVATE*/

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("authorization").get(0);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("authorization");
    }

    private String checkTypeOfRequest() {
        return "";
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {

        Algorithm algorithm = Algorithm.HMAC256("demo_jwt".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();// dung algorithm da tao token de build
        DecodedJWT decodedJWT = verifier.verify(token);// verify xem dung chu ky k
        String username = decodedJWT.getSubject();

        List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
        exchange.getRequest().mutate()
                .header("username", String.valueOf(username))// co the la id ng dung
                .header("roles", String.valueOf(roles))
                .header("authorization", token)
                .build();

    }

    private boolean isValid(String authorizationHeader) {


        if (authorizationHeader != null) {
            try {
//                    String token = authorizationHeader.substring("token ".length());// cat bearer ra khoi token
                String token = authorizationHeader;
                Algorithm algorithm = Algorithm.HMAC256("demo_jwt".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();// dung algorithm da tao token de build
                DecodedJWT decodedJWT = verifier.verify(token);// verify xem dung chu ky k
                String username = decodedJWT.getSubject();

                List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
                return true;
            } catch (Exception e) {
                return false;
            }

        }
        return false;
    }

    private HttpCookie getCookie(String name, ServerHttpRequest request) {
        Map<String, HttpCookie> cookies = request.getCookies().toSingleValueMap();
        for (Map.Entry hc : cookies.entrySet()) {
            if (hc.getKey().equals(name)) return (HttpCookie) hc.getValue();
        }
        return null;
    }

}
