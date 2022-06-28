package com.example.mock2.Controller;

import com.example.mock2.DTO.UserDTO;
import com.example.mock2.Entity.User;
import com.example.mock2.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.io.IOException;

@AllArgsConstructor
@RestController
@Validated
public class UserController {


    @Autowired
    private UserService userService;












}

