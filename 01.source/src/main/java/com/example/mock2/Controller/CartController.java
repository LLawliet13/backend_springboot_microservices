package com.example.mock2.Controller;

import com.example.mock2.DTO.CartDTO;
import com.example.mock2.Entity.Cart;
import com.example.mock2.Service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.example.mock2.filter.CustomAuthorizationFilter.USERNAME;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Validated
public class CartController {

    private CartService cartService;


//    @GetMapping("/cart")
//    public ResponseEntity<List<CartDTO>> getCartFromUser() {
//
//        List<Cart> carts = cartService.findCartByUserUsername(USERNAME);
//        List<CartDTO> cartDTOList = cartService.convertCartToCartDTO(carts);
//        return ResponseEntity.status(HttpStatus.OK).body(cartDTOList);
//    }


    @Secured({ "ROLE_USER" })
    @GetMapping("/cart")
    public ResponseEntity<?> getCartFromUser() {

        List<Cart> carts = cartService.findCartByUserUsername(USERNAME);
        List<CartDTO> cartDTOList = cartService.convertCartToCartDTO(carts);
        long totalPrice = cartService.getTotalPrice(USERNAME);

        Map<String, Object> result = new LinkedHashMap<String,Object>();

        result.put("All products in cart", cartDTOList);
        result.put("Total price", totalPrice);


        return new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);

    }


//    POST cart: nếu quantity <= 0 ném Exception
//    Chỉ cho phép add 1 product duy nhất cho 1 request
    @Secured({ "ROLE_USER" })
    @PostMapping("/cart")
    public String addCart(@RequestParam String productName, @RequestParam @Min(value = 1, message = "quantity must greater than 0") int quantity) {

        cartService.addCart(productName, quantity);

        return "Product add to cart successfully!";
    }

//    PUT cart: không cho phép add new product,
//    Cho phép chỉnh sửa số lượng nhiều sản phẩm cùng lúc
    @Secured({ "ROLE_USER" })
    @PutMapping("/cart")
    public String updateCart(@RequestParam String[] productName, @RequestParam int[] quantity) {

        cartService.updateCart(productName, quantity);

        return "Cart has been updated";
    }


    @Secured({ "ROLE_USER" })
    @DeleteMapping("/cart/reset")
    public String resetCart() {
        cartService.resetCart();
        return "Cart has been reset";
    }


}
