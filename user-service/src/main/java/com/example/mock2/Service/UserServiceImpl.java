package com.example.mock2.Service;


import com.example.mock2.DTO.UserDTO;
import com.example.mock2.Entity.Role;
import com.example.mock2.Entity.User;
import com.example.mock2.Repository.RoleRepository;
import com.example.mock2.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@AllArgsConstructor
@Service
@Configuration
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public long getUserIdByUsername(String username) {
        return userRepository.getUserIdByUsername(username);
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }



    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDTO updateAUser(User user) {
        User existedUser = userRepository.findById(user.getUserId());
        if (existedUser == null) throw new RuntimeException("this user id doesn't exit");

        if (user.getUserEmail() != null && !user.getUserEmail().isEmpty())
        existedUser.setUserEmail(user.getUserEmail());

        if (user.getUserAddress() != null && !user.getUserAddress().isEmpty())
        existedUser.setUserAddress(user.getUserAddress());

        if (user.getUserPhone() != null && !user.getUserPhone().isEmpty())
        existedUser.setUserPhone(user.getUserPhone());

        if (user.getUserFullname() != null && !user.getUserFullname().isEmpty())
        existedUser.setUserFullname(user.getUserFullname());

        if (user.getUserDob() != null)
        existedUser.setUserDob(user.getUserDob());

        if (user.getPassword() != null && !user.getPassword().isEmpty())
            existedUser.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.saveAndFlush(existedUser);
        return existedUser.convertToUserDTO();
    }

    @Override
    public UserDTO checkIfUserBoughtProduct(long userId,long productId) {
        User user =  userRepository.checkIfUserBoughtProduct( userId, productId);
        if(user==null) return null;
        return user.convertToUserDTO();
    }


    private PasswordEncoder passwordEncoder;

    @Override
    public boolean saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRoleName("ROLE_USER");
//        userRole.getUsers().add(user);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(userRole);
        user.setRoles(roleSet);
        userRepository.saveAndFlush(user);

        return true;
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
