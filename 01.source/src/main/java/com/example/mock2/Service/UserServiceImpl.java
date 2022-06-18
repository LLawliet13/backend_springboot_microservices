package com.example.mock2.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.mock2.Entity.Product;
import com.example.mock2.Entity.User;
import com.example.mock2.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;

    @Override
    public User findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<Product> findAllProduct() {
        return userRepository.findAllProduct();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }



    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    @Override
    public boolean saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }


    @Override
    public String login(User user,HttpServletRequest request,HttpServletResponse response) {
        Authentication authentication = authenticate(user);
        try {
            Cookie ac_cookie = new Cookie("jwt_token", getAccessToken(authentication));
            Cookie rf_cookie = new Cookie("refresh_token", getRefreshToken(authentication));
            System.out.println("token: " + ac_cookie.getValue());
            response.addCookie(ac_cookie);
            response.addCookie(rf_cookie);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Fail to login";
        }

        return "login successfully";
    }

    @Override
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie jwt_token_cookie = getCookie("jwt_token",request);
        Cookie refresh_token_cookie = getCookie("refresh_token",request);
        System.out.println("jwt_token"+jwt_token_cookie+"\nrefresh_token"+refresh_token_cookie);
        jwt_token_cookie.setMaxAge(0);
        response.addCookie(jwt_token_cookie);
        refresh_token_cookie.setMaxAge(0);
        response.addCookie(refresh_token_cookie);
        return "logout complete";
    }

    @Override
    public String getNewAccessToken(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String authorizationHeader;
        Cookie cookie = getCookie("refresh_token", request);
        if (cookie != null) {
            authorizationHeader = cookie.getValue();
            System.out.println("authorizationHeader in cookie: " + authorizationHeader);
        } else
            authorizationHeader = request.getHeader("authorization");

        if (authorizationHeader != null) {
            try {
                String token = authorizationHeader;
                Algorithm algorithm = Algorithm.HMAC256("demo_jwt".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();// dung algorithm da tao token de build
                DecodedJWT decodedJWT = verifier.verify(token);// verify xem dung chu ky k
                String username = decodedJWT.getSubject();

                List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                roles.forEach(
                        role -> authorities.add(new SimpleGrantedAuthority(role))
                );
                String issuer = decodedJWT.getIssuer();
                String access_token = JWT.create()
                        .withSubject(username)
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 100)) // 10p
                        .withIssuer(issuer)
                        .withClaim("roles",roles)
                        .sign(algorithm);

                response.addCookie(new Cookie("jwt_token",access_token));

            } catch (Exception e) {
                System.out.println("error:" + e.getMessage());
//                    response.setHeader("error:", e.getMessage());
                response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
//                    filterChain.doFilter(request, response); // tiep tuc cac filter con lai
            }
        }
        return "Refresh Access token Successfully";
    }

    private Authentication authenticate(User user){
        String username = user.getUsername();
        String password = user.getPassword();
        System.out.println("username:" + username + "\npassword:" + password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (Exception e) {
            return null;
        }
        return authentication;
    }
    private String getAccessToken(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("demo_jwt".getBytes());
        List<String> roles = new ArrayList<>();
        if(user.getAuthorities()!=null) roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 100)) // 10p
                .withIssuer("issuer")
                .withClaim("roles",roles)
                .sign(algorithm);


        return access_token;

    }
    private String getRefreshToken(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("demo_jwt".getBytes());
        List<String> roles = new ArrayList<>();
        if(user.getAuthorities()!=null) roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*1000)) // 1000phut
                .withIssuer("issuer")
                .withClaim("roles",roles)
                .sign(algorithm);
        return refresh_token;
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
