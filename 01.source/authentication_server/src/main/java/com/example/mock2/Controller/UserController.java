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

@AllArgsConstructor
@RestController
@Validated
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
    public ResponseEntity register(@Valid UserDTO userDTO,@RequestParam(required = false) @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
            message = "password format: Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character"
                           )String password) {
        User user =  userDTO.convertToUser();
        user.setPassword(password);
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.OK).body("Register Successfully");
    }

    @PostMapping("/signIn")
    public ResponseEntity login(User user, HttpServletRequest request, HttpServletResponse response) {

//        userService.findByUsername(user.getUsername())
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(user, request, response));
    }


    @GetMapping("/useRefreshToken")
    public ResponseEntity getNewAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getNewAccessToken(request, response));
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

    @GetMapping("/my-account")
    public String getInfo(Model model) {

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

