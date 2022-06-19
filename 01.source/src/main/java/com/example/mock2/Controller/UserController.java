package com.example.mock2.Controller;

import com.example.mock2.Entity.User;
import com.example.mock2.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
@RestController
public class UserController {


    @Autowired
    private UserService userService;


    @GetMapping
    public String getIndex() {
        return "index";
    }

    @GetMapping("/index")
    public String getIndex10() {
        return "index";
    }

    @GetMapping("/cart")
    public String getIndex1() {
        return "cart";
    }

    @GetMapping("/checkout")
    public String getIndex2() {
        return "checkout";
    }

    @GetMapping("/contact")
    public String getIndex3() {
        return "contact";
    }

    @GetMapping("/login")
    public ResponseEntity getIndex4(Model model) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("you need to login");
    }


    @GetMapping("/product-detail")
    public String getIndex6() {
        return "product-detail";
    }

    @GetMapping("/product-list")
    public String getIndex7() {
        return "product-list";
    }

    @GetMapping("/wishlist")
    public String getIndex8() {
        return "wishlist";
    }

    @PostMapping("/register")
    public String register(User user) {
        System.out.println(user);
        userService.saveUser(user);
        return "redirect:/login";
    }

    @PostMapping("/signIn")
    public ResponseEntity login(User user,HttpServletRequest request, HttpServletResponse response) {

//        userService.findByUsername(user.getUsername())
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(user,request,response));
    }


    @GetMapping("/useRefreshToken")
    public ResponseEntity getNewAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getNewAccessToken(request,response));
    }

    @GetMapping("/my-account")
    public String getInfo (Model model){

        User user = new User();
        user.setUserFullname("Hoang Nam");
        user.setUserAddress("Hanoi");
        user.setUserPhone("0987654321");
        user.setUserEmail("nam@gmail.com");
        System.out.println(user);
        model.addAttribute("user", user);
        return "my-account";

    }

}

