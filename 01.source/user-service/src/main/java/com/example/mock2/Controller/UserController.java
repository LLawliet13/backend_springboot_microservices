package com.example.mock2.Controller;

import com.example.mock2.DTO.UserDTO;
import com.example.mock2.Entity.User;
import com.example.mock2.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/User")
public class UserController {


    @Autowired
    private UserService userService;



    @GetMapping("/Search/{name}")
    public UserDTO findByName(@PathVariable String name){
        return userService.findByUsername(name).convertToUserDTO();
    }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable long id){
        return userService.findById(id).convertToUserDTO();
    }

    @PostMapping("/Check/{name}/{productId}")
    public UserDTO checkBuyByName(@PathVariable String name,@PathVariable long productId){
         long id = userService.findByUsername(name).convertToUserDTO().getUserId();

         return userService.checkIfUserBoughtProduct(id,productId);
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid UserDTO userDTO,@RequestParam(required = false) @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
            message = "password format: Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character"
                           )String password) {
        User user =  userDTO.convertToUser();
        user.setPassword(password);
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.OK).body("Register Successfully");
    }


    @Secured("ROLE_USER")
    @PutMapping("/Profile/{id}")
    public ResponseEntity updateUserProfile(@PathVariable @Min(value = 1, message = "userId must higher than 0") long id, @Valid UserDTO userDTO, @RequestParam(required = false) @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
            message = "password format: Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character")
            String password) {

        userDTO.setUserId(id);
        User user = userDTO.convertToUser();
        user.setPassword(password);
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateAUser(user));
    }


    @Secured("ROLE_USER")
    @PutMapping("/Profile/Password/{id}")
    public ResponseEntity updatePassword(@PathVariable @Min(value = 1, message = "userId must higher than 0") long id, @RequestParam(required = false) @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
            message = "password format: Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character")
            String password) {
        User user = new User();
        user.setUserId(id);
        user.setPassword(password);
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateAUser(user));
    }


}

