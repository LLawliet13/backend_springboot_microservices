package com.example.mock2.Service;

import com.example.mock2.DTO.UserDTO;
import com.example.mock2.Entity.User;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface UserService {

    long getUserIdByUsername(String username);

    User findById(long id);

    List<User> findAll();

    User loadUserByUsername(String username) ;



    User findByUsername(String username);


}
