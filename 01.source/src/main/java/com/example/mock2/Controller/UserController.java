package com.example.mock2.Controller;

import com.example.mock2.Entity.User;
import lombok.AllArgsConstructor;
import com.example.mock2.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@AllArgsConstructor
@Controller
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
    public String getIndex4(Model model) {
        return "login";
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
    public String login(User user, HttpServletResponse response, Model model) {
        try {
            Cookie cookie = new Cookie("jwt_token", userService.login(user));
            System.out.println("token: " + cookie.getValue());
            response.addCookie(cookie);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("message", "Fail to login");
            return "redirect:/login";
        }
        return "/index";
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
