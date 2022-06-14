package com.example.mock2.Controller;

import com.example.mock2.Entity.Product;
import com.example.mock2.Entity.User;
import com.example.mock2.Service.ProductService;
import com.example.mock2.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @GetMapping("/user")
    public List<User> getUser() {
        List<User> users = userService.findAll();
        return users;
    }

    @GetMapping("/product")
    public List<Product> getProduct() {
        List<Product> products = userService.findAllProduct();
        return products;
    }
}
