package com.example.mock2.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

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

    @GetMapping("/login1")
    public String getIndex4() {
        return "login";
    }

    @GetMapping("/my-account")
    public String getIndex5() {
        return "my-account";
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
}
