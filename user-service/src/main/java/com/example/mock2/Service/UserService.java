package com.example.mock2.Service;

import com.example.mock2.DTO.UserDTO;
import com.example.mock2.Entity.User;

import java.util.List;

public interface UserService {

    long getUserIdByUsername(String username);

    User findById(long id);

    List<User> findAll();

    boolean saveUser(User user);

    User findByUsername(String username);

    UserDTO updateAUser(User user);

    UserDTO checkIfUserBoughtProduct(long userId,long productId);

}
