package com.example.mock2.Service;

import com.example.mock2.Entity.Product;
import com.example.mock2.Entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {

    User findById(long id);

    List<User> findAll();

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    boolean saveUser(User user);

    String login(User user);

    List<Product> findAllProduct();

    User findByUsername(String username);

}
