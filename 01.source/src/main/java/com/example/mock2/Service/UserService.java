package com.example.mock2.Service;

import com.example.mock2.Entity.Product;
import com.example.mock2.Entity.User;
import org.springframework.http.HttpCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface UserService extends UserDetailsService {

    long getUserIdByUsername(String username);

    User findById(long id);

    List<User> findAll();

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    boolean saveUser(User user);

    String login(User user,HttpServletRequest request,HttpServletResponse response);
    String logout(HttpServletRequest request, HttpServletResponse response);

    List<Product> findAllProduct();

    User findByUsername(String username);

    String getNewAccessToken(HttpServletRequest request,HttpServletResponse response) throws IOException;
}
