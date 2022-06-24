package com.example.mock2.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Service
public class CustomLogoutHandler implements LogoutHandler {


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) {
        Cookie jwt_token_cookie = getCookie("jwt_token",request);
        Cookie refresh_token_cookie = getCookie("refresh_token",request);
        System.out.println("jwt_token"+jwt_token_cookie+"\nrefresh_token"+refresh_token_cookie);
        jwt_token_cookie.setMaxAge(0);
        response.addCookie(jwt_token_cookie);
        refresh_token_cookie.setMaxAge(0);
        response.addCookie(refresh_token_cookie);
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