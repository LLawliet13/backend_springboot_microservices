package com.example.mock2.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
    public static String USERNAME;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/login")) {
            filterChain.doFilter(request, response);
        } else {

            String authorizationHeader;
            Cookie cookie = getCookie("jwt_token", request);
            if (cookie != null) {
                authorizationHeader = cookie.getValue();
                System.out.println("authorizationHeader in cookie: " + authorizationHeader);
            } else
                authorizationHeader = request.getHeader("authorization");

            if (authorizationHeader != null) {
                try {
//                    String token = authorizationHeader.substring("token ".length());// cat bearer ra khoi token
                    String token = authorizationHeader;
                    Algorithm algorithm = Algorithm.HMAC256("demo_jwt".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();// dung algorithm da tao token de build
                    DecodedJWT decodedJWT = verifier.verify(token);// verify xem dung chu ky k
                    String username = decodedJWT.getSubject();
                    USERNAME = username;

                    List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    roles.forEach(
                            role -> authorities.add(new SimpleGrantedAuthority(role))
                    );

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    //tra ve token sau khi da xac thuc cho context
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    filterChain.doFilter(request, response); // tiep tuc cac filter con lai

                } catch (Exception e) {
                    System.out.println("error:" + e.getMessage());
                    response.setHeader("error:", e.getMessage());
//                    response.sendError(HttpStatus.FORBIDDEN.value());
                    filterChain.doFilter(request, response); // tiep tuc cac filter con lai
                }

            } else {
                filterChain.doFilter(request, response);
            }
        }
    }

    private Cookie getCookie(String name, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(c -> c.getName().equals(name)).findFirst().get();
        }
        return null;
    }
}
